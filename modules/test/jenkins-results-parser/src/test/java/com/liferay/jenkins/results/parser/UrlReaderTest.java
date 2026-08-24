/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class UrlReaderTest extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testToInputStream() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput(_STANDARD_OUT, _URL, mockUrlReaders);

		try (InputStream inputStream = JenkinsResultsParserUtil.toInputStream(
				_URL, false)) {

			Assert.assertEquals(
				_STANDARD_OUT,
				JenkinsResultsParserUtil.readInputStream(inputStream));
		}
	}

	@Test
	public void testToJSONArray() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		JSONArray jsonArray = new JSONArray();

		jsonArray.put("first");
		jsonArray.put("second");

		setUrlReaderOutput(String.valueOf(jsonArray), _URL, mockUrlReaders);

		JSONArray readJSONArray = JenkinsResultsParserUtil.toJSONArray(
			_URL, false, _MAX_RETRIES, null, 0, 0);

		Assert.assertEquals(2, readJSONArray.length());
		Assert.assertEquals("first", readJSONArray.getString(0));

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToJSONArrayWhenResponseIsMalformed() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput("not json at all", _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toJSONArray(
				_URL, false, _MAX_RETRIES, null, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
			Assert.assertEquals(
				"Unable to create a JSON array from the response body",
				ioException.getMessage());
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	@Test
	public void testToJSONObject() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 7800);

		setUrlReaderOutput(String.valueOf(jsonObject), _URL, mockUrlReaders);

		JSONObject readJSONObject = JenkinsResultsParserUtil.toJSONObject(
			_URL, false, _MAX_RETRIES, 0, 0);

		Assert.assertEquals(7800, readJSONObject.getInt("id"));

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToJSONObjectWhenResponseCodeIs404() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderException(
			new FileNotFoundException(_URL), _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toJSONObject(
				_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (FileNotFoundException fileNotFoundException) {
			Assert.assertEquals(_URL, fileNotFoundException.getMessage());
		}

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToJSONObjectWhenResponseIsMalformed() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput("not json at all", _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toJSONObject(
				_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
			Assert.assertEquals(
				"Unable to create a JSON object from the response body",
				ioException.getMessage());

			Throwable throwable = ioException.getCause();

			Assert.assertTrue(throwable instanceof JSONException);
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	@Test
	public void testToJSONObjectWhenURLIsFileAndAuthorizationIsClientCredentials()
		throws Exception {

		JenkinsMasterTestUtil.getJenkinsCohortProperties("test-9", 1);

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 7800);

		String url = "file:/tmp/" + RandomTestUtil.randomString() + ".json";

		setUrlReaderOutput(String.valueOf(jsonObject), url, mockUrlReaders);

		JSONObject readJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url,
			new JenkinsResultsParserUtil.ClientCredentialsHTTPAuthorization(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new URL("https://test.liferay.com/o/oauth2/token")));

		Assert.assertEquals(7800, readJSONObject.getInt("id"));

		verifyUrlReaderAttemptCount(0, mockUrlReaders, "/o/oauth2/token");
	}

	@Test
	public void testToString() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput(_STANDARD_OUT, _URL, mockUrlReaders);

		Assert.assertEquals(
			_STANDARD_OUT, JenkinsResultsParserUtil.toString(_URL, false));
	}

	@Test
	public void testToStringWhenConnectionTimesOut() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderException(
			new SocketTimeoutException("Read timed out"), _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (SocketTimeoutException socketTimeoutException) {
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseBodyIsEmpty() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput("", _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(
				_URL, false, _MAX_RETRIES, 0, 0, true);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
			String message = ioException.getMessage();

			Assert.assertTrue(
				message, message.startsWith("Unable to read a response body"));
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseBodyIsEmptyAndNotExpected()
		throws Exception {

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput("", _URL, mockUrlReaders);

		Assert.assertEquals(
			"",
			JenkinsResultsParserUtil.toString(
				_URL, false, _MAX_RETRIES, 0, 0, false));

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseCodeIs403AndURLIsGitHubAPI()
		throws Exception {

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"github.access.token", RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		String url = "https://api.github.com/" + RandomTestUtil.randomString();

		setUrlReaderResponseCode(403, url, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(url, false, 0, 0, 0);

			Assert.fail(
				"Expected a GitHubSecondaryRateLimitRuntimeException to " +
					"reach the caller");
		}
		catch (GitHubSecondaryRateLimitRuntimeException
					gitHubSecondaryRateLimitRuntimeException) {
		}

		verifyUrlReaderAttemptCount(1, mockUrlReaders, url);
	}

	@Test
	public void testToStringWhenResponseCodeIs403AndURLIsGitHubAPIWithRetries()
		throws Exception {

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"github.access.token", RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		String url = "https://api.github.com/" + RandomTestUtil.randomString();

		setUrlReaderResponseCode(403, url, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(url, false, 3, 5, 0);

			Assert.fail(
				"Expected a GitHubSecondaryRateLimitRuntimeException to " +
					"reach the caller");
		}
		catch (GitHubSecondaryRateLimitRuntimeException
					gitHubSecondaryRateLimitRuntimeException) {
		}

		verifyUrlReaderSleepDurations(
			Arrays.asList(5000L, 25000L, 60000L), mockUrlReaders);
	}

	@Test
	public void testToStringWhenResponseCodeIs404() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderException(
			new FileNotFoundException(_URL), _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (FileNotFoundException fileNotFoundException) {
		}

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseCodeIs422() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderResponseCode(422, _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected a RuntimeException to reach the caller");
		}
		catch (RuntimeException runtimeException) {
			Throwable throwable = runtimeException.getCause();

			Assert.assertTrue(throwable instanceof IOException);
		}

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseCodeIsRetryable() throws Exception {
		_testToStringWhenResponseCodeIsRetryable(403);
		_testToStringWhenResponseCodeIsRetryable(408);
		_testToStringWhenResponseCodeIsRetryable(429);
		_testToStringWhenResponseCodeIsRetryable(500);
	}

	@Test
	public void testToStringWhenResponseCodeIsTerminal() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderResponseCode(400, _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
		}

		verifyUrlReaderAttemptCount(1, mockUrlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseNeverArrives() throws Exception {
		MockUrlReaders mockUrlReaders = mockUrlReaders();

		List<HttpURLConnection> httpURLConnections = new ArrayList<>();

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			Mockito.doAnswer(
				invocation -> {
					HttpURLConnection httpURLConnection = Mockito.mock(
						HttpURLConnection.class);

					Mockito.doThrow(
						new SocketTimeoutException("Read timed out")
					).when(
						httpURLConnection
					).getInputStream();

					httpURLConnections.add(httpURLConnection);

					return httpURLConnection;
				}
			).when(
				urlReader
			).openURLConnection(
				Mockito.any(), Mockito.anyBoolean(), Mockito.any(),
				Mockito.any(), Mockito.anyBoolean(), Mockito.anyInt(),
				Mockito.argThat(
					readURL -> (readURL != null) && readURL.contains(_URL))
			);
		}

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (SocketTimeoutException socketTimeoutException) {
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);

		for (HttpURLConnection httpURLConnection : httpURLConnections) {
			Mockito.verify(
				httpURLConnection, Mockito.never()
			).getResponseCode();
		}
	}

	private void _testToStringWhenResponseCodeIsRetryable(int responseCode)
		throws Exception {

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderResponseCode(responseCode, _URL, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
		}

		verifyUrlReaderAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	private static final int _MAX_RETRIES = 2;

	private static final String _STANDARD_OUT = "Hello, World!\n";

	private static final String _URL = "http://test.liferay.com";

}