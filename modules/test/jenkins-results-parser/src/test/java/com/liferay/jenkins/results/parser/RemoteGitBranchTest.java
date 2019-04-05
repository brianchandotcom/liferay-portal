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
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		RemoteGitRef remoteGitRef = GitBranchFactory.newRemoteGitRef(
			remoteGitRepository, NAME_REF, SHA_REF, "heads");

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