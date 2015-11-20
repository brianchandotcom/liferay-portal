/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class UnstableMessageUtilTest extends BaseJenkinsResultsParserTestCase {

	@Before
	public void setUp() throws Exception {
		downloadSample(
			"1-of-1", "5141",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-9");
		downloadSample(
			"2-of-3888", "3415",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-18");
		downloadSample(
			"6-of-6", "1287",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19");
	}

	@Test
	public void testGetUnstableMessage() throws Exception {
		assertSamples();
	}

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

		JenkinsResultsParserUtil.write(jobJSONFile, jobJSONObject.toString(4));
	}

	@Override
	protected String getMessage(String urlString) throws Exception {
		return UnstableMessageUtil.getUnstableMessage(urlString);
	}

}