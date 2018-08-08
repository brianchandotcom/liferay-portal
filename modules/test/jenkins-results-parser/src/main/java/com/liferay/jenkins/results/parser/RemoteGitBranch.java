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
public class RemoteGitBranch
	extends BaseGitBranch implements Comparable<RemoteGitBranch> {

	@Override
	public int compareTo(RemoteGitBranch o) {
		String name = getName();

		return name.compareTo(o.getName());
	}

	public RemoteRepository getRemoteRepository() {
		return _remoteRepository;
	}

	@Override
	public String toString() {
		RemoteRepository remoteRepository = getRemoteRepository();

		StringBuilder sb = new StringBuilder();

		sb.append(remoteRepository.getRemoteURL());
		sb.append(" (");
		sb.append(getName());
		sb.append(" - ");
		sb.append(getSHA());
		sb.append(")");

		return sb.toString();
	}

	protected RemoteGitBranch(
		RemoteRepository remoteRepository, String name, String sha) {

		super(name, sha);

		if (remoteRepository == null) {
			throw new IllegalArgumentException("Remote repository is null");
		}

		_remoteRepository = remoteRepository;
	}

	private final RemoteRepository _remoteRepository;

}