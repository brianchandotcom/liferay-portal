/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class GitRepositoryFactoryTest extends GitRepositoryTest {

	@Test
	public void testGetLocalGitRepository() {
		LocalGitRepository localGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				NAME_REPOSITORY, NAME_REPOSITORY_UPSTREAM_BRANCH);

		if (!(localGitRepository instanceof DefaultLocalGitRepository)) {
			errorCollector.addError(
				new Throwable("Invalid LocalGitRepository instance"));
		}
	}

	@Test
	public void testGetRemoteGitRepository() {
		RemoteGitRepository gitHubRemoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github.com", NAME_REPOSITORY, USERNAME_REPOSITORY);

		if (!(gitHubRemoteGitRepository instanceof GitHubRemoteGitRepository)) {
			errorCollector.addError(
				new Throwable("Invalid GitHubRemoteGitRepository instance"));
		}

		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github-dev.liferay.com", NAME_REPOSITORY, USERNAME_REPOSITORY);

		if (!(remoteGitRepository instanceof DefaultRemoteGitRepository)) {
			errorCollector.addError(
				new Throwable("Invalid DefaultRemoteGitRepository instance"));
		}
	}

}