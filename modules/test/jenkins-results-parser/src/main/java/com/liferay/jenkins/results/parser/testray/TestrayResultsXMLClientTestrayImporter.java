/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.testray;

import com.liferay.jenkins.results.parser.Dom4JUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Brittney Nguyen
 */
public class TestrayResultsXMLClientTestrayImporter
	extends BaseClientTestrayImporter {

	public static void main(String[] args) throws Exception {
		TestrayResultsXMLClientTestrayImporter
			testrayResultsXMLClientTestrayImporter =
				new TestrayResultsXMLClientTestrayImporter();

		testrayResultsXMLClientTestrayImporter.importResults();
	}

	@Override
	public void importResults() throws Exception {
		List<File> resultsFiles = JenkinsResultsParserUtil.findFiles(
			_testrayResultsDir, ".*\\.xml");

		if (resultsFiles.isEmpty()) {
			throw new RuntimeException(
				"Unable to find Testray results XML files in " +
					_testrayResultsDir);
		}

		Collections.sort(resultsFiles);

		List<File> failedResultsFiles = new ArrayList<>();

		for (File resultsFile : resultsFiles) {
			try {
				TestrayBuild testrayBuild = _getTestrayBuild(resultsFile);

				uploadResults(
					Collections.singletonList(resultsFile), testrayBuild);

				System.out.println(
					JenkinsResultsParserUtil.combine(
						resultsFile.getPath(), " was imported to ",
						String.valueOf(testrayBuild.getURL())));
			}
			catch (Exception exception) {
				exception.printStackTrace();

				failedResultsFiles.add(resultsFile);
			}
		}

		if (!failedResultsFiles.isEmpty()) {
			throw new RuntimeException(
				"Unable to import " + failedResultsFiles);
		}
	}

	private TestrayResultsXMLClientTestrayImporter() {
		String testrayResultsDirPath = getEnvVarValue("testrayResultsDir");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayResultsDirPath)) {
			throw new RuntimeException("\"testrayResultsDir\" is not set");
		}

		_testrayResultsDir = new File(testrayResultsDirPath);

		if (!_testrayResultsDir.isDirectory()) {
			throw new RuntimeException(
				"Unable to find directory " + testrayResultsDirPath);
		}
	}

	private String _getRequiredProperty(
		String propertyName, File resultsFile,
		Map<String, String> testSuiteProperties) {

		String propertyValue = testSuiteProperties.get(propertyName);

		if (JenkinsResultsParserUtil.isNullOrEmpty(propertyValue)) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find property \"", propertyName, "\" in ",
					resultsFile.getPath()));
		}

		return propertyValue;
	}

	private Map<String, String> _getTestSuiteProperties(File resultsFile)
		throws Exception {

		Map<String, String> testSuiteProperties = new HashMap<>();

		Document document = Dom4JUtil.parse(
			JenkinsResultsParserUtil.read(resultsFile));

		Element rootElement = document.getRootElement();

		Element propertiesElement = rootElement.element("properties");

		if (propertiesElement == null) {
			return testSuiteProperties;
		}

		for (Element propertyElement : propertiesElement.elements("property")) {
			testSuiteProperties.put(
				propertyElement.attributeValue("name"),
				propertyElement.attributeValue("value"));
		}

		return testSuiteProperties;
	}

	private TestrayBuild _getTestrayBuild(File resultsFile) throws Exception {
		Map<String, String> testSuiteProperties = _getTestSuiteProperties(
			resultsFile);

		String testrayBuildSHA = testSuiteProperties.get("git.id");

		if (JenkinsResultsParserUtil.isNullOrEmpty(testrayBuildSHA)) {
			testrayBuildSHA = null;
		}

		return getTestrayBuild(
			_toDate(
				_getRequiredProperty(
					"testray.build.date", resultsFile, testSuiteProperties)),
			_getRequiredProperty(
				"testray.build.name", resultsFile, testSuiteProperties),
			testrayBuildSHA,
			_getRequiredProperty(
				"testray.product.version", resultsFile, testSuiteProperties),
			_getRequiredProperty(
				"testray.project.name", resultsFile, testSuiteProperties),
			_getRequiredProperty(
				"testray.build.type", resultsFile, testSuiteProperties));
	}

	private Date _toDate(String dateString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

		simpleDateFormat.setLenient(false);
		simpleDateFormat.setTimeZone(
			TimeZone.getTimeZone("America/Los_Angeles"));

		ParsePosition parsePosition = new ParsePosition(0);

		Date date = simpleDateFormat.parse(dateString, parsePosition);

		if ((date == null) ||
			(parsePosition.getIndex() != dateString.length())) {

			throw new RuntimeException(
				"Invalid \"testray.build.date\" " + dateString);
		}

		return date;
	}

	private final File _testrayResultsDir;

}