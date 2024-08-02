/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;
import com.liferay.jenkins.results.parser.test.suite.RelevantRuleConfigurationException;
import com.liferay.jenkins.results.parser.test.suite.RelevantRuleEngine;

import java.io.File;

import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public abstract class BaseTestSelector implements TestSelector {

	public BaseTestSelector(
		File propertiesFile, Properties properties, String batchName,
		String relevantRuleName, String testSuiteName) {

		_propertiesFile = propertiesFile;
		_properties = properties;
		_batchName = batchName;
		_relevantRuleName = relevantRuleName;
		_testSuiteName = testSuiteName;

		RelevantRuleEngine relevantRuleEngine =
			RelevantRuleEngine.getInstance();

		_baseDir = relevantRuleEngine.getBaseDir();

		_job = relevantRuleEngine.getJob();

		if (_job == null) {
			throw new RuntimeException("Job is not set for test selector");
		}
	}

	public String getBatchName() {
		return _batchName;
	}

	public JobProperty getGlobalJobProperty(String basePropertyName) {
		return JobPropertyFactory.newJobProperty(
			basePropertyName, _testSuiteName, _batchName, _job, _baseDir, null,
			true);
	}

	public JobProperty getGlobalJobProperty(
		String basePropertyName, JobProperty.Type type) {

		return JobPropertyFactory.newJobProperty(
			basePropertyName, _testSuiteName, _batchName, _job, _baseDir, type,
			true);
	}

	public Job getJob() {
		return _job;
	}

	public JobProperty getJobProperty(
		String basePropertyName, JobProperty.Type type) {

		return JobPropertyFactory.newJobProperty(
			basePropertyName, _testSuiteName, _batchName, _relevantRuleName,
			_job, _propertiesFile.getParentFile(), type, true);
	}

	public File getPropertiesFile() {
		return _propertiesFile;
	}

	public String getProperty(String propertyName) {
		return JenkinsResultsParserUtil.getProperty(
			_properties, propertyName, _batchName, _relevantRuleName,
			_testSuiteName);
	}

	public String getRelevantRuleName() {
		return _relevantRuleName;
	}

	public TestBatch getTestBatch() {
		return _testBatch;
	}

	public String getTestSuiteName() {
		return _testSuiteName;
	}

	public void setTestBatch(TestBatch testBatch) {
		_testBatch = testBatch;
	}

	protected void validate(String propertyName)
		throws RelevantRuleConfigurationException {

		if (getProperty(propertyName) == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to create batch ");
			sb.append(_batchName);
			sb.append(" since ");
			sb.append(propertyName);
			sb.append("[");
			sb.append(getRelevantRuleName());
			sb.append("][");
			sb.append(getTestSuiteName());
			sb.append("][");
			sb.append(getBatchName());
			sb.append("] is not set in ");
			sb.append(getPropertiesFile());

			throw new RelevantRuleConfigurationException(sb.toString());
		}
	}

	private final File _baseDir;
	private final String _batchName;
	private final Job _job;
	private final Properties _properties;
	private final File _propertiesFile;
	private final String _relevantRuleName;
	private TestBatch _testBatch;
	private final String _testSuiteName;

}