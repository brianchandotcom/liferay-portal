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

package com.liferay.jenkins.results.parser.job.property;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.TestSuiteJob;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobProperty implements JobProperty {

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof JobProperty)) {
			return false;
		}

		return Objects.equals(object.toString(), toString());
	}

	@Override
	public String getBasePropertyName() {
		return _basePropertyName;
	}

	@Override
	public Job getJob() {
		return _job;
	}

	@Override
	public String getName() {
		if (!_readJobProperties) {
			readProperties();
		}

		return _name;
	}

	@Override
	public String getPropertiesFilePath() {
		if (!_readJobProperties) {
			readProperties();
		}

		if (_propertiesFile == null) {
			return "liferay-jenkins-ee/commands/build.properties";
		}

		File workingDirectory = getWorkingDirectory(_propertiesFile);

		return JenkinsResultsParserUtil.getPathRelativeTo(
			_propertiesFile, workingDirectory.getParentFile());
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getValue() {
		if (!_readJobProperties) {
			readProperties();
		}

		return _value;
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.join(
			"_", _job.getJobName(), _basePropertyName, String.valueOf(_type),
			String.valueOf(_useBasePropertyName), _testSuiteName,
			_testBatchName);
	}

	protected static File getWorkingDirectory(File file) {
		if (file == null) {
			return null;
		}

		File canonicalFile = JenkinsResultsParserUtil.getCanonicalFile(file);

		File parentFile = canonicalFile.getParentFile();

		if ((parentFile == null) || !parentFile.exists()) {
			return file;
		}

		if (!canonicalFile.isDirectory()) {
			return getWorkingDirectory(parentFile);
		}

		File gitDir = new File(canonicalFile, ".git");

		if (!gitDir.exists()) {
			return getWorkingDirectory(parentFile);
		}

		return canonicalFile;
	}

	protected BaseJobProperty(
		Job job, Type type, String basePropertyName,
		boolean useBasePropertyName, String testSuiteName,
		String testBatchName) {

		_job = job;
		_type = type;
		_basePropertyName = basePropertyName;
		_useBasePropertyName = useBasePropertyName;

		if ((testSuiteName == null) && (job instanceof TestSuiteJob)) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		_testSuiteName = testSuiteName;
		_testBatchName = testBatchName;
	}

	protected List<File> getJobPropertiesFiles() {
		return _job.getJobPropertiesFiles();
	}

	protected void readProperties() {
		for (File jobPropertiesFile : getJobPropertiesFiles()) {
			Properties jobProperties = JenkinsResultsParserUtil.getProperties(
				jobPropertiesFile);

			String name = JenkinsResultsParserUtil.getPropertyName(
				jobProperties, _useBasePropertyName, _basePropertyName,
				_getJobPropertyOptions());

			if (JenkinsResultsParserUtil.isNullOrEmpty(name)) {
				continue;
			}

			String value = JenkinsResultsParserUtil.getProperty(
				jobProperties, name);

			if (value == null) {
				continue;
			}

			_name = name;
			_value = value;
			_propertiesFile = jobPropertiesFile;
			_readJobProperties = true;

			return;
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String name = JenkinsResultsParserUtil.getPropertyName(
				buildProperties, _useBasePropertyName, _basePropertyName,
				_getJobPropertyOptions());

			if (JenkinsResultsParserUtil.isNullOrEmpty(name)) {
				_readJobProperties = true;

				return;
			}

			String value = JenkinsResultsParserUtil.getProperty(
				buildProperties, name);

			if (value == null) {
				_readJobProperties = true;

				return;
			}

			_name = name;
			_value = value;
			_propertiesFile = null;
			_readJobProperties = true;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private String[] _getJobPropertyOptions() {
		Job job = getJob();

		List<String> jobPropertyOptions = job.getJobPropertyOptions();

		jobPropertyOptions.add(_testSuiteName);
		jobPropertyOptions.add(_testBatchName);

		jobPropertyOptions.removeAll(Collections.singleton(null));

		return jobPropertyOptions.toArray(new String[0]);
	}

	private final String _basePropertyName;
	private final Job _job;
	private String _name;
	private File _propertiesFile;
	private boolean _readJobProperties;
	private final String _testBatchName;
	private final String _testSuiteName;
	private final Type _type;
	private final boolean _useBasePropertyName;
	private String _value;

}