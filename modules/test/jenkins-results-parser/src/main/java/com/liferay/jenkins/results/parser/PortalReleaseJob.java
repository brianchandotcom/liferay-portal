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
import java.io.IOException;

import java.util.Collections;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalReleaseJob
	extends BaseJob implements PortalTestClassJob, BatchDependentJob {

	public PortalReleaseJob(String jobName, String portalBranchName) {
		super(jobName);

		_portalBranchName = portalBranchName;

		try {
			_jenkinsGitWorkingDirectory =
				JenkinsResultsParserUtil.getJenkinsGitWorkingDirectory();

			_portalGitWorkingDirectory =
				JenkinsResultsParserUtil.getPortalGitWorkingDirectory(
					portalBranchName);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to create a Git working directory", ioe);
		}

		jobProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				_jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/dependencies/test-portal-release.properties"));

		jobProperties.putAll(
			JenkinsResultsParserUtil.getProperties(
				new File(
					_portalGitWorkingDirectory.getWorkingDirectory(),
					"test.properties")));
	}

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[" + _portalBranchName + "]");

		return getSetFromString(testBatchNames);
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

	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final String _portalBranchName;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;

}