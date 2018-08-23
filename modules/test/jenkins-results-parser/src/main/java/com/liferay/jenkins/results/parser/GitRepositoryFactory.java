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

	public static LocalGitRepository getLocalRepository(
		String repositoryName, String upstreamBranchName) {

		String key = repositoryName + "/" + upstreamBranchName;

		if (_localRepositories.containsKey(key)) {
			return _localRepositories.get(key);
		}

		LocalGitRepository localRepository = null;

		if (repositoryName.startsWith("com-liferay-")) {
			localRepository = new SubrepositoryLocalGitRepository(
				repositoryName, upstreamBranchName);
		}
		else if (repositoryName.startsWith("liferay-plugins")) {
			localRepository = new PluginsLocalGitRepository(
				repositoryName, upstreamBranchName);
		}
		else if (repositoryName.startsWith("liferay-portal")) {
			localRepository = new PortalLocalGitRepository(
				repositoryName, upstreamBranchName);
		}
		else {
			localRepository = new LocalGitRepository(
				repositoryName, upstreamBranchName);
		}

		_localRepositories.put(key, localRepository);

		return _localRepositories.get(key);
	}

	public static RemoteGitRepository getRemoteGitRepository(GitRemote remote) {
		String hostname = remote.getHostname();

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(remote);
		}

		return new RemoteGitRepository(remote);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		String hostname, String repositoryName, String username) {

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(repositoryName, username);
		}

		return new RemoteGitRepository(hostname, repositoryName, username);
	}

	private static final Map<String, LocalGitRepository> _localRepositories =
		new HashMap<>();

}