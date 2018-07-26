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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class RepositoryJob extends BaseJob {

	public String getBranchName() {
		if (_branchName != null) {
			return _branchName;
		}

		Matcher matcher = _jobNamePattern.matcher(jobName);

		if (matcher.find()) {
			_branchName = matcher.group("branchName");

			return _branchName;
		}

		_branchName = "master";

		return _branchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		if (gitWorkingDirectory != null) {
			return gitWorkingDirectory;
		}

		checkRepositoryDir();

		gitWorkingDirectory = GitWorkingDirectoryFactory.newGitWorkingDirectory(
			getBranchName(), repositoryDir.getAbsolutePath());

		return gitWorkingDirectory;
	}

	public void setRepositoryDir(File repositoryDir) {
		if (this.repositoryDir != null) {
			throw new IllegalStateException(
				"Repository directory is already set to " +
					this.repositoryDir.getPath());
		}

		this.repositoryDir = repositoryDir;
	}

	protected RepositoryJob(String jobName) {
		super(jobName);
	}

	protected void checkRepositoryDir() {
		if (repositoryDir == null) {
			throw new IllegalStateException("Repository directory is not set");
		}

		if (!repositoryDir.exists()) {
			throw new IllegalStateException(
				repositoryDir.getPath() + " does not exist");
		}
	}

	protected GitWorkingDirectory gitWorkingDirectory;
	protected File repositoryDir;

	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<branchName>[^\\)]+)\\)");

	private String _branchName;

}