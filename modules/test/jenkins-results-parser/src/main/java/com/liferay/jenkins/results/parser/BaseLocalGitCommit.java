/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseLocalGitCommit
	extends BaseGitCommit implements LocalGitCommit {

	@Override
	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	protected BaseLocalGitCommit(
		String emailAddress, GitWorkingDirectory gitWorkingDirectory,
		String message, String sha, GitCommit.Type type, long commitTime) {

		super(
			emailAddress, gitWorkingDirectory.getGitRepositoryName(), message,
			sha, type, commitTime);

		_gitWorkingDirectory = gitWorkingDirectory;
	}

	@Override
	protected void initCommitTime() {
	}

	@Override
	protected void initEmailAddress() {
	}

	@Override
	protected void initMessage() {
	}

	private final GitWorkingDirectory _gitWorkingDirectory;

}