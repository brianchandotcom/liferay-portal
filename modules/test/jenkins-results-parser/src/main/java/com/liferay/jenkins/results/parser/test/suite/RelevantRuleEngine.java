/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;

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

	public static void clear() {
		_relevantRuleEngine = null;
	}

	public static RelevantRuleEngine getInstance() {
		if (_relevantRuleEngine == null) {
			throw new IllegalStateException("Relevant rule engine is null");
		}

		return _relevantRuleEngine;
	}

	public static RelevantRuleEngine getInstance(
		PortalAcceptancePullRequestJob portalAcceptancePullRequestJob) {

		if (_relevantRuleEngine == null) {
			_relevantRuleEngine = new RelevantRuleEngine(
				portalAcceptancePullRequestJob);
		}

		return _relevantRuleEngine;
	}

	public File getBaseDir() {
		return _baseDir;
	}

	public Job getJob() {
		return _job;
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

	public List<String> getRelevantRuleNames(List<RelevantRule> relevantRules) {
		List<String> relevantRuleNames = new ArrayList<>();

		for (RelevantRule relevantRule : relevantRules) {
			relevantRuleNames.add(relevantRule.getName());
		}

		return relevantRuleNames;
	}

	public String getTestSuiteName() {
		return _testSuiteName;
	}

	public void setBaseDir(File baseDir) {
		_baseDir = baseDir;
	}

	private RelevantRuleEngine(
		PortalAcceptancePullRequestJob portalAcceptancePullRequestJob) {

		_job = portalAcceptancePullRequestJob;
		_testSuiteName = portalAcceptancePullRequestJob.getTestSuiteName();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalAcceptancePullRequestJob.getPortalGitWorkingDirectory();

		_baseDir = portalGitWorkingDirectory.getWorkingDirectory();

		_relevantRuleEngine = this;
	}

	private RelevantRule _getRelevantRule(
		String filePath, Job job, String relevantRuleName,
		Properties properties) {

		String relevantRuleKey = filePath + "_" + relevantRuleName;

		for (RelevantRule relevantRule : _relevantRuleMap.keySet()) {
			if (relevantRuleKey.equals(relevantRule.getKey())) {
				return relevantRule;
			}
		}

		RelevantRule relevantRule = new RelevantRule(
			filePath, job, relevantRuleName, properties);

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

		if (file.equals(_baseDir)) {
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
						testPropertiesFilePath, _job, relevantRuleName,
						_getRelevantRuleProperties(
							relevantRuleName, properties)),
					entry.getValue());
			}
		}
	}

	private static RelevantRuleEngine _relevantRuleEngine;

	private File _baseDir;
	private final Job _job;
	private final Map<RelevantRule, Set<File>> _relevantRuleMap =
		new HashMap<>();
	private final String _testSuiteName;

}