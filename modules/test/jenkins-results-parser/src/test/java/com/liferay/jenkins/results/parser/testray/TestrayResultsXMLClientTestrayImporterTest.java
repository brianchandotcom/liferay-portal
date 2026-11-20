/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.time.Instant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class TestrayResultsXMLClientTestrayImporterTest
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

		Mockito.doAnswer(
			invocation -> {
				_resultsTarGzFileContents.putAll(
					_readTarGzip(invocation.getArgument(1)));

				return null;
			}
		).when(
			_testrayCloudBucket
		).createTestrayCloudObject(
			Mockito.anyString(), Mockito.any(File.class)
		);

		Mockito.doReturn(
			_testrayProductVersion
		).when(
			_testrayProject
		).createTestrayProductVersion(
			Mockito.anyString()
		);

		Mockito.doReturn(
			_testrayRoutine
		).when(
			_testrayProject
		).createTestrayRoutine(
			Mockito.anyString()
		);

		Mockito.doReturn(
			_testrayBuild
		).when(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.any(TestrayProductVersion.class), Mockito.anyString(),
			Mockito.any(Date.class), Mockito.isNull(), Mockito.any()
		);

		Mockito.doReturn(
			_testrayProject
		).when(
			_testrayServer
		).getTestrayProjectByName(
			Mockito.anyString()
		);

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
	public void testConstructor() throws Exception {
		File missingDir = new File(
			temporaryFolder.getRoot(), RandomTestUtil.randomString());

		_testConstructor(
			"Unable to find directory " + missingDir.getPath(),
			missingDir.getPath());

		File file = temporaryFolder.newFile();

		_testConstructor(
			"Unable to find directory " + file.getPath(), file.getPath());

		_testConstructor("\"testrayResultsDir\" is not set", "");
		_testConstructor("\"testrayResultsDir\" is not set", null);
	}

	@Test
	public void testImportResults() throws Exception {
		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		File resultsFile1 = new File(testrayResultsDir, "a.xml");

		Map<String, String> testSuiteProperties1 = _newTestSuiteProperties();

		_writeResultsFile(resultsFile1, testSuiteProperties1);

		File resultsFile2 = new File(testrayResultsDir, "b/c.xml");
		Map<String, String> testSuiteProperties2 = _newTestSuiteProperties();

		_writeResultsFile(resultsFile2, testSuiteProperties2);

		String randomString = RandomTestUtil.randomString();

		Files.write(
			new File(
				testrayResultsDir, RandomTestUtil.randomString() + ".txt"
			).toPath(),
			randomString.getBytes(StandardCharsets.UTF_8));

		TestrayResultsXMLClientTestrayImporter.main(new String[0]);

		InOrder inOrder = Mockito.inOrder(_testrayRoutine);

		_verifyGetTestrayBuild(inOrder, testSuiteProperties1);
		_verifyGetTestrayBuild(inOrder, testSuiteProperties2);

		testEquals(2, _resultsTarGzFileContents.size());

		Assert.assertArrayEquals(
			Files.readAllBytes(resultsFile1.toPath()),
			_resultsTarGzFileContents.get(resultsFile1.getName()));
		Assert.assertArrayEquals(
			Files.readAllBytes(resultsFile2.toPath()),
			_resultsTarGzFileContents.get(resultsFile2.getName()));
	}

	@Test
	public void testImportResultsBuildDate() throws Exception {
		TimeZone timeZone = TimeZone.getDefault();

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		try {
			_testImportResultsBuildDate(
				Date.from(Instant.parse("2026-01-15T18:00:00Z")),
				"2026-01-15 10:00:00");
			_testImportResultsBuildDate(
				Date.from(Instant.parse("2026-07-15T17:00:00Z")),
				"2026-07-15 10:00:00");
		}
		finally {
			TimeZone.setDefault(timeZone);
		}
	}

	@Test
	public void testImportResultsFailure() throws Exception {
		_testImportResultsFailure("<testsuite>");
		_testImportResultsFailure("<testsuite><testcase/></testsuite>");
		_testImportResultsFailureBuildDate(
			"2026-01-15 10:00:00" + RandomTestUtil.randomString());
		_testImportResultsFailureBuildDate("2026-02-30 10:00:00");
		_testImportResultsFailureBuildDate("2026/01/15 10:00:00");
		_testImportResultsFailureProperty("testray.build.date");
		_testImportResultsFailureProperty("testray.build.name");
		_testImportResultsFailureProperty("testray.build.type");
		_testImportResultsFailureProperty("testray.product.version");
		_testImportResultsFailureProperty("testray.project.name");
	}

	@Test
	public void testImportResultsGitId() throws Exception {
		_testImportResultsGitId("");
		_testImportResultsGitId(null);
	}

	@Test
	public void testImportResultsNoResultsFiles() throws Exception {
		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		String randomString = RandomTestUtil.randomString();

		Files.write(
			new File(
				testrayResultsDir, RandomTestUtil.randomString() + ".txt"
			).toPath(),
			randomString.getBytes(StandardCharsets.UTF_8));

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class,
			() -> TestrayResultsXMLClientTestrayImporter.main(new String[0]));

		testEquals(
			"Unable to find Testray results XML files in " +
				testrayResultsDir.getPath(),
			runtimeException.getMessage());

		_testrayFactoryMockedStatic.verify(
			() -> TestrayFactory.newTestrayServer(Mockito.anyString()),
			Mockito.never());
	}

	@Test
	public void testImportResultsTwice() throws Exception {
		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		_writeResultsFile(
			new File(testrayResultsDir, RandomTestUtil.randomString() + ".xml"),
			testSuiteProperties);

		TestrayResultsXMLClientTestrayImporter.main(new String[0]);
		TestrayResultsXMLClientTestrayImporter.main(new String[0]);

		Mockito.verify(
			_testrayRoutine, Mockito.times(2)
		).createTestrayBuild(
			_testrayProductVersion,
			testSuiteProperties.get("testray.build.name"),
			Date.from(Instant.parse("2026-01-15T18:00:00Z")), null,
			testSuiteProperties.get("git.id")
		);

		Mockito.verify(
			_testrayProject, Mockito.times(2)
		).createTestrayProductVersion(
			testSuiteProperties.get("testray.product.version")
		);

		Mockito.verify(
			_testrayProject, Mockito.times(2)
		).createTestrayRoutine(
			testSuiteProperties.get("testray.build.type")
		);

		Mockito.verify(
			_testrayServer, Mockito.times(2)
		).getTestrayProjectByName(
			testSuiteProperties.get("testray.project.name")
		);
	}

	@Test
	public void testImportResultsUploadFailure() throws Exception {
		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		File resultsFile = new File(
			testrayResultsDir, RandomTestUtil.randomString() + ".xml");

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		_writeResultsFile(resultsFile, testSuiteProperties);

		Mockito.doThrow(
			new RuntimeException()
		).when(
			_testrayCloudBucket
		).createTestrayCloudObject(
			Mockito.anyString(), Mockito.any(File.class)
		);

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class,
			() -> TestrayResultsXMLClientTestrayImporter.main(new String[0]));

		testEquals(
			"Unable to import [" + resultsFile.getPath() + "]",
			runtimeException.getMessage());

		Mockito.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.eq(_testrayProductVersion),
			Mockito.eq(testSuiteProperties.get("testray.build.name")),
			Mockito.any(Date.class), Mockito.isNull(),
			Mockito.eq(testSuiteProperties.get("git.id"))
		);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private Map<String, String> _newTestSuiteProperties() {
		Map<String, String> testSuiteProperties = new TreeMap<>();

		testSuiteProperties.put("git.id", RandomTestUtil.randomSHA());
		testSuiteProperties.put("testray.build.date", "2026-01-15 10:00:00");
		testSuiteProperties.put(
			"testray.build.name", RandomTestUtil.randomString());
		testSuiteProperties.put(
			"testray.build.type", RandomTestUtil.randomString());
		testSuiteProperties.put(
			"testray.product.version", RandomTestUtil.randomString());
		testSuiteProperties.put(
			"testray.project.name", RandomTestUtil.randomString());

		return testSuiteProperties;
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

	private void _testConstructor(
		String expectedMessage, String testrayResultsDirPath) {

		_environmentMap.remove("testrayResultsDir");

		if (testrayResultsDirPath != null) {
			_environmentMap.put("testrayResultsDir", testrayResultsDirPath);
		}

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class,
			() -> TestrayResultsXMLClientTestrayImporter.main(new String[0]));

		testEquals(expectedMessage, runtimeException.getMessage());
	}

	private void _testImportResultsBuildDate(
			Date expectedTestrayBuildDate, String testrayBuildDateString)
		throws Exception {

		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		testSuiteProperties.put("testray.build.date", testrayBuildDateString);

		_writeResultsFile(
			new File(testrayResultsDir, RandomTestUtil.randomString() + ".xml"),
			testSuiteProperties);

		TestrayResultsXMLClientTestrayImporter.main(new String[0]);

		Mockito.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.eq(_testrayProductVersion), Mockito.anyString(),
			Mockito.eq(expectedTestrayBuildDate), Mockito.isNull(),
			Mockito.anyString()
		);
	}

	private void _testImportResultsFailure(String resultsXML) throws Exception {
		Mockito.clearInvocations(_testrayRoutine);

		_resultsTarGzFileContents.clear();

		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		File invalidResultsFile = new File(testrayResultsDir, "a.xml");

		Files.write(
			invalidResultsFile.toPath(),
			resultsXML.getBytes(StandardCharsets.UTF_8));

		File validResultsFile = new File(testrayResultsDir, "b.xml");
		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		_writeResultsFile(validResultsFile, testSuiteProperties);

		RuntimeException runtimeException = Assert.assertThrows(
			RuntimeException.class,
			() -> TestrayResultsXMLClientTestrayImporter.main(new String[0]));

		testEquals(
			"Unable to import [" + invalidResultsFile.getPath() + "]",
			runtimeException.getMessage());

		Mockito.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.any(TestrayProductVersion.class), Mockito.anyString(),
			Mockito.any(Date.class), Mockito.isNull(), Mockito.any()
		);

		Mockito.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.eq(_testrayProductVersion),
			Mockito.eq(testSuiteProperties.get("testray.build.name")),
			Mockito.any(Date.class), Mockito.isNull(),
			Mockito.eq(testSuiteProperties.get("git.id"))
		);

		testEquals(
			Arrays.asList(validResultsFile.getName()),
			new ArrayList<>(_resultsTarGzFileContents.keySet()));
	}

	private void _testImportResultsFailureBuildDate(
			String testrayBuildDateString)
		throws Exception {

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		testSuiteProperties.put("testray.build.date", testrayBuildDateString);

		_testImportResultsFailure(_toResultsXML(testSuiteProperties));
	}

	private void _testImportResultsFailureProperty(String propertyName)
		throws Exception {

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		testSuiteProperties.remove(propertyName);

		_testImportResultsFailure(_toResultsXML(testSuiteProperties));

		testSuiteProperties.put(propertyName, "");

		_testImportResultsFailure(_toResultsXML(testSuiteProperties));
	}

	private void _testImportResultsGitId(String gitId) throws Exception {
		File testrayResultsDir = temporaryFolder.newFolder();

		_environmentMap.put("testrayResultsDir", testrayResultsDir.getPath());

		Map<String, String> testSuiteProperties = _newTestSuiteProperties();

		testSuiteProperties.remove("git.id");

		if (gitId != null) {
			testSuiteProperties.put("git.id", gitId);
		}

		_writeResultsFile(
			new File(testrayResultsDir, RandomTestUtil.randomString() + ".xml"),
			testSuiteProperties);

		TestrayResultsXMLClientTestrayImporter.main(new String[0]);

		Mockito.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.eq(_testrayProductVersion),
			Mockito.eq(testSuiteProperties.get("testray.build.name")),
			Mockito.any(Date.class), Mockito.isNull(), Mockito.isNull()
		);
	}

	private String _toResultsXML(Map<String, String> testSuiteProperties) {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<testsuite>\n");
		sb.append("  <properties>\n");

		for (Map.Entry<String, String> entry : testSuiteProperties.entrySet()) {
			sb.append("    <property name=\"");
			sb.append(entry.getKey());
			sb.append("\" value=\"");
			sb.append(entry.getValue());
			sb.append("\"/>\n");
		}

		sb.append("  </properties>\n  <testcase>\n    <metrics>\n");
		sb.append("      <metric name=\"");
		sb.append(RandomTestUtil.randomString());
		sb.append("\" unit=\"");
		sb.append(RandomTestUtil.randomString());
		sb.append("\" value=\"");
		sb.append(RandomTestUtil.randomInt());
		sb.append("\"/>\n    </metrics>\n  </testcase>\n</testsuite>\n");

		return sb.toString();
	}

	private void _verifyGetTestrayBuild(
		InOrder inOrder, Map<String, String> testSuiteProperties) {

		Mockito.verify(
			_testrayProject
		).createTestrayProductVersion(
			testSuiteProperties.get("testray.product.version")
		);

		Mockito.verify(
			_testrayProject
		).createTestrayRoutine(
			testSuiteProperties.get("testray.build.type")
		);

		Mockito.verify(
			_testrayServer
		).getTestrayProjectByName(
			testSuiteProperties.get("testray.project.name")
		);

		inOrder.verify(
			_testrayRoutine
		).createTestrayBuild(
			Mockito.eq(_testrayProductVersion),
			Mockito.eq(testSuiteProperties.get("testray.build.name")),
			Mockito.any(Date.class), Mockito.isNull(),
			Mockito.eq(testSuiteProperties.get("git.id"))
		);
	}

	private void _writeResultsFile(
			File file, Map<String, String> testSuiteProperties)
		throws Exception {

		File dir = file.getParentFile();

		dir.mkdirs();

		String resultsXML = _toResultsXML(testSuiteProperties);

		Files.write(file.toPath(), resultsXML.getBytes(StandardCharsets.UTF_8));
	}

	private final Map<String, String> _environmentMap = new HashMap<>();
	private final Map<String, byte[]> _resultsTarGzFileContents =
		new HashMap<>();
	private final TestrayBuild _testrayBuild = Mockito.mock(TestrayBuild.class);
	private final TestrayCloudBucket _testrayCloudBucket = Mockito.mock(
		TestrayCloudBucket.class);
	private MockedStatic<TestrayCloudBucket> _testrayCloudBucketMockedStatic;
	private MockedStatic<TestrayFactory> _testrayFactoryMockedStatic;
	private final TestrayProductVersion _testrayProductVersion = Mockito.mock(
		TestrayProductVersion.class);
	private final TestrayProject _testrayProject = Mockito.mock(
		TestrayProject.class);
	private final TestrayRoutine _testrayRoutine = Mockito.mock(
		TestrayRoutine.class);
	private final TestrayServer _testrayServer = Mockito.mock(
		TestrayServer.class);

}