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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class GitWorkingDirectory {

	public static String getGitHubUserName(Remote remote) {
		String remoteURL = remote.getRemoteURL();

		if (!remoteURL.contains("github.com")) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					remote.getName(),
					" does not point to a GitHub repository"));
		}

		String userName = null;

		if (remoteURL.startsWith("https://github.com/")) {
			userName = remoteURL.substring("https://github.com/".length());
		}
		else {
			userName = remoteURL.substring("git@github.com:".length());
		}

		return userName.substring(0, userName.indexOf("/"));
	}

	public GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		this(upstreamBranchName, workingDirectoryPath, null);
	}

	public GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String repositoryName)
		throws IOException {

		_upstreamBranchName = upstreamBranchName;

		setWorkingDirectory(workingDirectoryPath);

		waitForIndexLock();

		if ((repositoryName == null) || repositoryName.equals("")) {
			repositoryName = loadRepositoryName();
		}

		_repositoryName = repositoryName;

		_repositoryUsername = loadRepositoryUsername();
	}

	public Remote addRemote(
		boolean force, String remoteName, String remoteURL) {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Adding remote ", remoteName, " with url: ", remoteURL));

		Remote remote = getRemote(remoteName);

		if (remote != null) {
			if (force) {
				removeGitRemote(remote);
			}
			else {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Remote ", remoteName, " already exists"));
			}
		}

		BashCommandResult result = executeBashCommands(
			JenkinsResultsParserUtil.combine(
				"git remote add ", remoteName, " ", remoteURL));

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to add remote ", remoteName, "\n",
					result.getStandardErr()));
		}

		return getRemote(remoteName);
	}

	public boolean branchExists(String branchName, Remote remote) {
		List<String> branchNames = null;

		if (remote == null) {
			branchNames = getLocalBranchNames();
		}
		else {
			Branch branch = getRemoteBranch(branchName, remote);

			if (branch != null) {
				return true;
			}
		}

		return branchNames.contains(branchName);
	}

	public void checkoutBranch(String branchName) {
		checkoutBranch(branchName, "-f");
	}

	public void checkoutBranch(String branchName, String options) {
		Branch currentBranch = getCurrentBranch();

		List<String> localBranchNames = getLocalBranchNames();

		if (!branchName.contains("/") &&
			!localBranchNames.contains(branchName)) {

			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout ", branchName,
					" because it does not exist"));
		}

		if (branchName.equals(currentBranch._name)) {
			System.out.println(branchName + " is already checked out");

			return;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"The current branch is ", currentBranch._name,
				". Checking out branch ", branchName, "."));

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		sb.append(branchName);

		BashCommandResult result = executeBashCommands(
			1, 1000 * 60 * 10, sb.toString());

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout ", branchName, "\n",
					result.getStandardErr()));
		}

		int timeout = 0;

		File headFile = new File(_gitDirectory, "HEAD");

		String expectedContent = null;

		if (!branchName.contains("/")) {
			expectedContent = JenkinsResultsParserUtil.combine(
				"ref: refs/heads/", branchName);
		}
		else {
			int i = branchName.indexOf("/");

			String remoteBranchName = branchName.substring(i + 1);

			String remoteName = branchName.substring(0, i);

			Remote remote = getRemote(remoteName);

			Branch branch = getRemoteBranch(remoteBranchName, remote);

			expectedContent = branch.getSha();
		}

		while (true) {
			String headContent = null;

			try {
				headContent = JenkinsResultsParserUtil.read(headFile);
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to read file " + headFile.getPath(), ioe);
			}

			headContent = headContent.trim();

			if (headContent.equals(expectedContent)) {
				return;
			}

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"HEAD file content is currently: ", headContent,
					". Waiting for branch to be updated."));

			JenkinsResultsParserUtil.sleep(5000);

			timeout++;

			if (timeout >= 59) {
				if (branchName.equals(getCurrentBranch())) {
					return;
				}

				throw new RuntimeException(
					"Unable to checkout branch " + branchName);
			}
		}
	}

	public void clean(File workingDirectory) {
		if (workingDirectory == null) {
			workingDirectory = _workingDirectory;
		}

		BashCommandResult result = executeBashCommands("git clean -dfx");

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to clean repository\n", result.getStandardErr()));
		}
	}

	public void commitFileToCurrentBranch(String fileName, String message) {
		System.out.println("Committing file to current branch " + fileName);

		String commitCommand = JenkinsResultsParserUtil.combine(
			"git commit -m \'", message, "\' ", fileName);

		BashCommandResult result = executeBashCommands(commitCommand);

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to commit file ", fileName, "\n",
					result.getStandardErr()));
		}
	}

	public void createLocalBranch(String branchName) {
		createLocalBranch(branchName, false, null);
	}

	public void createLocalBranch(
		String localBranchName, boolean force, String startPoint) {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Creating branch ", localBranchName, " at starting point ",
				startPoint));

		StringBuilder sb = new StringBuilder();

		sb.append("git branch ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(localBranchName);

		if (startPoint != null) {
			sb.append(" ");
			sb.append(startPoint);
		}

		BashCommandResult result = executeBashCommands(sb.toString());

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create local branch ", localBranchName, " at ",
					startPoint, "\n", result.getStandardErr()));
		}
	}

	public String createPullRequest(
			String body, String pullRequestBranchName, String receiverUserName,
			String title)
		throws IOException {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("base", _upstreamBranchName);
		requestJSONObject.put("body", body);
		requestJSONObject.put(
			"head", receiverUserName + ":" + pullRequestBranchName);
		requestJSONObject.put("title", title);

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", receiverUserName, "/",
			_repositoryName, "/pulls");

		JSONObject responseJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());

		String pullRequestURL = responseJSONObject.getString("html_url");

		System.out.println("Created a pull request at " + pullRequestURL);

		return pullRequestURL;
	}

	public void deleteLocalBranch(String localBranchName) {
		System.out.println("Deleting local branch " + localBranchName);

		BashCommandResult result = executeBashCommands(
			"git branch -f -D " + localBranchName);

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to delete local branch ", localBranchName, "\n",
					result.getStandardErr()));
		}
	}

	public void deleteRemoteBranch(Branch remoteBranch) {
		Remote remote = remoteBranch._remote;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleting remote branch ", remoteBranch._name, " from ",
				remote.getRemoteURL()));

		pushToRemote(true, null, remoteBranch);
	}

	public void fetch(Branch localBranch, Branch remoteBranch) {
		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f ");

		Remote remote = remoteBranch.getGitRemote();

		sb.append(remote.getName());

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Fetching from ", remote.getName(), " ",
				remoteBranch.getName()));

		sb.append(" ");
		sb.append(remoteBranch.getName());

		if (localBranch != null) {
			sb.append(":");
			sb.append(localBranch.getName());
		}

		long start = System.currentTimeMillis();

		BashCommandResult result = executeBashCommands(
			3, 1000 * 60 * 30, sb.toString());

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch remote branch ", remoteBranch.getName(),
					"\n", result.getStandardErr()));
		}

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));
	}

	public List<String> getBranchNamesContainingSHA(String sha) {
		BashCommandResult result = executeBashCommands(
			1, 1000 * 60 * 2, "git branch --contains " + sha);

		String standardOut = result.getStandardOut();

		if (standardOut.contains("no such commit")) {
			return Collections.emptyList();
		}

		System.out.println(standardOut);

		String[] lines = standardOut.split("\n");

		List<String> branchNamesList = new ArrayList<>(lines.length - 1);

		for (String line : lines) {
			if (branchNamesList.size() == (lines.length - 1)) {
				break;
			}

			String branchName = line.trim();

			if (branchName.startsWith("* ")) {
				branchName = branchName.substring(2);
			}

			branchNamesList.add(branchName);
		}

		return branchNamesList;
	}

	public String getBranchSha(String localBranchName) {
		BashCommandResult result = executeBashCommands(
			1, 1000 * 60 * 2, "git rev-parse " + localBranchName);

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to determine SHA of branch ", localBranchName, "\n",
					result.getStandardErr()));
		}

		String standardOut = result.getStandardOut();

		String firstLine = standardOut.substring(0, standardOut.indexOf("\n"));

		return firstLine.trim();
	}

	public Branch getCurrentBranch() {
		waitForIndexLock();

		BashCommandResult result = executeBashCommands(
			"git rev-parse --abbrev-ref HEAD");

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get current branch name\n",
					result.getStandardErr()));
		}

		String branchName = result.getStandardOut();

		return new Branch(null, branchName, getBranchSha(branchName));
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		BashCommandResult result = executeBashCommands(
			"git config " + gitConfigPropertyName);

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to read git config property ",
					gitConfigPropertyName, "\n", result.getStandardErr()));
		}

		String configProperty = result.getStandardOut();

		if (configProperty != null) {
			configProperty = configProperty.trim();
		}

		if ((configProperty == null) || configProperty.isEmpty()) {
			return null;
		}

		return configProperty;
	}

	public Boolean getGitConfigPropertyBoolean(
		String gitConfigPropertyName, Boolean defaultValue) {

		String gitConfigProperty = getGitConfigProperty(gitConfigPropertyName);

		if (gitConfigProperty == null) {
			if (defaultValue != null) {
				return defaultValue;
			}

			return null;
		}

		return Boolean.parseBoolean(gitConfigProperty);
	}

	public File getGitDirectory() {
		return _gitDirectory;
	}

	public List<String> getLocalBranchNames() {
		BashCommandResult result = executeBashCommands(
			"git for-each-ref refs/heads --format=\'%(refname)\'");

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get local branch names\n",
					result.getStandardErr()));
		}

		String standardOut = result.getStandardOut();

		return toShortNameList(Arrays.asList(standardOut.split("\n")));
	}

	public Remote getRemote(String name) {
		Map<String, Remote> remotes = getRemotes();

		Remote remote = remotes.get(name);

		if ((remote == null) && name.equals("upstream-public")) {
			Remote upstreamGitRemote = remotes.get("upstream");

			String upstreamRemoteURL = upstreamGitRemote.getRemoteURL();

			upstreamRemoteURL = upstreamRemoteURL.replace("-ee", "");
			upstreamRemoteURL = upstreamRemoteURL.replace("-private", "");

			return addRemote(true, "upstream-public", upstreamRemoteURL);
		}

		if (name.equals("upstream")) {
			String upstreamRemoteURL = remote.getRemoteURL();

			if (upstreamRemoteURL.contains(_repositoryName + ".git")) {
				return remote;
			}

			return getRemote("upstream-public");
		}

		return remote;
	}

	public Branch getRemoteBranch(String branchName, Remote remote) {
		List<Branch> remoteBranches = getRemoteBranches(remote);

		for (Branch branch : remoteBranches) {
			if (branchName.equals(branch.getName())) {
				return branch;
			}
		}

		return null;
	}

	public List<Branch> getRemoteBranches(Remote remote) {
		BashCommandResult result = executeBashCommands(
			JenkinsResultsParserUtil.combine(
				"git ls-remote -h ", remote.getName()));

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branches from ", remote.getName(),
					"\n", result.getStandardErr()));
		}

		String input = result.getStandardOut();

		List<Branch> branches = new ArrayList<>();

		for (String line : input.split("\n")) {
			Matcher matcher = _gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				branches.add(
					new Branch(
						remote, matcher.group("name"), matcher.group("sha")));
			}
		}

		return branches;
	}

	public Set<String> getRemoteNames() {
		Map<String, Remote> remotes = getRemotes();

		return remotes.keySet();
	}

	public Map<String, Remote> getRemotes() {
		Map<String, Remote> remotes = new HashMap<>();

		BashCommandResult result = executeBashCommands("git remote -v");

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get list of remotes\n",
					result.getStandardErr()));
		}

		String standardOut = result.getStandardOut();

		String[] lines = standardOut.split("\n");

		for (int i = 0; i < lines.length; i = i + 2) {
			Remote remote = new Remote(
				this, Arrays.copyOfRange(lines, i, i + 1));

			remotes.put(remote.getName(), remote);
		}

		return remotes;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getRepositoryUsername() {
		return _repositoryUsername;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public File getWorkingDirectory() {
		return _workingDirectory;
	}

	public boolean pushToRemote(boolean force, Branch remoteBranch) {
		return pushToRemote(force, getCurrentBranch(), remoteBranch);
	}

	public boolean pushToRemote(
		boolean force, Branch localBranch, Branch remoteBranch) {

		String localBranchName = "";

		if (localBranch != null) {
			localBranchName = localBranch._name;
		}

		Remote remote = remoteBranch._remote;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushing ", localBranchName, " to ", remote.getRemoteURL(), " ",
				remoteBranch._name));

		StringBuilder sb = new StringBuilder();

		sb.append("git push ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(remote.getName());
		sb.append(" ");
		sb.append(localBranchName);
		sb.append(":");
		sb.append(remoteBranch._name);

		try {
			executeBashCommands(sb.toString());
		}
		catch (RuntimeException re) {
			return false;
		}

		return true;
	}

	public boolean pushToRemote(boolean force, Remote remote) {
		Branch currentBranch = getCurrentBranch();

		return pushToRemote(
			force, currentBranch,
			getRemoteBranch(currentBranch.getName(), remote));
	}

	public void rebase(
		boolean abortOnFail, String sourceBranchName, String targetBranchName) {

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", sourceBranchName, " ", targetBranchName);

		String sourceBranchSHA = getBranchSha(sourceBranchName);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Rebasing ", sourceBranchName, "(", sourceBranchSHA, ") to ",
				targetBranchName));

		try {
			System.out.println(
				executeBashCommands(1, 1000 * 60 * 10, rebaseCommand));
		}
		catch (RuntimeException re) {
			try {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to rebase ", targetBranchName, " to ",
						sourceBranchName),
					re);
			}
			finally {
				if (abortOnFail) {
					rebaseAbort();
				}
			}
		}
	}

	public void rebaseAbort() {
		rebaseAbort(true);
	}

	public void rebaseAbort(boolean ignoreFailure) {
		BashCommandResult result = executeBashCommands("git rebase --abort");

		if (!ignoreFailure && (result.getExitValue() != 0)) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to abort rebase\n", result.getStandardErr()));
		}
	}

	public boolean remoteExists(String remoteName) {
		if (getRemote(remoteName) != null) {
			return true;
		}

		return false;
	}

	public void removeGitRemote(Remote remote) {
		if (!remoteExists(remote.getName())) {
			return;
		}

		System.out.println("Removing remote " + remote.getName());

		BashCommandResult result = executeBashCommands(
			"git remote rm " + remote.getName());

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to remove remote ", remote.getName(), "\n",
					result.getStandardErr()));
		}
	}

	public void removeGitRemotes(List<Remote> remotes) {
		for (Remote remote : remotes) {
			removeGitRemote(remote);
		}
	}

	public void reset(String options) {
		String command = "git reset " + options;

		BashCommandResult result = executeBashCommands(command);

		if (result.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to reset\n", result.getStandardErr()));
		}
	}

	public static class Branch {

		public Remote getGitRemote() {
			return _remote;
		}

		public String getName() {
			return _name;
		}

		public String getSha() {
			return _sha;
		}

		private Branch(Remote remote, String name, String sha) {
			_remote = remote;
			_name = name;
			_sha = sha;
		}

		private String _name;
		private final Remote _remote;
		private final String _sha;

	};

	public static class Remote implements Comparable<Remote> {

		@Override
		public int compareTo(Remote otherGitRemote) {
			int result = _name.compareTo(otherGitRemote._name);

			if (result != 0) {
				return result;
			}

			return _fetchRemoteURL.compareTo(otherGitRemote._fetchRemoteURL);
		}

		public String getFetchRefSpec() {
			if (_fetchRefSpec == null) {
				_fetchRefSpec = _gitWorkingDirectory.getGitConfigProperty(
					JenkinsResultsParserUtil.combine(
						"remote.", _name, ".fetch"));
			}

			return _fetchRefSpec;
		}

		public String getName() {
			return _name;
		}

		public String getPushRefSpec() {
			if (_pushRefSpec == null) {
				_pushRefSpec = _gitWorkingDirectory.getGitConfigProperty(
					JenkinsResultsParserUtil.combine(
						"remote.", _name, ".push"));
			}

			if (_pushRefSpec == null) {
				return getFetchRefSpec();
			}

			return _fetchRefSpec;
		}

		public String getPushRemoteURL() {
			if (_pushRemoteURL != null) {
				return _pushRemoteURL;
			}

			return _fetchRemoteURL;
		}

		public String getRemoteURL() {
			return _fetchRemoteURL;
		}

		private Remote(
			GitWorkingDirectory gitWorkingDirectory,
			String[] remoteInputLines) {

			_gitWorkingDirectory = gitWorkingDirectory;

			if (remoteInputLines.length != 2) {
				throw new IllegalArgumentException(
					"remoteInputLines but be an array of 2 Strings");
			}

			if (remoteInputLines[0].equals(remoteInputLines[1])) {
				throw new IllegalArgumentException(
					"Duplicate remote input lines detected. " +
						remoteInputLines[0]);
			}

			String name = null;
			String fetchRemoteURL = null;
			String pushRemoteURL = null;

			for (String remoteInputLine : remoteInputLines) {
				Matcher matcher = _remotePattern.matcher(remoteInputLine);

				if (!matcher.matches()) {
					throw new IllegalArgumentException(
						"Invalid git remote input line " + remoteInputLine);
				}

				if (name == null) {
					name = matcher.group("name");
				}

				String remoteURL = matcher.group("remoteURL");
				String type = matcher.group("type");

				if ((fetchRemoteURL == null) && type.equals("fetch")) {
					fetchRemoteURL = remoteURL;
				}

				if ((pushRemoteURL == null) && type.equals("push")) {
					pushRemoteURL = remoteURL;
				}
			}

			_fetchRemoteURL = fetchRemoteURL;
			_name = name;
			_pushRemoteURL = pushRemoteURL;
		}

		private static final Pattern _remotePattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"(?<name>[^\\s]+)[\\s]+(?<remoteURL>[^\\s]+)[\\s]+\\(",
				"(?<type>[^\\s]+)\\)"));

		private String _fetchRefSpec;
		private final String _fetchRemoteURL;
		private final GitWorkingDirectory _gitWorkingDirectory;
		private final String _name;
		private String _pushRefSpec;
		private final String _pushRemoteURL;

	}

	public class BashCommandResult {

		public int getExitValue() {
			return _exitValue;
		}

		public String getStandardErr() {
			return _standardErr;
		}

		public String getStandardOut() {
			return _standardOut;
		}

		private BashCommandResult(
			int exitValue, String standardErr, String standardOut) {

			_exitValue = exitValue;
			_standardErr = standardErr;
			_standardOut = standardOut;
		}

		private final int _exitValue;
		private final String _standardErr;
		private final String _standardOut;

	}

	protected BashCommandResult executeBashCommands(
		int maxRetries, long timeout, String... commands) {

		Process process = null;

		int retries = 0;

		while (retries < maxRetries) {
			try {
				process = JenkinsResultsParserUtil.executeBashCommands(
					true, _workingDirectory, timeout, commands);
			}
			catch (InterruptedException | IOException | TimeoutException e) {
				retries++;

				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to execute bash commands: " + commands, e);
				}
			}
		}

		String standardErr = "";

		try {
			standardErr = JenkinsResultsParserUtil.readInputStream(
				process.getErrorStream());
		}
		catch (IOException ioe) {
			standardErr = "";
		}

		String standardOut = "";

		try {
			standardOut = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to read process input stream", ioe);
		}

		return new BashCommandResult(
			process.exitValue(), standardErr.trim(), standardOut.trim());
	}

	protected BashCommandResult executeBashCommands(String... commands) {
		return executeBashCommands(1, 1000 * 5, commands);
	}

	protected File getRealGitDirectory(File gitFile) {
		String gitFileContent;
		try {
			gitFileContent = JenkinsResultsParserUtil.read(gitFile);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Real .git directory could not be found", ioe);
		}

		for (String line : gitFileContent.split("\n")) {
			Matcher matcher = _gitDirectoryPathPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			return new File(matcher.group(1));
		}

		throw new RuntimeException(
			"Real git directory could not be found in " + gitFile.getPath());
	}

	protected String loadRepositoryName() {
		Remote remote = getRemote("upstream");

		String remoteURL = remote.getRemoteURL();

		int x = remoteURL.lastIndexOf("/") + 1;
		int y = remoteURL.indexOf(".git");

		String repositoryName = remoteURL.substring(x, y);

		if (repositoryName.equals("liferay-jenkins-tools-private")) {
			return repositoryName;
		}

		if ((repositoryName.equals("liferay-plugins-ee") ||
			 repositoryName.equals("liferay-portal-ee")) &&
			!_upstreamBranchName.contains("ee-") &&
			!_upstreamBranchName.contains("-private")) {

			repositoryName = repositoryName.replace("-ee", "");
		}

		if (repositoryName.contains("-private") &&
			!_upstreamBranchName.contains("-private")) {

			repositoryName = repositoryName.replace("-private", "");
		}

		return repositoryName;
	}

	protected String loadRepositoryUsername() {
		Remote remote = getRemote("upstream");

		String remoteURL = remote.getRemoteURL();

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setWorkingDirectory(String workingDirectoryPath)
		throws IOException {

		_workingDirectory = new File(workingDirectoryPath);

		if (!_workingDirectory.exists()) {
			throw new FileNotFoundException(
				_workingDirectory.getPath() + " is unavailable");
		}

		_gitDirectory = new File(workingDirectoryPath, ".git");

		if (_gitDirectory.isFile()) {
			_gitDirectory = getRealGitDirectory(_gitDirectory);
		}

		if (!_gitDirectory.exists()) {
			throw new FileNotFoundException(
				_gitDirectory.getPath() + " is unavailable");
		}
	}

	protected List<String> toShortNameList(List<String> fullNameList) {
		List<String> shortNames = new ArrayList<>(fullNameList.size());

		for (String fullName : fullNameList) {
			shortNames.add(fullName.substring(fullName.lastIndexOf("/") + 1));
		}

		return shortNames;
	}

	protected void waitForIndexLock() {
		int retries = 0;

		File file = new File(_gitDirectory, "index.lock");

		while (file.exists()) {
			System.out.println("Waiting for index.lock to be cleared.");

			JenkinsResultsParserUtil.sleep(5000);

			retries++;

			if (retries >= 24) {
				file.delete();
			}
		}
	}

	private static final Pattern _gitDirectoryPathPattern = Pattern.compile(
		"gitdir\\: (.*\\.git)");
	private static final Pattern _gitLsRemotePattern = Pattern.compile(
		"(?<SHA>[^\\s]{40}+)[\\s]+(?<name>[^\\s]+)");

	private File _gitDirectory;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}