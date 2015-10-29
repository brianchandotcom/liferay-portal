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
import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class UnstableMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadSample(
			"1-of-1", "5141",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-9");
		_downloadSample(
			"2-of-3888", "3415",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-18");
		_downloadSample(
			"6-of-6", "1287",
			"test-portal-acceptance-pullrequest-batch(master)", "test-1-19");
	}

	@Test
	public void testGetUnstableMessage() throws Exception {
		File[] files = _dependenciesDir.listFiles();

		for (File file : files) {
			assertSample(file);
		}
	}

	protected void assertSample(File caseDir) throws Exception {
		System.out.print("Asserting sample " + caseDir.getName() + ": ");

		File expectedUnstableMessageFile = new File(
			caseDir, "expected_unstable_message.html");

		String expectedUnstableMessage = _read(expectedUnstableMessageFile);

		String actualUnstableMessage = UnstableMessageUtil.getUnstableMessage(
			_toURLString(caseDir));

		boolean value = expectedUnstableMessage.equals(actualUnstableMessage);

		if (value) {
			System.out.println(" PASSED");
		}
		else {
			System.out.println(" FAILED");
			System.out.println("\nActual results: \n" + actualUnstableMessage);
			System.out.println(
				"\nExpected results: \n" + expectedUnstableMessage);
		}

		Assert.assertTrue(value);
	}

	private static URL _createURL(String urlString) throws Exception {
		URL url = new URL(urlString);

		return _encode(url);
	}

	private static void _deleteFile(File file) {
		if (!file.exists()) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		}
		else {
			File[] files = file.listFiles();

			for (File childFile : files) {
				_deleteFile(childFile);
			}

			file.delete();
		}
	}

	private static void _downloadSample(
			String sampleKey, String buildNumber, String jobName,
			String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/${buildNumber}/";

		urlString = _replaceToken(urlString, "buildNumber", buildNumber);
		urlString = _replaceToken(urlString, "hostName", hostName);
		urlString = _replaceToken(urlString, "jobName", jobName);

		URL url = _createURL(urlString);

		_downloadSample(sampleKey, url);
	}

	private static void _downloadSample(String sampleKey, URL url)
		throws Exception {

		System.out.println("Downloading sample " + sampleKey);

		String sampleDirName = _dependenciesDir.getPath() + "/" + sampleKey;

		File sampleDir = new File(sampleDirName);

		if (sampleDir.exists()) {
			return;
		}

		try {
			_downloadSampleURL(sampleDir, url, "/api/json");
			_downloadSampleURL(sampleDir, url, "/testReport/api/json");

			_downloadSampleAxisURLs(
				sampleDir, new File(sampleDir, "/api/json"));

			_writeExpectedUnstableMessage(sampleDir);
		}
		catch (IOException ioe) {
			_deleteFile(sampleDir);

			throw ioe;
		}
	}

	private static void _downloadSampleAxisURLs(
			File sampleDir, File jobJSONFile)
		throws Exception {

		JSONObject jobJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(_toURLString(jobJSONFile)));

		String number = jobJSONObject.getString("number");

		JSONArray runsJSONArray = jobJSONObject.getJSONArray("runs");

		for (int i = 0; i < runsJSONArray.length(); i++) {
			JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

			if (!number.equals(runJSONObject.getString("number"))) {
				continue;
			}

			String runURLString = URLDecoder.decode(
				runJSONObject.getString("url"), "UTF-8");

			File runDir = new File(sampleDir, "run-" + i + "/" + number + "/");

			_downloadSampleURL(runDir, _createURL(runURLString), "/api/json");
			_downloadSampleURL(
				runDir, _createURL(runURLString), "/testReport/api/json");

			runJSONObject.put("url", _toURLString(runDir));
		}

		_write(jobJSONFile, jobJSONObject.toString(4));
	}

	private static void _downloadSampleURL(File dir, URL url, String urlSuffix)
		throws Exception {

		String urlString = url + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		_write(
			new File(dir, urlSuffix),
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString)));
	}

	private static URL _encode(URL url) throws Exception {
		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		String uriASCIIString = uri.toASCIIString();

		return new URL(uriASCIIString.replace("#", "%23"));
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
	
	private static String _toURLString(File file) throws Exception {
		URI uri = file.toURI();

		URL url = uri.toURL();
		
		String urlString = url.toString();

		return urlString.replace(
			System.getProperty("user.dir"),  "${user.dir}");
	}

	private static void _write(File file, String content) throws Exception {
		System.out.println(
			"Write file " + file + " with length " + content.length());

		File parentDir = file.getParentFile();

		if (!parentDir.exists()) {
			System.out.println("Make parent directories for " + file);

			parentDir.mkdirs();
		}

		Files.write(Paths.get(file.toURI()), content.getBytes());
	}

	private static void _writeExpectedUnstableMessage(File sampleDir)
		throws Exception {

		File expectedUnstableMessageFile = new File(
			sampleDir, "expected_unstable_message.html");
		String expectedUnstableMessage = UnstableMessageUtil.getUnstableMessage(
			_toURLString(sampleDir));

		_write(expectedUnstableMessageFile, expectedUnstableMessage);
	}

	private static final File _dependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies" +
			"/UnstableMessageUtilTest");

}