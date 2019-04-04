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

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class LocalGitBranchTest extends GitRefTest {

	@Test
	public void testGetDirectory() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		File directory = new File(FILE_PATH_REPOSITORY);

		if (!directory.equals(localGitBranch.getDirectory())) {
			File localGitBranchDirectory = localGitBranch.getDirectory();

			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						directory.getPath(), localGitBranchDirectory.getPath(),
						"directory")));
		}
	}

	@Test
	public void testGetGitWorkingDirectory() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		LocalGitRepository localGitRepository = _getLocalGitRepository();

		GitWorkingDirectory gitWorkingDirectory =
			localGitRepository.getGitWorkingDirectory();

		if (!gitWorkingDirectory.equals(
				localGitBranch.getGitWorkingDirectory())) {

			errorCollector.addError(
				new Throwable("The Git working directory does not match"));
		}
	}

	@Test
	public void testGetLocalGitRepository() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!localGitRepository.equals(
				localGitBranch.getLocalGitRepository())) {

			errorCollector.addError(
				new Throwable("The local Git repository does not match"));
		}
	}

	@Test
	public void testGetName() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!NAME_REF.equals(localGitBranch.getName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REF, localGitBranch.getName(), "branch name")));
		}
	}

	@Test
	public void testGetSHA() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!SHA_REF.equals(localGitBranch.getSHA())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						SHA_REF, localGitBranch.getSHA(), "branch SHA")));
		}
	}

	@Test
	public void testGetUpstreamBranchName() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!NAME_REPOSITORY_UPSTREAM_BRANCH.equals(
				localGitBranch.getUpstreamBranchName())) {

			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						NAME_REPOSITORY_UPSTREAM_BRANCH,
						localGitBranch.getUpstreamBranchName(),
						"upstream branch name")));
		}
	}

	private LocalGitBranch _getLocalGitBranch() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		return GitBranchFactory.newLocalGitBranch(
			localGitRepository, NAME_REF, SHA_REF);
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			NAME_REPOSITORY, NAME_REPOSITORY_UPSTREAM_BRANCH);
	}

}