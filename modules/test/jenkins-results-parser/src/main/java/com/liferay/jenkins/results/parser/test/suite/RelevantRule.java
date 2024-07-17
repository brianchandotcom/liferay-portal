/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;
import com.liferay.jenkins.results.parser.test.batch.TestBatchFactory;

import java.io.File;

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public class RelevantRule implements Comparable<RelevantRule> {

	public RelevantRule(String filePath, String name, Properties properties) {
		_filePath = filePath;
		_name = name;
		_properties = properties;
	}

	@Override
	public int compareTo(RelevantRule relevantRule) {
		return _name.compareTo(relevantRule.getName());
	}

	public String getFilePath() {
		return _filePath;
	}

	public String getKey() {
		return _filePath + "_" + _name;
	}

	public List<PathMatcher> getModifiedFilesExcludesPathMatchers() {
		if (_modifiedFilesExcludesPathMatchers != null) {
			return _modifiedFilesExcludesPathMatchers;
		}

		List<PathMatcher> modifiedFilesExcludesPathMatchers = new ArrayList<>();

		String modifiedFilesExcludes = JenkinsResultsParserUtil.getProperty(
			getProperties(), "modified.files.excludes", getName(),
			getTestSuiteName());

		if (modifiedFilesExcludes != null) {
			modifiedFilesExcludesPathMatchers.addAll(
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesExcludes.split(",")));
		}

		String modifiedFilesGlobalExcludes = _getBaseDirTestProperty(
			"modified.files.global.excludes");

		if (modifiedFilesGlobalExcludes != null) {
			modifiedFilesExcludesPathMatchers.addAll(
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesGlobalExcludes.split(",")));
		}

		if (!modifiedFilesExcludesPathMatchers.isEmpty()) {
			_modifiedFilesExcludesPathMatchers =
				modifiedFilesExcludesPathMatchers;
		}

		return _modifiedFilesExcludesPathMatchers;
	}

	public List<PathMatcher> getModifiedFilesIncludesPathMatchers() {
		if (_modifiedFilesIncludesPathMatchers != null) {
			return _modifiedFilesIncludesPathMatchers;
		}

		String modifiedFilesIncludes = JenkinsResultsParserUtil.getProperty(
			getProperties(), "modified.files.includes", getName(),
			getTestSuiteName());

		if ((modifiedFilesIncludes == null) ||
			modifiedFilesIncludes.isEmpty()) {

			_modifiedFilesIncludesPathMatchers = Collections.emptyList();
		}
		else {
			_modifiedFilesIncludesPathMatchers =
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesIncludes.split(","));
		}

		String modifiedFilesGlobalIncludes = _getBaseDirTestProperty(
			"modified.files.global.includes");

		if (modifiedFilesGlobalIncludes != null) {
			_modifiedFilesIncludesPathMatchers.addAll(
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesGlobalIncludes.split(",")));
		}

		return _modifiedFilesIncludesPathMatchers;
	}

	public String getName() {
		return _name;
	}

	public Properties getProperties() {
		return _properties;
	}

	public List<TestBatch> getTestBatches() {
		if (_testBatches == null) {
			String testBatchNamesPropertyValue =
				JenkinsResultsParserUtil.getProperty(
					getProperties(), "test.batch.names", getName(),
					getTestSuiteName());

			if (testBatchNamesPropertyValue == null) {
				return Collections.emptyList();
			}

			_testBatches = new ArrayList<>();

			for (String testBatchName :
					testBatchNamesPropertyValue.split(",")) {

				_testBatches.add(
					TestBatchFactory.newTestBatch(
						new File(_filePath), getProperties(), testBatchName,
						getName(), getTestSuiteName()));
			}
		}

		return _testBatches;
	}

	public String getTestSuiteName() {
		RelevantRuleEngine relevantRuleEngine =
			RelevantRuleEngine.getInstance();

		return relevantRuleEngine.getTestSuiteName();
	}

	public boolean matches(File modifiedFile) {
		return JenkinsResultsParserUtil.isFileIncluded(
			getModifiedFilesExcludesPathMatchers(),
			getModifiedFilesIncludesPathMatchers(), modifiedFile);
	}

	public void validate() throws RelevantRuleConfigurationException {
		List<TestBatch> testBatches = getTestBatches();

		if (testBatches.isEmpty()) {
			throw new RelevantRuleConfigurationException(
				JenkinsResultsParserUtil.combine(
					"Unable to find test.batch.names for relevant rule '",
					getName(), "' in ", _filePath));
		}

		List<PathMatcher> modifiedFilesIncludes =
			getModifiedFilesIncludesPathMatchers();

		if (modifiedFilesIncludes.isEmpty()) {
			throw new RelevantRuleConfigurationException(
				JenkinsResultsParserUtil.combine(
					"Unable to find modified.files.includes for relevant ",
					"rule '", getName(), "' in ", _filePath));
		}
	}

	private String _getBaseDirTestProperty(String propertyName) {
		RelevantRuleEngine relevantRuleEngine =
			RelevantRuleEngine.getInstance();

		File baseTestPropertiesFile = new File(
			relevantRuleEngine.getBaseDir(), "test.properties");

		if (!baseTestPropertiesFile.exists()) {
			return null;
		}

		return JenkinsResultsParserUtil.getProperty(
			JenkinsResultsParserUtil.getProperties(baseTestPropertiesFile),
			propertyName, getTestSuiteName());
	}

	private String _getParentFilePath() {
		File file = new File(_filePath);

		return JenkinsResultsParserUtil.getCanonicalPath(file.getParentFile());
	}

	private final String _filePath;
	private List<PathMatcher> _modifiedFilesExcludesPathMatchers;
	private List<PathMatcher> _modifiedFilesIncludesPathMatchers;
	private final String _name;
	private final Properties _properties;
	private List<TestBatch> _testBatches;

}