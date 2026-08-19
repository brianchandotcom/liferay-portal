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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
	}

	/**
	 * The headline fix. Before the consolidation the outer catch replaced the
	 * cause with RuntimeException("Unable to create JSON object"), so no caller
	 * could tell a missing resource from a socket timeout.
	 */
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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	/**
	 * Resolving a client credentials authorization performs a token request, so
	 * a URL that cannot carry the header must not resolve one.
	 */
	@Test
	public void testToJSONObjectWhenURLIsFileAndAuthorizationIsClientCredentials()
		throws Exception {

		JenkinsMasterTestUtil.getJenkinsCohortProperties("test-9", 1);

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 7800);

		setUrlReaderOutput(
			String.valueOf(jsonObject), _URL_FILE, mockUrlReaders);

		JSONObject readJSONObject = JenkinsResultsParserUtil.toJSONObject(
			_URL_FILE,
			new JenkinsResultsParserUtil.ClientCredentialsHTTPAuthorization(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new URL("https://test.liferay.com/o/oauth2/token")));

		Assert.assertEquals(7800, readJSONObject.getInt("id"));

		verifyUrlReadAttemptCount(0, mockUrlReaders, "/o/oauth2/token");
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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	/**
	 * A caller that does not expect a body still gets the empty string, so the
	 * empty body rejection stays scoped to callers that asked for one.
	 */
	@Test
	public void testToStringWhenResponseBodyIsEmptyAndNotExpected()
		throws Exception {

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderOutput("", _URL, mockUrlReaders);

		Assert.assertEquals(
			"",
			JenkinsResultsParserUtil.toString(
				_URL, false, _MAX_RETRIES, 0, 0, false));

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
	}

	/**
	 * A 403 from the GitHub API is the secondary rate limit rather than a
	 * terminal 4xx, and the branch that tells them apart now reads the code
	 * from the connection instead of matching the exception message.
	 */
	@Test
	public void testToStringWhenResponseCodeIs403AndURLIsGitHubAPI()
		throws Exception {

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"github.access.token", RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		MockUrlReaders mockUrlReaders = mockUrlReaders();

		setUrlReaderResponseCode(403, _URL_GITHUB_API, mockUrlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL_GITHUB_API, false, 0, 0, 0);

			Assert.fail(
				"Expected a GitHubSecondaryRateLimitRuntimeException to " +
					"reach the caller");
		}
		catch (GitHubSecondaryRateLimitRuntimeException
					gitHubSecondaryRateLimitRuntimeException) {
		}

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL_GITHUB_API);
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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
	}

	/**
	 * A 422 is the one terminal 4xx that surfaces as a RuntimeException
	 * instead of the IOException the rest throw, and the code deciding that
	 * now comes from the connection rather than the exception message.
	 */
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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
	}

	/**
	 * A 403 carries the GitHub secondary rate limit and an expired Testray 2
	 * token, and a 408 and a 429 are retryable by definition. A 5xx is not a
	 * 4xx at all and keeps its retries.
	 */
	@Test
	public void testToStringWhenResponseCodeIsRetryable() throws Exception {
		_testToStringWhenResponseCodeIsRetryable(403);
		_testToStringWhenResponseCodeIsRetryable(408);
		_testToStringWhenResponseCodeIsRetryable(429);
		_testToStringWhenResponseCodeIsRetryable(500);
	}

	/**
	 * A 4xx that carries no remedy fails the whole read on the first attempt,
	 * rather than spending the caller's retry budget on an answer that cannot
	 * change.
	 */
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

		verifyUrlReadAttemptCount(1, mockUrlReaders, _URL);
	}

	/**
	 * A socket that never produced a status line has no code to read, and
	 * asking for one sends the request again and blocks for another full
	 * timeout before answering. The failure is retryable whatever the code, so
	 * the lookup has to be skipped.
	 */
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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);

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

		verifyUrlReadAttemptCount(_MAX_RETRIES + 1, mockUrlReaders, _URL);
	}

	private static final int _MAX_RETRIES = 2;

	private static final String _STANDARD_OUT = "Hello, World!\n";

	private static final String _URL = "http://test.liferay.com";

	private static final String _URL_FILE = "file:/tmp/queue-item.json";

	private static final String _URL_GITHUB_API =
		"https://api.github.com/repos/liferay/liferay-portal";

}