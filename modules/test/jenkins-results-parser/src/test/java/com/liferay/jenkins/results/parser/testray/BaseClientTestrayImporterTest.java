/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;
import com.liferay.poshi.core.util.GetterUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.rules.TemporaryFolder;

import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class BaseClientTestrayImporterTest
	extends com.liferay.jenkins.results.parser.Test {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_environmentMap.put(
			"testrayCloudBucketName", RandomTestUtil.randomString());
		_environmentMap.put(
			"testrayOAuth2ClientId", RandomTestUtil.randomString());
		_environmentMap.put(
			"testrayOAuth2ClientSecret", RandomTestUtil.randomString());

		mockEnvironment(_environmentMap);

		Mockito.doReturn(
			new URL("https://" + RandomTestUtil.randomLowerCaseString())
		).when(
			_testrayServer
		).getURL();

		_testrayCloudBucketMockedStatic = Mockito.mockStatic(
			TestrayCloudBucket.class);

		_testrayCloudBucketMockedStatic.when(
			() -> TestrayCloudBucket.getInstance(Mockito.anyString())
		).thenReturn(
			_testrayCloudBucket
		);

		_testrayCloudBucketMockedStatic.when(
			() -> TestrayCloudBucket.hasGoogleApplicationCredentials(
				Mockito.anyString())
		).thenReturn(
			true
		);

		_testrayFactoryMockedStatic = Mockito.mockStatic(TestrayFactory.class);

		_testrayFactoryMockedStatic.when(
			() -> TestrayFactory.newTestrayServer(Mockito.anyString())
		).thenReturn(
			_testrayServer
		);
	}

	@After
	@Override
	public void tearDown() {
		_testrayCloudBucketMockedStatic.close();
		_testrayFactoryMockedStatic.close();

		super.tearDown();
	}

	@Test
	public void testConstructorGoogleApplicationCredentials() {
		String testrayCloudBucketName = _environmentMap.get(
			"testrayCloudBucketName");

		_testrayCloudBucketMockedStatic.when(
			() -> TestrayCloudBucket.hasGoogleApplicationCredentials(
				testrayCloudBucketName)
		).thenReturn(
			false
		);

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class, this::_newBaseClientTestrayImporter);

		testEquals(
			"Testray 2 requires \"GOOGLE_APPLICATION_CREDENTIALS\" to point " +
				"to a credential with access to " + testrayCloudBucketName,
			runtimeException.getMessage());

		_testrayCloudBucketMockedStatic.verify(
			() -> TestrayCloudBucket.getInstance(Mockito.anyString()),
			Mockito.never());
		_testrayFactoryMockedStatic.verify(
			() -> TestrayFactory.newTestrayServer(Mockito.anyString()),
			Mockito.never());
	}

	@Test
	public void testConstructorOAuth2() throws Exception {
		_testConstructorOAuth2(
			"testrayOAuth2ClientId", "",
			() -> TestrayResultsXMLClientTestrayImporter.main(new String[0]));
		_testConstructorOAuth2(
			"testrayOAuth2ClientId", null,
			() -> DXPCloudClientTestrayImporter.main(new String[0]));
		_testConstructorOAuth2(
			"testrayOAuth2ClientSecret", "",
			this::_newBaseClientTestrayImporter);
		_testConstructorOAuth2(
			"testrayOAuth2ClientSecret", null,
			this::_newBaseClientTestrayImporter);
	}

	@Test
	public void testConstructorTestrayCloudBucketName() {
		_testConstructorTestrayCloudBucketName(
			TestrayCloudBucket.DEFAULT_BUCKET_NAME, null, null);

		String testrayCloudBucketName = RandomTestUtil.randomString();
		String testrayS3BucketName = RandomTestUtil.randomString();

		_testConstructorTestrayCloudBucketName(
			testrayCloudBucketName, testrayCloudBucketName,
			testrayS3BucketName);
		_testConstructorTestrayCloudBucketName(
			testrayS3BucketName, null, testrayS3BucketName);
	}

	@Test
	public void testGetEnvVarValue() {
		String environmentValue = RandomTestUtil.randomString();
		String systemPropertyValue = RandomTestUtil.randomString();

		_testGetEnvVarValue("", systemPropertyValue, systemPropertyValue);
		_testGetEnvVarValue(
			environmentValue, environmentValue, systemPropertyValue);
		_testGetEnvVarValue(null, systemPropertyValue, systemPropertyValue);

		_testGetEnvVarValue(null, null, null);
	}

	@Test
	public void testGetTestrayBuild() throws Exception {
		_testGetTestrayBuild(RandomTestUtil.randomSHA());
		_testGetTestrayBuild(null);
	}

	@Test
	public void testGetTestrayBuildMissingProject() {
		String testrayServerURL =
			"https://" + RandomTestUtil.randomLowerCaseString();

		_environmentMap.put("testrayServerURL", testrayServerURL);

		String testrayProjectName = RandomTestUtil.randomString();

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class,
			() -> baseClientTestrayImporter.getTestrayBuild(
				new Date(), RandomTestUtil.randomString(),
				RandomTestUtil.randomSHA(), RandomTestUtil.randomString(),
				testrayProjectName, RandomTestUtil.randomString()));

		testEquals(
			JenkinsResultsParserUtil.combine(
				"Unable to find Testray project ", testrayProjectName, " on ",
				testrayServerURL),
			runtimeException.getMessage());
	}

	@Test
	public void testGetTestrayServer() throws Exception {
		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"testray.cloud.bucket", RandomTestUtil.randomString());
		buildProperties.setProperty(
			"testray.oauth2.client.id", RandomTestUtil.randomString());
		buildProperties.setProperty(
			"testray.oauth2.client.secret", RandomTestUtil.randomString());

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		_testGetTestrayServer("https://testray.liferay.com", null);

		String testrayServerURL =
			"https://" + RandomTestUtil.randomLowerCaseString();

		_testGetTestrayServer(testrayServerURL, testrayServerURL);
	}

	@Test
	public void testUploadAttachment() throws Exception {
		String testrayCloudBaseURL =
			"https://" + RandomTestUtil.randomLowerCaseString();

		Mockito.doReturn(
			testrayCloudBaseURL
		).when(
			_testrayCloudBucket
		).getTestrayCloudBaseURL();

		File file = temporaryFolder.newFile();
		String key = RandomTestUtil.randomLowerCaseString();

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testEquals(
			JenkinsResultsParserUtil.combine(
				testrayCloudBaseURL, "/", key, "?authuser=0"),
			baseClientTestrayImporter.uploadAttachment(file, key));

		Mockito.verify(
			_testrayCloudBucket
		).createTestrayCloudObject(
			key, file
		);
	}

	@Test
	public void testUploadResults() throws Exception {
		File resultsDir = temporaryFolder.newFolder();

		List<File> resultsFiles = Arrays.asList(
			_newResultsFile(resultsDir), _newResultsFile(resultsDir));

		File workingTestrayResultsDir = new File("testray-results");

		Assert.assertFalse(workingTestrayResultsDir.exists());

		File workingTestrayResultsFile = new File(
			workingTestrayResultsDir, RandomTestUtil.randomString());

		try {
			JenkinsResultsParserUtil.write(
				workingTestrayResultsFile, RandomTestUtil.randomString());

			long testrayBuildId = RandomTestUtil.randomLong();

			TestrayBuild testrayBuild = Mockito.mock(TestrayBuild.class);

			Mockito.doReturn(
				testrayBuildId
			).when(
				testrayBuild
			).getId();

			List<File> resultsTarGzFiles = new ArrayList<>();
			Map<String, byte[]> resultsTarGzFileContents = new HashMap<>();

			Mockito.doAnswer(
				invocation -> {
					File resultsTarGzFile = invocation.getArgument(1);

					resultsTarGzFiles.add(resultsTarGzFile);

					resultsTarGzFileContents.putAll(
						_readTarGzip(resultsTarGzFile));

					return null;
				}
			).when(
				_testrayCloudBucket
			).createTestrayCloudObject(
				Mockito.anyString(), Mockito.any(File.class)
			);

			long startTimeMillis = System.currentTimeMillis();

			BaseClientTestrayImporter baseClientTestrayImporter =
				_newBaseClientTestrayImporter();

			baseClientTestrayImporter.uploadResults(resultsFiles, testrayBuild);

			long endTimeMillis = System.currentTimeMillis();

			ArgumentCaptor<String> keyArgumentCaptor = ArgumentCaptor.forClass(
				String.class);

			Mockito.verify(
				_testrayCloudBucket
			).createTestrayCloudObject(
				keyArgumentCaptor.capture(), Mockito.any(File.class)
			);

			Matcher matcher = _inboxKeyPattern.matcher(
				keyArgumentCaptor.getValue());

			Assert.assertTrue(matcher.matches());

			long millis = GetterUtil.getLong(matcher.group("millis"));

			Assert.assertTrue(
				(millis >= startTimeMillis) && (millis <= endTimeMillis));

			testEquals(
				String.valueOf(testrayBuildId),
				matcher.group("testrayBuildId"));

			testEquals(resultsFiles.size(), resultsTarGzFileContents.size());

			for (File resultsFile : resultsFiles) {
				Assert.assertArrayEquals(
					Files.readAllBytes(resultsFile.toPath()),
					resultsTarGzFileContents.get(resultsFile.getName()));
			}

			Assert.assertTrue(workingTestrayResultsFile.exists());

			_assertDeleted(resultsTarGzFiles.get(0));
		}
		finally {
			JenkinsResultsParserUtil.delete(workingTestrayResultsDir);
		}
	}

	@Test
	public void testUploadResultsFailure() throws Exception {
		File resultsFile = _newResultsFile(temporaryFolder.getRoot());

		List<File> resultsTarGzFiles = new ArrayList<>();

		RuntimeException runtimeException = new RuntimeException();

		Mockito.doAnswer(
			invocation -> {
				resultsTarGzFiles.add(invocation.getArgument(1));

				throw runtimeException;
			}
		).when(
			_testrayCloudBucket
		).createTestrayCloudObject(
			Mockito.anyString(), Mockito.any(File.class)
		);

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testSame(
			runtimeException,
			Assert.assertThrows(
				RuntimeException.class,
				() -> baseClientTestrayImporter.uploadResults(
					Arrays.asList(resultsFile),
					Mockito.mock(TestrayBuild.class))));

		_assertDeleted(resultsTarGzFiles.get(0));

		Assert.assertTrue(resultsFile.exists());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _assertDeleted(File resultsTarGzFile) {
		Assert.assertFalse(resultsTarGzFile.exists());

		File absoluteResultsTarGzFile = resultsTarGzFile.getAbsoluteFile();

		File tempDir = absoluteResultsTarGzFile.getParentFile();

		Assert.assertFalse(tempDir.exists());
	}

	private BaseClientTestrayImporter _newBaseClientTestrayImporter() {
		return new BaseClientTestrayImporter() {

			@Override
			public void importResults() {
			}

		};
	}

	private File _newResultsFile(File dir) throws Exception {
		File file = new File(dir, RandomTestUtil.randomString() + ".xml");

		String resultsXML =
			"<testsuite><metrics><metric name=\"" +
				RandomTestUtil.randomString() +
					"\" value=\"é\"/></metrics></testsuite>";

		Files.write(file.toPath(), resultsXML.getBytes(StandardCharsets.UTF_8));

		return file;
	}

	private Map<String, byte[]> _readTarGzip(File file) throws IOException {
		Map<String, byte[]> fileContents = new HashMap<>();

		try (TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(
					new GzipCompressorInputStream(
						new BufferedInputStream(new FileInputStream(file))))) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry = tarArchiveInputStream.getNextEntry()) !=
						null) {

				fileContents.put(
					tarArchiveEntry.getName(),
					IOUtils.toByteArray(tarArchiveInputStream));
			}
		}

		return fileContents;
	}

	private void _testConstructorOAuth2(
			String environmentVariableName, String environmentVariableValue,
			ThrowingRunnable throwingRunnable)
		throws Exception {

		String originalValue = _environmentMap.remove(environmentVariableName);

		if (environmentVariableValue != null) {
			_environmentMap.put(
				environmentVariableName, environmentVariableValue);
		}

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class, throwingRunnable);

		testEquals(
			"Testray 2 requires \"testrayOAuth2ClientId\" and " +
				"\"testrayOAuth2ClientSecret\" to be set",
			runtimeException.getMessage());

		_testrayCloudBucketMockedStatic.verify(
			() -> TestrayCloudBucket.hasGoogleApplicationCredentials(
				Mockito.anyString()),
			Mockito.never());
		_testrayFactoryMockedStatic.verify(
			() -> TestrayFactory.newTestrayServer(Mockito.anyString()),
			Mockito.never());

		_environmentMap.put(environmentVariableName, originalValue);
	}

	private void _testConstructorTestrayCloudBucketName(
		String expectedTestrayCloudBucketName, String testrayCloudBucketName,
		String testrayS3BucketName) {

		_environmentMap.remove("testrayCloudBucketName");
		_environmentMap.remove("testrayS3BucketName");

		if (testrayCloudBucketName != null) {
			_environmentMap.put(
				"testrayCloudBucketName", testrayCloudBucketName);
		}

		if (testrayS3BucketName != null) {
			_environmentMap.put("testrayS3BucketName", testrayS3BucketName);
		}

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testSame(
			_testrayCloudBucket,
			baseClientTestrayImporter.getTestrayCloudBucket());

		_testrayCloudBucketMockedStatic.verify(
			() -> TestrayCloudBucket.getInstance(
				expectedTestrayCloudBucketName));
		_testrayCloudBucketMockedStatic.verify(
			() -> TestrayCloudBucket.hasGoogleApplicationCredentials(
				expectedTestrayCloudBucketName));
	}

	private void _testGetEnvVarValue(
		String environmentValue, String expectedValue,
		String systemPropertyValue) {

		String varName = RandomTestUtil.randomString();

		if (environmentValue != null) {
			_environmentMap.put(varName, environmentValue);
		}

		if (systemPropertyValue != null) {
			System.setProperty(varName, systemPropertyValue);
		}

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testEquals(
			expectedValue, baseClientTestrayImporter.getEnvVarValue(varName));

		System.clearProperty(varName);
	}

	private void _testGetTestrayBuild(String testrayBuildSHA) {
		TestrayProject testrayProject = Mockito.mock(TestrayProject.class);

		TestrayProductVersion testrayProductVersion = Mockito.mock(
			TestrayProductVersion.class);
		String testrayProductVersionName = RandomTestUtil.randomString();

		Mockito.doReturn(
			testrayProductVersion
		).when(
			testrayProject
		).createTestrayProductVersion(
			testrayProductVersionName
		);

		TestrayRoutine testrayRoutine = Mockito.mock(TestrayRoutine.class);
		String testrayRoutineName = RandomTestUtil.randomString();

		Mockito.doReturn(
			testrayRoutine
		).when(
			testrayProject
		).createTestrayRoutine(
			testrayRoutineName
		);

		String testrayProjectName = RandomTestUtil.randomString();

		Mockito.doReturn(
			testrayProject
		).when(
			_testrayServer
		).getTestrayProjectByName(
			testrayProjectName
		);

		TestrayBuild testrayBuild = Mockito.mock(TestrayBuild.class);
		Date testrayBuildDate = new Date(RandomTestUtil.randomLong());
		String testrayBuildName = RandomTestUtil.randomString();

		Mockito.doReturn(
			testrayBuild
		).when(
			testrayRoutine
		).createTestrayBuild(
			testrayProductVersion, testrayBuildName, testrayBuildDate, null,
			testrayBuildSHA
		);

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testSame(
			testrayBuild,
			baseClientTestrayImporter.getTestrayBuild(
				testrayBuildDate, testrayBuildName, testrayBuildSHA,
				testrayProductVersionName, testrayProjectName,
				testrayRoutineName));
	}

	private void _testGetTestrayServer(
			String expectedTestrayServerURL, String testrayServerURL)
		throws Exception {

		_environmentMap.remove("testrayServerURL");

		if (testrayServerURL != null) {
			_environmentMap.put("testrayServerURL", testrayServerURL);
		}

		TestrayServer testrayServer = Mockito.mock(TestrayServer.class);

		Mockito.doReturn(
			new URL(expectedTestrayServerURL)
		).when(
			testrayServer
		).getURL();

		_testrayFactoryMockedStatic.when(
			() -> TestrayFactory.newTestrayServer(expectedTestrayServerURL)
		).thenReturn(
			testrayServer
		);

		BaseClientTestrayImporter baseClientTestrayImporter =
			_newBaseClientTestrayImporter();

		testSame(testrayServer, baseClientTestrayImporter.getTestrayServer());
		testSame(testrayServer, baseClientTestrayImporter.getTestrayServer());

		_testrayFactoryMockedStatic.verify(
			() -> TestrayFactory.newTestrayServer(expectedTestrayServerURL));

		_testrayCloudBucketMockedStatic.verify(
			() -> TestrayCloudBucket.getInstance(
				_environmentMap.get("testrayCloudBucketName")),
			Mockito.atLeastOnce());

		ArgumentCaptor<JenkinsResultsParserUtil.HTTPAuthorization>
			httpAuthorizationArgumentCaptor = ArgumentCaptor.forClass(
				JenkinsResultsParserUtil.HTTPAuthorization.class);

		Mockito.verify(
			testrayServer
		).setCommunicationFailureNotificationEnabled(
			false
		);

		Mockito.verify(
			testrayServer
		).setHTTPAuthorization(
			httpAuthorizationArgumentCaptor.capture()
		);

		JenkinsResultsParserUtil.HTTPAuthorization httpAuthorization =
			httpAuthorizationArgumentCaptor.getValue();

		testEquals(
			_environmentMap.get("testrayOAuth2ClientId"),
			ReflectionTestUtil.getFieldValue(httpAuthorization, "_clientId"));
		testEquals(
			_environmentMap.get("testrayOAuth2ClientSecret"),
			ReflectionTestUtil.getFieldValue(
				httpAuthorization, "_clientSecret"));
		testEquals(
			new URL(expectedTestrayServerURL + "/o/oauth2/token"),
			ReflectionTestUtil.getFieldValue(httpAuthorization, "_tokenURL"));
	}

	private static final Pattern _inboxKeyPattern = Pattern.compile(
		"inbox/(?<millis>\\d+)-(?<testrayBuildId>-?\\d+)-results\\.tar\\.gz");

	private final Map<String, String> _environmentMap = new HashMap<>();
	private final TestrayCloudBucket _testrayCloudBucket = Mockito.mock(
		TestrayCloudBucket.class);
	private MockedStatic<TestrayCloudBucket> _testrayCloudBucketMockedStatic;
	private MockedStatic<TestrayFactory> _testrayFactoryMockedStatic;
	private final TestrayServer _testrayServer = Mockito.mock(
		TestrayServer.class);

}