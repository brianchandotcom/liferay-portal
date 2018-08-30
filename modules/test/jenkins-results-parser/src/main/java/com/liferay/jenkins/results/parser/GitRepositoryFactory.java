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

		LocalGitRepository localGitRepository = null;

		if (gitRepositoryName.startsWith("com-liferay-")) {
			localGitRepository = new GitSubrepositoryLocalGitRepository(
				gitRepositoryName, upstreamBranchName);
		}
		else if (gitRepositoryName.startsWith("liferay-plugins")) {
			localGitRepository = new PluginsLocalGitRepository(
				gitRepositoryName, upstreamBranchName);
		}
		else if (gitRepositoryName.startsWith("liferay-portal")) {
			localGitRepository = new PortalLocalGitRepository(
				gitRepositoryName, upstreamBranchName);
		}
		else {
			localGitRepository = new LocalGitRepository(
				gitRepositoryName, upstreamBranchName);
		}

		_localGitRepositories.put(key, localGitRepository);

		return _localGitRepositories.get(key);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		GitRemote gitRemote) {

		String hostname = gitRemote.getHostname();

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRemote);
		}

		return new RemoteGitRepository(gitRemote);
	}

	public static RemoteGitRepository getRemoteGitRepository(
		String hostname, String gitRepositoryName, String username) {

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteGitRepository(gitRepositoryName, username);
		}

		return new RemoteGitRepository(hostname, gitRepositoryName, username);
	}

	private static final Map<String, LocalGitRepository> _localGitRepositories =
		new HashMap<>();

}