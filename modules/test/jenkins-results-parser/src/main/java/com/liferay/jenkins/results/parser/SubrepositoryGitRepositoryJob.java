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

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryGitRepositoryJob
	extends GitRepositoryJob implements SubrepositoryTestClassJob {

	@Override
	public Set<String> getBatchNames() {
		Properties jobProperties = getJobProperties();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[" + getBranchName() + "]");

		if (testBatchNames == null) {
			testBatchNames = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.names");
		}

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		String distTypes = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "subrepo.dist.app.servers");

		return new TreeSet<>(Arrays.asList(distTypes.split(",")));
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkGitRepositoryDir();

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			getBranchName(), gitRepositoryDir.getPath());

		return gitWorkingDirectory;
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		if (portalGitWorkingDirectory == null) {
			portalGitWorkingDirectory =
				JenkinsResultsParserUtil.getPortalGitWorkingDirectory(
					getBranchName());
		}

		return portalGitWorkingDirectory;
	}

	@Override
	public SubrepositoryGitWorkingDirectory
		getSubrepositoryGitWorkingDirectory() {

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof
				SubrepositoryGitWorkingDirectory)) {

			throw new RuntimeException(
				"Invalid subrepository Git working directory");
		}

		return (SubrepositoryGitWorkingDirectory)gitWorkingDirectory;
	}

	public boolean getTestRunValidation() {
		return testRunValidation;
	}

	@Override
	public void setGitRepositoryDir(File repositoryDir) {
		String dirName = repositoryDir.getName();

		if (!dirName.endsWith("-private")) {
			dirName += "-private";

			repositoryDir = new File(repositoryDir.getParentFile(), dirName);
		}

		super.setGitRepositoryDir(repositoryDir);
	}

	protected SubrepositoryGitRepositoryJob(
		String jobName, String repositoryName) {

		super(jobName);

		gitWorkingDirectory =
			GitWorkingDirectoryFactory.newSubrepositoryGitWorkingDirectory(
				jobName, repositoryName);

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));

		jobPropertiesFiles.add(
			new File(
				JenkinsResultsParserUtil.combine(
					buildProperties.getProperty("base.repository.dir"),
					"/liferay-jenkins-ee/commands/dependencies",
					"/test-subrepository-batch.properties")));

		readJobProperties();
	}

	protected PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected boolean testRunValidation;

}