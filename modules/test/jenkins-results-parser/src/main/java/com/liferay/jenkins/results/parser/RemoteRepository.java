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
 * @author Peter Yoo
 */
public class RemoteRepository extends BaseRepository {

	public String getHostname() {
		return hostname;
	}

	public String getRemoteURL() {
		return JenkinsResultsParserUtil.combine(
			"git@", hostname, ":", username, "/", name);
	}

	public String getUsername() {
		return username;
	}

	protected RemoteRepository(BaseGitRemote baseGitRemote) {
		this(
			baseGitRemote.getHostname(), baseGitRemote.getRepositoryName(),
			baseGitRemote.getUsername());
	}

	protected RemoteRepository(
		String hostname, String repositoryName, String username) {

		super(repositoryName);

		if ((hostname == null) || hostname.isEmpty()) {
			throw new IllegalArgumentException("Hostname is null");
		}

		if ((username == null) || username.isEmpty()) {
			throw new IllegalArgumentException("Username is null");
		}

		this.hostname = hostname;
		this.username = username;
	}

	protected final String hostname;
	protected final String username;

}