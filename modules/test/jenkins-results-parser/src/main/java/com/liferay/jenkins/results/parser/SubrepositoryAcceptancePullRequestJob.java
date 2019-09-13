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

import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryAcceptancePullRequestJob
	extends SubrepositoryGitRepositoryJob implements TestSuiteJob {

	public SubrepositoryAcceptancePullRequestJob(
		String jobName, String testSuiteName, String repositoryName) {

		super(jobName, repositoryName);

		_testSuiteName = testSuiteName;

		_setTestRunValidation();
	}

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			return super.getBatchNames();
		}

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		Properties jobProperties = getJobProperties();

		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			jobProperties, "subrepo.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
				jobProperties, "subrepo.dist.app.servers");
		}

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	private void _setTestRunValidation() {
		Properties jobProperties = getJobProperties();

		String testRunValidationProperty = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.run.validation[" + _testSuiteName + "]");

		if (testRunValidationProperty == null) {
			testRunValidationProperty = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.run.validation");
		}

		testRunValidation = Boolean.parseBoolean(testRunValidationProperty);
	}

	private final String _testSuiteName;

}