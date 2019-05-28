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
	public List<LocalGitCommit> getHistoricalLocalGitCommits() {
		if (_historicalLocalGitCommits != null) {
			return _historicalLocalGitCommits;
		}

		if (!has("commits")) {
			return new ArrayList<>();
		}

		_historicalLocalGitCommits = new ArrayList<>();

		JSONArray commitsJSONArray = getJSONArray("commits");

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		for (int i = 0; i < commitsJSONArray.length(); i++) {
			JSONObject commitJSONObject = commitsJSONArray.getJSONObject(i);

			_historicalLocalGitCommits.add(
				GitCommitFactory.newLocalGitCommit(
					gitWorkingDirectory, commitJSONObject.getString("message"),
					commitJSONObject.getString("sha"),
					commitJSONObject.getLong("commitTime")));
		}

		return _historicalLocalGitCommits;
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
	public List<List<LocalGitCommit>> partitionLocalGitCommits(
		List<LocalGitCommit> localGitCommits, int count) {

		if (count <= 0) {
			throw new IllegalArgumentException("Invalid count " + count);
		}

		int localGitCommitsSize = 0;

		if ((localGitCommits != null) && !localGitCommits.isEmpty()) {
			localGitCommitsSize = localGitCommits.size();
		}

		if (count > localGitCommitsSize) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					String.valueOf(localGitCommitsSize),
					" commits cannot be split into ", String.valueOf(count),
					" lists"));
		}

		List<LocalGitCommit> lastLocalGitCommitsPartition = Lists.newArrayList(
			localGitCommits.get(localGitCommitsSize - 1));

		List<List<LocalGitCommit>> localGitCommitsPartitions = new ArrayList<>(
			count);

		if (localGitCommits.size() > 1) {
			localGitCommitsPartitions.addAll(
				JenkinsResultsParserUtil.partitionByCount(
					localGitCommits.subList(0, localGitCommitsSize - 2),
					count - 1));
		}

		localGitCommitsPartitions.add(lastLocalGitCommitsPartition);

		return localGitCommitsPartitions;
	}

	@Override
	public void setBranchSHA(String branchSHA) {
		if (branchSHA == null) {
			throw new RuntimeException("Branch SHA is null");
		}

		if (!branchSHA.matches(_REGEX_SHA)) {
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
			GitHubDevSyncUtil.fetchCacheBranchFromGitHubDev(
				gitWorkingDirectory, getGitHubDevBranchName());
		}

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				_getBranchName(), true, getBranchSHA());

		gitWorkingDirectory.createLocalGitBranch(localGitBranch, true);

		gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch);

		gitWorkingDirectory.reset("--hard " + localGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public void storeCommitHistory(List<String> commitSHAs) {
		List<LocalGitCommit> historicalLocalGitCommits =
			getHistoricalLocalGitCommits();

		List<String> requiredCommitSHAs = new ArrayList<>();

		requiredCommitSHAs.addAll(commitSHAs);

		JSONArray commitsJSONArray = new JSONArray();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		int index = 0;

		while (index < COMMITS_HISTORY_SIZE_MAX) {
			int currentGroupSize = COMMITS_HISTORY_GROUP_SIZE;

			if (index >
					(COMMITS_HISTORY_SIZE_MAX - COMMITS_HISTORY_GROUP_SIZE)) {

				currentGroupSize =
					COMMITS_HISTORY_SIZE_MAX % COMMITS_HISTORY_GROUP_SIZE;
			}

			List<LocalGitCommit> localGitCommits = gitWorkingDirectory.log(
				index, currentGroupSize);

			for (LocalGitCommit localGitCommit : localGitCommits) {
				historicalLocalGitCommits.add(localGitCommit);

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

			index += COMMITS_HISTORY_GROUP_SIZE;
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
			gitWorkingDirectory.getUpstreamLocalGitBranch();

		System.out.println();
		System.out.println("##");
		System.out.println("## " + upstreamLocalGitBranch.toString());
		System.out.println("##");
		System.out.println();

		gitWorkingDirectory.checkoutLocalGitBranch(upstreamLocalGitBranch);

		gitWorkingDirectory.reset("--hard " + upstreamLocalGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.cleanTempBranches();

		gitWorkingDirectory.displayLog();
	}

	@Override
	public String toString() {
		return JenkinsResultsParserUtil.combine(
			getGitHubURL(), " - ", getBranchSHA());
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
			validateKeys(_CI_KEYS_REQUIRED);
		}
	}

	protected BaseWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(
			pullRequest.getGitHubRemoteGitRepositoryName(), upstreamBranchName);

		_setGitHubURL(pullRequest.getHtmlURL());

		LocalGitBranch localGitBranch =
			GitHubDevSyncUtil.createCacheLocalGitBranch(
				this, pullRequest, JenkinsResultsParserUtil.isCINode());

		_setBranchHeadSHA(localGitBranch.getSHA());
		_setBranchName(localGitBranch.getName());

		setBranchSHA(localGitBranch.getSHA());

		_setType();

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			_setGitHubDevBranchName(
				GitHubDevSyncUtil.getCacheBranchName(pullRequest));

			validateKeys(_CI_KEYS_REQUIRED);
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
			GitHubDevSyncUtil.createCacheLocalGitBranch(
				this, remoteGitRef, JenkinsResultsParserUtil.isCINode());

		_setBranchHeadSHA(localGitBranch.getSHA());
		_setBranchName(localGitBranch.getName());

		setBranchSHA(localGitBranch.getSHA());

		_setType();

		validateKeys(_REQUIRED_KEYS);

		if (JenkinsResultsParserUtil.isCINode()) {
			_setGitHubDevBranchName(
				GitHubDevSyncUtil.getCacheBranchName(remoteGitRef));

			validateKeys(_CI_KEYS_REQUIRED);
		}
	}

	protected String getBranchSHA() {
		return optString("branch_sha");
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

		if (!branchHeadSHA.matches(_REGEX_SHA)) {
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

	private static final String[] _CI_KEYS_REQUIRED = {
		"git_hub_dev_branch_name"
	};

	private static final String _REGEX_SHA = "[0-9a-f]{7,40}";

	private static final String[] _REQUIRED_KEYS = {
		"branch_head_sha", "branch_name", "branch_sha", "git_hub_url", "type"
	};

	private List<LocalGitCommit> _historicalLocalGitCommits;
	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();

}