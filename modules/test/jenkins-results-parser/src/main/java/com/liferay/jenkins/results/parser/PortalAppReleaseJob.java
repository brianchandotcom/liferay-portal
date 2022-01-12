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
import java.util.List;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalAppReleaseJob extends BaseJob implements PortalTestClassJob {

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	@Override
	public List<String> getJobPropertyOptions() {
		List<String> jobPropertyOptions = super.getJobPropertyOptions();

		jobPropertyOptions.add(
			_portalGitWorkingDirectory.getUpstreamBranchName());

		return jobPropertyOptions;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	@Override
	public boolean isSegmentEnabled() {
		return true;
	}

	protected PortalAppReleaseJob(
		String jobName, BuildProfile buildProfile, String portalBranchName) {

		super(jobName, buildProfile);

		GitWorkingDirectory jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/build.properties"));

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				portalBranchName);

		jobPropertiesFiles.add(
			new File(
				_portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		readJobProperties();
	}

	@Override
	protected Set<String> getRawBatchNames() {
		BuildProfile buildProfile = getBuildProfile();

		if (buildProfile == null) {
			buildProfile = BuildProfile.PORTAL;
		}

		String testBatchName = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names", false, getJobName(),
			buildProfile.toString());

		if (JenkinsResultsParserUtil.isNullOrEmpty(testBatchName)) {
			testBatchName = JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "test.batch.names", false, getJobName(),
				_portalGitWorkingDirectory.getUpstreamBranchName(),
				buildProfile.toString());
		}

		return getSetFromString(testBatchName);
	}

	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;

}