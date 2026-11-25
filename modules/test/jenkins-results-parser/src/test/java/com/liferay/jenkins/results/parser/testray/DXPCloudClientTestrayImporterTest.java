/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import org.dom4j.Document;
import org.dom4j.Element;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Brittney Nguyen
 */
public class DXPCloudClientTestrayImporterTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testMainInvalidResultsFile() throws Exception {
		File projectDir = temporaryFolder.newFolder();

		JenkinsResultsParserUtil.write(
			new File(projectDir, "test-results/TEST-playwright.xml"),
			"<testsuites>" + RandomTestUtil.randomString());

		Assert.assertThrows(
			RuntimeException.class, () -> _runMain(projectDir, "playwright"));

		testEquals(1, _keys.size());

		Pattern invalidResultsFileKeyPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"gcp/\\d{4}-\\d{2}/dxp-cloud/",
				String.valueOf(_testrayBuild.getId()),
				"/TEST-playwright\\.xml\\.gz"));

		Matcher matcher = invalidResultsFileKeyPattern.matcher(_keys.get(0));

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public void testMainPlaywright() throws Exception {
		File projectDir = temporaryFolder.newFolder();

		String failureMessage = RandomTestUtil.randomString();
		String testSuiteName = JenkinsResultsParserUtil.combine(
			"tests/", RandomTestUtil.randomString(), ".spec.ts");

		JenkinsResultsParserUtil.write(
			new File(projectDir, "test-results/TEST-playwright.xml"),
			JenkinsResultsParserUtil.combine(
				"<testsuites><testsuite name=\"", testSuiteName,
				"\"><testcase classname=\"", testSuiteName, "\" name=\"",
				RandomTestUtil.randomString(), "\"/>", "<testcase classname=\"",
				testSuiteName, "\" name=\"", RandomTestUtil.randomString(),
				"\"><failure ", "message=\"", failureMessage, "\"/>",
				"</testcase></testsuite></testsuites>"));

		JenkinsResultsParserUtil.write(
			new File(projectDir, "playwright-report/data/failure.png"),
			RandomTestUtil.randomString());
		JenkinsResultsParserUtil.write(
			new File(projectDir, "playwright-report/index.html"),
			RandomTestUtil.randomString());

		_runMain(projectDir, "playwright");

		TestrayProductVersion testrayProductVersion =
			_testrayBuild.getTestrayProductVersion();
		TestrayRoutine testrayRoutine = _testrayBuild.getTestrayRoutine();

		Mockito.verify(
			testrayRoutine
		).createTestrayBuild(
			Mockito.eq(testrayProductVersion),
			Mockito.startsWith("DXP Cloud Client Build - "),
			Mockito.any(Date.class), Mockito.isNull(),
			Mockito.eq(_TESTRAY_BUILD_SHA)
		);

		Document document = Dom4JUtil.parse(
			_resultsFileContents.get(
				JenkinsResultsParserUtil.combine(
					"TESTS-dxp-cloud-client-",
					String.valueOf(_testrayBuild.getId()), ".xml")));

		Element rootElement = document.getRootElement();

		TestrayProject testrayProject = _testrayBuild.getTestrayProject();

		testEquals(
			_testrayBuild.getName(),
			_getPropertyValue(rootElement, "testray.build.name"));
		testEquals(
			testrayRoutine.getName(),
			_getPropertyValue(rootElement, "testray.build.type"));
		testEquals(
			testrayProductVersion.getName(),
			_getPropertyValue(rootElement, "testray.product.version"));
		testEquals(
			testrayProject.getName(),
			_getPropertyValue(rootElement, "testray.project.name"));
		testEquals(
			"Google Chrome 86|CentOS 7",
			_getPropertyValue(rootElement, "testray.run.id"));

		List<String> environments = new ArrayList<>();
		Element environmentsElement = rootElement.element("environments");

		for (Element environmentElement :
				environmentsElement.elements("environment")) {

			environments.add(
				JenkinsResultsParserUtil.combine(
					environmentElement.attributeValue("type"), "=",
					environmentElement.attributeValue("option")));
		}

		testEquals(
			Arrays.asList(
				"Browser=Google Chrome 86", "Operating System=CentOS 7"),
			environments);

		List<Element> testcaseElements = rootElement.elements("testcase");

		testEquals(2, testcaseElements.size());

		Pattern attachmentKeyPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"gcp/\\d{4}-\\d{2}/dxp-cloud/",
				String.valueOf(_testrayBuild.getId()),
				"/playwright-report/(data/failure\\.png|index\\.html)\\.gz"));

		for (Element testcaseElement : testcaseElements) {
			testEquals(
				testSuiteName,
				_getPropertyValue(testcaseElement, "testray.testcase.name"));

			List<String> attachmentNames = new ArrayList<>();
			Element attachmentsElement = testcaseElement.element("attachments");

			for (Element fileElement : attachmentsElement.elements("file")) {
				attachmentNames.add(fileElement.attributeValue("name"));

				String key = fileElement.attributeValue("value");

				Matcher matcher = attachmentKeyPattern.matcher(key);

				Assert.assertTrue(matcher.matches());

				testEquals(
					JenkinsResultsParserUtil.combine(
						_TESTRAY_CLOUD_BASE_URL, "/", key, "?authuser=0"),
					fileElement.attributeValue("url"));
			}

			Collections.sort(attachmentNames);

			testEquals(
				Arrays.asList("Failure Screenshot", "Playwright Report"),
				attachmentNames);
		}

		Element passedTestcaseElement = testcaseElements.get(0);

		testEquals(
			"passed",
			_getPropertyValue(
				passedTestcaseElement, "testray.testcase.status"));

		Assert.assertNull(passedTestcaseElement.element("failure"));

		Element failedTestcaseElement = testcaseElements.get(1);

		testEquals(
			"failed",
			_getPropertyValue(
				failedTestcaseElement, "testray.testcase.status"));

		Element failureElement = failedTestcaseElement.element("failure");

		testEquals(failureMessage, failureElement.attributeValue("message"));

		testEquals(5, _keys.size());

		Pattern inboxKeyPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"inbox/\\d+-", String.valueOf(_testrayBuild.getId()),
				"-results\\.tar\\.gz"));

		int inboxKeysCount = 0;

		for (String key : _keys) {
			Matcher matcher = inboxKeyPattern.matcher(key);

			if (!matcher.matches()) {
				continue;
			}

			inboxKeysCount++;
		}

		testEquals(1, inboxKeysCount);
	}

	@Test
	public void testMainPoshi() throws Exception {
		File projectDir = temporaryFolder.newFolder();

		String poshiLog = RandomTestUtil.randomString();
		String testName = JenkinsResultsParserUtil.combine(
			RandomTestUtil.randomString(), "#", RandomTestUtil.randomString());

		JenkinsResultsParserUtil.write(
			new File(
				projectDir,
				"test-results/TEST-com.liferay.poshi.runner.PoshiRunner.xml"),
			JenkinsResultsParserUtil.combine(
				"<testsuite><testcase classname=\"",
				RandomTestUtil.randomString(), "\" name=\"test[", testName,
				"]\"><system-out>", poshiLog,
				"</system-out></testcase></testsuite>"));

		File testDir = new File(
			projectDir, "test-results/" + testName.replace("#", "_"));

		JenkinsResultsParserUtil.write(
			new File(testDir, "console.txt"), RandomTestUtil.randomString());
		JenkinsResultsParserUtil.write(
			new File(testDir, "index.html"), RandomTestUtil.randomString());
		JenkinsResultsParserUtil.write(
			new File(testDir, "summary.html"), RandomTestUtil.randomString());

		_runMain(projectDir, "poshi");

		Document document = Dom4JUtil.parse(
			_resultsFileContents.get(
				JenkinsResultsParserUtil.combine(
					"TESTS-dxp-cloud-client-",
					String.valueOf(_testrayBuild.getId()), ".xml")));

		Element rootElement = document.getRootElement();

		List<Element> testcaseElements = rootElement.elements("testcase");

		testEquals(1, testcaseElements.size());

		Element testcaseElement = testcaseElements.get(0);

		testEquals(
			testName,
			_getPropertyValue(testcaseElement, "testray.testcase.name"));

		Pattern attachmentKeyPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"gcp/\\d{4}-\\d{2}/dxp-cloud/",
				String.valueOf(_testrayBuild.getId()), "/",
				Pattern.quote(testDir.getName()),
				"/(console\\.txt|index\\.html|poshi-log\\.txt|summary\\.html)",
				"\\.gz"));
		List<String> attachmentNames = new ArrayList<>();
		Element attachmentsElement = testcaseElement.element("attachments");

		for (Element fileElement : attachmentsElement.elements("file")) {
			attachmentNames.add(fileElement.attributeValue("name"));

			String key = fileElement.attributeValue("value");

			Matcher matcher = attachmentKeyPattern.matcher(key);

			Assert.assertTrue(matcher.matches());

			testEquals(
				JenkinsResultsParserUtil.combine(
					_TESTRAY_CLOUD_BASE_URL, "/", key, "?authuser=0"),
				fileElement.attributeValue("url"));
		}

		Collections.sort(attachmentNames);

		testEquals(
			Arrays.asList(
				"Poshi Console", "Poshi Log", "Poshi Report", "Poshi Summary"),
			attachmentNames);

		testEquals(poshiLog, read(testDir, "poshi-log.txt"));

		testEquals(5, _keys.size());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private String _getPropertyValue(Element element, String propertyName) {
		Element propertiesElement = element.element("properties");

		for (Element propertyElement : propertiesElement.elements("property")) {
			if (propertyName.equals(propertyElement.attributeValue("name"))) {
				return propertyElement.attributeValue("value");
			}
		}

		return null;
	}

	private TestrayBuild _mockTestrayBuild() {
		TestrayBuild testrayBuild = Mockito.mock(TestrayBuild.class);
		TestrayProductVersion testrayProductVersion = Mockito.mock(
			TestrayProductVersion.class);
		TestrayProject testrayProject = Mockito.mock(TestrayProject.class);
		TestrayRoutine testrayRoutine = Mockito.mock(TestrayRoutine.class);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			testrayBuild
		).getId();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayBuild
		).getName();

		Mockito.doReturn(
			testrayProductVersion
		).when(
			testrayBuild
		).getTestrayProductVersion();

		Mockito.doReturn(
			testrayProject
		).when(
			testrayBuild
		).getTestrayProject();

		Mockito.doReturn(
			testrayRoutine
		).when(
			testrayBuild
		).getTestrayRoutine();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayProductVersion
		).getName();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayProject
		).getName();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayRoutine
		).getName();

		return testrayBuild;
	}

	private Map<String, String> _readTarGzip(File file) throws IOException {
		Map<String, String> fileContents = new HashMap<>();

		try (TarArchiveInputStream tarArchiveInputStream =
				new TarArchiveInputStream(
					new GzipCompressorInputStream(
						new BufferedInputStream(new FileInputStream(file))))) {

			TarArchiveEntry tarArchiveEntry = null;

			while ((tarArchiveEntry = tarArchiveInputStream.getNextEntry()) !=
						null) {

				fileContents.put(
					tarArchiveEntry.getName(),
					IOUtils.toString(
						tarArchiveInputStream, StandardCharsets.UTF_8));
			}
		}

		return fileContents;
	}

	private void _runMain(File projectDir, String testType) throws Exception {
		String testrayCloudBucketName = RandomTestUtil.randomString();
		String testrayServerURL =
			"https://" + RandomTestUtil.randomLowerCaseString();

		Map<String, String> environmentMap = new HashMap<>();

		environmentMap.put("projectDir", projectDir.getPath());
		environmentMap.put("testrayBuildSHA", _TESTRAY_BUILD_SHA);
		environmentMap.put("testrayCloudBucketName", testrayCloudBucketName);
		environmentMap.put(
			"testrayOAuth2ClientId", RandomTestUtil.randomString());
		environmentMap.put(
			"testrayOAuth2ClientSecret", RandomTestUtil.randomString());
		environmentMap.put("testrayServerURL", testrayServerURL);
		environmentMap.put("testType", testType);

		mockEnvironment(environmentMap);

		TestrayCloudBucket testrayCloudBucket = Mockito.mock(
			TestrayCloudBucket.class);
		TestrayServer testrayServer = Mockito.mock(TestrayServer.class);

		TestrayProductVersion testrayProductVersion =
			_testrayBuild.getTestrayProductVersion();
		TestrayProject testrayProject = _testrayBuild.getTestrayProject();
		TestrayRoutine testrayRoutine = _testrayBuild.getTestrayRoutine();

		Mockito.doAnswer(
			invocation -> {
				String key = invocation.getArgument(0);

				_keys.add(key);

				if (key.startsWith("inbox/")) {
					_resultsFileContents.putAll(
						_readTarGzip(invocation.getArgument(1)));
				}

				TestrayCloudObject testrayCloudObject = Mockito.mock(
					TestrayCloudObject.class);

				Mockito.doReturn(
					new URL(
						JenkinsResultsParserUtil.combine(
							_TESTRAY_CLOUD_BASE_URL, "/", key))
				).when(
					testrayCloudObject
				).getURL();

				return testrayCloudObject;
			}
		).when(
			testrayCloudBucket
		).createTestrayCloudObject(
			Mockito.anyString(), Mockito.any(File.class)
		);

		Mockito.doReturn(
			testrayProductVersion
		).when(
			testrayProject
		).createTestrayProductVersion(
			Mockito.anyString()
		);

		Mockito.doReturn(
			testrayRoutine
		).when(
			testrayProject
		).createTestrayRoutine(
			Mockito.anyString()
		);

		Mockito.doReturn(
			_testrayBuild
		).when(
			testrayRoutine
		).createTestrayBuild(
			Mockito.any(TestrayProductVersion.class), Mockito.anyString(),
			Mockito.any(Date.class), Mockito.isNull(), Mockito.anyString()
		);

		Mockito.doReturn(
			testrayProject
		).when(
			testrayServer
		).getTestrayProjectByName(
			Mockito.anyString()
		);

		Mockito.doReturn(
			new URL(testrayServerURL)
		).when(
			testrayServer
		).getURL();

		try (MockedStatic<TestrayCloudBucket> testrayCloudBucketMockedStatic =
				Mockito.mockStatic(TestrayCloudBucket.class);
			MockedStatic<TestrayFactory> testrayFactoryMockedStatic =
				Mockito.mockStatic(TestrayFactory.class)) {

			testrayCloudBucketMockedStatic.when(
				() -> TestrayCloudBucket.getInstance(testrayCloudBucketName)
			).thenReturn(
				testrayCloudBucket
			);

			testrayCloudBucketMockedStatic.when(
				() -> TestrayCloudBucket.hasGoogleApplicationCredentials(
					testrayCloudBucketName)
			).thenReturn(
				true
			);

			testrayFactoryMockedStatic.when(
				() -> TestrayFactory.newTestrayServer(testrayServerURL)
			).thenReturn(
				testrayServer
			);

			DXPCloudClientTestrayImporter.main(new String[0]);
		}
	}

	private static final String _TESTRAY_BUILD_SHA = RandomTestUtil.randomSHA();

	private static final String _TESTRAY_CLOUD_BASE_URL =
		"https://" + RandomTestUtil.randomLowerCaseString();

	private final List<String> _keys = new ArrayList<>();
	private final Map<String, String> _resultsFileContents = new HashMap<>();
	private final TestrayBuild _testrayBuild = _mockTestrayBuild();

}