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

/**
 * @author Michael Hashimoto
 */
public class PluginsWorkspaceGitRepository extends BaseWorkspaceGitRepository {

	protected PluginsWorkspaceGitRepository(
		PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		super(
			_getGitHubURL(portalWorkspaceGitRepository),
			_getUpstreamBranchName(portalWorkspaceGitRepository),
			_getBranchSHA(portalWorkspaceGitRepository));
	}

	protected PluginsWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(gitHubURL, upstreamBranchName, branchSHA);

		String name = getName();

		if (upstreamBranchName.startsWith("ee-") ||
			upstreamBranchName.endsWith("-private")) {

			if (!name.endsWith("-ee")) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"The local Git repository, ", name,
						" should not be used with upstream branch ",
						upstreamBranchName, ". Use ", name, "-ee."));
			}
		}

		if (upstreamBranchName.contains("master") ||
			upstreamBranchName.equals("7.0.x-private") ||
			upstreamBranchName.contains("7.1.x")) {

			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"The upstream branch, ", upstreamBranchName,
					" should not be used with Git repository ", name, "."));
		}
	}

	@Override
	protected String getDefaultRelativeGitRepositoryDirPath() {
		String name = getName();
		String upstreamBranchName = getUpstreamBranchName();

		if (upstreamBranchName.equals("master")) {
			return name.replace("-ee", "");
		}

		return JenkinsResultsParserUtil.combine(
			name.replace("-ee", ""), "-", upstreamBranchName);
	}

	private static String _getBranchSHA(
		PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		String gitCommitFileContent = _getGitCommitFileContent(
			portalWorkspaceGitRepository);

		if (gitCommitFileContent.matches("[0-9a-f]{7,40}")) {
			return gitCommitFileContent;
		}

		return null;
	}

	private static String _getGitCommitFileContent(
		PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		return portalWorkspaceGitRepository.getFileContent(
			"git-commit-plugins");
	}

	private static String _getGitHubURL(
		PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		String gitCommitFileContent = _getGitCommitFileContent(
			portalWorkspaceGitRepository);

		if (GitUtil.isValidGitHubRefURL(gitCommitFileContent) ||
			PullRequest.isValidGitHubPullRequestURL(gitCommitFileContent)) {

			return gitCommitFileContent;
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/liferay/liferay-plugins-ee/tree/",
			_getUpstreamBranchName(portalWorkspaceGitRepository));
	}

	private static String _getUpstreamBranchName(
		PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		String portalUpstreamBranchName =
			portalWorkspaceGitRepository.getUpstreamBranchName();

		if (portalUpstreamBranchName.contains("7.0.x") ||
			portalUpstreamBranchName.contains("7.1.x") ||
			portalUpstreamBranchName.contains("master")) {

			return "7.0.x";
		}

		return portalUpstreamBranchName;
	}

}