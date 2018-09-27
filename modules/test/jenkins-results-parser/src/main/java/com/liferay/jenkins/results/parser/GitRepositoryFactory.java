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

import org.json.JSONObject;

/**
 * @author Peter Yoo
 */
public class GitRepositoryFactory {

	public static WorkspaceGitRepository getDependencyWorkspaceGitRepository(
		String repositoryType, WorkspaceGitRepository workspaceGitRepository,
		PullRequest pullRequest, String upstreamBranchName) {

		if (repositoryType.equals("portal.companion")) {
			return new CompanionPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName, workspaceGitRepository);
		}
		else if (repositoryType.equals("portal.other")) {
			return new OtherPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}
		else if (repositoryType.equals("portal.plugins")) {
			return new PortalPluginsWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}

		throw new RuntimeException(
			"Unsupported dependency workspace Git repository");
	}

	public static WorkspaceGitRepository getDependencyWorkspaceGitRepository(
		String repositoryType, WorkspaceGitRepository workspaceGitRepository,
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		if (repositoryType.equals("portal.companion")) {
			return new CompanionPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName, workspaceGitRepository);
		}
		else if (repositoryType.equals("portal.other")) {
			return new OtherPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}
		else if (repositoryType.equals("portal.plugins")) {
			return new PortalPluginsWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}

		throw new RuntimeException(
			"Unsupported dependency workspace Git repository");
	}

	public static LocalGitRepository getLocalGitRepository(
		String repositoryName, String upstreamBranchName) {

		return new DefaultLocalGitRepository(
			repositoryName, upstreamBranchName);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		GitRemote gitRemote) {

		String hostname = gitRemote.getHostname();

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRemote);
		}

		return new DefaultRemoteGitRepository(gitRemote);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		String hostname, String gitRepositoryName, String username) {

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRepositoryName, username);
		}

		return new DefaultRemoteGitRepository(
			hostname, gitRepositoryName, username);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		JSONObject jsonObject) {

		if (CompanionPortalWorkspaceGitRepository.isValidJSONObject(
				jsonObject)) {

			return new CompanionPortalWorkspaceGitRepository(jsonObject);
		}
		else if (PrimaryPortalWorkspaceGitRepository.isValidJSONObject(
					jsonObject)) {

			return new PrimaryPortalWorkspaceGitRepository(jsonObject);
		}
		else if (JenkinsWorkspaceGitRepository.isValidJSONObject(jsonObject)) {
			return new JenkinsWorkspaceGitRepository(jsonObject);
		}
		else if (OtherPortalWorkspaceGitRepository.isValidJSONObject(
					jsonObject)) {

			return new OtherPortalWorkspaceGitRepository(jsonObject);
		}
		else if (PluginsWorkspaceGitRepository.isValidJSONObject(jsonObject)) {
			return new PluginsWorkspaceGitRepository(jsonObject);
		}
		else if (PortalPluginsWorkspaceGitRepository.isValidJSONObject(
					jsonObject)) {

			return new PortalPluginsWorkspaceGitRepository(jsonObject);
		}

		throw new RuntimeException("Invalid JSONObject " + jsonObject);
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, PullRequest pullRequest, String upstreamBranchName) {

		if (gitHubURL.contains("/liferay-jenkins-ee")) {
			return new JenkinsWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new PrimaryPortalWorkspaceGitRepository(
				pullRequest, upstreamBranchName);
		}

		throw new RuntimeException("Unsupported workspace Git repository");
	}

	public static WorkspaceGitRepository getWorkspaceGitRepository(
		String gitHubURL, RemoteGitRef remoteGitRef,
		String upstreamBranchName) {

		if (gitHubURL.contains("/liferay-jenkins-ee")) {
			return new JenkinsWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-plugins")) {
			return new PluginsWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			return new PrimaryPortalWorkspaceGitRepository(
				remoteGitRef, upstreamBranchName);
		}

		throw new RuntimeException("Unsupported workspace Git repository");
	}

}