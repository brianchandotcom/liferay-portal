/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			throw new IllegalArgumentException("Local Git repository is null");
		}

		_localGitRepository = localGitRepository;
	}

	private final LocalGitRepository _localGitRepository;

}