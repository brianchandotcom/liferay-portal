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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public String getGitHubDevBranchName() {
		return GitHubDevSyncUtil.getCacheBranchName(
			_getReceiverUsername(), _getSenderUsername(), _getSenderBranchSHA(),
			_getUpstreamBranchSHA());
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
					commitJSONObject.getString("emailAddress"),
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
		if (!JenkinsResultsParserUtil.isSHA(branchSHA)) {
			throw new RuntimeException("Branch SHA is invalid");
		}

		_branchSHA = branchSHA;
	}

	@Override
	public void setUp() {
		System.out.println(toString());

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		gitWorkingDirectory.checkoutLocalGitBranch(getLocalGitBranch());

		gitWorkingDirectory.reset("--hard " + getBranchSHA());

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
	public void synchronizeToGitHubDev() {
		try {
			GitHubDevSyncUtil.synchronizeToGitHubDev(
				getGitWorkingDirectory(), _getReceiverUsername(),
				_getSenderBranchName(), _getSenderUsername(),
				_getSenderBranchSHA(), _getUpstreamBranchSHA());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void tearDown() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		LocalGitBranch upstreamLocalGitBranch =
			gitWorkingDirectory.getUpstreamLocalGitBranch();

		System.out.println(upstreamLocalGitBranch);

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
	}

	protected BaseWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(
			pullRequest.getGitHubRemoteGitRepositoryName(), upstreamBranchName);

		_setType();

		_setGitHubURL(pullRequest.getHtmlURL());
		_setReceiverUsername(pullRequest.getReceiverUsername());
		_setSenderBranchName(pullRequest.getSenderBranchName());
		_setSenderUsername(pullRequest.getSenderUsername());
		_setSenderBranchSHA(pullRequest.getSenderSHA());
		_setUpstreamBranchSHA(pullRequest.getUpstreamBranchSHA());

		validateKeys(_REQUIRED_KEYS);
	}

	protected BaseWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef.getRepositoryName(), upstreamBranchName);

		_setType();

		_setGitHubURL(remoteGitRef.getHtmlURL());
		_setReceiverUsername(remoteGitRef.getUsername());
		_setSenderBranchName(remoteGitRef.getName());
		_setSenderUsername(remoteGitRef.getUsername());
		_setSenderBranchSHA(remoteGitRef.getSHA());
		_setUpstreamBranchSHA(remoteGitRef.getSHA());

		validateKeys(_REQUIRED_KEYS);
	}

	protected String getBranchSHA() {
		if (_branchSHA != null) {
			return _branchSHA;
		}

		return _getSenderBranchSHA();
	}

	protected LocalGitBranch getLocalGitBranch() {
		if (_localGitBranch != null) {
			return _localGitBranch;
		}

		if (_isPullRequest()) {
			_localGitBranch = _createPullRequestLocalGitBranch();
		}
		else {
			_localGitBranch = _createRemoteGitRefLocalGitBranch();
		}

		return _localGitBranch;
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

	private LocalGitBranch _createPullRequestLocalGitBranch() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		List<GitRemote> gitHubDevGitRemotes =
			GitHubDevSyncUtil.getGitHubDevGitRemotes(gitWorkingDirectory);

		for (int i = 0; i < 3; i++) {
			GitRemote gitHubDevGitRemote = gitHubDevGitRemotes.get(i);

			RemoteGitBranch remoteGitBranch =
				gitWorkingDirectory.getRemoteGitBranch(
					getGitHubDevBranchName(), gitHubDevGitRemote);

			if (remoteGitBranch == null) {
				continue;
			}

			String remoteGitBranchSHA = remoteGitBranch.getSHA();

			gitWorkingDirectory.fetch(remoteGitBranch);

			if (!gitWorkingDirectory.localSHAExists(remoteGitBranchSHA)) {
				continue;
			}

			setBranchSHA(remoteGitBranchSHA);

			return gitWorkingDirectory.createLocalGitBranch(
				_getBranchName(), true, getBranchSHA());
		}

		String senderBranchSHA = _getSenderBranchSHA();

		if (!gitWorkingDirectory.localSHAExists(senderBranchSHA)) {
			gitWorkingDirectory.fetch(_getSenderRemoteGitRef());
		}

		String upstreamBranchSHA = _getUpstreamBranchSHA();

		if (!gitWorkingDirectory.localSHAExists(upstreamBranchSHA)) {
			gitWorkingDirectory.fetch(_getUpstreamRemoteGitRef());
		}

		LocalGitBranch localGitBranch =
			gitWorkingDirectory.getRebasedLocalGitBranch(
				_getBranchName(), _getSenderBranchName(),
				JenkinsResultsParserUtil.combine(
					"git@github.com:", _getSenderUsername(), "/", getName()),
				senderBranchSHA, getUpstreamBranchName(), upstreamBranchSHA);

		setBranchSHA(localGitBranch.getSHA());

		return localGitBranch;
	}

	private LocalGitBranch _createRemoteGitRefLocalGitBranch() {
		String senderBranchSHA = _getSenderBranchSHA();

		setBranchSHA(senderBranchSHA);

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (gitWorkingDirectory.localSHAExists(senderBranchSHA)) {
			return gitWorkingDirectory.createLocalGitBranch(
				_getBranchName(), true, getBranchSHA());
		}

		List<GitRemote> gitHubDevGitRemotes =
			GitHubDevSyncUtil.getGitHubDevGitRemotes(gitWorkingDirectory);

		for (int i = 0; i < 3; i++) {
			GitRemote gitHubDevGitRemote = gitHubDevGitRemotes.get(i);

			RemoteGitBranch remoteGitBranch =
				gitWorkingDirectory.getRemoteGitBranch(
					getGitHubDevBranchName(), gitHubDevGitRemote);

			if (remoteGitBranch == null) {
				continue;
			}

			String remoteGitBranchSHA = remoteGitBranch.getSHA();

			gitWorkingDirectory.fetch(remoteGitBranch);

			if (!gitWorkingDirectory.localSHAExists(remoteGitBranchSHA)) {
				continue;
			}

			return gitWorkingDirectory.createLocalGitBranch(
				_getBranchName(), true, getBranchSHA());
		}

		if (!gitWorkingDirectory.localSHAExists(senderBranchSHA)) {
			gitWorkingDirectory.fetch(_getSenderRemoteGitRef());
		}

		return gitWorkingDirectory.createLocalGitBranch(
			_getBranchName(), true, getBranchSHA());
	}

	private String _getBranchName() {
		if (_branchName != null) {
			return _branchName;
		}

		_branchName = JenkinsResultsParserUtil.combine(
			getUpstreamBranchName(), "-temp-",
			String.valueOf(JenkinsResultsParserUtil.getCurrentTimeMillis()));

		return _branchName;
	}

	private String _getReceiverUsername() {
		return optString("receiver_username");
	}

	private String _getSenderBranchName() {
		return optString("sender_branch_name");
	}

	private String _getSenderBranchSHA() {
		return optString("sender_branch_sha");
	}

	private RemoteGitRef _getSenderRemoteGitRef() {
		if (_senderRemoteGitRef != null) {
			return _senderRemoteGitRef;
		}

		_senderRemoteGitRef = GitUtil.getRemoteGitRef(
			JenkinsResultsParserUtil.combine(
				"https://github.com/", _getSenderUsername(), "/", getName(),
				"/tree/", _getSenderBranchName()));

		return _senderRemoteGitRef;
	}

	private String _getSenderUsername() {
		return optString("sender_username");
	}

	private String _getUpstreamBranchSHA() {
		return optString("upstream_branch_sha");
	}

	private RemoteGitRef _getUpstreamRemoteGitRef() {
		if (_upstreamRemoteGitRef != null) {
			return _upstreamRemoteGitRef;
		}

		_upstreamRemoteGitRef = GitUtil.getRemoteGitRef(
			JenkinsResultsParserUtil.combine(
				"https://github.com/liferay/", getName(), "/tree/",
				getUpstreamBranchName()));

		return _upstreamRemoteGitRef;
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

	private boolean _isPullRequest() {
		return !Objects.equals(_getSenderBranchSHA(), _getUpstreamBranchSHA());
	}

	private void _setGitHubURL(String gitHubURL) {
		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		put("git_hub_url", gitHubURL);
	}

	private void _setReceiverUsername(String receiverUsername) {
		if (receiverUsername == null) {
			throw new RuntimeException("Receiver username is null");
		}

		put("receiver_username", receiverUsername);
	}

	private void _setSenderBranchName(String senderBranchName) {
		if (senderBranchName == null) {
			throw new RuntimeException("Sender branch name is null");
		}

		put("sender_branch_name", senderBranchName);
	}

	private void _setSenderBranchSHA(String senderBranchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(senderBranchSHA)) {
			throw new RuntimeException("Sender branch SHA is invalid");
		}

		put("sender_branch_sha", senderBranchSHA);
	}

	private void _setSenderUsername(String senderUsername) {
		if (senderUsername == null) {
			throw new RuntimeException("Sender username is null");
		}

		put("sender_username", senderUsername);
	}

	private void _setType() {
		put("type", getType());
	}

	private void _setUpstreamBranchSHA(String upstreamBranchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(upstreamBranchSHA)) {
			throw new RuntimeException("Upstream branch SHA is invalid");
		}

		put("upstream_branch_sha", upstreamBranchSHA);
	}

	private static final String[] _REQUIRED_KEYS = {
		"git_hub_url", "receiver_username", "sender_branch_name",
		"sender_branch_sha", "sender_username", "upstream_branch_sha", "type"
	};

	private String _branchName;
	private String _branchSHA;
	private List<LocalGitCommit> _historicalLocalGitCommits;
	private LocalGitBranch _localGitBranch;
	private final Map<String, Properties> _propertiesFilesMap = new HashMap<>();
	private RemoteGitRef _senderRemoteGitRef;
	private RemoteGitRef _upstreamRemoteGitRef;

}