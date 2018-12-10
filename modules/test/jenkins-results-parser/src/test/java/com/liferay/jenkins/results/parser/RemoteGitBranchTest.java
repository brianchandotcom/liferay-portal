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

		if (!REF_NAME.equals(remoteGitBranch.getName())) {
			errorCollector.addError(
				new Throwable("The branch name should be " + REF_NAME));
		}
	}

	@Test
	public void testGetRemoteGitRepository() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		if (!remoteGitRepository.equals(
				remoteGitBranch.getRemoteGitRepository())) {

			errorCollector.addError(
				new Throwable("The remote git repository does not match"));
		}
	}

	@Test
	public void testGetRemoteURL() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		String remoteURL = JenkinsResultsParserUtil.combine(
			"git@", REPOSITORY_HOSTNAME, ":", REPOSITORY_USERNAME, "/",
			REPOSITORY_NAME);

		if (!remoteURL.equals(remoteGitBranch.getRemoteURL())) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"The remote url should be ", remoteURL)));
		}
	}

	@Test
	public void testGetRepositoryName() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!REPOSITORY_NAME.equals(remoteGitBranch.getRepositoryName())) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"The repository name should be ", REPOSITORY_NAME)));
		}
	}

	@Test
	public void testGetSHA() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!REF_SHA.equals(remoteGitBranch.getSHA())) {
			errorCollector.addError(
				new Throwable("The branch sha should be " + REF_SHA));
		}
	}

	@Test
	public void testGetUsername() {
		RemoteGitBranch remoteGitBranch = _getRemoteGitBranch();

		if (!REPOSITORY_USERNAME.equals(remoteGitBranch.getUsername())) {
			errorCollector.addError(
				new Throwable(
					JenkinsResultsParserUtil.combine(
						"The username should be ", REPOSITORY_USERNAME)));
		}
	}

	private RemoteGitBranch _getRemoteGitBranch() {
		RemoteGitRepository remoteGitRepository = _getRemoteGitRepository();

		RemoteGitRef remoteGitRef = GitBranchFactory.newRemoteGitRef(
			remoteGitRepository, REF_NAME, REF_SHA, "heads");

		if (!(remoteGitRef instanceof RemoteGitBranch)) {
			throw new RuntimeException("");
		}

		return (RemoteGitBranch)remoteGitRef;
	}

	private RemoteGitRepository _getRemoteGitRepository() {
		return GitRepositoryFactory.getRemoteGitRepository(
			REPOSITORY_HOSTNAME, REPOSITORY_NAME, REPOSITORY_USERNAME);
	}

}