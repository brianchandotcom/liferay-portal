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

import java.io.File;

import java.util.Collections;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalReleaseJob
	extends BaseJob implements BatchDependentJob, PortalTestClassJob {

	public BasePortalReleaseJob(String jobName, String portalBranchName) {
		super(jobName);

		_portalBranchName = portalBranchName;

		_jenkinsGitWorkingDirectory =
			JenkinsResultsParserUtil.getJenkinsGitWorkingDirectory();

		_portalGitWorkingDirectory =
			JenkinsResultsParserUtil.getPortalGitWorkingDirectory(
				portalBranchName);

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(
					_portalGitWorkingDirectory.getWorkingDirectory(),
					"test.properties")));
	}

	@Override
	public Set<String> getBatchNames() {
		String testBatchNamesString = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[" + _portalBranchName + "]");

		Set<String> testBatchNames = getSetFromString(testBatchNamesString);

		return testBatchNames;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names.smoke[" + _portalBranchName + "]");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	protected GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final String _portalBranchName;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;

}