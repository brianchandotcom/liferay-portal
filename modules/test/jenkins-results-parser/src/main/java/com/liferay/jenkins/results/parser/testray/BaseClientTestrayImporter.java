/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Environment;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;

/**
 * @author Brittney Nguyen
 */
public abstract class BaseClientTestrayImporter {

	public abstract void importResults() throws Exception;

	protected BaseClientTestrayImporter() {
		String testrayServerURL = getEnvVarValue("testrayServerURL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayServerURL)) {
			_testrayServerURL = testrayServerURL;
		}

		_testrayOAuth2ClientId = getEnvVarValue("testrayOAuth2ClientId");
		_testrayOAuth2ClientSecret = getEnvVarValue(
			"testrayOAuth2ClientSecret");

		if (JenkinsResultsParserUtil.isNullOrEmpty(_testrayOAuth2ClientId) ||
			JenkinsResultsParserUtil.isNullOrEmpty(
				_testrayOAuth2ClientSecret)) {

			throw new RuntimeException(
				"Testray 2 requires \"testrayOAuth2ClientId\" and " +
					"\"testrayOAuth2ClientSecret\" to be set");
		}

		String testrayCloudBucketName = getEnvVarValue(
			"testrayCloudBucketName");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayCloudBucketName)) {
			testrayCloudBucketName = getEnvVarValue("testrayS3BucketName");
		}

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayCloudBucketName)) {
			testrayCloudBucketName = TestrayCloudBucket.DEFAULT_BUCKET_NAME;
		}

		if (!TestrayCloudBucket.hasGoogleApplicationCredentials(
				testrayCloudBucketName)) {

			throw new RuntimeException(
				"Testray 2 requires \"GOOGLE_APPLICATION_CREDENTIALS\" to " +
					"point to a credential with access to " +
						testrayCloudBucketName);
		}

		_testrayCloudBucket = TestrayCloudBucket.getInstance(
			testrayCloudBucketName);
	}

	protected String getEnvVarValue(String varName) {
		String varValue = Environment.get(varName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(varValue)) {
			varValue = System.getProperty(varName);
		}

		return varValue;
	}

	protected TestrayBuild getTestrayBuild(
		Date testrayBuildDate, String testrayBuildName, String testrayBuildSHA,
		String testrayProductVersionName, String testrayProjectName,
		String testrayRoutineName) {

		TestrayServer testrayServer = getTestrayServer();

		TestrayProject testrayProject = testrayServer.getTestrayProjectByName(
			testrayProjectName);

		if (testrayProject == null) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find Testray project ", testrayProjectName,
					" on ", _testrayServerURL));
		}

		TestrayRoutine testrayRoutine = testrayProject.createTestrayRoutine(
			testrayRoutineName);

		TestrayProductVersion testrayProductVersion =
			testrayProject.createTestrayProductVersion(
				testrayProductVersionName);

		return testrayRoutine.createTestrayBuild(
			testrayProductVersion, testrayBuildName, testrayBuildDate, null,
			testrayBuildSHA);
	}

	protected TestrayCloudBucket getTestrayCloudBucket() {
		return _testrayCloudBucket;
	}

	protected TestrayServer getTestrayServer() {
		if (_testrayServer != null) {
			return _testrayServer;
		}

		TestrayServer testrayServer = TestrayFactory.newTestrayServer(
			_testrayServerURL);

		testrayServer.setCommunicationFailureNotificationEnabled(false);

		try {
			testrayServer.setHTTPAuthorization(
				new JenkinsResultsParserUtil.ClientCredentialsHTTPAuthorization(
					_testrayOAuth2ClientId, _testrayOAuth2ClientSecret,
					new URL(testrayServer.getURL() + "/o/oauth2/token")));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}

		_testrayServer = testrayServer;

		return _testrayServer;
	}

	protected String uploadAttachment(File file, String key) {
		_testrayCloudBucket.createTestrayCloudObject(key, file);

		URIBuilder uriBuilder = JenkinsResultsParserUtil.newURIBuilder(
			JenkinsResultsParserUtil.combine(
				_testrayCloudBucket.getTestrayCloudBaseURL(), "/", key));

		uriBuilder.addParameter("authuser", "0");

		return uriBuilder.toString();
	}

	protected void uploadResults(
			List<File> resultsFiles, TestrayBuild testrayBuild)
		throws IOException {

		File tempDir = null;

		try {
			Path path = Files.createTempDirectory("testray-results-");

			tempDir = path.toFile();

			File testrayResultsDir = new File(tempDir, "testray-results");

			testrayResultsDir.mkdirs();

			for (File resultsFile : resultsFiles) {
				JenkinsResultsParserUtil.copy(
					resultsFile,
					new File(testrayResultsDir, resultsFile.getName()));
			}

			File resultsTarGzFile = new File(
				tempDir,
				JenkinsResultsParserUtil.combine(
					String.valueOf(
						JenkinsResultsParserUtil.getCurrentTimeMillis()),
					"-", String.valueOf(testrayBuild.getId()),
					"-results.tar.gz"));

			JenkinsResultsParserUtil.tarGzip(
				testrayResultsDir, resultsTarGzFile);

			_testrayCloudBucket.createTestrayCloudObject(
				"inbox/" + resultsTarGzFile.getName(), resultsTarGzFile);
		}
		finally {
			if (tempDir != null) {
				JenkinsResultsParserUtil.delete(tempDir);
			}
		}
	}

	private final TestrayCloudBucket _testrayCloudBucket;
	private final String _testrayOAuth2ClientId;
	private final String _testrayOAuth2ClientSecret;
	private TestrayServer _testrayServer;
	private String _testrayServerURL = "https://testray.liferay.com";

}