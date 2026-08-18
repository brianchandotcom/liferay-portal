/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.lang.reflect.Method;

import java.net.HttpURLConnection;
import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

/**
 * @author Peter Yoo
 */
public class Test {

	@Before
	public void setUp() throws Exception {
		JenkinsResultsParserUtil.clearCache();
	}

	@After
	public void tearDown() {
		BuildDatabaseUtil.clearBuildDatabases();

		Environment.setInstance(new Environment());

		JenkinsMasterTestUtil.resetCaches();

		JenkinsResultsParserUtil.setBuildProperties(new Properties());

		JenkinsResultsParserUtil.setTopLevelJobNames(null);

		Map<String, Job> jobs = ReflectionTestUtil.getFieldValue(
			JobFactory.class, "_jobs");

		jobs.clear();

		Shell.setInstance(new Shell());

		JSONArrayUrlReader.setInstance(new JSONArrayUrlReader());

		JSONObjectUrlReader.setInstance(new JSONObjectUrlReader());

		StreamUrlReader.setInstance(new StreamUrlReader());

		TextUrlReader.setInstance(new TextUrlReader());
	}

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	protected static List<File> getDependenciesDirs(
		List<String> simpleClassNames) {

		List<File> dirs = new ArrayList<>(simpleClassNames.size());

		for (String simpleClassName : simpleClassNames) {
			dirs.add(
				new File("src/test/resources/dependencies/" + simpleClassName));
		}

		return dirs;
	}

	protected String getMismatchMessage(
		String expectedValue, String actualValue, String valueName) {

		return JenkinsResultsParserUtil.combine(
			"The expected ", valueName, " value ", expectedValue,
			", Did not match the actual ", valueName, " value ", actualValue,
			".");
	}

	protected List<String> getSimpleClassNames() {
		if (_simpleClassNames == null) {
			_simpleClassNames = new ArrayList<>();

			Class<?> clazz = getClass();

			String simpleName = clazz.getSimpleName();

			while (!simpleName.equals("Object")) {
				_simpleClassNames.add(simpleName);

				clazz = clazz.getSuperclass();

				simpleName = clazz.getSimpleName();
			}
		}

		return _simpleClassNames;
	}

	protected boolean hasCommand(
		Shell.ExecutionRequest executionRequest, String... substrings) {

		if (executionRequest == null) {
			return false;
		}

		String command = executionRequest.getCommands()[0];

		for (String substring : substrings) {
			if (!command.contains(substring)) {
				return false;
			}
		}

		return true;
	}

	protected Environment mockEnvironment(Map<String, String> environmentMap) {
		Environment environment = Mockito.mock(Environment.class);

		Mockito.doAnswer(
			invocation -> environmentMap.get(invocation.getArgument(0))
		).when(
			environment
		).doGet(
			Mockito.anyString()
		);

		Mockito.doReturn(
			environmentMap
		).when(
			environment
		).doGetAll();

		Environment.setInstance(environment);

		return environment;
	}

	protected Shell mockShell() {
		Shell shell = Mockito.mock(
			Shell.class,
			invocation -> {
				Shell.ExecutionRequest executionRequest =
					invocation.getArgument(0);

				throw new AssertionError(
					"No output set for shell command: " +
						Arrays.toString(executionRequest.getCommands()));
			});

		Shell.setInstance(shell);

		return shell;
	}

	/**
	 * Builds a connection whose body and response code the caller controls, so
	 * a stub sits below the retry loop rather than replacing it. Each
	 * invocation must produce a new connection, because the retry loop consumes
	 * the input stream once per attempt.
	 *
	 * <p>
	 * The overload without a body is how a 4xx or a 5xx arrives: the response
	 * code is legible while <code>getInputStream</code> throws.
	 * </p>
	 */
	protected HttpURLConnection mockURLConnection(int responseCode)
		throws IOException {

		HttpURLConnection httpURLConnection = Mockito.mock(
			HttpURLConnection.class);

		Mockito.doThrow(
			new IOException(
				"Server returned HTTP response code: " + responseCode)
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

	protected HttpURLConnection mockURLConnection(
			int responseCode, String standardOut)
		throws IOException {

		HttpURLConnection httpURLConnection = Mockito.mock(
			HttpURLConnection.class);

		Mockito.doReturn(
			new ByteArrayInputStream(standardOut.getBytes())
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

	protected MockUrlReaders mockUrlReaders() {
		JSONArrayUrlReader jsonArrayUrlReader = Mockito.spy(
			new JSONArrayUrlReader());
		JSONObjectUrlReader jsonObjectUrlReader = Mockito.spy(
			new JSONObjectUrlReader());
		StreamUrlReader streamUrlReader = Mockito.spy(new StreamUrlReader());
		TextUrlReader textUrlReader = Mockito.spy(new TextUrlReader());

		JSONArrayUrlReader.setInstance(jsonArrayUrlReader);
		JSONObjectUrlReader.setInstance(jsonObjectUrlReader);
		StreamUrlReader.setInstance(streamUrlReader);
		TextUrlReader.setInstance(textUrlReader);

		MockUrlReaders mockUrlReaders = new MockUrlReaders(
			jsonArrayUrlReader, jsonObjectUrlReader, streamUrlReader,
			textUrlReader);

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			try {
				Mockito.doAnswer(
					invocation -> {
						String url = invocation.getArgument(6);

						throw new AssertionError(
							"No output set for URL: " + url);
					}
				).when(
					urlReader
				).openURLConnection(
					Mockito.any(), Mockito.anyBoolean(), Mockito.any(),
					Mockito.any(), Mockito.anyBoolean(), Mockito.anyInt(),
					Mockito.any()
				);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}

			// A retried read would otherwise sleep out the caller's retry
			// period for real, which is minutes across a suite that exercises
			// failure paths.

			Mockito.doNothing(
			).when(
				urlReader
			).sleep(
				Mockito.anyLong()
			);
		}

		return mockUrlReaders;
	}

	protected String read(File file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file.toURI())));
	}

