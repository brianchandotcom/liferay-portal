/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class RemoteGitRefTest extends GitRefTest {

	@Test
	public void testGetName() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		if (!NAME_REF.equals(remoteGitRef.getName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REF, remoteGitRef.getName(), "ref name")));
		}
	}

	@Test
	public void testGetRemoteGitRepository() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!remoteGitRepository.equals(
				remoteGitRef.getRemoteGitRepository())) {

			errorCollector.addError(
				new Throwable("The remote Git repository does not match"));
		}
	}

	@Test
	public void testGetRemoteURL() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@", HOSTNAME_REPOSITORY, ":", USERNAME_REPOSITORY, "/",
			NAME_REPOSITORY);

		if (!remoteURL.equals(remoteGitRef.getRemoteURL())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						remoteURL, remoteGitRef.getRemoteURL(), "remote URL")));
		}
	}

	@Test
	public void testGetRepositoryName() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		if (!NAME_REPOSITORY.equals(remoteGitRef.getRepositoryName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REPOSITORY, remoteGitRef.getRepositoryName(),
						"repository name")));
		}
	}

	@Test
	public void testGetSHA() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		if (!SHA_REF.equals(remoteGitRef.getSHA())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						SHA_REF, remoteGitRef.getSHA(), "ref SHA")));
		}
	}

	@Test
	public void testGetUsername() {
		RemoteGitRef remoteGitRef = _getRemoteGitRef();

		if (!USERNAME_REPOSITORY.equals(remoteGitRef.getUsername())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						USERNAME_REPOSITORY, remoteGitRef.getUsername(),
						"username")));
		}
	}

	private RemoteGitRef _getRemoteGitRef() {
		return GitBranchFactory.newRemoteGitRef(
			_getRemoteGitRepository(), NAME_REF, SHA_REF, "tag");
	}

	private RemoteGitRepository _getRemoteGitRepository() {
		return GitRepositoryFactory.getRemoteGitRepository(
			HOSTNAME_REPOSITORY, NAME_REPOSITORY, USERNAME_REPOSITORY);
	}

}