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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildRunnerGitRepository
	extends LocalGitRepository implements BuildRunnerGitRepository {

	@Override
	public String getFileContent(String filePath) {
		File file = new File(getDirectory(), filePath);

		try {
			String fileContent = JenkinsResultsParserUtil.read(file);

			return fileContent.trim();
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Override
	public String getGitHubDevBranchName() {
		return _jsonObject.getString("git_hub_dev_branch_name");
	}

	@Override
	public String getUpstreamBranchName() {
		return _jsonObject.getString("upstream_branch_name");
	}

	@Override
	public void setUp() {
		System.out.println();
		System.out.println("##");
		System.out.println("## " + getDirectory());
		System.out.println("## " + toString());
		System.out.println("##");
		System.out.println();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				_getBranchSHA(), true, _getBranchSHA());

		gitWorkingDirectory.createLocalGitBranch(localGitBranch, true);

		gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch);

		gitWorkingDirectory.reset("--hard " + localGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public void tearDown() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getLocalGitBranch(
				gitWorkingDirectory.getUpstreamBranchName());

		System.out.println();
		System.out.println("##");
		System.out.println("## " + getDirectory());
		System.out.println("## " + upstreamLocalGitBranch.toString());
		System.out.println("##");
		System.out.println();

		gitWorkingDirectory.checkoutLocalGitBranch(upstreamLocalGitBranch);

		gitWorkingDirectory.reset("--hard " + upstreamLocalGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			_getGitHubURL(), " - ", _getBranchSHA());
	}

	protected static String getRepositoryName(String gitHubURL) {
		Matcher matcher = _gitHubURLPattern.matcher(gitHubURL);

		matcher.find();

		return matcher.group("repositoryName");
	}

	protected BaseBuildRunnerGitRepository(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(getRepositoryName(gitHubURL), upstreamBranchName);

		Matcher matcher = _gitHubURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		_jsonObject = new JSONObject();

		_jsonObject.put("git_hub_url", gitHubURL);
		_jsonObject.put("upstream_branch_name", upstreamBranchName);

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					this, pullRequest, JenkinsResultsParserUtil.isCINode());

			_jsonObject.put("branch_head_sha", localGitBranch.getSHA());
			_jsonObject.put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				_jsonObject.put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(pullRequest));
			}
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					this, remoteGitRef, JenkinsResultsParserUtil.isCINode());

			_jsonObject.put("branch_head_sha", localGitBranch.getSHA());
			_jsonObject.put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				_jsonObject.put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(remoteGitRef));
			}
		}
		else {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		if ((branchSHA != null) && branchSHA.matches("[0-9a-f]{7,40}")) {
			_jsonObject.put("branch_sha", branchSHA);
		}
		else {
			_jsonObject.put("branch_sha", _getBranchHeadSHA());
		}

		_validateJSONObject();
	}

	private String _getBranchHeadSHA() {
		return _jsonObject.getString("branch_head_sha");
	}

	private String _getBranchSHA() {
		return _jsonObject.getString("branch_sha");
	}

	private String _getGitHubURL() {
		return _jsonObject.getString("git_hub_url");
	}

	private void _validateJSONObject() {
		if (!_jsonObject.has("branch_head_sha")) {
			throw new RuntimeException("Please set required branch_head_sha");
		}

		if (!_jsonObject.has("branch_name")) {
			throw new RuntimeException("Please set required branch_name");
		}

		if (!_jsonObject.has("branch_sha")) {
			throw new RuntimeException("Please set required branch_sha");
		}

		if (JenkinsResultsParserUtil.isCINode()) {
			if (!_jsonObject.has("git_hub_dev_branch_name")) {
				throw new RuntimeException(
					"Please set required git_hub_dev_branch_name");
			}
		}

		if (!_jsonObject.has("git_hub_url")) {
			throw new RuntimeException("Please set required git_hub_url");
		}

		if (!_jsonObject.has("upstream_branch_name")) {
			throw new RuntimeException(
				"Please set required upstream_branch_name");
		}
	}

	private static final Pattern _gitHubURLPattern = Pattern.compile(
		"https://[^/]+/[^/]+/(?<repositoryName>[^/]+)/.*");

	private final JSONObject _jsonObject;

}