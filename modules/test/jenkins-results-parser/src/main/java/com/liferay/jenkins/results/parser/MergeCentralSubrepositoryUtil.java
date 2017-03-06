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
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @author Michael Hashimoto
 */
public class MergeCentralSubrepositoryUtil {

	public static void createSubrepositoryMergePullRequests(
			String centralWorkingDirectory, String centralUpstreamBranchName,
			String receiverUserName, String topLevelBranchName)
		throws FileNotFoundException, GitAPIException, InterruptedException,
			IOException {

		GitWorkingDirectory centralGitWorkingDirectory =
			new GitWorkingDirectory(
				centralUpstreamBranchName, centralWorkingDirectory);

		File modulesDir = new File(
			centralGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (modulesDir.exists()) {
			List<File> gitrepoFiles = JenkinsResultsParserUtil.findFiles(
				modulesDir, ".gitrepo");

			for (File gitrepoFile : gitrepoFiles) {
				CentralSubrepository centralSubrepository =
					new CentralSubrepository(
						gitrepoFile, centralUpstreamBranchName);

				if (centralSubrepository.isCentralPullRequestCandidate()) {
					_createMergeBranch(
						centralGitWorkingDirectory, centralSubrepository,
						topLevelBranchName);

					_commitCiMergeFile(
						centralGitWorkingDirectory, centralSubrepository,
						gitrepoFile);

					_pushMergeBranchToOriging(
						centralGitWorkingDirectory, centralSubrepository,
						receiverUserName);

					_createMergePullRequest(
						centralGitWorkingDirectory, centralSubrepository,
						receiverUserName);
				}
			}
		}
	}

	private static void _commitCiMergeFile(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, File gitrepoFile)
		throws GitAPIException, IOException {

		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String ciMergeFilePath = _getCiMergeFilePath(
			centralGitWorkingDirectory, gitrepoFile);

		JenkinsResultsParserUtil.write(
			new File(
				centralGitWorkingDirectory.getWorkingDirectory(),
				ciMergeFilePath),
			subrepositoryUpstreamCommit);

		centralGitWorkingDirectory.stageFileInCurrentBranch(ciMergeFilePath);

		centralGitWorkingDirectory.commitStagedFilesToCurrentBranch(
			"subrepo:ignore Create '" + ciMergeFilePath + "'");
	}

	private static void _createMergeBranch(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository,
			String topLevelBranchName)
		throws GitAPIException, InterruptedException, IOException {

		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);

		centralGitWorkingDirectory.resetHardToHEAD();

		centralGitWorkingDirectory.checkoutBranch(topLevelBranchName);

		centralGitWorkingDirectory.deleteBranch(mergeBranchName);

		centralGitWorkingDirectory.createBranch(mergeBranchName);

		centralGitWorkingDirectory.checkoutBranch(mergeBranchName);
	}

	private static void _createMergePullRequest(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String receiverUserName)
		throws FileNotFoundException, IOException {

		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);

		String title = subrepositoryName + " - Central Merge Pull Request";

		StringBuilder sb = new StringBuilder();

		sb.append("Merging the following commit: [");
		sb.append(subrepositoryUpstreamCommit);
		sb.append("](https://github.com/");
		sb.append(receiverUserName);
		sb.append("/");
		sb.append(subrepositoryName);
		sb.append("/commit/");
		sb.append(subrepositoryUpstreamCommit);
		sb.append(")");

		centralGitWorkingDirectory.createPullRequest(
			title, sb.toString(), receiverUserName, mergeBranchName);
	}

	private static String _getCiMergeFilePath(
			GitWorkingDirectory centralGitWorkingDirectory, File gitrepoFile)
		throws IOException {

		File centralWorkingDirectory =
			centralGitWorkingDirectory.getWorkingDirectory();

		String ciMergeFilePath = gitrepoFile.getCanonicalPath();

		ciMergeFilePath = ciMergeFilePath.replace(".gitrepo", "ci-merge");

		return ciMergeFilePath.replace(
			centralWorkingDirectory.getCanonicalPath() + File.separator, "");
	}

	private static String _getMergeBranchName(
		String subrepositoryName, String subrepositoryUpstreamCommit) {

		StringBuilder sb = new StringBuilder();

		sb.append("ci-merge-");
		sb.append(subrepositoryName);
		sb.append("-");
		sb.append(subrepositoryUpstreamCommit);

		return sb.toString();
	}

	private static void _pushMergeBranchToOriging(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String receiverUserName)
		throws GitAPIException, IOException {

		String centralRepositoryName =
			centralGitWorkingDirectory.getRepositoryName();
		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String mergeBranchName = _getMergeBranchName(
			subrepositoryName, subrepositoryUpstreamCommit);

		StringBuilder sb = new StringBuilder();

		sb.append("git@github.com:");
		sb.append(receiverUserName);
		sb.append("/");
		sb.append(centralRepositoryName);
		sb.append(".git");

		centralGitWorkingDirectory.pushBranchToOrigin(
			mergeBranchName, sb.toString());
	}

}