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

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Peter Yoo
 */
public class FailureMessageUtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_downloadSample(
			"generic-1", "0,label_exp=!master", "129",
			"test-portal-acceptance-pullrequest-batch(master)", "test-4-1");
		_downloadSample(
			"rebase-1", null, "267",
			"test-portal-acceptance-pullrequest-source(ee-6.2.x)", "test-1-1");
		_downloadSample(
			"plugin-compile-1", "9,label_exp=!master", "233",
			"test-portal-acceptance-pullrequest-batch(ee-6.2.x)", "test-1-20");
	}

	@Test
	public void testGetFailureMessage() throws Exception {
		File[] files = _dependenciesDir.listFiles();

		for (File file : files) {
			assertSample(_project, file);
		}
	}

	protected void assertSample(Project project, File caseDir)
		throws Exception {

		System.out.print("Asserting sample " + caseDir.getName() + ": ");

		File expectedFailureMessageFile = new File(
			caseDir, "expected_failure_message.html");

		String expectedFailureMessage = _read(expectedFailureMessageFile);

		String actualFailureMessage = FailureMessageUtil.getFailureMessage(
			project, _toExternalForm(caseDir));

		boolean value = expectedFailureMessage.equals(actualFailureMessage);

		if (value) {
			System.out.println(" PASSED");
		}
		else {
			System.out.println(" FAILED");
			System.out.println("\nActual results: \n" + actualFailureMessage);
			System.out.println(
				"\nExpected results: \n" + expectedFailureMessage);
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
			String sampleKey, String axisVariable, String buildNumber,
			String jobName, String hostName)
		throws Exception {

		String urlString =
			"https://${hostName}.liferay.com/job/${jobName}/" +
				"/${buildNumber}/";

		if (axisVariable != null) {
			urlString =
				"https://${hostName}.liferay.com/job/${jobName}/" +
					"AXIS_VARIABLE=${axis}/${buildNumber}/";

			urlString = _replaceToken(urlString, "axis", axisVariable);
		}

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
			_downloadSampleURL(sampleDir, url, "/logText/progressiveText");

			_writeExpectedFailureMessage(_project, sampleDir);
		}
		catch (IOException ioe) {
			_deleteFile(sampleDir);

			throw ioe;
		}
	}

	private static void _downloadSampleURL(File dir, URL url, String urlSuffix)
		throws Exception {

		_write(
			new File(dir, urlSuffix),
			JenkinsResultsParserUtil.toString(url + urlSuffix));
	}

	private static URL _encode(URL url) throws Exception {
		URI uri = new URI(
			url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
			url.getPath(), url.getQuery(), url.getRef());

		return new URL(uri.toASCIIString());
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

	private static String _toExternalForm(File file) throws Exception {
		URI uri = file.toURI();

		URL url = uri.toURL();

		return url.toExternalForm();
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

	private static void _writeExpectedFailureMessage(
			Project project, File sampleDir)
		throws Exception {

		File expectedFailureMessageFile = new File(
			sampleDir, "expected_failure_message.html");
		String expectedFailureMessage = FailureMessageUtil.getFailureMessage(
			project, _toExternalForm(sampleDir));

		_write(expectedFailureMessageFile, expectedFailureMessage);
	}

	private static final File _dependenciesDir = new File(
		"src/test/resources/com/liferay/results/parser/dependencies" +
			"/FailureMessageUtilTest");
	private static final Project _project = new Project();

	static {
		_project.setProperty(
			"github.pull.request.head.branch", "junit-pr-head-branch");
		_project.setProperty(
			"github.pull.request.head.username", "junit-pr-head-username");
		_project.setProperty(
			"plugins.branch.name", "junit-plugins-branch-name");
		_project.setProperty("plugins.repository", "junit-plugins-repository");
		_project.setProperty("portal.repository", "junit-portal-repository");
		_project.setProperty("repository", "junit-repository");
	}

}