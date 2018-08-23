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
public class RepositoryFactory {

	public static LocalRepository getLocalRepository(
		String repositoryName, String upstreamBranchName) {

		String key = repositoryName + "/" + upstreamBranchName;

		if (_localRepositories.containsKey(key)) {
			return _localRepositories.get(key);
		}

		LocalRepository localRepository = null;

		if (repositoryName.startsWith("com-liferay-")) {
			localRepository = new SubrepositoryLocalRepository(
				repositoryName, upstreamBranchName);
		}
		else if (repositoryName.startsWith("liferay-plugins")) {
			localRepository = new PluginsLocalRepository(
				repositoryName, upstreamBranchName);
		}
		else if (repositoryName.startsWith("liferay-portal")) {
			localRepository = new PortalLocalRepository(
				repositoryName, upstreamBranchName);
		}
		else {
			localRepository = new LocalRepository(
				repositoryName, upstreamBranchName);
		}

		_localRepositories.put(key, localRepository);

		return _localRepositories.get(key);
	}

	public static RemoteRepository getRemoteRepository(GitRemote gitRemote) {
		String hostname = gitRemote.getHostname();

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteRepository(gitRemote);
		}

		return new RemoteRepository(gitRemote);
	}

	public static RemoteRepository getRemoteRepository(
		String hostname, String repositoryName, String username) {

		if (hostname.equalsIgnoreCase("github.com")) {
			return new GitHubRemoteRepository(repositoryName, username);
		}

		return new RemoteRepository(hostname, repositoryName, username);
	}

	private static final Map<String, LocalRepository> _localRepositories =
		new HashMap<>();

}