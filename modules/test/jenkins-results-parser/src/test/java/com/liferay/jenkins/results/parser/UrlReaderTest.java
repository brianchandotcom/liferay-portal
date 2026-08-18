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

import org.json.JSONException;
import org.json.JSONObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class UrlReaderTest extends com.liferay.jenkins.results.parser.Test {

	@After
	@Override
	public void tearDown() {
		super.tearDown();

		JenkinsMasterTestUtil.resetCaches();
	}

	@Test
	public void testToInputStream() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderOutput(_STANDARD_OUT, _URL, urlReaders);

		try (InputStream inputStream = JenkinsResultsParserUtil.toInputStream(
				_URL, false)) {

			Assert.assertEquals(
				_STANDARD_OUT,
				JenkinsResultsParserUtil.readInputStream(inputStream));
		}
	}

	@Test
	public void testToJSONObject() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 7800);

		setUrlReaderOutput(String.valueOf(jsonObject), _URL, urlReaders);

		JSONObject readJSONObject = JenkinsResultsParserUtil.toJSONObject(
			_URL, false, _MAX_RETRIES, 0, 0);

		Assert.assertEquals(7800, readJSONObject.getInt("id"));

		verifyUrlReadCount(1, urlReaders, _URL);
	}

	/**
	 * The headline fix. Before the consolidation the outer catch replaced the
	 * cause with RuntimeException("Unable to create JSON object"), so no caller
	 * could tell a missing resource from a socket timeout.
	 */
	@Test
	public void testToJSONObjectWhenResponseCodeIs404() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderError(new FileNotFoundException(_URL), _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toJSONObject(
				_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (FileNotFoundException fileNotFoundException) {
			Assert.assertEquals(_URL, fileNotFoundException.getMessage());
		}

		verifyUrlReadCount(1, urlReaders, _URL);
	}

	@Test
	public void testToJSONObjectWhenResponseIsMalformed() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderOutput("not json at all", _URL, urlReaders);

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

		verifyUrlReadCount(_MAX_RETRIES + 1, urlReaders, _URL);
	}

	/**
	 * Resolving a client credentials authorization performs a token request, so
	 * a URL that cannot carry the header must not resolve one.
	 */
	@Test
	public void testToJSONObjectWhenURLIsFileAndAuthorizationIsClientCredentials()
		throws Exception {

		JenkinsMasterTestUtil.getJenkinsCohortProperties("test-9", 1);

		MockUrlReaders urlReaders = mockUrlReader();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 7800);

		setUrlReaderOutput(String.valueOf(jsonObject), _URL_FILE, urlReaders);

		JSONObject readJSONObject = JenkinsResultsParserUtil.toJSONObject(
			_URL_FILE,
			new JenkinsResultsParserUtil.ClientCredentialsHTTPAuthorization(
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new URL("https://test.liferay.com/o/oauth2/token")));

		Assert.assertEquals(7800, readJSONObject.getInt("id"));

		verifyUrlReadCount(0, urlReaders, "/o/oauth2/token");
	}

	@Test
	public void testToString() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderOutput(_STANDARD_OUT, _URL, urlReaders);

		Assert.assertEquals(
			_STANDARD_OUT, JenkinsResultsParserUtil.toString(_URL, false));
	}

	@Test
	public void testToStringWhenConnectionTimesOut() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderError(
			new SocketTimeoutException("Read timed out"), _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (SocketTimeoutException socketTimeoutException) {
		}

		verifyUrlReadCount(_MAX_RETRIES + 1, urlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseBodyIsEmpty() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderOutput("", _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toString(
				_URL, false, _MAX_RETRIES, 0, 0, true);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
			Assert.assertTrue(
				ioException.getMessage(),
				ioException.getMessage(
				).startsWith(
					"Unable to read a response body"
				));
		}

		verifyUrlReadCount(_MAX_RETRIES + 1, urlReaders, _URL);
	}

	/**
	 * A caller that does not expect a body still gets the empty string, so the
	 * empty-body rejection stays scoped to callers that asked for one.
	 */
	@Test
	public void testToStringWhenResponseBodyIsEmptyAndNotExpected()
		throws Exception {

		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderOutput("", _URL, urlReaders);

		Assert.assertEquals(
			"",
			JenkinsResultsParserUtil.toString(
				_URL, false, _MAX_RETRIES, 0, 0, false));

		verifyUrlReadCount(1, urlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseCodeIs403() throws Exception {
		_testResponseCodeIsRetried(403);
	}

	@Test
	public void testToStringWhenResponseCodeIs404() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		setUrlReaderError(new FileNotFoundException(_URL), _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (FileNotFoundException fileNotFoundException) {
		}

		verifyUrlReadCount(1, urlReaders, _URL);
	}

	@Test
	public void testToStringWhenResponseCodeIs429() throws Exception {
		_testResponseCodeIsRetried(429);
	}

	@Test
	public void testToStringWhenResponseCodeIs500() throws Exception {
		_testResponseCodeIsRetried(500);
	}

	/**
	 * A 4xx that carries no remedy fails the whole read on the first attempt,
	 * rather than spending the caller's retry budget on an answer that cannot
	 * change.
	 */
	@Test
	public void testToStringWhenResponseCodeIsTerminal() throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		_setResponseCodeError(400, _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
		}

		verifyUrlReadCount(1, urlReaders, _URL);
	}

	private void _setResponseCodeError(
			int responseCode, String url, MockUrlReaders mockUrlReaders)
		throws Exception {

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			Mockito.doAnswer(
				invocation -> {
					HttpURLConnection httpURLConnection = Mockito.mock(
						HttpURLConnection.class);

					Mockito.doThrow(
						new IOException(
							"Server returned HTTP response code: " +
								responseCode)
					).when(
						httpURLConnection
					).getInputStream();

					Mockito.doReturn(
						responseCode
					).when(
						httpURLConnection
					).getResponseCode();

					return httpURLConnection;
				}
			).when(
				urlReader
			).openURLConnection(
				Mockito.any(), Mockito.anyBoolean(), Mockito.any(),
				Mockito.any(), Mockito.anyBoolean(), Mockito.anyInt(),
				Mockito.argThat(
					readURL -> (readURL != null) && readURL.contains(url))
			);
		}
	}

	private void _testResponseCodeIsRetried(int responseCode) throws Exception {
		MockUrlReaders urlReaders = mockUrlReader();

		_setResponseCodeError(responseCode, _URL, urlReaders);

		try {
			JenkinsResultsParserUtil.toString(_URL, false, _MAX_RETRIES, 0, 0);

			Assert.fail("Expected an IOException to reach the caller");
		}
		catch (IOException ioException) {
		}

		verifyUrlReadCount(_MAX_RETRIES + 1, urlReaders, _URL);
	}

	private static final int _MAX_RETRIES = 2;

	private static final String _STANDARD_OUT = "Hello, World!\n";

	private static final String _URL = "http://test.liferay.com";

	private static final String _URL_FILE = "file:/tmp/queue-item.json";

}