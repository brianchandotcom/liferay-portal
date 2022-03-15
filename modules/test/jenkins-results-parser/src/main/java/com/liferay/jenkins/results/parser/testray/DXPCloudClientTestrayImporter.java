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

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.net.URLEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class DXPCloudClientTestrayImporter {

	public static void main(String[] args) throws Exception {
		_initEnvironmentVariables();

		Element rootElement = Dom4JUtil.getNewElement("testsuite");

		rootElement.add(_getTestSuiteEnvironmentsElement());
		rootElement.add(_getTestSuitePropertiesElement());

		for (Element testCaseResultElement : _getTestCaseResultElements()) {
			Element testCaseElement = rootElement.addElement("testcase");

			testCaseElement.add(
				_getTestCaseAttachmentsElement(testCaseResultElement));
			testCaseElement.add(
				_getTestCasePropertiesElement(testCaseResultElement));
		}

		if (!TestrayS3Bucket.googleCredentialsAvailable()) {
			JenkinsResultsParserUtil.HTTPAuthorization httpAuthorization = null;

			if (!JenkinsResultsParserUtil.isNullOrEmpty(_testrayUserName) &&
				!JenkinsResultsParserUtil.isNullOrEmpty(_testrayUserPassword)) {

				httpAuthorization =
					new JenkinsResultsParserUtil.BasicHTTPAuthorization(
						_testrayUserPassword, _testrayUserName);
			}

			JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.combine(
					_testrayServerURL, "/web/guest/home/-/testray/case_results",
					"/importResults.json"),
				JenkinsResultsParserUtil.combine(
					"results=",
					URLEncoder.encode(Dom4JUtil.format(rootElement), "UTF-8"),
					"&type=poshi"),
				httpAuthorization);

			return;
		}

		TestrayBuild testrayBuild = _getTestrayBuild();

		File testrayResultsDir = new File("testray-results");

		JenkinsResultsParserUtil.delete(testrayResultsDir);

		testrayResultsDir.mkdirs();

		File resultsFile = new File(
			testrayResultsDir,
			JenkinsResultsParserUtil.combine(
				"TESTS-dxp-cloud-client-", String.valueOf(testrayBuild.getID()),
				".xml"));

		JenkinsResultsParserUtil.write(
			resultsFile, Dom4JUtil.format(rootElement));

		File resultsTarGzFile = new File(
			JenkinsResultsParserUtil.combine(
				String.valueOf(JenkinsResultsParserUtil.getCurrentTimeMillis()),
				"-", String.valueOf(testrayBuild.getID()), "-results.tar.gz"));

		JenkinsResultsParserUtil.tarGzip(testrayResultsDir, resultsTarGzFile);

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		testrayS3Bucket.createTestrayS3Object(
			"to-be-done/" + resultsTarGzFile.getName(), resultsTarGzFile);

		JenkinsResultsParserUtil.delete(testrayResultsDir);
		JenkinsResultsParserUtil.delete(resultsTarGzFile);
	}

	private static void _fixImageURLs(File htmlFile) {
		try {
			String htmlFileContent = JenkinsResultsParserUtil.read(htmlFile);

			File parentFile = htmlFile.getParentFile();

			JenkinsResultsParserUtil.write(
				htmlFile,
				htmlFileContent.replaceAll(
					"(screenshots/(?:after|before|screenshot)\\d+)\\.jpg",
					JenkinsResultsParserUtil.combine(
						_testrayServerURL, "/reports/", _testrayReleaseName,
						"/logs/", _getRelativeURLPath(), "/",
						parentFile.getName(), "/$1.jpg.gz")));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _getEnvVarValue(String varName) {
		String varValue = System.getenv(varName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(varValue)) {
			varValue = System.getProperty(varName);
		}

		return varValue;
	}

	private static Element _getPropertiesElement(Properties properties) {
		Element element = Dom4JUtil.getNewElement("properties");

		for (String propertyName : properties.stringPropertyNames()) {
			Element propertyElement = element.addElement("property");

			String propertyValue = JenkinsResultsParserUtil.getProperty(
				properties, propertyName);

			if (JenkinsResultsParserUtil.isNullOrEmpty(propertyName) ||
				JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {

				continue;
			}

			propertyElement.addAttribute("name", propertyName);
			propertyElement.addAttribute("value", propertyValue);
		}

		return element;
	}

	private static String _getRelativeURLPath() {
		if (_relativeURLPath != null) {
			return _relativeURLPath;
		}

		TestrayBuild testrayBuild = _getTestrayBuild();

		_relativeURLPath = JenkinsResultsParserUtil.combine(
			_localDate.format(DateTimeFormatter.ofPattern("yyyy-MM")),
			"/dxp-cloud/", String.valueOf(testrayBuild.getID()));

		return _relativeURLPath;
	}

	private static Element _getTestCaseAttachmentsElement(
		Element testCaseResultElement) {

		Element attachmentsElement = Dom4JUtil.getNewElement("attachments");

		if (!TestrayS3Bucket.googleCredentialsAvailable()) {
			return attachmentsElement;
		}

		Matcher matcher = _pattern.matcher(
			testCaseResultElement.attributeValue("name"));

		if (!matcher.find()) {
			return attachmentsElement;
		}

		String testName = matcher.group("testName");

		File testDir = new File(
			_projectDir, "test-results/" + testName.replace("#", "_"));

		if (!testDir.exists()) {
			return attachmentsElement;
		}

		for (File htmlFile :
				JenkinsResultsParserUtil.findFiles(testDir, ".*\\.html")) {

			_fixImageURLs(htmlFile);
		}

		_removeUnreferencedImages(new File(testDir, "index.html"));

		TestrayS3Bucket testrayS3Bucket = TestrayS3Bucket.getInstance();

		for (File file : JenkinsResultsParserUtil.findFiles(testDir, ".*")) {
			String fileName = file.getName();

			if (!fileName.endsWith(".gz")) {
				File gzipFile = new File(
					file.getParent(), file.getName() + ".gz");

				JenkinsResultsParserUtil.gzip(file, gzipFile);

				JenkinsResultsParserUtil.delete(file);

				file = gzipFile;
			}

			fileName = file.getName();

			String key = JenkinsResultsParserUtil.combine(
				_getRelativeURLPath(), "/",
				JenkinsResultsParserUtil.getPathRelativeTo(
					file, testDir.getParentFile()));

			testrayS3Bucket.createTestrayS3Object(key, file);

			String attachmentName;

			if (fileName.equals("index.html.gz")) {
				attachmentName = "Poshi Report";
			}
			else if (fileName.equals("summary.html.gz")) {
				attachmentName = "Poshi Summary";
			}
			else {
				continue;
			}

			Element attachmentElement = attachmentsElement.addElement("file");

			attachmentElement.addAttribute("name", attachmentName);

			attachmentElement.addAttribute(
				"url",
				JenkinsResultsParserUtil.combine(
					_testrayServerURL, "/reports/", _testrayReleaseName,
					"/logs/", key, "?authuser=0"));
			attachmentElement.addAttribute("value", key + "?authuser=0");
		}

		return attachmentsElement;
	}

	private static Element _getTestCasePropertiesElement(
		Element testCaseResultElement) {

		Properties properties = new Properties();

		Matcher matcher = _pattern.matcher(
			testCaseResultElement.attributeValue("name"));

		if (!matcher.find()) {
			return _getPropertiesElement(properties);
		}

		TestrayCaseResult.Status status = TestrayCaseResult.Status.PASSED;

		Element failureElement = testCaseResultElement.element("failure");

		if (failureElement != null) {
			status = TestrayCaseResult.Status.FAILED;
		}

		properties.setProperty(
			"testray.case.type.name", "Automated Functional Test");
		properties.setProperty(
			"testray.component.names", _testrayComponentName);
		properties.setProperty(
			"testray.main.component.name", _testrayComponentName);
		properties.setProperty("testray.team.name", _testrayTeamName);
		properties.setProperty(
			"testray.testcase.name", matcher.group("testName"));
		properties.setProperty(
			"testray.testcase.priority", String.valueOf(_testrayCasePriority));
		properties.setProperty("testray.testcase.status", status.getName());

		return _getPropertiesElement(properties);
	}

	private static List<Element> _getTestCaseResultElements() {
		try {
			String content = JenkinsResultsParserUtil.read(
				new File(
					_projectDir,
					"test-results" +
						"/TEST-com.liferay.poshi.runner.PoshiRunner.xml"));

			int x = content.indexOf("<system-out>");
			int y = content.indexOf("</system-err>") + 13;

			content = content.substring(0, x) + content.substring(y);

			Document document = Dom4JUtil.parse(content);

			Element rootElement = document.getRootElement();

			return rootElement.elements("testcase");
		}
		catch (DocumentException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static TestrayBuild _getTestrayBuild() {
		if (_testrayBuild != null) {
			return _testrayBuild;
		}

		TestrayServer testrayServer = TestrayFactory.newTestrayServer(
			_testrayServerURL);

		TestrayProject testrayProject = testrayServer.getTestrayProjectByName(
			_testrayProjectName);

		TestrayRoutine testrayRoutine = testrayProject.createTestrayRoutine(
			_testrayRoutineName);

		TestrayProductVersion testrayProductVersion =
			testrayProject.createTestrayProductVersion(_testrayProductVersion);

		_testrayBuild = testrayRoutine.createTestrayBuild(
			testrayProductVersion, _getTestrayBuildName());

		return _testrayBuild;
	}

	private static String _getTestrayBuildName() {
		return _testrayBuildName.replace(
			"$(start.time)",
			JenkinsResultsParserUtil.toDateString(
				new Date(_START_TIME), "yyyy-MM-dd[HH:mm:ss]",
				"America/Los_Angeles"));
	}

	private static Element _getTestSuiteEnvironmentsElement() {
		Element environmentsElement = Dom4JUtil.getNewElement("environments");

		Element browserEnvironmentElement = environmentsElement.addElement(
			"environment");

		browserEnvironmentElement.addAttribute(
			"option", _environmentBrowserName);
		browserEnvironmentElement.addAttribute("type", "Browser");

		Element operatingSystemEnvironmentElement =
			environmentsElement.addElement("environment");

		operatingSystemEnvironmentElement.addAttribute(
			"option", _environmentOperatingSystemName);
		operatingSystemEnvironmentElement.addAttribute(
			"type", "Operating System");

		return environmentsElement;
	}

	private static Element _getTestSuitePropertiesElement() {
		TestrayBuild testrayBuild = _getTestrayBuild();

		TestrayProductVersion testrayProductVersion =
			testrayBuild.getTestrayProductVersion();
		TestrayProject testrayProject = testrayBuild.getTestrayProject();
		TestrayRoutine testrayRoutine = testrayBuild.getTestrayRoutine();

		Properties properties = new Properties();

		properties.setProperty("testray.build.name", testrayBuild.getName());
		properties.setProperty("testray.build.type", testrayRoutine.getName());
		properties.setProperty(
			"testray.product.version", testrayProductVersion.getName());
		properties.setProperty(
			"testray.project.name", testrayProject.getName());
		properties.setProperty(
			"testray.run.id",
			JenkinsResultsParserUtil.join(
				"|", _environmentBrowserName, _environmentOperatingSystemName));

		return _getPropertiesElement(properties);
	}

	private static void _initEnvironmentVariables() {
		String projectDirPath = _getEnvVarValue("projectDir");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(projectDirPath)) {
			_projectDir = new File(projectDirPath);
		}

		if (!_projectDir.exists()) {
			throw new RuntimeException(
				"Could not find '" + projectDirPath + "'");
		}

		String environmentBrowserName = _getEnvVarValue(
			"environmentBrowserName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(environmentBrowserName)) {
			_environmentBrowserName = environmentBrowserName;
		}

		String environmentOperatingSystemName = _getEnvVarValue(
			"environmentOperatingSystemName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				environmentOperatingSystemName)) {

			_environmentOperatingSystemName = environmentOperatingSystemName;
		}

		String testrayBuildName = _getEnvVarValue("testrayBuildName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildName)) {
			_testrayBuildName = testrayBuildName;
		}

		String testrayCasePriority = _getEnvVarValue("testrayCasePriority");

		if ((testrayCasePriority != null) &&
			testrayCasePriority.matches("\\d+")) {

			_testrayCasePriority = Integer.valueOf(testrayCasePriority);
		}

		String testrayComponentName = _getEnvVarValue("testrayComponentName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayComponentName)) {
			_testrayComponentName = testrayComponentName;
		}

		String testrayProductVersion = _getEnvVarValue("testrayProductVersion");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayProductVersion)) {
			_testrayProductVersion = testrayProductVersion;
		}

		String testrayProjectName = _getEnvVarValue("testrayProjectName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayProjectName)) {
			_testrayProjectName = testrayProjectName;
		}

		String testrayReleaseName = _getEnvVarValue("testrayReleaseName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayReleaseName)) {
			_testrayReleaseName = testrayReleaseName;
		}

		String testrayRoutineName = _getEnvVarValue("testrayRoutineName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayRoutineName)) {
			_testrayRoutineName = testrayRoutineName;
		}

		String testrayServerURL = _getEnvVarValue("testrayServerURL");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayServerURL)) {
			_testrayServerURL = testrayServerURL;
		}

		String testrayTeamName = _getEnvVarValue("testrayTeamName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayTeamName)) {
			_testrayTeamName = testrayTeamName;
		}

		String testrayUserName = _getEnvVarValue("testrayUserName");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayUserName)) {
			_testrayUserName = testrayUserName;
		}

		String testrayUserPassword = _getEnvVarValue("testrayUserPassword");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(testrayUserPassword)) {
			_testrayUserPassword = testrayUserPassword;
		}
	}

	private static void _removeUnreferencedImages(File htmlFile) {
		if (!htmlFile.exists()) {
			return;
		}

		try {
			String htmlFileContent = JenkinsResultsParserUtil.read(htmlFile);

			List<File> jpgFiles = JenkinsResultsParserUtil.findFiles(
				htmlFile.getParentFile(), ".*\\.jpg");

			for (File jpgFile : jpgFiles) {
				String jpgFileName = jpgFile.getName();

				if (htmlFileContent.contains("/" + jpgFileName)) {
					continue;
				}

				System.out.println("Removing unreferenced file " + jpgFile);

				JenkinsResultsParserUtil.delete(jpgFile);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final long _START_TIME = System.currentTimeMillis();

	private static String _environmentBrowserName = "Google Chrome 86";
	private static String _environmentOperatingSystemName = "Cent OS 7";
	private static final LocalDate _localDate = LocalDate.now();
	private static final Pattern _pattern = Pattern.compile(
		"test\\[(?<testName>[^\\]]{1,150})[^\\]]*\\]");
	private static File _projectDir = new File(".");
	private static String _relativeURLPath;
	private static TestrayBuild _testrayBuild;
	private static String _testrayBuildName =
		"DXP Cloud Client Build - $(start.time)";
	private static Integer _testrayCasePriority = 1;
	private static String _testrayComponentName = "DXP Cloud Client Component";
	private static String _testrayProductVersion = "1.x";
	private static String _testrayProjectName = "DXP Cloud Client";
	private static String _testrayReleaseName = "production";
	private static String _testrayRoutineName = "DXP Cloud Client Routine";
	private static String _testrayServerURL = "https://testray.liferay.com";
	private static String _testrayTeamName = "DXP Cloud Client Team";
	private static String _testrayUserName;
	private static String _testrayUserPassword;

}