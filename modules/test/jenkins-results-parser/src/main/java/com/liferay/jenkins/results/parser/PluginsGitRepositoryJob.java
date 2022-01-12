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

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author Peter Yoo
 */
public abstract class PluginsGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob {

	@Override
	public String getBranchName() {
		return _branchName;
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		String pluginsBranchName = getBranchName();

		String workingDirectoryPath = getBuildPropertyValue(
			JenkinsResultsParserUtil.combine(
				"plugins.dir[", pluginsBranchName, "]"));

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			pluginsBranchName, workingDirectoryPath);

		return gitWorkingDirectory;
	}

	public abstract List<File> getPluginsTestBaseDirs();

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return portalGitWorkingDirectory;
	}

	protected PluginsGitRepositoryJob(
		String jobName, BuildProfile buildProfile, String branchName) {

		super(jobName, buildProfile);

		_branchName = branchName;

		getGitWorkingDirectory();

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		String portalBranchName = getBuildPropertyValue(
			JenkinsResultsParserUtil.combine(
				"plugins.portal.branch.name[", getBranchName(), "]"));

		File portalGitRepositoryDir = new File(
			getBuildPropertyValue(
				JenkinsResultsParserUtil.combine(
					"portal.dir[", portalBranchName, "]")));

		jobPropertiesFiles.add(
			new File(portalGitRepositoryDir, "test.properties"));

		portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					portalBranchName, portalGitRepositoryDir.getPath());
	}

	protected String getBuildPropertyValue(String buildPropertyName) {
		if (buildProperties == null) {
			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to get build properties", ioException);
			}
		}

		return buildProperties.getProperty(buildPropertyName);
	}

	protected Properties buildProperties;
	protected PortalGitWorkingDirectory portalGitWorkingDirectory;

	private final String _branchName;

}