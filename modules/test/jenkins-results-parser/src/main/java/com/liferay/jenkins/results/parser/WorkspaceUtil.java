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

		String upstreamBranchName =
			workspaceGitRepositoryData.getUpstreamBranchName();

		if (upstreamBranchName == null) {
			return null;
		}

		String gitHubURL = workspaceGitRepositoryData.getRepositoryGitHubURL();

		WorkspaceGitRepository dependencyWorkspaceGitRepository = null;

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		if (buildDatabase.hasWorkspaceGitRepository(repositoryType)) {
			dependencyWorkspaceGitRepository =
				buildDatabase.getWorkspaceGitRepository(repositoryType);
		}
		else if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			dependencyWorkspaceGitRepository =
				GitRepositoryFactory.getDependencyWorkspaceGitRepository(
					repositoryType, workspaceGitRepository, pullRequest,
					upstreamBranchName);
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			dependencyWorkspaceGitRepository =
				GitRepositoryFactory.getDependencyWorkspaceGitRepository(
					repositoryType, workspaceGitRepository,
					GitUtil.getRemoteGitRef(gitHubURL), upstreamBranchName);
		}

		if (dependencyWorkspaceGitRepository == null) {
			throw new RuntimeException("Invalid repository GitHub URL");
		}

		String branchSHA = workspaceGitRepositoryData.getBranchSHA();

		if (branchSHA != null) {
			dependencyWorkspaceGitRepository.setBranchSHA(branchSHA);
		}

		return dependencyWorkspaceGitRepository;
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String repositoryType, String gitHubURL, String upstreamBranchName) {

		return getWorkspaceGitRepository(
			repositoryType, gitHubURL, upstreamBranchName, null);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String repositoryType, String gitHubURL, String upstreamBranchName,
		String branchSHA) {

		WorkspaceGitRepository workspaceGitRepository = null;

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		if (buildDatabase.hasWorkspaceGitRepository(repositoryType)) {
			workspaceGitRepository = buildDatabase.getWorkspaceGitRepository(
				repositoryType);
		}
		else if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			workspaceGitRepository =
				GitRepositoryFactory.getWorkspaceGitRepository(
					gitHubURL, pullRequest, upstreamBranchName);
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			workspaceGitRepository =
				GitRepositoryFactory.getWorkspaceGitRepository(
					gitHubURL, GitUtil.getRemoteGitRef(gitHubURL),
					upstreamBranchName);
		}

		if (workspaceGitRepository == null) {
			throw new RuntimeException("Invalid repository GitHub URL");
		}

		if (branchSHA != null) {
			workspaceGitRepository.setBranchSHA(branchSHA);
		}

		return workspaceGitRepository;
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
						_URL_WORKSPACE_PROPERTIES, false)));
		}
		catch (IOException ioe) {
			System.out.println(
				"Skipped downloading " + _URL_WORKSPACE_PROPERTIES);
		}

		File propertiesFile = new File("workspace.properties");

		_workspaceProperties = JenkinsResultsParserUtil.getProperties(
			propertiesFile);

		return _workspaceProperties;
	}

	private static final String _URL_WORKSPACE_PROPERTIES =
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
				getUpstreamBranchName());
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