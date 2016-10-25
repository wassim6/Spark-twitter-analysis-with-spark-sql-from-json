package twitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetsCollection {
	
	
	public static long count = 0;
    public static void main(String[] args) throws IOException, TwitterException {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled (true);
        configurationBuilder.setJSONStoreEnabled(true);
        configurationBuilder.setOAuthConsumerKey("tg1vMHVv9e9X28XRQs92U20J1");
        configurationBuilder.setOAuthConsumerSecret("MgzIbhkQwPGNaXIRg8PSZA2AXwAKc2yGovhzOAAceYQnh6GUFO");
        configurationBuilder.setOAuthAccessToken("257451099-rBv4erS1oYPVPX1TVHDd9IZJlT293vb8eKYlP9nF");
        configurationBuilder.setOAuthAccessTokenSecret("CfQGrf7tCih1MtRvOgpF7RMot0YyLojFUrSRtUDRVqs3m");
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();

        StatusListener sListner = new StatusListener() {

            
            public void onException(Exception arg0) {
            }

            public void onTrackLimitationNotice(int arg0) {
            }

            public void onStatus(Status status) {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter("F:\\tweets.json",true));
                    String tweetJson = TwitterObjectFactory.getRawJSON(status);
                    bw.write(tweetJson);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    try {
                        if (bw != null) {
                            bw.flush();
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Count: " + count);
                count++;

            }

            public void onStallWarning(StallWarning arg0) {
            }

            public void onScrubGeo(long arg0, long arg1) {
            }

            public void onDeletionNotice(StatusDeletionNotice arg0) {
            }
        };

        FilterQuery filterQuery = new FilterQuery();
        String keywords[] = {"food","","foodporn","recipe","cooking","healthy ","cook","recipes","yummy","instafood"};
        filterQuery.track(keywords);
        twitterStream.addListener(sListner);
        twitterStream.filter(filterQuery);
    }

}
