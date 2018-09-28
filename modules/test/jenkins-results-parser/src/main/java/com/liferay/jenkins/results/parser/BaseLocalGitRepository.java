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
import java.util.List;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public abstract class BaseLocalGitRepository
	extends BaseGitRepository implements LocalGitRepository {

	@Override
	public File getDirectory() {
		return getFile("directory");
	}

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		if (_gitWorkingDirectory != null) {
			return _gitWorkingDirectory;
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				getUpstreamBranchName(), getDirectory(), getName());

		return _gitWorkingDirectory;
	}

	@Override
	public String getUpstreamBranchName() {
		return getString("upstream_branch_name");
	}

	protected BaseLocalGitRepository(String name, String upstreamBranchName) {
		super(name);

		_setDirectory(upstreamBranchName);
		_setUpstreamBranchName(upstreamBranchName);

		validateKeys(_REQUIRED_KEYS);
	}

	protected String getDefaultRelativeGitRepositoryDirPath() {
		return getName();
	}

	private void _setDirectory(String upstreamBranchName) {
		File directory = null;

		String repositoryDirPath = JenkinsResultsParserUtil.getProperty(
			getRepositoryProperties(), "repository.dir", getName(),
			upstreamBranchName);

		if (repositoryDirPath != null) {
			directory = new File(repositoryDirPath);
		}

		if ((directory == null) || !directory.exists()) {
			directory = new File(
				JenkinsResultsParserUtil.getBaseGitRepositoryDir(),
				getDefaultRelativeGitRepositoryDirPath());
		}

		try {
			put("directory", directory.getCanonicalPath());
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find Git repository directory.\n",
					"Please set this location in repository.dir[", getName(),
					"][", getUpstreamBranchName(),
					"] in repository.properties."),
				ioe);
		}
	}

	private void _setUpstreamBranchName(String upstreamBranchName) {
		if ((upstreamBranchName == null) || upstreamBranchName.isEmpty()) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		String upstreamBranchNamesString = JenkinsResultsParserUtil.getProperty(
			getRepositoryProperties(), "upstream.branch.names", getName());

		if (upstreamBranchNamesString != null) {
			List<String> upstreamBranchNames = Arrays.asList(
				upstreamBranchNamesString.split(","));

			if (!upstreamBranchNames.contains(upstreamBranchName)) {
				throw new IllegalArgumentException(
					"Invalid upstream branch name: " + upstreamBranchName);
			}
		}

		put("upstream_branch_name", upstreamBranchName);
	}

	private static final String[] _REQUIRED_KEYS =
		{"directory", "upstream_branch_name"};

	private GitWorkingDirectory _gitWorkingDirectory;

}