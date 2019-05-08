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

import java.util.List;
import java.util.Objects;

/**
 * @author Peter Yoo
 */
public abstract class BaseRemoteGitRepository
	extends BaseGitRepository implements RemoteGitRepository {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BaseRemoteGitRepository)) {
			return false;
		}

		BaseRemoteGitRepository baseRemoteGitRepository =
			(BaseRemoteGitRepository)obj;

		if (Objects.equals(
				getHostname(), baseRemoteGitRepository.getHostname()) &&
			JenkinsResultsParserUtil.isJSONObjectEqual(
				getJSONObject(), baseRemoteGitRepository.getJSONObject()) &&
			Objects.equals(getName(), baseRemoteGitRepository.getName()) &&
			Objects.equals(
				getRemoteURL(), baseRemoteGitRepository.getRemoteURL()) &&
			Objects.equals(
				getUsername(), baseRemoteGitRepository.getUsername())) {

			return true;
		}

		return false;
	}

	@Override
	public String getHostname() {
		return getString("hostname");
	}

	@Override
	public String getRemoteURL() {
		List<String> gitHubDevNodeHostnames =
			GitHubDevSyncUtil.getGitHubDevNodeHostnames();

		if (gitHubDevNodeHostnames.contains("slave-" + getHostname())) {
			return JenkinsResultsParserUtil.combine(
				"root@", getHostname(), ":/opt/dev/projects/github/",
				getName());
		}

		return JenkinsResultsParserUtil.combine(
			"git@", getHostname(), ":", getUsername(), "/", getName());
	}

	@Override
	public String getUsername() {
		return getString("username");
	}

	@Override
	public int hashCode() {
		String hash = JenkinsResultsParserUtil.combine(
			getHostname(), getName(), getRemoteURL(), getUsername());

		return hash.hashCode();
	}

	protected BaseRemoteGitRepository(GitRemote gitRemote) {
		this(
			gitRemote.getHostname(), gitRemote.getGitRepositoryName(),
			gitRemote.getUsername());
	}

	protected BaseRemoteGitRepository(
		String hostname, String gitRepositoryName, String username) {

		super(gitRepositoryName);

		if ((hostname == null) || hostname.isEmpty()) {
			throw new IllegalArgumentException("Hostname is null");
		}

		if ((username == null) || username.isEmpty()) {
			throw new IllegalArgumentException("Username is null");
		}

		put("hostname", hostname);
		put("username", username);

		validateKeys(_KEYS_REQUIRED);
	}

	private static final String[] _KEYS_REQUIRED = {"hostname", "username"};

}