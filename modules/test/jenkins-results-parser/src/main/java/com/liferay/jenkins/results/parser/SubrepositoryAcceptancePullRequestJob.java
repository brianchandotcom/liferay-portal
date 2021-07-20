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

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryAcceptancePullRequestJob
	extends SubrepositoryGitRepositoryJob implements TestSuiteJob {

	public SubrepositoryAcceptancePullRequestJob(
		String jobName, BuildProfile buildProfile, String testSuiteName,
		String repositoryName) {

		super(jobName, buildProfile, repositoryName);

		_testSuiteName = testSuiteName;

		_setValidationRequired();
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.dist.app.servers",
			getTestSuiteName());

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	@Override
	protected Set<String> getRawBatchNames() {
		String batchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names", getBranchName(),
			getTestSuiteName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(batchNames)) {
			return super.getRawBatchNames();
		}

		return getSetFromString(batchNames);
	}

	protected Set<String> getRawDependentBatchNames() {
		String dependentBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names.smoke", getBranchName(),
			getTestSuiteName());

		if (JenkinsResultsParserUtil.isNullOrEmpty(dependentBatchNames)) {
			return super.getRawDependentBatchNames();
		}

		return getSetFromString(dependentBatchNames);
	}

	private void _setValidationRequired() {
		String testRunValidationProperty = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.run.validation", getTestSuiteName());

		validationRequired = Boolean.parseBoolean(testRunValidationProperty);
	}

	private final String _testSuiteName;

}