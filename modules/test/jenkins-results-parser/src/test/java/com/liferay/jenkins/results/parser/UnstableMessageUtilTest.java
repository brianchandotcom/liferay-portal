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

import org.junit.After;
import org.junit.Before;
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
		_downloadBatchDependency(
			"multiple", "test-1-19.liferay.com",
			"test-portal-acceptance-pullrequest-batch(master)", "1287");
	}

	@Before
	public void setUp() throws Exception {
		_replaceInAllFiles(
			_testDependenciesDir, _USER_DIR_TOKEN,
			System.getProperty("user.dir"));
	}

	@After
	public void tearDown() throws Exception {
		_replaceInAllFiles(
			_testDependenciesDir, System.getProperty("user.dir"),
			_USER_DIR_TOKEN);
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
						batchDir, runUrlString, buildNumber, jobName, hostName);

					if (localRunUrlString != null) {
						runJSONObject.put("url", localRunUrlString);
					}
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
						batchDir, runUrlString, buildNumber, jobName, hostName);

					if (localRunUrlString != null) {
						childJSONObject.put("url", localRunUrlString);
					}
				}
			}
		}

		_write(testReportJSONFile, testReportJSONObject);
	}

	private static File _downloadJSONFile(File targetFile, URL url)
		throws Exception {

		File targetFileParentDir = targetFile.getParentFile();
		targetFileParentDir.mkdirs();

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(url.toString()));

		_write(targetFile, jsonObject.toString(4));

		return targetFile;
	}

	private static String _downloadSlaveDependency(
			File batchDir, String urlString, String buildNumber, String jobName,
			String hostName)
		throws Exception {

		URL jsonURL = _createURL(urlString + "/api/json");
		URL jsonTestReportURL = _createURL(urlString + "/testReport/api/json");

		int x = urlString.indexOf("AXIS_VARIABLE=") + "AXIS_VARIABLE=".length();

		String axis = urlString.substring(x, urlString.indexOf("/", x));

		x = urlString.indexOf("/", x) + 1;

		String slaveBuildNumber = urlString.substring(
			x, urlString.indexOf("/", x));

		if (!slaveBuildNumber.equals(buildNumber)) {
			return null;
		}

		String name = jobName + "_" + axis + "_" + hostName;

		File slaveDependencyRootDir = new File(
			batchDir.getPath() + "/" + name + "/" + buildNumber + "/");

		slaveDependencyRootDir.mkdirs();

		File slaveDependencyFile = new File(
			slaveDependencyRootDir.getPath() + "/api/json");

		File slaveTestReportDependencyFile = new File(
			slaveDependencyRootDir.getPath() + "/testReport/api/json");

		_downloadJSONFile(slaveDependencyFile, jsonURL);
		_downloadJSONFile(slaveTestReportDependencyFile, jsonTestReportURL);

		URI slaveDependencyFileUri = slaveDependencyFile.toURI();
		String slaveDependencyJsonFilePath =
			slaveDependencyFileUri.toASCIIString();

		return slaveDependencyJsonFilePath.substring(
			0, slaveDependencyJsonFilePath.indexOf("api/json"));
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

	private static void _replaceInAllFiles(
			File rootDir, String token, String value)
		throws Exception {

		File[] descendantFileArray = rootDir.listFiles();

		for (File descendantFile : descendantFileArray) {
			if (descendantFile.isDirectory()) {
				_replaceInAllFiles(descendantFile, token, value);
			}
			else {
				_replaceInFile(descendantFile, token, value);
			}
		}
	}

	private static void _replaceInFile(
			File targetFile, String token, String value)
		throws Exception {

		String fileContents = _read(targetFile);

		fileContents = fileContents.replace(token, value);

		targetFile.delete();

		_write(targetFile, fileContents);
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

	private static final String _USER_DIR_TOKEN = "${user.dir}";

	private static final File _testDependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies/" +
		"UnstableMessageUtilTest");

}