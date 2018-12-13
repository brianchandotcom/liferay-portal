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

		File directory = new File(REPOSITORY_DIR);

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
				new Throwable("The git working directory does not match"));
		}
	}

	@Test
	public void testGetLocalGitRepository() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		LocalGitRepository localGitRepository = _getLocalGitRepository();

		if (!localGitRepository.equals(
				localGitBranch.getLocalGitRepository())) {

			errorCollector.addError(
				new Throwable("The local git repository does not match"));
		}
	}

	@Test
	public void testGetName() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!REF_NAME.equals(localGitBranch.getName())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						REF_NAME, localGitBranch.getName(), "branch name")));
		}
	}

	@Test
	public void testGetSHA() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!REF_SHA.equals(localGitBranch.getSHA())) {
			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						REF_SHA, localGitBranch.getSHA(), "branch SHA")));
		}
	}

	@Test
	public void testGetUpstreamBranchName() {
		LocalGitBranch localGitBranch = _getLocalGitBranch();

		if (!REPOSITORY_UPSTREAM_BRANCH_NAME.equals(
				localGitBranch.getUpstreamBranchName())) {

			errorCollector.addError(
				new Throwable(
					getMismatchMessage(
						REPOSITORY_UPSTREAM_BRANCH_NAME,
						localGitBranch.getUpstreamBranchName(),
						"upstream branch name")));
		}
	}

	private LocalGitBranch _getLocalGitBranch() {
		LocalGitRepository localGitRepository = _getLocalGitRepository();

		return GitBranchFactory.newLocalGitBranch(
			localGitRepository, REF_NAME, REF_SHA);
	}

	private LocalGitRepository _getLocalGitRepository() {
		return GitRepositoryFactory.getLocalGitRepository(
			REPOSITORY_NAME, REPOSITORY_UPSTREAM_BRANCH_NAME);
	}

}