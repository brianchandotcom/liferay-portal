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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class GitWorkingDirectoryFactory {

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, File repositoryDir, String repositoryName) {

		if (!repositoryDir.exists()) {
			throw new RuntimeException(
				"Directory path not found " + repositoryDir);
		}

		String repositoryDirName = repositoryDir.getName();

		if (_gitWorkingDirectories.containsKey(repositoryDirName)) {
			return _gitWorkingDirectories.get(repositoryDirName);
		}

		try {
			GitWorkingDirectory gitWorkingDirectory = null;
			String repositoryDirPath = repositoryDir.getCanonicalPath();

			if (repositoryDirName.startsWith("com-liferay-")) {
				gitWorkingDirectory = new SubrepositoryGitWorkingDirectory(
					upstreamBranchName, repositoryDirPath, repositoryName);
			}
			else if (repositoryDirName.startsWith("liferay-plugins")) {
				gitWorkingDirectory = new PluginsGitWorkingDirectory(
					upstreamBranchName, repositoryDirPath, repositoryName);
			}
			else if (repositoryDirName.startsWith("liferay-portal")) {
				gitWorkingDirectory = new PortalGitWorkingDirectory(
					upstreamBranchName, repositoryDirPath, repositoryName);
			}
			else {
				gitWorkingDirectory = new GitWorkingDirectory(
					upstreamBranchName, repositoryDirPath, repositoryName);
			}

			_gitWorkingDirectories.put(repositoryDirName, gitWorkingDirectory);

			return gitWorkingDirectory;
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create git working directory for directory ",
					repositoryDir.getPath()),
				ioe);
		}
	}

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, String repositoryDirPath) {

		return newGitWorkingDirectory(
			upstreamBranchName, new File(repositoryDirPath), null);
	}

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, String repositoryDirPath,
		String repositoryName) {

		return newGitWorkingDirectory(
			upstreamBranchName, new File(repositoryDirPath), repositoryName);
	}

	private static final Map<String, GitWorkingDirectory>
		_gitWorkingDirectories = new HashMap<>();

}