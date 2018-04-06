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
import java.io.IOException;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MergeCentralSubrepositoryUtil {

	public static void createSubrepositoryMergePullRequests(
			String centralWorkingDirectory, String centralUpstreamBranchName,
			String receiverUserName, String topLevelBranchName)
		throws IOException {

		GitWorkingDirectory centralGitWorkingDirectory =
			new GitWorkingDirectory(
				centralUpstreamBranchName, centralWorkingDirectory);

		File modulesDir = new File(
			centralGitWorkingDirectory.getWorkingDirectory(), "modules");

		if (!modulesDir.exists()) {
			return;
		}

		List<File> gitrepoFiles = JenkinsResultsParserUtil.findFiles(
			modulesDir, ".gitrepo");

		for (File gitrepoFile : gitrepoFiles) {
			CentralSubrepository centralSubrepository =
				new CentralSubrepository(
					gitrepoFile, centralUpstreamBranchName);

			if (!centralSubrepository.isAutoPullEnabled()) {
				continue;
			}

			String mergeBranchName = _getMergeBranchName(
				centralUpstreamBranchName,
				centralSubrepository.getSubrepositoryName(),
				centralSubrepository.getSubrepositoryUpstreamCommit());

			if (centralSubrepository.isCentralPullRequestCandidate()) {
				GitWorkingDirectory.Remote upstreamRemote =
					centralGitWorkingDirectory.getRemote("upstream");

				if (!centralGitWorkingDirectory.branchExists(
						mergeBranchName, upstreamRemote)) {

					GitWorkingDirectory.Branch topLevelBranch =
						centralGitWorkingDirectory.getBranch(
							topLevelBranchName, null);

					GitWorkingDirectory.Branch mergeBranch = _createMergeBranch(
						centralGitWorkingDirectory, mergeBranchName,
						topLevelBranch);

					_commitCiMergeFile(
						centralGitWorkingDirectory, centralSubrepository,
						gitrepoFile);

					_pushMergeBranchToRemote(
						centralGitWorkingDirectory, mergeBranch,
						receiverUserName);
				}

				_createMergePullRequest(
					centralGitWorkingDirectory, centralSubrepository,
					mergeBranchName, receiverUserName);
			}

			_deleteStalePulls(
				centralGitWorkingDirectory, centralSubrepository,
				mergeBranchName, receiverUserName);

			_deleteStaleBranches(
				centralGitWorkingDirectory, centralSubrepository,
				mergeBranchName);
		}
	}

	private static void _commitCiMergeFile(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, File gitrepoFile)
		throws IOException {

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
			"Create " + ciMergeFilePath + ".");
	}

	private static GitWorkingDirectory.Branch _createMergeBranch(
		GitWorkingDirectory centralGitWorkingDirectory, String mergeBranchName,
		GitWorkingDirectory.Branch topLevelBranch) {

		centralGitWorkingDirectory.reset("--hard");

		centralGitWorkingDirectory.checkoutBranch(topLevelBranch);

		GitWorkingDirectory.Branch mergeBranch =
			centralGitWorkingDirectory.getBranch(mergeBranchName, null);

		if (mergeBranch != null) {
			centralGitWorkingDirectory.deleteBranch(mergeBranch);
		}

		mergeBranch = centralGitWorkingDirectory.createLocalBranch(
			mergeBranchName);

		centralGitWorkingDirectory.checkoutBranch(mergeBranch);

		return mergeBranch;
	}

	private static void _createMergePullRequest(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String mergeBranchName,
			String receiverUserName)
		throws IOException {

		String subrepositoryName = centralSubrepository.getSubrepositoryName();
		String subrepositoryUpstreamCommit =
			centralSubrepository.getSubrepositoryUpstreamCommit();

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", receiverUserName, "/",
			subrepositoryName, "/statuses/", subrepositoryUpstreamCommit);

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("context", "liferay/central-pull-request");
		requestJSONObject.put("description", "Tests are queued on Jenkins.");
		requestJSONObject.put("state", "pending");

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

		String subrepoPullMentionUsers = centralSubrepository.getCIProperty(
			"subrepo.merge.pull.mention.users");

		if (subrepoPullMentionUsers != null) {
			sb.append("\n\n");

			for (String subrepoPullMentionUser :
					subrepoPullMentionUsers.split(",")) {

				sb.append("@");
				sb.append(subrepoPullMentionUser);
				sb.append(" ");
			}
		}

		String title = subrepositoryName + " - Central Merge Pull Request";

		String pullRequestURL = centralGitWorkingDirectory.createPullRequest(
			sb.toString(), mergeBranchName, receiverUserName, title);

		requestJSONObject.put("target_url", pullRequestURL);

		JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());
	}

	private static void _deleteStaleBranches(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String mergeBranchName)
		throws IOException {

		GitWorkingDirectory.Remote upstreamRemote =
			centralGitWorkingDirectory.getRemote("upstream");

		if (_upstreamRemoteBranchNames == null) {
			_upstreamRemoteBranchNames =
				centralGitWorkingDirectory.getRemoteBranchNames(upstreamRemote);
		}

		String mergeBranchNamePrefix = mergeBranchName.substring(
			0, mergeBranchName.lastIndexOf("-"));

		for (String upstreamRemoteBranchName : _upstreamRemoteBranchNames) {
			if (upstreamRemoteBranchName.equals(mergeBranchName) &&
				!centralSubrepository.isSubrepositoryUpstreamCommitMerged()) {

				continue;
			}

			if (!upstreamRemoteBranchName.startsWith(mergeBranchNamePrefix)) {
				continue;
			}

			centralGitWorkingDirectory.deleteBranch(
				upstreamRemoteBranchName, upstreamRemote);
		}
	}

	private static void _deleteStalePulls(
			GitWorkingDirectory centralGitWorkingDirectory,
			CentralSubrepository centralSubrepository, String mergeBranchName,
			String receiverUserName)
		throws IOException {

		if (_pullsJSONArray == null) {
			_pullsJSONArray = new JSONArray();

			int page = 1;

			while (page < 10) {
				String url = JenkinsResultsParserUtil.combine(
					"https://api.github.com/repos/", receiverUserName, "/",
					centralGitWorkingDirectory.getRepositoryName(),
					"/pulls?page=", String.valueOf(page));

				JSONArray jsonArray = JenkinsResultsParserUtil.toJSONArray(url);

				if ((jsonArray != null) && (jsonArray.length() > 0)) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);

						JSONObject userJSONObject = jsonObject.getJSONObject(
							"user");

						String login = userJSONObject.getString("login");

						if (login.equals("liferay-continuous-integration")) {
							_pullsJSONArray.put(jsonObject);
						}
					}
				}
				else {
					break;
				}

				page++;
			}
		}

		String mergeBranchNamePrefix = mergeBranchName.substring(
			0, mergeBranchName.lastIndexOf("-"));

		for (int i = 0; i < _pullsJSONArray.length(); i++) {
			JSONObject jsonObject = _pullsJSONArray.getJSONObject(i);

			JSONObject headJSONObject = jsonObject.getJSONObject("head");

			String refName = headJSONObject.getString("ref");

			if (refName.equals(mergeBranchName) &&
				!centralSubrepository.isSubrepositoryUpstreamCommitMerged()) {

				continue;
			}

			if (!refName.startsWith(mergeBranchNamePrefix)) {
				continue;
			}

			System.out.println(
				"Closing pull request " + jsonObject.getString("html_url"));

			JSONObject requestJSONObject = new JSONObject();

			requestJSONObject.put(
				"body", "This stale merge pull request has been closed.");

			JenkinsResultsParserUtil.toJSONObject(
				jsonObject.getString("comments_url"),
				requestJSONObject.toString());

			requestJSONObject = new JSONObject();

			requestJSONObject.put("state", "closed");

			JenkinsResultsParserUtil.toJSONObject(
				jsonObject.getString("url"), requestJSONObject.toString());
		}
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
		String centralUpstreamBranchName, String subrepositoryName,
		String subrepositoryUpstreamCommit) {

		return JenkinsResultsParserUtil.combine(
			"ci-merge-", subrepositoryName, "-", centralUpstreamBranchName, "-",
			subrepositoryUpstreamCommit);
	}

	private static void _pushMergeBranchToRemote(
		GitWorkingDirectory centralGitWorkingDirectory,
		GitWorkingDirectory.Branch mergeBranch, String receiverUserName) {

		String centralRepositoryName =
			centralGitWorkingDirectory.getRepositoryName();

		String originRemoteURL = JenkinsResultsParserUtil.combine(
			"git@github.com:", receiverUserName, "/", centralRepositoryName,
			".git");

		GitWorkingDirectory.Remote originRemote =
			centralGitWorkingDirectory.addRemote(
				true, "tempRemote", originRemoteURL);

		try {
			centralGitWorkingDirectory.pushToRemote(
				false, mergeBranch, mergeBranch.getName(), originRemote);
		}
		finally {
			centralGitWorkingDirectory.removeRemote(originRemote);
		}
	}

	private static JSONArray _pullsJSONArray;
	private static List<String> _upstreamRemoteBranchNames;

}