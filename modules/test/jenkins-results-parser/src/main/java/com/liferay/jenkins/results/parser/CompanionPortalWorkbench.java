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
public class CompanionPortalWorkbench extends BasePortalWorkbench {

	protected CompanionPortalWorkbench(PortalWorkbench portalWorkbench) {
		super(
			_getGitHubURL(portalWorkbench),
			_getUpstreamBranchName(portalWorkbench),
			_getBranchSHA(portalWorkbench));
	}

	private static String _getBranchSHA(PortalWorkbench portalWorkbench) {
		String gitCommitFileContent = _getGitCommitFileContent(portalWorkbench);

		if (gitCommitFileContent.matches("[0-9a-f]{7,40}")) {
			return gitCommitFileContent;
		}

		return null;
	}

	private static String _getGitCommitFileContent(
		PortalWorkbench portalWorkbench) {

		String portalUpstreamBranchName =
			portalWorkbench.getUpstreamBranchName();

		String gitCommitFileName = "git-commit-portal";

		if (!portalUpstreamBranchName.endsWith("-private")) {
			gitCommitFileName += "-private";
		}

		return portalWorkbench.getFileContent(gitCommitFileName);
	}

	private static String _getGitHubURL(PortalWorkbench portalWorkbench) {
		String gitCommitFileContent = _getGitCommitFileContent(portalWorkbench);

		if (GitUtil.isValidGitHubRefURL(gitCommitFileContent) ||
			PullRequest.isValidGitHubPullRequestURL(gitCommitFileContent)) {

			return gitCommitFileContent;
		}

		String upstreamBranchName = _getUpstreamBranchName(portalWorkbench);

		StringBuilder sb = new StringBuilder();

		sb.append("https://github.com/liferay/liferay-portal");

		if (!upstreamBranchName.equals("master")) {
			sb.append("-ee");
		}

		sb.append("/tree/");
		sb.append(upstreamBranchName);

		return sb.toString();
	}

	private static String _getUpstreamBranchName(
		PortalWorkbench portalWorkbench) {

		String portalUpstreamBranchName =
			portalWorkbench.getUpstreamBranchName();

		if (portalUpstreamBranchName.endsWith("-private")) {
			return portalUpstreamBranchName.replace("-private", "");
		}

		return portalUpstreamBranchName + "-private";
	}

}