/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.batch;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.job.property.JobPropertyFactory;

import java.io.File;

import java.util.Properties;

/**
 * @author Kenji Heigel
 */
public abstract class BaseTestSelector implements TestSelector {

	public BaseTestSelector(
		File propertiesFile, Properties properties, String batchName,
		String relevantRuleName, String testSuiteName, Job job) {

		_propertiesFile = propertiesFile;
		_properties = properties;
		_batchName = batchName;
		_relevantRuleName = relevantRuleName;
		_testSuiteName = testSuiteName;
		_job = job;
	}

	public BaseTestSelector(
		Properties properties, String batchName, String relevantRuleName,
		String testSuiteName) {

		_properties = properties;
		_batchName = batchName;
		_relevantRuleName = relevantRuleName;
		_testSuiteName = testSuiteName;
	}

	public String getBatchName() {
		return _batchName;
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

	public String getProperty(String propertyName) {
		return JenkinsResultsParserUtil.getProperty(
			_properties, propertyName, _batchName, _relevantRuleName,
			_testSuiteName);
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

	protected void validate(String propertyName) {
		if (getProperty(propertyName) == null) {
			throw new IllegalStateException(
				"Unable to create batch " + _batchName + " since " +
					propertyName + " is not set");
		}
	}

	private final String _batchName;
	private Job _job;
	private final Properties _properties;
	private File _propertiesFile;
	private final String _relevantRuleName;
	private TestBatch _testBatch;
	private final String _testSuiteName;

}