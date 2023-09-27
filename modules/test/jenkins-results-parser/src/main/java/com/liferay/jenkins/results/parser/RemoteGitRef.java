/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class RemoteGitRef
	extends BaseGitRef implements Comparable<RemoteGitRef> {

	@Override
	public int compareTo(RemoteGitRef o) {
		String name = getName();

		return name.compareTo(o.getName());
	}

	public String getHtmlURL() {
		return JenkinsResultsParserUtil.combine(
			"https://github.com/", getUsername(), "/", getRepositoryName(),
			"/tree/", getName());
	}

	public RemoteGitRepository getRemoteGitRepository() {
		return _remoteGitRepository;
	}

	public String getRemoteURL() {
		RemoteGitRepository remoteRepository = getRemoteGitRepository();

		return remoteRepository.getRemoteURL();
	}

	public String getRepositoryName() {
		return _remoteGitRepository.getName();
	}

	public String getUsername() {
		RemoteGitRepository remoteGitRepository = getRemoteGitRepository();

		return remoteGitRepository.getUsername();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getRemoteURL());
		sb.append(" (");
		sb.append(getName());
		sb.append(" - ");
		sb.append(getSHA());
		sb.append(")");

		return sb.toString();
	}

	protected RemoteGitRef(
		RemoteGitRepository remoteGitRepository, String name, String sha) {

		super(name, sha);

		if (remoteGitRepository == null) {
			throw new IllegalArgumentException("Remote Git repository is null");
		}

		_remoteGitRepository = remoteGitRepository;
	}

	private final RemoteGitRepository _remoteGitRepository;

}