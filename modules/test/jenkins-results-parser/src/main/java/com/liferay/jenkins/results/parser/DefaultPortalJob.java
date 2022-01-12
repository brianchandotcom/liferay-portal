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

import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalJob
	extends BaseJob implements PortalTestClassJob, TestSuiteJob {

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		if (_portalGitWorkingDirectory != null) {
			return _portalGitWorkingDirectory;
		}

		File portalRepositoryDir = new File(".");

		File portalTestPropertiesFile = new File(
			portalRepositoryDir, "test.properties");

		Properties portalTestProperties =
			JenkinsResultsParserUtil.getProperties(portalTestPropertiesFile);

		String portalUpstreamBranchName = portalTestProperties.getProperty(
			"liferay.portal.branch", "master");

		String portalRepositoryName = "liferay-portal";

		if (!portalUpstreamBranchName.equals("master")) {
			portalRepositoryName += "-ee";
		}

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				portalUpstreamBranchName, portalRepositoryDir,
				portalRepositoryName);

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			throw new RuntimeException("Invalid portal Git working directory");
		}

		_portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)gitWorkingDirectory;

		return _portalGitWorkingDirectory;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	protected DefaultPortalJob(
		String jobName, BuildProfile buildProfile, String testSuiteName) {

		super(jobName, buildProfile);

		_testSuiteName = testSuiteName;

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		File portalWorkingDirectory =
			portalGitWorkingDirectory.getWorkingDirectory();

		jobPropertiesFiles.add(
			new File(portalWorkingDirectory, "build.properties"));
		jobPropertiesFiles.add(
			new File(portalWorkingDirectory, "test.properties"));

		readJobProperties();
	}

	private PortalGitWorkingDirectory _portalGitWorkingDirectory;
	private final String _testSuiteName;

}