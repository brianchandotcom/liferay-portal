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

import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;
import com.liferay.jenkins.results.parser.test.clazz.group.SegmentTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryGitRepositoryJob
	extends GitRepositoryJob
	implements BatchDependentJob, SubrepositoryTestClassJob {

	@Override
	public String getBranchName() {
		return _portalUpstreamBranchName;
	}

	@Override
	public List<AxisTestClassGroup> getDependentAxisTestClassGroups() {
		List<AxisTestClassGroup> axisTestClassGroups = new ArrayList<>();

		for (BatchTestClassGroup batchTestClassGroup :
				getDependentBatchTestClassGroups()) {

			axisTestClassGroups.addAll(
				batchTestClassGroup.getAxisTestClassGroups());
		}

		return axisTestClassGroups;
	}

	@Override
	public Set<String> getDependentBatchNames() {
		return getFilteredBatchNames(getRawDependentBatchNames());
	}

	@Override
	public List<BatchTestClassGroup> getDependentBatchTestClassGroups() {
		return getBatchTestClassGroups(getRawDependentBatchNames());
	}

	@Override
	public Set<String> getDependentSegmentNames() {
		return getFilteredSegmentNames(getRawDependentBatchNames());
	}

	@Override
	public List<SegmentTestClassGroup> getDependentSegmentTestClassGroups() {
		return getSegmentTestClassGroups(getRawDependentBatchNames());
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
				GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
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

	@Override
	public boolean isValidationRequired() {
		return validationRequired;
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
		String jobName, BuildProfile buildProfile, String repositoryName,
		String portalUpstreamBranchName) {

		super(jobName, buildProfile);

		_portalUpstreamBranchName = portalUpstreamBranchName;

		gitWorkingDirectory =
			GitWorkingDirectoryFactory.newSubrepositoryGitWorkingDirectory(
				portalUpstreamBranchName, repositoryName);

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build properties", ioException);
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

	protected Set<String> getRawDependentBatchNames() {
		JobProperty jobProperty = getJobProperty("test.batch.names.smoke");

		return getSetFromString(jobProperty.getValue());
	}

	protected PortalGitWorkingDirectory portalGitWorkingDirectory;
	protected boolean validationRequired;

	private final String _portalUpstreamBranchName;

}