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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspaceGitRepository
	extends BaseLocalGitRepository implements WorkspaceGitRepository {

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
		return getString("git_hub_dev_branch_name");
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

	@Override
	public void writePropertiesFiles() {
		for (Map.Entry<String, Properties> entry :
				_propertiesFilesMap.entrySet()) {

			JenkinsResultsParserUtil.writePropertiesFile(
				new File(getDirectory(), entry.getKey()), entry.getValue(),
				true);
		}
	}

	protected BaseWorkspaceGitRepository(
		String gitHubURL, String upstreamBranchName, String branchSHA) {

		super(_getRepositoryName(gitHubURL), upstreamBranchName);

		Matcher matcher = _gitHubURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		put("git_hub_url", gitHubURL);

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = new PullRequest(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					this, pullRequest, JenkinsResultsParserUtil.isCINode());

			put("branch_head_sha", localGitBranch.getSHA());
			put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(pullRequest));
			}
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			RemoteGitRef remoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			LocalGitBranch localGitBranch =
				GitHubDevSyncUtil.createCachedLocalGitBranch(
					this, remoteGitRef, JenkinsResultsParserUtil.isCINode());

			put("branch_head_sha", localGitBranch.getSHA());
			put("branch_name", localGitBranch.getName());

			if (JenkinsResultsParserUtil.isCINode()) {
				put(
					"git_hub_dev_branch_name",
					GitHubDevSyncUtil.getCachedBranchName(remoteGitRef));
			}
		}
		else {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		if ((branchSHA != null) && branchSHA.matches("[0-9a-f]{7,40}")) {
			put("branch_sha", branchSHA);
		}
		else {
			put("branch_sha", _getBranchHeadSHA());
		}

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			validateKeys(_REQUIRED_CI_KEYS);
		}
	}

	protected void setProperties(String filePath, Properties properties) {
		if (!_propertiesFilesMap.containsKey(filePath)) {
			_propertiesFilesMap.put(filePath, new Properties());
		}

		Properties fileProperties = _propertiesFilesMap.get(filePath);

		fileProperties.putAll(properties);

		_propertiesFilesMap.put(filePath, fileProperties);
	}

	private static String _getRepositoryName(String gitHubURL) {
		Matcher matcher = _gitHubURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		return matcher.group("repositoryName");
	}

	private String _getBranchHeadSHA() {
		return getString("branch_head_sha");
	}

	private String _getBranchSHA() {
		return getString("branch_sha");
	}

	private String _getGitHubURL() {
		return getString("git_hub_url");
	}

	private static final String[] _REQUIRED_CI_KEYS =
		{"git_hub_dev_branch_name"};

	private static final String[] _REQUIRED_KEYS =
		{"branch_head_sha", "branch_name", "branch_sha", "git_hub_url"};

	private static final Pattern _gitHubURLPattern = Pattern.compile(
		"https://[^/]+/[^/]+/(?<repositoryName>[^/]+)/.*");

	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();

}