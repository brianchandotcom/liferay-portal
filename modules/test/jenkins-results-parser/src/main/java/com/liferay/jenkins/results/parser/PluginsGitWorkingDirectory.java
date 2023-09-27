/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PluginsGitWorkingDirectory extends GitWorkingDirectory {

	protected PluginsGitWorkingDirectory(
			String portalUpstreamBranchName, String workingDirectoryPath)
		throws IOException {

		super(
			_getPluginsUpstreamBranchName(portalUpstreamBranchName),
			workingDirectoryPath);
	}

	protected PluginsGitWorkingDirectory(
			String portalUpstreamBranchName, String workingDirectoryPath,
			String gitRepositoryName)
		throws IOException {

		super(
			_getPluginsUpstreamBranchName(portalUpstreamBranchName),
			workingDirectoryPath, gitRepositoryName);
	}

	private static String _getPluginsUpstreamBranchName(
		String portalUpstreamBranchName) {

		if (portalUpstreamBranchName.contains("7.0.x") ||
			portalUpstreamBranchName.contains("7.1.x") ||
			portalUpstreamBranchName.contains("7.2.x") ||
			portalUpstreamBranchName.contains("master")) {

			return "7.0.x";
		}

		return portalUpstreamBranchName;
	}

}