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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.BeforeClass;
import org.junit.Test;
public class UnstableMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadBatchDependency(
			"generic", "test-1-9.liferay.com",
			"test-portal-acceptance-pullrequest-batch(master)", "5141");
		_downloadBatchDependency(
			"generic", "test-1-18.liferay.com",
			"test-portal-acceptance-pullrequest-batch(master)", "3415");
	}

	@Test
	public void test() throws Exception {
		for (File batchDir : _testDependenciesDir.listFiles()) {
			if (batchDir.isDirectory()) {
				String expectedResults = _getExpectedResults(batchDir);
				File batchJsonDir = new File(batchDir.getPath() + "/");
				URI batchJsonDirUri = batchJsonDir.toURI();
				assertEquals(
					expectedResults,
					UnstableMessageUtil.getUnstableMessage(
						batchJsonDirUri.toASCIIString()));
			}
		}
	}

	private static URL _createURL(String urlString) throws Exception {
		URL url = new URL(urlString);

		return _encode(url);
	}

	private static void _downloadBatchDependency(
			String description, String hostName, String jobName,
			String buildNumber)
		throws Exception {

		String batchDependencyIdentifier =
			description + "_" + jobName + "_" + hostName + "_" + buildNumber;

		File batchDir = new File(
			_testDependenciesDir.getPath() + "/" +
				batchDependencyIdentifier + "/");

		if (batchDir.exists()) {
			return;
		}

		batchDir.mkdir();

		_downloadBatchTestReportDependency(
			batchDir, hostName, jobName, buildNumber);

		File batchJSONDir = new File(batchDir.getPath() + "/api");

		batchJSONDir.mkdirs();

		File batchJSONFile = new File(batchJSONDir.getPath() + "/json");

		String batchURLString =
			"https://${hostName}/job/${jobName}/${buildNumber}/api/json";

		batchURLString = _replaceToken(
			batchURLString, "buildNumber", buildNumber);
		batchURLString = _replaceToken(batchURLString, "hostName", hostName);
		batchURLString = _replaceToken(batchURLString, "jobName", jobName);

		URL url = _createURL(batchURLString);

		URI uri = url.toURI();

		JSONObject batchJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(uri.toASCIIString()));

		JSONArray runsJSONArray = batchJSONObject.getJSONArray("runs");

		if (runsJSONArray != null) {
			for (int i = 0; i<runsJSONArray.length(); i++) {
				JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

				if (runJSONObject != null) {
					String runUrlString = URLDecoder.decode(
						runJSONObject.getString("url"), "UTF8");
					String localRunUrlString = _downloadSlaveDependency(
						batchDir, runUrlString);
					runJSONObject.put("url", localRunUrlString);
				}
			}
		}

		_write(batchJSONFile, batchJSONObject);

		URI batchDirUri = batchDir.toURI();

		String expectedResultsString = UnstableMessageUtil.getUnstableMessage(
			batchDirUri.toASCIIString());

		File expectedResultsDir = new File(
			batchDir.getPath() + "/expected-results");

		expectedResultsDir.mkdir();

		File expectedResultsFile = new File(
			expectedResultsDir.getPath() + "/UnstableMessageUtilTest.html");

		_write(expectedResultsFile, expectedResultsString);
	}

	private static void _downloadBatchTestReportDependency(
		File batchDir, String hostName, String jobName,
		String buildNumber) throws Exception {

		File testReportJSONDir = new File(
			batchDir.getPath() + "/testReport/api");

		testReportJSONDir.mkdirs();

		File testReportJSONFile = new File(
			testReportJSONDir.getPath() + "/json");

		String testReportURLString =
			"https://${hostName}/job/${jobName}/${buildNumber}/testReport/" +
				"api/json";

		testReportURLString = _replaceToken(
			testReportURLString, "buildNumber", buildNumber);
		testReportURLString = _replaceToken(
			testReportURLString, "hostName", hostName);
		testReportURLString = _replaceToken(
			testReportURLString, "jobName", jobName);

		URL url = _createURL(testReportURLString);

		URI uri = url.toURI();

		JSONObject testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(uri.toASCIIString()));

		JSONArray childJSONArray = testReportJSONObject.getJSONArray(
			"childReports");

		if (childJSONArray != null) {
			for (int i = 0; i<childJSONArray.length(); i++) {
				JSONObject childReportJSONObject = childJSONArray.getJSONObject(
					i);

				if (childReportJSONObject != null) {
					JSONObject childJSONObject =
						childReportJSONObject.getJSONObject("child");
					String runUrlString = URLDecoder.decode(
						childJSONObject.getString("url"), "UTF8");
					String localRunUrlString = _downloadSlaveDependency(
						batchDir, runUrlString);
					childJSONObject.put("url", localRunUrlString);
				}
			}
		}

		_write(testReportJSONFile, testReportJSONObject);
	}

	private static String _downloadSlaveDependency(
			File batchDir, String urlString)
		throws Exception {

		URL jsonUrl = _createURL(urlString + "/api/json");

		URI jsonUri = jsonUrl.toURI();

		String hostName = jsonUrl.getHost();

		int x = urlString.indexOf("/job/") + 5;

		String jobName = urlString.substring(x, urlString.indexOf("/", x));

		x = urlString.indexOf("AXIS_VARIABLE=") + "AXIS_VARIABLE=".length();

		String axis = urlString.substring(x, urlString.indexOf("/", x));

		x = urlString.indexOf("/", x) + 1;

		String buildNumber = urlString.substring(x, urlString.indexOf("/", x));

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(jsonUri.toASCIIString()));

		String name = jobName + "_" + axis + "_" + hostName + "_" + buildNumber;

		File slaveDependencyDir = new File(
			batchDir.getPath() + "/" + name + "/api/");
		slaveDependencyDir.mkdirs();

		File slaveDependencyFile = new File(
			slaveDependencyDir.getPath() + "/json");
		_write(slaveDependencyFile, jsonObject);

		URI slaveDependencyFileUri = slaveDependencyFile.toURI();
		String slaveDependencyJsonFilePath =
			slaveDependencyFileUri.toASCIIString();

		return slaveDependencyJsonFilePath.substring(
			0, slaveDependencyJsonFilePath.indexOf("/api/json"));
	}

	private static URL _encode(URL url) throws Exception {
		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		String urlString = uri.toASCIIString();
		urlString = urlString.replace("#", "%23");

		return new URL(urlString);
	}

	private static String _getExpectedResults(File batchDir) throws Exception {
		File expectedResultsFile =
			new File(
				batchDir.getPath() +
				"/expected-results/UnstableMessageUtilTest.html");

		return _read(expectedResultsFile);
	}

	private static String _read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	private static String _replaceToken(
		String string, String token, String value) {

		if (string == null) {
			return string;
		}

		return string.replace("${" + token + "}", value);
	}

	private static void _write(File file, JSONObject jsonObject)
		throws Exception {

		_write(file, jsonObject.toString(4));
	}

	private static void _write(File file, String content) throws Exception {
		Files.write(Paths.get(file.toURI()), content.getBytes());
	}

	private static final String _EXPECTED_RESULTS_FILE_PATH =
		"expected-results/UnstableMessageUtilTest.html";

	private static final File _testDependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies/" +
		"UnstableMessageUtilTest");

}