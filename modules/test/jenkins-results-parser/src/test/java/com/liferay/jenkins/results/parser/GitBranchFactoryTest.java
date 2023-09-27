/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class GitBranchFactoryTest extends GitRefTest {

	@Test
	public void testNewLocalGitBranch() {
		LocalGitBranch localGitBranch = GitBranchFactory.newLocalGitBranch(
			_getLocalGitRepository(), NAME_REF, SHA_REF);

		if (localGitBranch == null) {
			errorCollector.addError(new Throwable("Local Git branch is null"));
		}
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			NAME_REPOSITORY, NAME_REPOSITORY_UPSTREAM_BRANCH);
	}

}