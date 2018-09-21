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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Yoo
 */
public class GitRepositoryFactory {

	public static LocalGitRepository getLocalGitRepository(
		String gitRepositoryName, String upstreamBranchName) {

		String key = gitRepositoryName + "/" + upstreamBranchName;

		if (_localGitRepositories.containsKey(key)) {
			return _localGitRepositories.get(key);
		}

		LocalGitRepository localGitRepository = new DefaultLocalGitRepository(
			gitRepositoryName, upstreamBranchName);

		_localGitRepositories.put(key, localGitRepository);

		return _localGitRepositories.get(key);
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

	public static CompanionPortalWorkspaceGitRepository
		newCompanionPortalWorkspaceGitRepository(
			PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		if (portalWorkspaceGitRepository == null) {
			throw new RuntimeException(
				"Portal build runner Git repository is null");
		}

		return new CompanionPortalWorkspaceGitRepository(
			portalWorkspaceGitRepository);
	}

	public static OtherPortalWorkspaceGitRepository
		newOtherPortalWorkspaceGitRepository(
			PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		if (portalWorkspaceGitRepository == null) {
			throw new RuntimeException(
				"Portal build runner Git repository is null");
		}

		return new OtherPortalWorkspaceGitRepository(
			portalWorkspaceGitRepository);
	}

	public static PluginsWorkspaceGitRepository
		newPluginsWorkspaceGitRepository(
			PortalWorkspaceGitRepository portalWorkspaceGitRepository) {

		if (portalWorkspaceGitRepository == null) {
			throw new RuntimeException(
				"Portal build runner Git repository is null");
		}

		return new PluginsWorkspaceGitRepository(portalWorkspaceGitRepository);
	}

	public static WorkspaceGitRepository newWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName) {

		return newWorkspaceGitRepository(gitHubURL, upstreamBranchName, null);
	}

	public static WorkspaceGitRepository newWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		if (_workspaceGitRepository.containsKey(gitHubURL)) {
			return _workspaceGitRepository.get(gitHubURL);
		}

		WorkspaceGitRepository workspaceGitRepository;

		if (gitHubURL.contains("/liferay-plugins")) {
			workspaceGitRepository = new PluginsWorkspaceGitRepository(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else if (gitHubURL.contains("/liferay-portal")) {
			workspaceGitRepository = new DefaultPortalWorkspaceGitRepository(
				gitHubURL, upstreamBranchName, branchSHA);
		}
		else {
			workspaceGitRepository = new DefaultWorkspaceGitRepository(
				gitHubURL, upstreamBranchName, branchSHA);
		}

		_workspaceGitRepository.put(gitHubURL, workspaceGitRepository);

		return _workspaceGitRepository.get(gitHubURL);
	}

	private static final Map<String, LocalGitRepository> _localGitRepositories =
		new HashMap<>();
	private static final Map<String, WorkspaceGitRepository>
		_workspaceGitRepository = new HashMap<>();

}