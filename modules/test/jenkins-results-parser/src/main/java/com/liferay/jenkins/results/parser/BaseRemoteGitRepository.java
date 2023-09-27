/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BaseRemoteGitRepository)) {
			return false;
		}

		BaseRemoteGitRepository baseRemoteGitRepository =
			(BaseRemoteGitRepository)object;

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