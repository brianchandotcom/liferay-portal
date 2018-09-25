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
import java.io.StringReader;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class WorkspaceUtil {

	public static WorkspaceGitRepository getDependencyWorkspaceGitRepository(
		String repositoryType, WorkspaceGitRepository workspaceGitRepository) {

		WorkspaceGitRepositoryData workspaceGitRepositoryData =
			new WorkspaceGitRepositoryData(
				repositoryType, workspaceGitRepository);

		String gitHubURL = workspaceGitRepositoryData.getRepositoryGitHubURL();

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			return GitRepositoryFactory.getDependencyWorkspaceGitRepository(
				repositoryType, workspaceGitRepository, pullRequest,
				workspaceGitRepositoryData.getUpstreamBranchName());
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			return GitRepositoryFactory.getDependencyWorkspaceGitRepository(
				repositoryType, workspaceGitRepository, remoteGitRef,
				workspaceGitRepositoryData.getUpstreamBranchName(),
				workspaceGitRepositoryData.getBranchSHA());
		}

		throw new RuntimeException("Invalid repository GitHub URL");
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName) {

		return getWorkspaceGitRepository(gitHubURL, upstreamBranchName, null);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName, String branchSha) {

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			return GitRepositoryFactory.getWorkspaceGitRepository(
				gitHubURL, pullRequest, upstreamBranchName);
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			return GitRepositoryFactory.getWorkspaceGitRepository(
				gitHubURL, remoteGitRef, upstreamBranchName, branchSha);
		}

		throw new RuntimeException("Invalid repository GitHub URL");
	}

	private static Properties _getWorkspaceProperties() {
		if (_workspaceProperties != null) {
			return _workspaceProperties;
		}

		_workspaceProperties = new Properties();

		try {
			_workspaceProperties.load(
				new StringReader(
					JenkinsResultsParserUtil.toString(
						_WORKSPACE_PROPERTIES_URL, false)));
		}
		catch (IOException ioe) {
			System.out.println(
				"SKIPPED downloading " + _WORKSPACE_PROPERTIES_URL);
		}

		File propertiesFile = new File("workspace.properties");

		_workspaceProperties = JenkinsResultsParserUtil.getProperties(
			propertiesFile);

		return _workspaceProperties;
	}

	private static final String _WORKSPACE_PROPERTIES_URL =
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/workspace.properties";

	private static Properties _workspaceProperties;

	private static class WorkspaceGitRepositoryData {

		public String getBranchSHA() {
			String gitCommitFileContent = _getGitCommitFileContent();

			if ((gitCommitFileContent != null) &&
				gitCommitFileContent.matches("[0-9a-f]{7,40}")) {

				return gitCommitFileContent;
			}

			return null;
		}

		public String getRepositoryGitHubURL() {
			String gitCommitFileContent = _getGitCommitFileContent();

			if ((gitCommitFileContent == null) ||
				gitCommitFileContent.matches("[0-9a-f]{7,40}")) {

				return _getUpstreamGitHubURL();
			}

			return gitCommitFileContent;
		}

		public String getUpstreamBranchName() {
			return JenkinsResultsParserUtil.getProperty(
				_getWorkspaceProperties(), "upstream.branch.name",
				_repositoryType, _getParentUpstreamBranchName());
		}

		private WorkspaceGitRepositoryData(
			String repositoryType,
			WorkspaceGitRepository workspaceGitRepository) {

			_repositoryType = repositoryType;
			_workspaceGitRepository = workspaceGitRepository;
		}

		private String _getGitCommitFileContent() {
			if (_workspaceGitRepository == null) {
				return null;
			}

			String gitCommitFilePath = _getGitCommitFilePath();

			if (gitCommitFilePath == null) {
				return null;
			}

			return _workspaceGitRepository.getFileContent(gitCommitFilePath);
		}

		private String _getGitCommitFilePath() {
			return JenkinsResultsParserUtil.getProperty(
				_getWorkspaceProperties(), "git.commit.file.path",
				_repositoryType, _getParentUpstreamBranchName());
		}

		private String _getParentUpstreamBranchName() {
			if (_workspaceGitRepository == null) {
				return null;
			}

			return _workspaceGitRepository.getUpstreamBranchName();
		}

		private String _getRepositoryName() {
			return JenkinsResultsParserUtil.getProperty(
				_getWorkspaceProperties(), "repository.name", _repositoryType,
				_getParentUpstreamBranchName());
		}

		private String _getUpstreamGitHubURL() {
			return JenkinsResultsParserUtil.combine(
				"https://github.com/liferay/", _getRepositoryName(), "/tree/",
				getUpstreamBranchName());
		}

		private final String _repositoryType;
		private final WorkspaceGitRepository _workspaceGitRepository;

	}

}