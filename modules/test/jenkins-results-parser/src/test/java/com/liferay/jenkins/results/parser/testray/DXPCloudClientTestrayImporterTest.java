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
			"gcp/\\d{4}-\\d{2}/dxp-cloud/" + _testrayBuild.getId() +
				"/TEST-playwright\\.xml\\.gz");

		Matcher matcher = invalidResultsFileKeyPattern.matcher(_keys.get(0));

		Assert.assertTrue(matcher.matches());
	}

	@Test
	public void testMainPlaywright() throws Exception {
		File projectDir = temporaryFolder.newFolder();

		String failureMessage = RandomTestUtil.randomString();
		String testSuiteName =
			"tests/" + RandomTestUtil.randomString() + ".spec.ts";

		JenkinsResultsParserUtil.write(
			new File(projectDir, "test-results/TEST-playwright.xml"),
			JenkinsResultsParserUtil.combine(
				"<testsuites><testsuite name=\"", testSuiteName,
				"\"><testcase classname=\"", testSuiteName, "\" name=\"",
				RandomTestUtil.randomString(), "\" time=\"",
				String.valueOf(RandomTestUtil.randomInt()), "\"/>",
				"<testcase classname=\"", testSuiteName, "\" name=\"",
				RandomTestUtil.randomString(), "\" time=\"",
				String.valueOf(RandomTestUtil.randomInt()), "\"><failure ",
				"message=\"", failureMessage, "\" type=\"FAILURE\"/>",
				"</testcase></testsuite></testsuites>"));

		JenkinsResultsParserUtil.write(
			new File(projectDir, "playwright-report/data/failure.png"),
			RandomTestUtil.randomString());
		JenkinsResultsParserUtil.write(
			new File(projectDir, "playwright-report/index.html"),
			"<html><body><img src=\"data/failure.png\"/></body></html>");

		_runMain(projectDir, "playwright");

		TestrayRoutine testrayRoutine = _testrayBuild.getTestrayRoutine();
		TestrayProductVersion testrayProductVersion =
			_testrayBuild.getTestrayProductVersion();

		Mockito.verify(
			testrayRoutine
		).createTestrayBuild(
			Mockito.eq(testrayProductVersion),
			Mockito.startsWith("DXP Cloud Client Build - "),
			Mockito.any(Date.class), Mockito.isNull(),
			Mockito.eq(_testrayBuildSHA)
		);

		Document document = Dom4JUtil.parse(
			_resultsFileContents.get(
				"TESTS-dxp-cloud-client-" + _testrayBuild.getId() + ".xml"));

		Element rootElement = document.getRootElement();

		testEquals(
			_testrayBuild.getName(),
			_getPropertyValue(rootElement, "testray.build.name"));
		testEquals(
			testrayRoutine.getName(),
			_getPropertyValue(rootElement, "testray.build.type"));
		testEquals(
			testrayProductVersion.getName(),
			_getPropertyValue(rootElement, "testray.product.version"));

		TestrayProject testrayProject = _testrayBuild.getTestrayProject();

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
				environmentElement.attributeValue("type") + "=" +
					environmentElement.attributeValue("option"));
		}

		testEquals(
			Arrays.asList(
				"Browser=Google Chrome 86", "Operating System=CentOS 7"),
			environments);

		List<Element> testcaseElements = rootElement.elements("testcase");

		testEquals(2, testcaseElements.size());

		Pattern attachmentKeyPattern = Pattern.compile(
			"gcp/\\d{4}-\\d{2}/dxp-cloud/" + _testrayBuild.getId() +
				"/playwright-report/(data/failure\\.png|index\\.html)\\.gz");

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

				Assert.assertTrue(_keys.contains(key));

				testEquals(
					JenkinsResultsParserUtil.combine(
						_testrayCloudBaseURL, "/", key, "?authuser=0"),
					fileElement.attributeValue("url"));
			}

			attachmentNames.sort(null);

			testEquals(
				Arrays.asList("Failure Screenshot", "Playwright Report"),
				attachmentNames);
		}

		Element passedTestcaseElement = testcaseElements.get(0);

		Assert.assertNull(passedTestcaseElement.element("failure"));

		testEquals(
			"passed",
			_getPropertyValue(
				passedTestcaseElement, "testray.testcase.status"));

		Element failedTestcaseElement = testcaseElements.get(1);

		Element failureElement = failedTestcaseElement.element("failure");

		testEquals(failureMessage, failureElement.attributeValue("message"));

		testEquals(
			"failed",
			_getPropertyValue(
				failedTestcaseElement, "testray.testcase.status"));

		testEquals(5, _keys.size());

		Pattern inboxKeyPattern = Pattern.compile(
			"inbox/\\d+-" + _testrayBuild.getId() + "-results\\.tar\\.gz");

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

		for (String fileName :
				Arrays.asList("console.txt", "index.html", "summary.html")) {

			JenkinsResultsParserUtil.write(
				new File(testDir, fileName), RandomTestUtil.randomString());
		}

		_runMain(projectDir, "poshi");

		Document document = Dom4JUtil.parse(
			_resultsFileContents.get(
				"TESTS-dxp-cloud-client-" + _testrayBuild.getId() + ".xml"));

		Element rootElement = document.getRootElement();

		List<Element> testcaseElements = rootElement.elements("testcase");

		testEquals(1, testcaseElements.size());

		Element testcaseElement = testcaseElements.get(0);

		testEquals(
			testName,
			_getPropertyValue(testcaseElement, "testray.testcase.name"));

		List<String> attachmentNames = new ArrayList<>();
		Pattern attachmentKeyPattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"gcp/\\d{4}-\\d{2}/dxp-cloud/",
				String.valueOf(_testrayBuild.getId()), "/",
				Pattern.quote(testDir.getName()),
				"/(console\\.txt|index\\.html|poshi-log\\.txt|summary\\.html)",
				"\\.gz"));
		Element attachmentsElement = testcaseElement.element("attachments");

		for (Element fileElement : attachmentsElement.elements("file")) {
			attachmentNames.add(fileElement.attributeValue("name"));

			String key = fileElement.attributeValue("value");

			Matcher matcher = attachmentKeyPattern.matcher(key);

			Assert.assertTrue(matcher.matches());

			Assert.assertTrue(_keys.contains(key));

			testEquals(
				JenkinsResultsParserUtil.combine(
					_testrayCloudBaseURL, "/", key, "?authuser=0"),
				fileElement.attributeValue("url"));
		}

		attachmentNames.sort(null);

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
		TestrayProductVersion testrayProductVersion = Mockito.mock(
			TestrayProductVersion.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayProductVersion
		).getName();

		TestrayProject testrayProject = Mockito.mock(TestrayProject.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayProject
		).getName();

		TestrayRoutine testrayRoutine = Mockito.mock(TestrayRoutine.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			testrayRoutine
		).getName();

		TestrayBuild testrayBuild = Mockito.mock(TestrayBuild.class);

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

		_testrayBuildSHA = RandomTestUtil.randomSHA();

		Map<String, String> environmentMap = new HashMap<>();

		environmentMap.put("projectDir", projectDir.getPath());
		environmentMap.put("testrayBuildSHA", _testrayBuildSHA);
		environmentMap.put("testrayCloudBucketName", testrayCloudBucketName);
		environmentMap.put(
			"testrayOAuth2ClientId", RandomTestUtil.randomString());
		environmentMap.put(
			"testrayOAuth2ClientSecret", RandomTestUtil.randomString());
		environmentMap.put("testrayServerURL", testrayServerURL);
		environmentMap.put("testType", testType);

		mockEnvironment(environmentMap);

		_testrayBuild = _mockTestrayBuild();

		TestrayRoutine testrayRoutine = _testrayBuild.getTestrayRoutine();

		Mockito.doReturn(
			_testrayBuild
		).when(
			testrayRoutine
		).createTestrayBuild(
			Mockito.any(TestrayProductVersion.class), Mockito.anyString(),
			Mockito.any(Date.class), Mockito.isNull(), Mockito.anyString()
		);

		TestrayProject testrayProject = _testrayBuild.getTestrayProject();
		TestrayProductVersion testrayProductVersion =
			_testrayBuild.getTestrayProductVersion();

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

		TestrayServer testrayServer = Mockito.mock(TestrayServer.class);

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

		TestrayCloudBucket testrayCloudBucket = Mockito.mock(
			TestrayCloudBucket.class);

		Mockito.doAnswer(
			invocation -> {
				String key = invocation.getArgument(0);

				_keys.add(key);

				if (key.startsWith("inbox/")) {
					_resultsFileContents.putAll(
						_readTarGzip(invocation.getArgument(1)));
				}

				return null;
			}
		).when(
			testrayCloudBucket
		).createTestrayCloudObject(
			Mockito.anyString(), Mockito.any(File.class)
		);

		_testrayCloudBaseURL =
			"https://" + RandomTestUtil.randomLowerCaseString();

		Mockito.doReturn(
			_testrayCloudBaseURL
		).when(
			testrayCloudBucket
		).getTestrayCloudBaseURL();

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

	private final List<String> _keys = new ArrayList<>();
	private final Map<String, String> _resultsFileContents = new HashMap<>();
	private TestrayBuild _testrayBuild;
	private String _testrayBuildSHA;
	private String _testrayCloudBaseURL;

}