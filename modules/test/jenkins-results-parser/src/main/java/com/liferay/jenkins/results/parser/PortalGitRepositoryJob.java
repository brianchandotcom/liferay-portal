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

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob {

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			throw new RuntimeException("Invalid portal Git working directory");
		}

		return (PortalGitWorkingDirectory)gitWorkingDirectory;
	}

	protected PortalGitRepositoryJob(
		String jobName, BuildProfile buildProfile) {

		super(jobName, buildProfile);

		init();
	}

	protected GitWorkingDirectory getNewGitWorkingDirectory() {
		return GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
			getBranchName());
	}

	@Override
	protected Set<String> getRawBatchNames() {
		return getSetFromString(
			JenkinsResultsParserUtil.getProperty(
				getJobProperties(), "test.batch.names"));
	}

	protected void init() {
		GitWorkingDirectory jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/build.properties"));

		gitWorkingDirectory = getNewGitWorkingDirectory();

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(
			new File(gitRepositoryDir, "tools/sdk/build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));

		readJobProperties();
	}

}