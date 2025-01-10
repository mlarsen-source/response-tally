import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * The Tallyer class provides functionality for reading ID and topic pairs from user input,
 * and tallying the number of occurrences of each topic.
 */
public class Tallyer {

    /**
     * The main method serves as the entry point for the program. It reads pairs of IDs and topics
     * from standard input, stores them in lists, and then calculates the number of occurrences
     * of each topic. The IDs and topics are guaranteed to not include internal whitespace.
     *
     * @param args command-line arguments (not used in this implementation)
     */
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);

        List<String> ids = new ArrayList<>();
        List<String> topics = new ArrayList<>();
        
        // Reading input for IDs and topics
        // Assumes file is well formed into pairs
        while (input.hasNextLine()) 
        {
            // skip empty lines in file
            String line = input.nextLine();
            if(line.isEmpty())
            {
               continue;
            }

            // only add ID and topic to applicable lists if the line contains both items
            String[] lineArray = line.split(" ");
            if (lineArray.length == 2) 
            {
                ids.add(lineArray[0]);
                topics.add(lineArray[1]);

            }

            //ids.add(input.next());
            //topics.add(input.next());
        }
        input.close();
        
        // Wave 1
        Map<String, Integer> topicCounts = tallyTopics(topics);
        System.out.println("Here are how many times each topic appears (unfiltered):");
        System.out.println(topicCounts);
        System.out.println();

        // Wave 2
        Map<String, Integer> topicCountsFiltered = tallyTopicsFiltered(ids, topics);
        System.out.println("Here are how many times each topic appears (filtered):");
        System.out.println(topicCountsFiltered);
        System.err.println();

        Map<String,String> removed = removedFromTally(ids);
        System.out.println("Here are the users who did not have their votes counted:"); 
        System.out.println(removed);

        }

    /**
     * Tally the occurrences of each topic from the provided list of topics.
     * This method takes a list of topics. It returns a Map where the keys are topics
     * and the values are how many times they appear in the input.
     *
     * @param topics a list of strings representing the topics to be tallied
     * @return a map containing topics as keys and their occurrence counts as values
     */
    public static Map<String, Integer> tallyTopics(List<String> topics) {
        // WAVE 1
        // TODO: Implement this method
        Map<String, Integer> newMap = new TreeMap<>();

        for(String word: topics)
        {
            if(!newMap.containsKey(word))
            {
                newMap.put(word, 1);
            }
            else
            {
                int count = newMap.get(word);
                newMap.put(word, count+1);
            }
        }
        return newMap;
    }

    /**
     * Tally the occurrences of valid votes for each topic from the provided lists of IDs and topics.
     * 
     * The lists are of equal length and are aligned: the id at index zero cast a vote for
     * the topic at endex 0, the id at index 1 cast a vote for the topic at index 1, etc.
     * It returns a map where each topic is associated with the number of times it appears in the input.
     * However, any user who did not enter exactly 2 topics should not have their votes counted.
     *
     * @param ids a list of strings representing IDs associated with each topic
     * @param topics a list of strings representing the topics to be tallied
     * @return a map containing topics as keys and their occurrence counts as values
     */
    public static Map<String, Integer> tallyTopicsFiltered(List<String> ids, List<String> topics) 
    {
      // WAVE 2
      // TODO: Implement this method
      Map<String, Integer> idCount = tallyTopics(ids);
      Map<String, Integer> topicsCount = new TreeMap<>();
    
        for(int i = 0; i<topics.size(); i++)
        {
        if(idCount.get(ids.get(i)).equals(2))
        {
            if(!topicsCount.containsKey(topics.get(i)))
            {
                topicsCount.put(topics.get(i), 1);
            }
            else
            {
                int count = topicsCount.get(topics.get(i));
                topicsCount.put(topics.get(i), count+1);
            }
        }
        
    }
      return topicsCount;
    }


    public static Map<String, String> removedFromTally(List<String> ids) 
    {
      // generates a list of users who did not enter exactly 2 topics and will not have their votes counted.
      Map<String, Integer> idCount = tallyTopics(ids);
      Map<String, String> removed = new TreeMap<>();
      
      for (Map.Entry<String, Integer> entry : idCount.entrySet())
      {
        String id = entry.getKey();
        int count = entry.getValue();

        if (count < 2) {
            removed.put(id, "Not Enough Votes");
        } else if (count > 2) {
            removed.put(id, "Too Many Votes");
        }
    }
    return removed;
    }
}
