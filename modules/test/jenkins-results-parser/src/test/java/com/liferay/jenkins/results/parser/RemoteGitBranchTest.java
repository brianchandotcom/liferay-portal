/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class RemoteGitBranchTest extends GitRefTest {

	@Test
	public void testGetName() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!NAME_REF.equals(remoteGitBranch.getName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REF, remoteGitBranch.getName(), "branch name")));
		}
	}

	@Test
	public void testGetRemoteGitRepository() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!remoteGitRepository.equals(
				remoteGitBranch.getRemoteGitRepository())) {

			errorCollector.addError(
				new Throwable("The remote Git repository does not match"));
		}
	}

	@Test
	public void testGetRemoteURL() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@", HOSTNAME_REPOSITORY, ":", USERNAME_REPOSITORY, "/",
			NAME_REPOSITORY);

		if (!remoteURL.equals(remoteGitBranch.getRemoteURL())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						remoteURL, remoteGitBranch.getRemoteURL(),
						"remote URL")));
		}
	}

	@Test
	public void testGetRepositoryName() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!NAME_REPOSITORY.equals(remoteGitBranch.getRepositoryName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REPOSITORY, remoteGitBranch.getRepositoryName(),
						"repository name")));
		}
	}

	@Test
	public void testGetSHA() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!SHA_REF.equals(remoteGitBranch.getSHA())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						SHA_REF, remoteGitBranch.getSHA(), "branch SHA")));
		}
	}

	@Test
	public void testGetUsername() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!USERNAME_REPOSITORY.equals(remoteGitBranch.getUsername())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						USERNAME_REPOSITORY, remoteGitBranch.getUsername(),
						"username")));
		}
	}

	private RemoteGitBranch _getRemoteGitBranch() {
		RemoteGitRef remoteGitRef = GitBranchFactory.newRemoteGitRef(
			_getRemoteGitRepository(), NAME_REF, SHA_REF, "heads");

		if (!(remoteGitRef instanceof RemoteGitBranch)) {
			throw new RuntimeException("");
		}

		return (RemoteGitBranch)remoteGitRef;
	}

	private RemoteGitRepository _getRemoteGitRepository() {
		return GitRepositoryFactory.getRemoteGitRepository(
			HOSTNAME_REPOSITORY, NAME_REPOSITORY, USERNAME_REPOSITORY);
	}

}