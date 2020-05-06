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

package com.liferay.jenkins.results.parser;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Yi-Chen Tsai
 */
public abstract class PortalAcceptanceTestSuiteJob
	extends PortalGitRepositoryJob implements BatchDependentJob, TestSuiteJob {

	public PortalAcceptanceTestSuiteJob(String jobName) {
		this(jobName, "default");
	}

	public PortalAcceptanceTestSuiteJob(String jobName, String testSuiteName) {
		super(jobName);

		_testSuiteName = testSuiteName;
	}

	@Override
	public Set<String> getBatchNames() {
		Properties jobProperties = getJobProperties();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			testBatchNames = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.names");
		}

		Set<String> testBatchNamesSet = getSetFromString(testBatchNames);

		if (!_testSuiteName.equals("relevant")) {
			return testBatchNamesSet;
		}

		String stableTestBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[stable]");

		if (stableTestBatchNames != null) {
			testBatchNamesSet.addAll(getSetFromString(stableTestBatchNames));
		}

		return testBatchNamesSet;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names.smoke", getBranchName(),
			getTestSuiteName());

		if (testBatchNames == null) {
			return new HashSet<>();
		}

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		Properties jobProperties = getJobProperties();

		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			jobProperties,
			"test.batch.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.dist.app.servers");
		}

		Set<String> testBatchDistAppServersSet = getSetFromString(
			testBatchDistAppServers);

		if (!_testSuiteName.equals("relevant")) {
			return testBatchDistAppServersSet;
		}

		String stableTestBatchDistAppServers =
			JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.dist.app.servers[stable]");

		if (stableTestBatchDistAppServers != null) {
			testBatchDistAppServersSet.addAll(
				getSetFromString(stableTestBatchDistAppServers));
		}

		return testBatchDistAppServersSet;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	private final String _testSuiteName;

}