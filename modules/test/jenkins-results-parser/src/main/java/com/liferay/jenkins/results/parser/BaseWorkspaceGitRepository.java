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

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspaceGitRepository
	extends BaseLocalGitRepository implements WorkspaceGitRepository {

	@Override
	public List<List<LocalGitCommit>> getCommitGroups(
		List<LocalGitCommit> localGitCommits, int count) {

		if (count >= localGitCommits.size()) {
			List<List<LocalGitCommit>> commitGroups = new ArrayList<>();

			for (LocalGitCommit localGitCommit : localGitCommits) {
				commitGroups.add(Lists.newArrayList(localGitCommit));
			}

			return commitGroups;
		}

		List<List<LocalGitCommit>> commitGroups = new ArrayList<>();

		int totalCommitCount = localGitCommits.size();

		int commitGroupSize = totalCommitCount / (count - 1);

		List<LocalGitCommit> commitGroup = null;

		for (int i = 0; i < (totalCommitCount - 1); i++) {
			if ((i % commitGroupSize) == 0) {
				commitGroup = new ArrayList<>();

				commitGroups.add(commitGroup);
			}

			commitGroup.add(localGitCommits.get(i));
		}

		commitGroups.add(
			Lists.newArrayList(localGitCommits.get(totalCommitCount - 1)));

		return commitGroups;
	}

	@Override
	public List<LocalGitCommit> getCommitHistory() {
		if (_commitHistory != null) {
			return _commitHistory;
		}

		if (!has("commits")) {
			return new ArrayList<>();
		}

		_commitHistory = new ArrayList<>();

		JSONArray commitsJSONArray = getJSONArray("commits");

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		for (int i = 0; i < commitsJSONArray.length(); i++) {
			JSONObject commitJSONObject = commitsJSONArray.getJSONObject(i);

			_commitHistory.add(
				GitCommitFactory.newLocalGitCommit(
					gitWorkingDirectory, commitJSONObject.getString("message"),
					commitJSONObject.getString("sha"),
					commitJSONObject.getLong("commitTime")));
		}

		return _commitHistory;
	}

	@Override
	public List<LocalGitCommit> getCommitsInRange(
		String earliestSHA, String latestSHA) {

		List<LocalGitCommit> localGitCommitsInRange = new ArrayList<>();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		int index = 0;

		while (index < MAX_COMMIT_HISTORY) {
			int currentGroupSize = _COMMIT_HISTORY_GROUP_SIZE;

			if (index > (MAX_COMMIT_HISTORY - _COMMIT_HISTORY_GROUP_SIZE)) {
				currentGroupSize =
					MAX_COMMIT_HISTORY % _COMMIT_HISTORY_GROUP_SIZE;
			}

			List<LocalGitCommit> localGitCommits = gitWorkingDirectory.log(
				index, currentGroupSize, latestSHA);

			for (LocalGitCommit localGitCommit : localGitCommits) {
				localGitCommitsInRange.add(localGitCommit);

				if (earliestSHA.equals(localGitCommit.getSHA())) {
					return localGitCommitsInRange;
				}
			}

			index += _COMMIT_HISTORY_GROUP_SIZE;
		}

		return localGitCommitsInRange;
	}

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
	public String getGitHubURL() {
		return getString("git_hub_url");
	}

	@Override
	public Properties getWorkspaceJobProperties(String propertyType, Job job) {
		Properties jobProperties = job.getJobProperties();

		Set<String> workspaceJobPropertyNames = new HashSet<>();

		for (String jobPropertyName : jobProperties.stringPropertyNames()) {
			if (!jobPropertyName.startsWith(propertyType)) {
				continue;
			}

			String workspaceJobPropertyName = _getWorkspaceJobPropertyName(
				jobPropertyName);

			if (workspaceJobPropertyName == null) {
				continue;
			}

			workspaceJobPropertyNames.add(workspaceJobPropertyName);
		}

		Properties workspaceJobProperties = new Properties();

		for (String workspaceJobPropertyName : workspaceJobPropertyNames) {
			String workspaceJobPropertyValue =
				JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyType, workspaceJobPropertyName,
					getUpstreamBranchName());

			if ((workspaceJobPropertyValue == null) &&
				(job instanceof TestSuiteJob)) {

				TestSuiteJob testSuiteJob = (TestSuiteJob)job;

				workspaceJobPropertyValue =
					JenkinsResultsParserUtil.getProperty(
						jobProperties, propertyType, workspaceJobPropertyName,
						testSuiteJob.getTestSuiteName());
			}

			if ((workspaceJobPropertyValue == null) &&
				JenkinsResultsParserUtil.isWindows()) {

				workspaceJobPropertyValue =
					JenkinsResultsParserUtil.getProperty(
						jobProperties, propertyType, workspaceJobPropertyName,
						"windows");
			}

			if (workspaceJobPropertyValue != null) {
				workspaceJobProperties.put(
					workspaceJobPropertyName, workspaceJobPropertyValue);
			}
		}

		return workspaceJobProperties;
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
	public void storeCommitHistory(List<String> commitSHAs) {
		_commitHistory = getCommitHistory();

		List<String> requiredCommitSHAs = new ArrayList<>();

		requiredCommitSHAs.addAll(commitSHAs);

		JSONArray commitsJSONArray = new JSONArray();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		int index = 0;

		while (index < MAX_COMMIT_HISTORY) {
			int currentGroupSize = _COMMIT_HISTORY_GROUP_SIZE;

			if (index > (MAX_COMMIT_HISTORY - _COMMIT_HISTORY_GROUP_SIZE)) {
				currentGroupSize =
					MAX_COMMIT_HISTORY % _COMMIT_HISTORY_GROUP_SIZE;
			}

			List<LocalGitCommit> localGitCommits = gitWorkingDirectory.log(
				index, currentGroupSize);

			for (LocalGitCommit localGitCommit : localGitCommits) {
				_commitHistory.add(localGitCommit);

				commitsJSONArray.put(localGitCommit.toJSONObject());

				String sha = localGitCommit.getSHA();

				if (requiredCommitSHAs.contains(sha)) {
					requiredCommitSHAs.remove(sha);
				}

				if (requiredCommitSHAs.isEmpty()) {
					break;
				}
			}

			if (requiredCommitSHAs.isEmpty()) {
				break;
			}

			index += _COMMIT_HISTORY_GROUP_SIZE;
		}

		if (!requiredCommitSHAs.isEmpty()) {
			throw new RuntimeException(
				"Unable to find the following SHAs: " + requiredCommitSHAs);
		}

		put("commits", commitsJSONArray);
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
			getGitHubURL(), " - ", _getBranchSHA());
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

	private String _getWorkspaceJobPropertyName(String jobPropertyName) {
		Stack<Integer> stack = new Stack<>();

		Integer start = null;
		Integer end = null;

		for (int i = 0; i < jobPropertyName.length(); i++) {
			char c = jobPropertyName.charAt(i);

			if (c == '[') {
				stack.push(i);

				if (start == null) {
					start = i;
				}
			}

			if (c == ']') {
				if (start == null) {
					continue;
				}

				stack.pop();

				if (stack.isEmpty()) {
					end = i;

					break;
				}
			}
		}

		if ((start != null) && (end != null)) {
			return jobPropertyName.substring(start + 1, end);
		}

		return null;
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

	private static final Integer _COMMIT_HISTORY_GROUP_SIZE = 100;

	private static final String[] _REQUIRED_CI_KEYS =
		{"git_hub_dev_branch_name"};

	private static final String[] _REQUIRED_KEYS =
		{"branch_head_sha", "branch_name", "branch_sha", "git_hub_url", "type"};

	private static final String _SHA_REGEX = "[0-9a-f]{7,40}";

	private List<LocalGitCommit> _commitHistory;
	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();

}