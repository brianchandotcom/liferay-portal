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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseWorkspaceGitRepository
	extends BaseLocalGitRepository implements WorkspaceGitRepository {

	@Override
	public void addPropertyOption(String propertyOption) {
		if (JenkinsResultsParserUtil.isNullOrEmpty(propertyOption)) {
			return;
		}

		_propertyOptions.add(propertyOption);
	}

	@Override
	public String getBaseBranchSHA() {
		return getString("base_branch_sha");
	}

	@Override
	public String getBranchName() {
		if (_branchName != null) {
			return _branchName;
		}

		_branchName = JenkinsResultsParserUtil.combine(
			getUpstreamBranchName(), "-temp-",
			String.valueOf(JenkinsResultsParserUtil.getCurrentTimeMillis()));

		return _branchName;
	}

	@Override
	public String getFileContent(String filePath) {
		File file = new File(getDirectory(), filePath);

		if (!file.exists()) {
			return null;
		}

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
		String baseBranchSHA = _getBaseBranchHeadSHA();
		String senderBranchSHA = _getSenderBranchHeadSHA();

		if (_isPullRequest()) {
			baseBranchSHA = getBaseBranchSHA();
			senderBranchSHA = getSenderBranchSHA();
		}

		return GitHubDevSyncUtil.getCacheBranchName(
			_getBaseBranchUsername(), getSenderBranchUsername(),
			senderBranchSHA, baseBranchSHA);
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
	public String getSenderBranchName() {
		return getString("sender_branch_name");
	}

	@Override
	public String getSenderBranchSHA() {
		return getString("sender_branch_sha");
	}

	@Override
	public String getSenderBranchUsername() {
		return getString("sender_branch_username");
	}

	@Override
	public List<List<LocalGitCommit>> partitionLocalGitCommits(
		List<LocalGitCommit> localGitCommits, int count) {

		if (count <= 0) {
			throw new IllegalArgumentException("Invalid count " + count);
		}

		if ((localGitCommits == null) || localGitCommits.isEmpty()) {
			return Collections.emptyList();
		}

		int localGitCommitsSize = localGitCommits.size();

		if (count > localGitCommitsSize) {
			List<List<LocalGitCommit>> partitionedLocalGitCommits =
				new ArrayList<>(localGitCommitsSize);

			for (LocalGitCommit localGitCommit : localGitCommits) {
				partitionedLocalGitCommits.add(
					Lists.newArrayList(localGitCommit));
			}

			return partitionedLocalGitCommits;
		}

		List<List<LocalGitCommit>> partitionedLocalGitCommits = new ArrayList<>(
			count);

		LocalGitCommit lastLocalGitCommit = localGitCommits.remove(
			localGitCommits.size() - 1);

		if (!localGitCommits.isEmpty()) {
			partitionedLocalGitCommits.addAll(
				JenkinsResultsParserUtil.partitionByCount(
					localGitCommits, count - 1));
		}

		partitionedLocalGitCommits.add(Lists.newArrayList(lastLocalGitCommit));

		return partitionedLocalGitCommits;
	}

	@Override
	public void setBaseBranchSHA(String branchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(branchSHA)) {
			throw new RuntimeException("Invalid base branch SHA " + branchSHA);
		}

		put("base_branch_sha", branchSHA);
	}

	@Override
	public void setGitHubURL(String gitHubURL) {
		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		if (gitHubURL.equals(optString("git_hub_url")) && !_rebase) {
			return;
		}

		_localGitBranch = null;

		_setGitHubURL(gitHubURL);

		if (PullRequest.isValidGitHubPullRequestURL(gitHubURL)) {
			PullRequest pullRequest = PullRequestFactory.newPullRequest(
				gitHubURL);

			_upstreamRemoteGitRef = pullRequest.getUpstreamRemoteGitBranch();

			_setBaseBranchHeadSHA(_upstreamRemoteGitRef.getSHA());
			setBaseBranchSHA(_upstreamRemoteGitRef.getSHA());
			_setBaseBranchUsername(_upstreamRemoteGitRef.getUsername());

			_senderRemoteGitRef = pullRequest.getSenderRemoteGitBranch();

			_setSenderBranchHeadSHA(_senderRemoteGitRef.getSHA());
			_setSenderBranchName(_senderRemoteGitRef.getName());
			setSenderBranchSHA(_senderRemoteGitRef.getSHA());
			_setSenderBranchUsername(_senderRemoteGitRef.getUsername());
		}
		else if (GitUtil.isValidGitHubRefURL(gitHubURL)) {
			_upstreamRemoteGitRef = _getUpstreamRemoteGitRef();

			_setBaseBranchHeadSHA(_upstreamRemoteGitRef.getSHA());
			setBaseBranchSHA(_upstreamRemoteGitRef.getSHA());
			_setBaseBranchUsername(_upstreamRemoteGitRef.getUsername());

			_senderRemoteGitRef = GitUtil.getRemoteGitRef(gitHubURL);

			_setSenderBranchHeadSHA(_senderRemoteGitRef.getSHA());
			_setSenderBranchName(_senderRemoteGitRef.getName());
			setSenderBranchSHA(_senderRemoteGitRef.getSHA());
			_setSenderBranchUsername(_senderRemoteGitRef.getUsername());

			if (_rebase) {
				_setBaseBranchHeadSHA(_upstreamRemoteGitRef.getSHA());
				setBaseBranchSHA(_upstreamRemoteGitRef.getSHA());
				_setBaseBranchUsername(_upstreamRemoteGitRef.getUsername());
			}
		}
		else {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		validateKeys(_REQUIRED_KEYS);

		BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

		buildDatabase.putWorkspaceGitRepository(getDirectoryName(), this);
	}

	public void setRebase(boolean rebase) {
		_rebase = rebase;
	}

	@Override
	public void setSenderBranchSHA(String branchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(branchSHA)) {
			throw new RuntimeException(
				"Invalid sender branch SHA " + branchSHA);
		}

		put("sender_branch_sha", branchSHA);

		if (!_isPullRequest()) {
			setBaseBranchSHA(branchSHA);
		}
	}

	@Override
	public synchronized void setUp() {
		if (_setUp) {
			return;
		}

		System.out.println(toString());

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (_rebase) {
			gitWorkingDirectory.createLocalGitBranch(
				getUpstreamBranchName(), true, getBaseBranchSHA());
		}

		LocalGitBranch localGitBranch = getLocalGitBranch();

		gitWorkingDirectory.checkoutLocalGitBranch(localGitBranch);

		LocalGitBranch baseLocalGitBranch =
			gitWorkingDirectory.createLocalGitBranch(
				getUpstreamBranchName(), true, getBaseBranchSHA());

		if (_rebase) {
			gitWorkingDirectory.rebase(
				true, baseLocalGitBranch, localGitBranch);
		}

		gitWorkingDirectory.reset("--hard " + localGitBranch.getSHA());

		gitWorkingDirectory.clean();

		gitWorkingDirectory.displayLog();

		_setUp = true;
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
		GitHubDevSyncUtil.synchronizeToGitHubDev(getLocalGitBranch(), this);
	}

	@Override
	public void tearDown() {
		_deleteLockFiles();

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
		StringBuilder sb = new StringBuilder();

		sb.append(getDirectory());
		sb.append(" - ");
		sb.append(getGitHubURL());
		sb.append(" - ");
		sb.append(getSenderBranchName());
		sb.append(" (");
		sb.append(getSenderBranchSHA(), 0, 7);
		sb.append(")");

		if (_isPullRequest()) {
			sb.append(" - ");
			sb.append(getUpstreamBranchName());
			sb.append(" (");
			sb.append(getBaseBranchSHA(), 0, 7);
			sb.append(")");
		}

		return sb.toString();
	}

	@Override
	public void writePropertiesFiles() {
	}

	protected BaseWorkspaceGitRepository(JSONObject jsonObject) {
		super(jsonObject);

		validateKeys(_REQUIRED_KEYS);
	}

	protected BaseWorkspaceGitRepository(
		PullRequest pullRequest, String upstreamBranchName) {

		super(
			pullRequest.getGitHubRemoteGitRepositoryName(), upstreamBranchName);

		setGitHubURL(pullRequest.getHtmlURL());

		validateKeys(_REQUIRED_KEYS);
	}

	protected BaseWorkspaceGitRepository(
		RemoteGitRef remoteGitRef, String upstreamBranchName) {

		super(remoteGitRef.getRepositoryName(), upstreamBranchName);

		setGitHubURL(remoteGitRef.getHtmlURL());

		validateKeys(_REQUIRED_KEYS);
	}

	protected synchronized LocalGitBranch getLocalGitBranch() {
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

	protected Properties getProperties(String propertyType) {
		Properties buildProperties = new Properties();

		Map<String, String> envMap = System.getenv();

		for (Map.Entry<String, String> envEntry : envMap.entrySet()) {
			buildProperties.setProperty(
				"env." + envEntry.getKey(), envEntry.getValue());
		}

		buildProperties.putAll(System.getenv());

		try {
			buildProperties.putAll(
				JenkinsResultsParserUtil.getBuildProperties());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		Properties properties = new Properties();

		for (String buildPropertyName : buildProperties.stringPropertyNames()) {
			if (!buildPropertyName.startsWith(propertyType)) {
				continue;
			}

			List<String> buildPropertyOptions =
				JenkinsResultsParserUtil.getPropertyOptions(buildPropertyName);

			if (buildPropertyOptions.isEmpty()) {
				continue;
			}

			String propertyName = buildPropertyOptions.get(0);

			List<String> propertyOptions = new ArrayList<>(
				getPropertyOptions());

			propertyOptions.removeAll(Collections.singleton(null));

			String propertyValue = JenkinsResultsParserUtil.getProperty(
				buildProperties, propertyType + "[" + propertyName + "]",
				propertyOptions.toArray(new String[0]));

			if (propertyValue == null) {
				continue;
			}

			if (JenkinsResultsParserUtil.isWindows() &&
				propertyValue.startsWith("/") &&
				!propertyValue.startsWith("/c/")) {

				propertyValue = "C:" + propertyValue;
			}

			properties.put(propertyName, propertyValue);
		}

		return properties;
	}

	protected Set<String> getPropertyOptions() {
		return _propertyOptions;
	}

	private LocalGitBranch _createPullRequestLocalGitBranch() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		List<GitRemote> gitHubDevGitRemotes =
			GitHubDevSyncUtil.getGitHubDevGitRemotes(gitWorkingDirectory);

		for (int i = 0; i < 3; i++) {
			if (gitHubDevGitRemotes.isEmpty()) {
				break;
			}

			GitRemote randomGitRemote =
				JenkinsResultsParserUtil.getRandomListItem(gitHubDevGitRemotes);

			gitHubDevGitRemotes.remove(randomGitRemote);

			RemoteGitBranch remoteGitBranch =
				gitWorkingDirectory.getRemoteGitBranch(
					getGitHubDevBranchName(), randomGitRemote);

			if (remoteGitBranch == null) {
				continue;
			}

			String remoteGitBranchSHA = remoteGitBranch.getSHA();

			try {
				gitWorkingDirectory.fetch(remoteGitBranch);
			}
			catch (Exception exception) {
				continue;
			}

			if (!gitWorkingDirectory.localSHAExists(remoteGitBranchSHA)) {
				continue;
			}

			return gitWorkingDirectory.createLocalGitBranch(
				getBranchName(), true, remoteGitBranchSHA);
		}

		String senderBranchSHA = getSenderBranchSHA();

		if (!gitWorkingDirectory.localSHAExists(senderBranchSHA)) {
			gitWorkingDirectory.fetch(_getSenderRemoteGitRef());
		}

		String baseBranchSHA = getBaseBranchSHA();

		if (!gitWorkingDirectory.localSHAExists(baseBranchSHA)) {
			gitWorkingDirectory.fetch(_getUpstreamRemoteGitRef());
		}

		gitWorkingDirectory.createLocalGitBranch(
			getUpstreamBranchName(), true, baseBranchSHA);

		return gitWorkingDirectory.getRebasedLocalGitBranch(
			getBranchName(), getSenderBranchName(),
			JenkinsResultsParserUtil.combine(
				"git@github.com:", getSenderBranchUsername(), "/", getName()),
			senderBranchSHA, getUpstreamBranchName(), baseBranchSHA);
	}

	private LocalGitBranch _createRemoteGitRefLocalGitBranch() {
		String senderBranchHeadSHA = _getSenderBranchHeadSHA();

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!gitWorkingDirectory.localSHAExists(senderBranchHeadSHA)) {
			List<GitRemote> gitHubDevGitRemotes =
				GitHubDevSyncUtil.getGitHubDevGitRemotes(gitWorkingDirectory);

			for (int i = 0; i < 3; i++) {
				if (gitHubDevGitRemotes.isEmpty()) {
					break;
				}

				GitRemote randomGitRemote =
					JenkinsResultsParserUtil.getRandomListItem(
						gitHubDevGitRemotes);

				gitHubDevGitRemotes.remove(randomGitRemote);

				RemoteGitBranch remoteGitBranch =
					gitWorkingDirectory.getRemoteGitBranch(
						getGitHubDevBranchName(), randomGitRemote);

				if (remoteGitBranch == null) {
					continue;
				}

				try {
					gitWorkingDirectory.fetch(remoteGitBranch);
				}
				catch (Exception exception) {
					continue;
				}

				if (!gitWorkingDirectory.localSHAExists(senderBranchHeadSHA)) {
					continue;
				}

				break;
			}

			if (!gitWorkingDirectory.localSHAExists(senderBranchHeadSHA)) {
				gitWorkingDirectory.fetch(_getSenderRemoteGitRef());
			}
		}

		return gitWorkingDirectory.createLocalGitBranch(
			getBranchName(), true, getSenderBranchSHA());
	}

	private void _deleteLockFiles() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		File gitDirectory = gitWorkingDirectory.getGitDirectory();

		String[] lockFilenames = gitDirectory.list(
			JenkinsResultsParserUtil.newFilenameFilter(".*\\.lock"));

		for (String lockFilename : lockFilenames) {
			File lockFile = new File(gitDirectory, lockFilename);

			boolean deleted = false;

			if (lockFile.exists() && lockFile.canWrite()) {
				System.out.println("Deleting lock file " + lockFile.getPath());

				deleted = lockFile.delete();
			}

			if (!deleted) {
				System.out.println("Unable to delete " + lockFile.getPath());
			}
		}
	}

	private String _getBaseBranchHeadSHA() {
		return getString("base_branch_head_sha");
	}

	private String _getBaseBranchUsername() {
		return getString("base_branch_username");
	}

	private String _getSenderBranchHeadSHA() {
		return getString("sender_branch_head_sha");
	}

	private RemoteGitRef _getSenderRemoteGitRef() {
		if (_senderRemoteGitRef != null) {
			return _senderRemoteGitRef;
		}

		_senderRemoteGitRef = GitUtil.getRemoteGitRef(
			JenkinsResultsParserUtil.combine(
				"https://github.com/", getSenderBranchUsername(), "/",
				getName(), "/tree/", getSenderBranchName()));

		return _senderRemoteGitRef;
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

	private boolean _isPullRequest() {
		return PullRequest.isValidGitHubPullRequestURL(getGitHubURL());
	}

	private void _setBaseBranchHeadSHA(String branchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(branchSHA)) {
			throw new RuntimeException(
				"Invalid base branch head SHA " + branchSHA);
		}

		put("base_branch_head_sha", branchSHA);
	}

	private void _setBaseBranchUsername(String username) {
		put("base_branch_username", username);
	}

	private void _setGitHubURL(String gitHubURL) {
		if (gitHubURL == null) {
			throw new RuntimeException("GitHub URL is null");
		}

		put("git_hub_url", gitHubURL);
	}

	private void _setSenderBranchHeadSHA(String branchSHA) {
		if (!JenkinsResultsParserUtil.isSHA(branchSHA)) {
			throw new RuntimeException(
				"Invalid sender branch head SHA " + branchSHA);
		}

		put("sender_branch_head_sha", branchSHA);
	}

	private void _setSenderBranchName(String branchName) {
		put("sender_branch_name", branchName);
	}

	private void _setSenderBranchUsername(String username) {
		put("sender_branch_username", username);
	}

	private static final String[] _REQUIRED_KEYS = {
		"base_branch_head_sha", "base_branch_sha", "base_branch_username",
		"git_hub_url", "sender_branch_head_sha", "sender_branch_name",
		"sender_branch_sha", "sender_branch_username"
	};

	private String _branchName;
	private List<LocalGitCommit> _historicalLocalGitCommits;
	private LocalGitBranch _localGitBranch;
	private final Set<String> _propertyOptions = new HashSet<>();
	private boolean _rebase;
	private RemoteGitRef _senderRemoteGitRef;
	private boolean _setUp;
	private RemoteGitRef _upstreamRemoteGitRef;

}