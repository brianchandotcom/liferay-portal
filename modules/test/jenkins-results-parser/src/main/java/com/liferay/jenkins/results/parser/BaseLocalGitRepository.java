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

import java.util.Properties;

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
		return _gitWorkingDirectory;
	}

	@Override
	public String getUpstreamBranchName() {
		return getString("upstream_branch_name");
	}

	protected BaseLocalGitRepository(String name, String upstreamBranchName) {
		super(name);

		if ((upstreamBranchName == null) || upstreamBranchName.isEmpty()) {
			throw new IllegalArgumentException("Upstream branch name is null");
		}

		putIntoJSONObject("upstream_branch_name", upstreamBranchName);

		Properties repositoryProperties = _getRepositoryProperties();

		String gitRepositoryDirPropertyKey = _getGitRepositoryDirPropertyKey(
			name, upstreamBranchName);

		File directory;

		if (repositoryProperties.containsKey(gitRepositoryDirPropertyKey)) {
			directory = new File(
				repositoryProperties.getProperty(gitRepositoryDirPropertyKey));
		}
		else {
			directory = new File(
				JenkinsResultsParserUtil.getBaseGitRepositoryDir(),
				getDefaultRelativeGitRepositoryDirPath());
		}

		if (!directory.exists()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find Git repository directory for ", name,
					" at ", directory.toString()));
		}

		try {
			putIntoJSONObject("directory", directory.getCanonicalPath());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		File dotGitFile = new File(directory, ".git");

		if (!dotGitFile.exists()) {
			throw new IllegalArgumentException(
				directory + " is not a valid Git repository");
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, directory, name);

		validateJSONObject(_REQUIRED_KEYS);
	}

	protected String getDefaultRelativeGitRepositoryDirPath() {
		return getName();
	}

	private static Properties _getRepositoryProperties() {
		if (_properties != null) {
			return _properties;
		}

		File propertiesFile = new File("repository.properties");

		_properties = JenkinsResultsParserUtil.getProperties(propertiesFile);

		return _properties;
	}

	private String _getGitRepositoryDirPropertyKey(
		String name, String upstreamBranchName) {

		return JenkinsResultsParserUtil.combine(
			"repository.dir[", name, "/" + upstreamBranchName, "]");
	}

	private static final String[] _REQUIRED_KEYS =
		{"directory", "upstream_branch_name"};

	private static Properties _properties;

	private final GitWorkingDirectory _gitWorkingDirectory;

}