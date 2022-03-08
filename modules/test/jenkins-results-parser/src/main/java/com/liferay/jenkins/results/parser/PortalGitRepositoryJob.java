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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob {

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			throw new RuntimeException("Invalid portal Git working directory");
		}

		return (PortalGitWorkingDirectory)gitWorkingDirectory;
	}

	protected PortalGitRepositoryJob(JSONObject jsonObject) {
		super(jsonObject);

		_initialize(null);
	}

	protected PortalGitRepositoryJob(
		String jobName, BuildProfile buildProfile) {

		this(jobName, buildProfile, null, null);
	}

	protected PortalGitRepositoryJob(
		String jobName, BuildProfile buildProfile, String upstreamBranchName,
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		super(jobName, buildProfile, upstreamBranchName);

		_initialize(portalGitWorkingDirectory);
	}

	private void _initialize(
		PortalGitWorkingDirectory portalGitWorkingDirectory) {

		if (portalGitWorkingDirectory != null) {
			gitWorkingDirectory = portalGitWorkingDirectory;
		}
		else {
			gitWorkingDirectory =
				GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
					getUpstreamBranchName());
		}

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(
			new File(gitRepositoryDir, "tools/sdk/build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));
	}

}