/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Kenji Heigel
 */
public class RelevantRuleEngine {

	public RelevantRuleEngine(File baseDir) {
		_baseDir = baseDir;
	}

	public RelevantRuleEngine(File baseDir, String testSuiteName) {
		_baseDir = baseDir;
		_testSuiteName = testSuiteName;
	}

	public File getBaseDir() {
		return _baseDir;
	}

	public List<RelevantRule> getMatchingRelevantRules(
		List<File> modifiedFiles) {

		_populateRelevantRuleMap(
			_getTestPropertiesModifiedFilesMap(modifiedFiles));

		List<RelevantRule> matchingRelevantRules = new ArrayList<>();

		for (Map.Entry<RelevantRule, Set<File>> entry :
				_relevantRuleMap.entrySet()) {

			RelevantRule relevantRule = entry.getKey();

			for (File modifiedFile : entry.getValue()) {
				if (relevantRule.matches(modifiedFile)) {
					matchingRelevantRules.add(relevantRule);

					break;
				}
			}
		}

		return matchingRelevantRules;
	}

	public String getTestSuiteName() {
		return _testSuiteName;
	}

	private RelevantRule _getRelevantRule(
		String filePath, String relevantRuleName, Properties properties) {

		String relevantRuleKey = filePath + "_" + relevantRuleName;

		for (RelevantRule relevantRule : _relevantRuleMap.keySet()) {
			if (relevantRuleKey.equals(relevantRule.getKey())) {
				return relevantRule;
			}
		}

		RelevantRule relevantRule = new RelevantRule(
			filePath, relevantRuleName, properties, getTestSuiteName());

		_relevantRuleMap.put(relevantRule, new HashSet<>());

		return relevantRule;
	}

	private Properties _getRelevantRuleProperties(
		String relevantRuleName, Properties properties) {

		Properties relevantRuleProperties = new Properties();

		for (Object object : properties.keySet()) {
			String propertyName = (String)object;

			if (propertyName.contains("[" + relevantRuleName + "]") &&
				propertyName.contains("[" + getTestSuiteName() + "]")) {

				String propertyValue = properties.getProperty(propertyName);

				relevantRuleProperties.setProperty(propertyName, propertyValue);
			}
		}

		return relevantRuleProperties;
	}

	private Set<String> _getTestPropertiesFilePaths(
		File file, Set<String> testPropertiesFilePaths) {

		if (testPropertiesFilePaths == null) {
			testPropertiesFilePaths = new HashSet<>();
		}

		file = file.getParentFile();

		File testPropertiesFile = new File(file, "test.properties");

		String testPropertiesFilePath =
			JenkinsResultsParserUtil.getCanonicalPath(testPropertiesFile);

		if (testPropertiesFile.exists() &&
			!testPropertiesFilePaths.contains(testPropertiesFilePath)) {

			testPropertiesFilePaths.add(testPropertiesFilePath);
		}

		if (file.equals(getBaseDir())) {
			return testPropertiesFilePaths;
		}

		return _getTestPropertiesFilePaths(file, testPropertiesFilePaths);
	}

	private Map<String, Set<File>> _getTestPropertiesModifiedFilesMap(
		List<File> modifiedFiles) {

		Map<String, Set<File>> testPropertiesModifiedFilesMap = new HashMap<>();

		for (File modifiedFile : modifiedFiles) {
			for (String testPropertiesFilePath :
					_getTestPropertiesFilePaths(modifiedFile, null)) {

				if (!testPropertiesModifiedFilesMap.containsKey(
						testPropertiesFilePath)) {

					testPropertiesModifiedFilesMap.put(
						testPropertiesFilePath, new HashSet<File>());
				}

				Set<File> testPropertiesModifiedFiles =
					testPropertiesModifiedFilesMap.get(testPropertiesFilePath);

				testPropertiesModifiedFiles.add(modifiedFile);
			}
		}

		return testPropertiesModifiedFilesMap;
	}

	private void _populateRelevantRuleMap(
		Map<String, Set<File>> testPropertiesModifiedFilesMap) {

		for (Map.Entry<String, Set<File>> entry :
				testPropertiesModifiedFilesMap.entrySet()) {

			String testPropertiesFilePath = entry.getKey();

			File testPropertiesFile = new File(testPropertiesFilePath);

			Properties properties = JenkinsResultsParserUtil.getProperties(
				testPropertiesFile);

			String relevantRuleNames = JenkinsResultsParserUtil.getProperty(
				properties, "relevant.rule.names");

			if (relevantRuleNames == null) {
				continue;
			}

			for (String relevantRuleName : relevantRuleNames.split(",")) {
				_relevantRuleMap.put(
					_getRelevantRule(
						testPropertiesFilePath, relevantRuleName,
						_getRelevantRuleProperties(
							relevantRuleName, properties)),
					entry.getValue());
			}
		}
	}

	private final File _baseDir;
	private final Map<RelevantRule, Set<File>> _relevantRuleMap =
		new HashMap<>();
	private String _testSuiteName = "relevant";

}