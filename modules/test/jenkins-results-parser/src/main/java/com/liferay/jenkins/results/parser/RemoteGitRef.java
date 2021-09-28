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