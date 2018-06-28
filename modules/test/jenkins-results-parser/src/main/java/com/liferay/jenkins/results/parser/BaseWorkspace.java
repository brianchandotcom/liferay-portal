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
 * @author Michael Hashimoto
 */
public class BaseWorkspace {

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public File getRepositoryDir() {
		return _repositoryDir;
	}

	public String getRepositoryType() {
		return _repositoryType;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public String getUpstreamRepositoryName() {
		return _repositoryType;
	}

	public void setupWorkspace() {
		System.out.println("##");
		System.out.println("## " + getRepositoryDir());
		System.out.println("##");

		_gitWorkingDirectory.reset("--hard HEAD");

		_gitWorkingDirectory.clean();
	}

	protected static File getBaseRepositoryDir() {
		if (_baseRepositoryDir != null) {
			return _baseRepositoryDir;
		}

		Properties buildProperties = new Properties();

		try {
			buildProperties.putAll(
				JenkinsResultsParserUtil.getBuildProperties());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		_baseRepositoryDir = new File(
			buildProperties.getProperty("base.repository.dir"));

		return _baseRepositoryDir;
	}

	protected BaseWorkspace(String repositoryType, String upstreamBranchName) {
		_repositoryType = repositoryType;
		_upstreamBranchName = upstreamBranchName;

		validateRepositoryType(repositoryType);

		File repositoryDir = _getRepositoryDir();

		try {
			_repositoryDir = new File(repositoryDir.getCanonicalPath());

			_gitWorkingDirectory =
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					getUpstreamBranchName(), getRepositoryDir(),
					getUpstreamRepositoryName());
		}
		catch (Exception e) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Please set '", _getRepositoryDirKey(),
					"' to a valid repository in ",
					_workspacePropertiesFile.toString()),
				e);
		}
	}

	protected File getDefaultRepositoryDir() {
		return new File(getBaseRepositoryDir(), getUpstreamRepositoryName());
	}

	protected void validateRepositoryType(String repositoryType) {
		if (repositoryType == null) {
			throw new RuntimeException("The repositoryType cannot be null");
		}
	}

	private static Properties _getWorkspaceProperties() {
		if (_workspaceProperties != null) {
			return _workspaceProperties;
		}

		File currentDir = new File(".");

		try {
			_workspacePropertiesFile = new File(
				new File(currentDir.getCanonicalPath()),
				"workspace.properties");

			_workspaceProperties = JenkinsResultsParserUtil.getProperties(
				_workspacePropertiesFile);

			return _workspaceProperties;
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private File _getRepositoryDir() {
		Properties workspaceProperties = _getWorkspaceProperties();

		String repositoryDirKey = _getRepositoryDirKey();

		if (workspaceProperties.containsKey(repositoryDirKey)) {
			return new File(workspaceProperties.getProperty(repositoryDirKey));
		}

		return getDefaultRepositoryDir();
	}

	private String _getRepositoryDirKey() {
		return JenkinsResultsParserUtil.combine(
			"repository.dir[", getRepositoryType(), "/",
			getUpstreamBranchName(), "]");
	}

	private static File _baseRepositoryDir;
	private static Properties _workspaceProperties;
	private static File _workspacePropertiesFile;

	private final GitWorkingDirectory _gitWorkingDirectory;
	private final File _repositoryDir;
	private final String _repositoryType;
	private final String _upstreamBranchName;

}