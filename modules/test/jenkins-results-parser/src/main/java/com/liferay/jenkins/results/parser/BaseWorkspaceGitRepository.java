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

import org.json.JSONObject;

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
	public void setBranchSHA(String branchSHA) {
		if (branchSHA == null) {
			throw new RuntimeException("Branch SHA is null");
		}

		if (!branchSHA.matches(_SHA_REGEX)) {
			throw new RuntimeException("Branch SHA is invalid");
		}

		put("branch_sha", branchSHA);
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

		if (!gitWorkingDirectory.localSHAExists(_getBranchHeadSHA())) {
			GitHubDevSyncUtil.fetchCachedBranchFromGitHubDev(
				gitWorkingDirectory, getGitHubDevBranchName());
		}

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				_getBranchName(), true, _getBranchSHA());

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

	protected BaseWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			validateKeys(_REQUIRED_CI_KEYS);
		}
	}

	protected BaseWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(
			pullRequest.getGitHubRemoteGitRepositoryName(), upstreamBranchName);

		_setGitHubURL(pullRequest.getHtmlURL());

		LocalGitBranch localGitBranch =
			GitHubDevSyncUtil.createCachedLocalGitBranch(
				this, pullRequest, JenkinsResultsParserUtil.isCINode());

		_setBranchHeadSHA(localGitBranch.getSHA());
		_setBranchName(localGitBranch.getName());

		setBranchSHA(localGitBranch.getSHA());

		_setType();

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			_setGitHubDevBranchName(
				GitHubDevSyncUtil.getCachedBranchName(pullRequest));

			validateKeys(_REQUIRED_CI_KEYS);
		}
	}

	protected BaseWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef.getRepositoryName(), upstreamBranchName);

		_setGitHubURL(
			JenkinsResultsParserUtil.combine(
				"https://github.com/", remoteGitRef.getUsername(), "/",
				remoteGitRef.getRepositoryName(), "/tree/",
				remoteGitRef.getName()));

		LocalGitBranch localGitBranch =
			GitHubDevSyncUtil.createCachedLocalGitBranch(
				this, remoteGitRef, JenkinsResultsParserUtil.isCINode());

		_setBranchHeadSHA(localGitBranch.getSHA());
		_setBranchName(localGitBranch.getName());

		setBranchSHA(localGitBranch.getSHA());

		_setType();

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			_setGitHubDevBranchName(
				GitHubDevSyncUtil.getCachedBranchName(remoteGitRef));

			validateKeys(_REQUIRED_CI_KEYS);
		}
	}

	@Override
	protected void put(String key, Object value) {
		super.put(key, value);

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		buildDatabase.putWorkspaceGitRepository(getType(), this);
	}

	protected void setProperties(String filePath, Properties properties) {
		if (!_propertiesFilesMap.containsKey(filePath)) {
			_propertiesFilesMap.put(filePath, new Properties());
		}

		Properties fileProperties = _propertiesFilesMap.get(filePath);

		fileProperties.putAll(properties);

		_propertiesFilesMap.put(filePath, fileProperties);
	}

	private String _getBranchHeadSHA() {
		return getString("branch_head_sha");
	}

	private String _getBranchName() {
		return getString("branch_name");
	}

	private String _getBranchSHA() {
		return optString("branch_sha");
	}

	private String _getGitHubURL() {
		return getString("git_hub_url");
	}

	private void _setBranchHeadSHA(String branchHeadSHA) {
		if (branchHeadSHA == null) {
			throw new RuntimeException("Branch head SHA is null");
		}

		if (!branchHeadSHA.matches(_SHA_REGEX)) {
			throw new RuntimeException("Branch head SHA is invalid");
		}

		put("branch_head_sha", branchHeadSHA);
	}

	private void _setBranchName(String branchName) {
		if (branchName == null) {
			throw new RuntimeException("Branch name is null");
		}

		put("branch_name", branchName);
	}

	private void _setGitHubDevBranchName(String gitHubDevBranchName) {
		if (gitHubDevBranchName == null) {
			throw new RuntimeException("GitHub dev branch name is null");
		}

		put("git_hub_dev_branch_name", gitHubDevBranchName);
	}

	private void _setGitHubURL(String gitHubURL) {
		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		put("git_hub_url", gitHubURL);
	}

	private void _setType() {
		put("type", getType());
	}

	private static final String[] _REQUIRED_CI_KEYS =
		{"git_hub_dev_branch_name"};

	private static final String[] _REQUIRED_KEYS =
		{"branch_head_sha", "branch_name", "branch_sha", "git_hub_url", "type"};

	private static final String _SHA_REGEX = "[0-9a-f]{7,40}";

	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();

}