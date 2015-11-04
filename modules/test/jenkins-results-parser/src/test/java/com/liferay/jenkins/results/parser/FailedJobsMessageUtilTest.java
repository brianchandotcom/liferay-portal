package com.liferay.jenkins.results.parser;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class FailedJobsMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Override
	protected void downloadSample(File sampleDir, URL url) throws Exception {
		downloadSampleURL(sampleDir, url, "/api/json");
		downloadSampleURL(sampleDir, url, "/testReport/api/json");

		downloadSampleAxisURLs(sampleDir, new File(sampleDir, "/api/json"));
	}

	protected void downloadSample(
			String sampleKey, String buildNumber, String jobName,
			String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/${buildNumber}/";

		urlString = replaceToken(urlString, "buildNumber", buildNumber);
		urlString = replaceToken(urlString, "hostName", hostName);
		urlString = replaceToken(urlString, "jobName", jobName);

		URL url = createURL(urlString);

		downloadSample(sampleKey, url);
	}

	protected void downloadSampleAxisURLs(File sampleDir, File jobJSONFile)
		throws Exception {

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(toURLString(jobJSONFile)));

		int number = jobJSONObject.getInt("number");

		JSONArray runsJSONArray = jobJSONObject.getJSONArray("runs");

		for (int i = 0; i < runsJSONArray.length(); i++) {
			JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

			if (number != runJSONObject.getInt("number")) {
				continue;
			}

			String runURLString = URLDecoder.decode(
				runJSONObject.getString("url"), "UTF-8");

			File runDir = new File(sampleDir, "run-" + i + "/" + number + "/");

			downloadSampleURL(runDir, createURL(runURLString), "/api/json");
			downloadSampleURL(
				runDir, createURL(runURLString), "/testReport/api/json");

			runJSONObject.put("url", toURLString(runDir));
		}

		write(jobJSONFile, jobJSONObject.toString(4));
	}
	@Override
	protected String getMessage(String urlString) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
