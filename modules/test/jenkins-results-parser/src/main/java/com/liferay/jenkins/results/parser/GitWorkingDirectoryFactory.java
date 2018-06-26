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
import java.util.Properties;

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

		try {
			String repositoryDirPath = repositoryDir.getCanonicalPath();

			if (_gitWorkingDirectories.containsKey(repositoryDirPath)) {
				return _gitWorkingDirectories.get(repositoryDirPath);
			}

			if (repositoryName.startsWith("com-liferay-")) {
				_gitWorkingDirectories.put(
					repositoryDirPath,
					new SubrepositoryGitWorkingDirectory(
						upstreamBranchName, repositoryDirPath, repositoryName));
			}
			else if (repositoryName.startsWith("liferay-portal")) {
				_gitWorkingDirectories.put(
					repositoryDirPath,
					new PortalGitWorkingDirectory(
						upstreamBranchName, repositoryDirPath, repositoryName));
			}
			else {
				_gitWorkingDirectories.put(
					repositoryDirPath,
					new GitWorkingDirectory(
						upstreamBranchName, repositoryDirPath, repositoryName));
			}

			return _gitWorkingDirectories.get(repositoryDirPath);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, String repositoryName) {

		File repositoryDir = new File(
			_getRepositoryDirPath(upstreamBranchName, repositoryName));

		return newGitWorkingDirectory(
			upstreamBranchName, repositoryDir, repositoryName);
	}

	private static String _getRepositoryDirPath(
		String branchName, String repositoryName) {

		StringBuilder sb = new StringBuilder();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			sb.append(buildProperties.getProperty("base.repository.dir"));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		sb.append("/");
		sb.append(repositoryName);

		if (repositoryName.startsWith("liferay-portal") &&
			!branchName.equals("master")) {

			sb.append("-");
			sb.append(branchName);
		}
		else if (repositoryName.startsWith("com-liferay-") &&
				 !repositoryName.endsWith("-private")) {

			sb.append("-private");
		}

		return sb.toString();
	}

	private static final Map<String, GitWorkingDirectory>
		_gitWorkingDirectories = new HashMap<>();

}