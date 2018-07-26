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

/**
 * @author Peter Yoo
 */
public class LocalRepository extends BaseRepository {

	public File getDirectory() {
		return directory;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public RemoteRepository getUpstreamRemoteRepository() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		return RepositoryFactory.getRemoteRepository(
			gitWorkingDirectory.getRemote("upstream"));
	}

	public void setup() {
		System.out.println("##");
		System.out.println("## " + getDirectory());
		System.out.println("##");

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		gitWorkingDirectory.reset("--hard HEAD");

		gitWorkingDirectory.clean();
	}

	protected LocalRepository(String name, String upstreamBranchName) {
		super(name);

		if ((upstreamBranchName == null) || upstreamBranchName.isEmpty()) {
			throw new IllegalArgumentException("upstreamBranchName is null");
		}

		_upstreamBranchName = upstreamBranchName;

		Properties repositoryProperties = _getRepositoryProperties();

		String repositoryPropertyKey = getRepositoryPropertyKey();

		if (repositoryProperties.containsKey(repositoryPropertyKey)) {
			directory = new File(
				repositoryProperties.getProperty(repositoryPropertyKey));
		}
		else {
			directory = new File(
				JenkinsResultsParserUtil.getBaseRepositoryDir(),
				getDefaultRelativeRepositoryDirPath());
		}

		if (!directory.exists()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find repository directory for ", getName(),
					" at ", directory.toString()));
		}

		File dotGit = new File(directory, ".git");

		if (!dotGit.exists()) {
			throw new IllegalArgumentException(
				directory + " is not a valid repository");
		}

		_gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				upstreamBranchName, getDirectory(), getName());
	}

	protected String getDefaultRelativeRepositoryDirPath() {
		return getName();
	}

	protected String getRepositoryPropertyKey() {
		return JenkinsResultsParserUtil.combine(
			"repository.dir[", name, "/" + getUpstreamBranchName(), "]");
	}

	protected final File directory;

	private static Properties _getRepositoryProperties() {
		if (_repositoryProperties != null) {
			return _repositoryProperties;
		}

		File repositoryPropertiesFile = new File("repository.properties");

		_repositoryProperties = JenkinsResultsParserUtil.getProperties(
			repositoryPropertiesFile);

		return _repositoryProperties;
	}

	private static Properties _repositoryProperties;

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final String _upstreamBranchName;

}