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
public class RelevantRule {

	public RelevantRule(
		String filePath, String name, Properties properties, String suiteName) {

		_filePath = filePath;
		_name = name;
		_properties = properties;

		_testSuiteName = suiteName;
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

		String modifiedFilesExcludes = JenkinsResultsParserUtil.getProperty(
			getProperties(), "modified.files.excludes", getName(),
			getTestSuiteName());

		if (modifiedFilesExcludes != null) {
			_modifiedFilesExcludesPathMatchers =
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesExcludes.split(","));
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

		if (modifiedFilesIncludes == null) {
			_modifiedFilesIncludesPathMatchers = Collections.emptyList();
		}
		else {
			_modifiedFilesIncludesPathMatchers =
				JenkinsResultsParserUtil.toPathMatchers(
					_getParentFilePath() + "/",
					modifiedFilesIncludes.split(","));
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
		return _testSuiteName;
	}

	public boolean matches(File modifiedFile) {
		return JenkinsResultsParserUtil.isFileIncluded(
			getModifiedFilesExcludesPathMatchers(),
			getModifiedFilesIncludesPathMatchers(), modifiedFile);
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
	private final String _testSuiteName;

}