	protected String read(File dir, String fileName) throws IOException {
		return read(new File(dir, fileName));
	}

	protected void setShellCommandOutput(
			String command, Shell shell, String standardOut)
		throws Exception {

		Mockito.doReturn(
			new Shell.ExecutionResult(0, "", standardOut)
		).when(
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest -> hasCommand(executionRequest, command))
		);
	}

	/**
	 * Fails the attempt with a response code the retry policy can classify,
	 * rather than a bare transport failure.
	 */
	protected void setUrlReaderError(
			int responseCode, String url, MockUrlReaders mockUrlReaders)
		throws Exception {

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			Mockito.doAnswer(
				invocation -> mockURLConnection(responseCode)
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

	/**
	 * Fails the attempt rather than the whole read, so the retry loop still
	 * runs and a test can tell a terminal failure from a retried one.
	 */
	protected void setUrlReaderError(
			IOException ioException, String url, MockUrlReaders mockUrlReaders)
		throws Exception {

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			Mockito.doThrow(
				ioException
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

	protected void setUrlReaderOutput(
			String standardOut, String url, MockUrlReaders mockUrlReaders)
		throws Exception {

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			Mockito.doAnswer(
				invocation -> mockURLConnection(200, standardOut)
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

	protected void testEquals(Object expected, Object actual) {
		errorCollector.checkThat(actual, CoreMatchers.equalTo(expected));
	}

	protected void testSame(Object expected, Object actual) {
		errorCollector.checkThat(actual, CoreMatchers.sameInstance(expected));
	}

	protected String toURLString(File file) throws Exception {
		URI uri = file.toURI();

		String urlString = String.valueOf(uri.toURL());

		File dependenciesDir = dependenciesDirs.get(0);

		String path = dependenciesDir.getPath();

		int x =
			path.indexOf("src/test/resources/dependencies/") +
				"src/test/resources/dependencies/".length();

		path = path.substring(x);

		return urlString.replace(
			"file:" +
				JenkinsResultsParserUtil.getCanonicalPath(dependenciesDir),
			"${dependencies.url}/" + path);
	}

	/**
	 * Counts attempts rather than logical reads, and counts them across every
	 * reader type, so a test stays correct when a call is rerouted from one
	 * reader to another. A retried read counts once per attempt, which is what
	 * makes the retry policy assertable.
	 */
	protected void verifyUrlReadAttemptCount(
		int expectedCount, MockUrlReaders mockUrlReaders, String url) {

		int count = 0;

		for (UrlReader<?> urlReader : mockUrlReaders.getUrlReaders()) {
			MockingDetails mockingDetails = Mockito.mockingDetails(urlReader);

			for (Invocation invocation : mockingDetails.getInvocations()) {
				Method method = invocation.getMethod();

				if (!method.equals(_openURLConnectionMethod)) {
					continue;
				}

				String readURL = invocation.getArgument(6);

				if ((readURL != null) && readURL.contains(url)) {
					count++;
				}
			}
		}

		testEquals(expectedCount, count);
	}

	protected List<File> dependenciesDirs = getDependenciesDirs(
		getSimpleClassNames());

	/**
	 * Resolved once so that renaming the seam fails loudly here, rather than
	 * silently matching nothing and letting every attempt count pass at zero.
	 */
	private static Method _getOpenURLConnectionMethod() {
		try {
			return UrlReader.class.getDeclaredMethod(
				"openURLConnection", String.class, boolean.class,
				JenkinsResultsParserUtil.HttpRequestMethod.class, String.class,
				boolean.class, int.class, String.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new ExceptionInInitializerError(noSuchMethodException);
		}
	}

	private static final Method _openURLConnectionMethod =
		_getOpenURLConnectionMethod();

	private List<String> _simpleClassNames;

}