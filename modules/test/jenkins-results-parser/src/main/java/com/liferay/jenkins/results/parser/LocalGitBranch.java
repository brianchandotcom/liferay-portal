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

import java.io.File;

/**
 * @author Michael Hashimoto
 */
public class LocalGitBranch extends BaseGitRef {

	public File getDirectory() {
		LocalGitRepository localGitRepository = getLocalGitRepository();

		return localGitRepository.getDirectory();
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		LocalGitRepository localGitRepository = getLocalGitRepository();

		return localGitRepository.getGitWorkingDirectory();
	}

	public LocalGitRepository getLocalGitRepository() {
		return _localGitRepository;
	}

	public String getUpstreamBranchName() {
		LocalGitRepository localGitRepository = getLocalGitRepository();

		return localGitRepository.getUpstreamBranchName();
	}

	@Override
	public String toString() {
		LocalGitRepository localGitRepository = getLocalGitRepository();

		StringBuilder sb = new StringBuilder();

		sb.append(localGitRepository.getDirectory());
		sb.append(" (");
		sb.append(getName());
		sb.append(" - ");
		sb.append(getSHA());
		sb.append(")");

		return sb.toString();
	}

	protected LocalGitBranch(
		LocalGitRepository localGitRepository, String name, String sha) {

		super(name, sha);

		if (localGitRepository == null) {
			throw new IllegalArgumentException("Local repository is null");
		}

		_localGitRepository = localGitRepository;
	}

	private final LocalGitRepository _localGitRepository;

}