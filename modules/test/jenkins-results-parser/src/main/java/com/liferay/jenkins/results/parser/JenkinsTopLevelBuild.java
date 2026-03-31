/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class JenkinsTopLevelBuild
	extends DefaultTopLevelBuild implements WorkspaceBuild {

	public JenkinsTopLevelBuild(String buildURL) {
		super(buildURL);
	}

	public JenkinsTopLevelBuild(String buildURL, TopLevelBuild topLevelBuild) {
		super(buildURL, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		return "liferay-jenkins-ee";
	}

	@Override
	public Workspace getWorkspace() {
		Workspace workspace = WorkspaceFactory.newWorkspace(
			getBaseGitRepositoryName(), getBranchName(), getJobName());

		String jenkinsGitHubURL = _getJenkinsGitHubURL();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jenkinsGitHubURL)) {
			WorkspaceGitRepository workspaceGitRepository =
				workspace.getPrimaryWorkspaceGitRepository();

			workspaceGitRepository.setGitHubURL(jenkinsGitHubURL);
		}

		return workspace;
	}

	private String _getJenkinsGitHubURL() {
		String jenkinsGitHubURL = getParameterValue("JENKINS_GITHUB_URL");

		if (JenkinsResultsParserUtil.isURL(jenkinsGitHubURL)) {
			return jenkinsGitHubURL;
		}

		String jenkinsGithubBranchName = getParameterValue(
			"JENKINS_GITHUB_BRANCH_NAME");
		String jenkinsGithubBranchUsername = getParameterValue(
			"JENKINS_GITHUB_BRANCH_USERNAME");

		String baseGitRepositoryName = getBaseGitRepositoryName();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(jenkinsGithubBranchName) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(
				jenkinsGithubBranchUsername)) {

			return JenkinsResultsParserUtil.combine(
				"https://github.com/", jenkinsGithubBranchUsername, "/",
				baseGitRepositoryName, "/tree/", jenkinsGithubBranchName);
		}

		String gitHubPullRequestNumber = getParameterValue(
			"GITHUB_PULL_REQUEST_NUMBER");
		String gitHubReceiverUsername = getParameterValue(
			"GITHUB_RECEIVER_USERNAME");

		if (!JenkinsResultsParserUtil.isNullOrEmpty(gitHubPullRequestNumber) &&
			!JenkinsResultsParserUtil.isNullOrEmpty(gitHubReceiverUsername)) {

			return JenkinsResultsParserUtil.combine(
				"https://github.com/", gitHubReceiverUsername, "/",
				baseGitRepositoryName, "/pull/", gitHubPullRequestNumber);
		}

		return JenkinsResultsParserUtil.combine(
			"https://github.com/liferay/", baseGitRepositoryName, "/tree/",
			getBranchName());
	}

}