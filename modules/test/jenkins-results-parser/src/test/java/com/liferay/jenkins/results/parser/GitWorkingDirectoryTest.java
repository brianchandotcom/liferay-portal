/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.Mockito;

/**
 * @author Kenji Heigel
 */
public class GitWorkingDirectoryTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetLocalGitBranchSHA() throws Exception {
		File gitRepositoryDir = temporaryFolder.getRoot();

		File gitDir = new File(gitRepositoryDir, ".git");

		gitDir.mkdir();

		Shell shell = mockShell();

		_stubCommand(
			shell, "git remote -v",
			"upstream\tgit@github.com:liferay/liferay-portal.git (fetch)\n" +
				"upstream\tgit@github.com:liferay/liferay-portal.git (push)\n" +
					"Finished executing Bash commands.\n");

		_stubCommand(
			shell, "git rev-parse master",
			"abcdef1234567890\nFinished executing Bash commands.\n");

		GitWorkingDirectory gitWorkingDirectory =
			GitWorkingDirectoryFactory.newGitWorkingDirectory(
				"master", gitRepositoryDir, "liferay-portal");

		Assert.assertEquals(
			"abcdef1234567890",
			gitWorkingDirectory.getLocalGitBranchSHA("master"));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _stubCommand(
			Shell shell, String commandSubstring, String standardOut)
		throws Exception {

		Mockito.doReturn(
			new Shell.ExecutionResult(0, "", standardOut)
		).when(
			shell
		).doExecute(
			Mockito.argThat(
				executionRequest ->
					(executionRequest != null) &&
					executionRequest.getCommands()[0].contains(
						commandSubstring))
		);
	}

}