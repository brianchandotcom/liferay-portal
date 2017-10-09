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

		if (!_privateOnlyRepositories.contains(_repositoryName)) {
			if (upstreamBranchName.equals("master")) {
				setUpstreamRemoteToPublicRepository();
			}
			else {
				setUpstreamRemoteToPrivateRepository();
			}
		}

		_repositoryUsername = loadRepositoryUsername();
	}

	public Remote addRemote(
		boolean force, String remoteName, String remoteURL) {

		if (remoteExists(remoteName)) {
			if (force) {
				removeRemote(getRemote(remoteName));
			}
			else {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Remote ", remoteName, " already exists"));
			}
		}

		ExecutionResult executionResult = executeBashCommands(
			JenkinsResultsParserUtil.combine(
				"git remote add ", remoteName, " ", remoteURL));

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to add remote ", remoteName, "\n",
					executionResult.getStandardError()));
		}

		return getRemote(remoteName);
	}

	public boolean branchExists(String branchName, Remote remote) {
		if (getBranch(branchName, remote) != null) {
			return true;
		}

		return false;
	}

	public void checkoutBranch(Branch branch) {
		checkoutBranch(branch, "-f");
	}

	public void checkoutBranch(Branch branch, String options) {
		if (!branchExists(branch.getName(), null)) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"The branch ", branch.getName(), " could not be found"));
		}

		Branch currentBranch = getCurrentBranch();

		if (currentBranch != null) {
			String currentBranchName = currentBranch.getName();

			if (currentBranchName.equals(branch.getName())) {
				System.out.println(
					currentBranchName + " is already checked out");

				return;
			}
		}

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		String branchName = branch.getName();

		sb.append(branchName);

		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60 * 10, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout ", branchName, "\n",
					executionResult.getStandardError()));
		}

		int timeout = 0;

		File headFile = new File(_gitDirectory, "HEAD");

		String expectedContent = JenkinsResultsParserUtil.combine(
			"ref: refs/heads/", branchName);

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
				currentBranch = getCurrentBranch();

				if ((currentBranch != null) &&
					branchName.equals(currentBranch.getName())) {

					return;
				}

				throw new RuntimeException(
					"Unable to checkout branch " + branchName);
			}
		}
	}

	public void clean() {
		clean(null);
	}

	public void clean(File workingDirectory) {
		if (workingDirectory == null) {
			workingDirectory = _workingDirectory;
		}

		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60 * 10, "git clean -dfx");

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to clean repository\n",
					executionResult.getStandardError()));
		}
	}

	public void commitFileToCurrentBranch(String fileName, String message) {
		String commitCommand = JenkinsResultsParserUtil.combine(
			"git commit -m \"", message, "\" ", fileName);

		ExecutionResult executionResult = executeBashCommands(commitCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to commit file ", fileName, "\n",
					executionResult.getStandardError()));
		}
	}

	public void commitStagedFilesToCurrentBranch(String message) {
		String commitCommand = JenkinsResultsParserUtil.combine(
			"git commit -m \"", message, "\" ");

		ExecutionResult executionResult = executeBashCommands(commitCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to commit staged files", "\n",
					executionResult.getStandardError()));
		}
	}

	public Branch createLocalBranch(String branchName) {
		return createLocalBranch(branchName, false, null);
	}

	public Branch createLocalBranch(
		String branchName, boolean force, String startPoint) {

		StringBuilder sb = new StringBuilder();

		sb.append("git branch ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(branchName);

		if (startPoint != null) {
			sb.append(" ");
			sb.append(startPoint);
		}

		ExecutionResult executionResult = executeBashCommands(sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create local branch ", branchName, " at ",
					startPoint, "\n", executionResult.getStandardError()));
		}

		return getBranch(branchName, null);
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

	public void deleteBranch(Branch branch) {
		if (!branchExists(branch.getName(), branch.getRemote())) {
			return;
		}

		if (branch.getRemote() != null) {
			pushToRemote(true, null, branch);

			return;
		}

		ExecutionResult executionResult = executeBashCommands(
			"git branch -f -D " + branch.getName());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to delete local branch ", branch.getName(), "\n",
					executionResult.getStandardError()));
		}
	}

	public void deleteBranch(String branchName, Remote remote) {
		Branch branch = null;

		branch = getBranch(branchName, remote);

		if (branch != null) {
			deleteBranch(branch);
		}
	}

	public void fetch(Branch localBranch, Branch remoteBranch) {
		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f ");

		Remote remote = remoteBranch.getRemote();

		sb.append(remote.getName());

		String remoteBranchName = remoteBranch.getName();

		if ((remoteBranchName != null) && !remoteBranchName.isEmpty()) {
			sb.append(" ");
			sb.append(remoteBranch.getName());

			if (localBranch != null) {
				sb.append(":");
				sb.append(localBranch.getName());
			}
		}

		long start = System.currentTimeMillis();

		ExecutionResult executionResult = executeBashCommands(
			3, 1000 * 60 * 30, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch remote branch ", remoteBranch.getName(),
					"\n", executionResult.getStandardError()));
		}

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));
	}

	public void fetch(Remote remote) {
		fetch(null, new Branch(null, remote, null));
	}

	public Branch getBranch(String branchName, Remote remote) {
		if (branchName.equals("HEAD") && (remote == null)) {
			ExecutionResult executionResult = executeBashCommands(
				"git rev-parse --abbrev-ref " + branchName);

			if (executionResult.getExitValue() != 0) {
				return null;
			}

			System.out.println(executionResult.getStandardOut());

			branchName = executionResult.getStandardOut();

			branchName = branchName.trim();

			if (branchName.isEmpty()) {
				return null;
			}

			return new Branch(branchName, null, getBranchSha(branchName));
		}

		List<Branch> branches = getBranches(remote);

		for (Branch branch : branches) {
			if (branchName.equals(branch.getName())) {
				return branch;
			}
		}

		return null;
	}

	public List<Branch> getBranches(Remote remote) {
		if (remote == null) {
			List<String> localBranchNames = getLocalBranchNames();

			List<Branch> localBranches = new ArrayList<>(
				localBranchNames.size());

			for (String localBranchName : localBranchNames) {
				localBranches.add(
					new Branch(
						localBranchName, null, getBranchSha(localBranchName)));
			}

			return localBranches;
		}

		return getRemoteBranches(remote);
	}

	public List<String> getBranchNames(Remote remote) {
		if (remote == null) {
			return getLocalBranchNames();
		}

		return getRemoteBranchNames(remote);
	}

	public List<String> getBranchNamesContainingSha(String sha) {
		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60 * 2, "git branch --contains " + sha);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get branches with SHA ", sha, "\n",
					executionResult.getStandardError()));
		}

		String standardOut = executionResult.getStandardOut();

		if (standardOut.contains("no such commit")) {
			return Collections.emptyList();
		}

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
		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60 * 2, "git rev-parse " + localBranchName);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to determine SHA of branch ", localBranchName, "\n",
					executionResult.getStandardError()));
		}

		return executionResult.getStandardOut();
	}

	public Branch getCurrentBranch() {
		waitForIndexLock();

		return getBranch("HEAD", null);
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		ExecutionResult executionResult = executeBashCommands(
			"git config " + gitConfigPropertyName);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to read Git config property ",
					gitConfigPropertyName, "\n",
					executionResult.getStandardError()));
		}

		String configProperty = executionResult.getStandardOut();

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

	public Remote getRemote(String name) {
		Map<String, Remote> remotes = getRemotes();

		return remotes.get(name);
	}

	public Set<String> getRemoteNames() {
		Map<String, Remote> remotes = getRemotes();

		return remotes.keySet();
	}

	public Map<String, Remote> getRemotes() {
		Map<String, Remote> remotes = new HashMap<>();

		int retries = 0;

		String standardOut = null;

		while (true) {
			if (retries > 1) {
				return remotes;
			}

			ExecutionResult executionResult = executeBashCommands(
				"git remote -v");

			if (executionResult.getExitValue() != 0) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get list of remotes\n",
						executionResult.getStandardError()));
			}

			standardOut = executionResult.getStandardOut();

			standardOut = standardOut.trim();

			if (!standardOut.isEmpty()) {
				break;
			}

			retries++;

			JenkinsResultsParserUtil.sleep(1000);
		}

		String[] lines = standardOut.split("\n");

		Arrays.sort(lines);

		int x = 0;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			if (line == null) {
				continue;
			}

			line = line.trim();

			if (line.isEmpty()) {
				continue;
			}

			x = i;

			break;
		}

		lines = Arrays.copyOfRange(lines, x, lines.length);

		try {
			for (int i = 0; i < lines.length; i = i + 2) {
				Remote remote = new Remote(
					this, Arrays.copyOfRange(lines, i, i + 2));

				remotes.put(remote.getName(), remote);
			}
		}
		catch (Throwable t) {
			System.out.println("Unable to parse remotes\n" + standardOut);

			throw t;
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
		Branch currentBranch = getCurrentBranch();

		if (currentBranch == null) {
			Remote remote = remoteBranch.getRemote();

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to push current branch to remote branch ",
					remoteBranch.getName(), " on ", remote.getName(),
					" because the current branch is invalid"));
		}

		return pushToRemote(force, currentBranch, remoteBranch);
	}

	public boolean pushToRemote(
		boolean force, Branch localBranch, Branch remoteBranch) {

		return pushToRemote(
			force, localBranch, remoteBranch.getName(),
			remoteBranch.getRemote());
	}

	public boolean pushToRemote(
		boolean force, Branch localBranch, String remoteBranchName,
		Remote remote) {

		String localBranchName = "";

		if (localBranch != null) {
			localBranchName = localBranch._name;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git push ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(remote.getName());
		sb.append(" ");
		sb.append(localBranchName);
		sb.append(":");
		sb.append(remoteBranchName);

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
			force, currentBranch, currentBranch.getName(), remote);
	}

	public void rebase(
		boolean abortOnFail, Branch sourceBranch, Branch targetBranch) {

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", sourceBranch.getName(), " ", targetBranch.getName());

		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60 * 10, rebaseCommand);

		if (executionResult.getExitValue() != 0) {
			if (abortOnFail) {
				rebaseAbort();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to rebase ", targetBranch.getName(), " to ",
					sourceBranch.getName(), "\n",
					executionResult.getStandardError()));
		}
	}

	public void rebaseAbort() {
		rebaseAbort(true);
	}

	public void rebaseAbort(boolean ignoreFailure) {
		ExecutionResult executionResult = executeBashCommands(
			"git rebase --abort");

		if (!ignoreFailure && (executionResult.getExitValue() != 0)) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to abort rebase\n",
					executionResult.getStandardError()));
		}
	}

	public boolean remoteExists(String remoteName) {
		if (getRemote(remoteName) != null) {
			return true;
		}

		return false;
	}

	public void removeRemote(Remote remote) {
		if ((remote == null) || !remoteExists(remote.getName())) {
			return;
		}

		ExecutionResult executionResult = executeBashCommands(
			"git remote rm " + remote.getName());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to remove remote ", remote.getName(), "\n",
					executionResult.getStandardError()));
		}
	}

	public void removeRemotes(List<Remote> remotes) {
		for (Remote remote : remotes) {
			removeRemote(remote);
		}
	}

	public void reset(String options) {
		String command = "git reset " + options;

		ExecutionResult executionResult = executeBashCommands(
			2, 1000 * 60 * 2, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to reset\n", executionResult.getStandardError()));
		}
	}

	public void stageFileInCurrentBranch(String fileName) {
		String command = "git stage " + fileName;

		ExecutionResult result = executeBashCommands(command);

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to stage file " + fileName);
		}
	}

	public static class Branch {

		public String getName() {
			return _name;
		}

		public Remote getRemote() {
			return _remote;
		}

		public String getSha() {
			return _sha;
		}

		private Branch(String name, Remote remote, String sha) {
			_name = name;
			_remote = remote;
			_sha = sha;
		}

		private final String _name;
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

		public GitWorkingDirectory getGitWorkingDirectory() {
			return _gitWorkingDirectory;
		}

		public String getName() {
			return _name;
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

		public String toString() {
			return JenkinsResultsParserUtil.combine(
				getName(), " (", getRemoteURL(), ")");
		}

		private Remote(
			GitWorkingDirectory gitWorkingDirectory,
			String[] remoteInputLines) {

			_gitWorkingDirectory = gitWorkingDirectory;

			if (remoteInputLines.length != 2) {
				throw new IllegalArgumentException(
					"\"remoteInputLines\" must be an array of 2 strings");
			}

			if (remoteInputLines[0].equals(remoteInputLines[1])) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"\"remoteInputLines[0]\" and ",
						"\"remoteInputLines[1]\" are identical: ",
						remoteInputLines[0]));
			}

			if ((remoteInputLines[0] == null) ||
				(remoteInputLines[1] == null)) {

				throw new IllegalArgumentException(
					"Neither \"remoteInputLines[0]\" nor " +
						"\"remoteInputLines[1]\" may be NULL: " +
							Arrays.toString(remoteInputLines));
			}

			String name = null;
			String fetchRemoteURL = null;
			String pushRemoteURL = null;

			for (String remoteInputLine : remoteInputLines) {
				Matcher matcher = _remotePattern.matcher(remoteInputLine);

				if (!matcher.matches()) {
					throw new IllegalArgumentException(
						"Invalid Git remote input line " + remoteInputLine);
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

		private final String _fetchRemoteURL;
		private final GitWorkingDirectory _gitWorkingDirectory;
		private final String _name;
		private final String _pushRemoteURL;

	}

	protected ExecutionResult executeBashCommands(
		int maxRetries, long timeout, String... commands) {

		Process process = null;

		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				process = JenkinsResultsParserUtil.executeBashCommands(
					true, _workingDirectory, timeout, commands);

				break;
			}
			catch (InterruptedException | IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to execute bash commands: " +
							Arrays.toString(commands),
						e);
				}
				else {
					System.out.println("Fetch attempt failed retrying... ");
					e.printStackTrace();
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

		return new ExecutionResult(
			process.exitValue(), standardErr.trim(), standardOut.trim());
	}

	protected ExecutionResult executeBashCommands(String... commands) {
		return executeBashCommands(1, 1000 * 30, commands);
	}

	protected List<String> getLocalBranchNames() {
		ExecutionResult executionResult = executeBashCommands(
			"git for-each-ref refs/heads --format=\"%(refname)\"");

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get local branch names\n",
					executionResult.getStandardError()));
		}

		String standardOut = executionResult.getStandardOut();

		return toShortNameList(Arrays.asList(standardOut.split("\n")));
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
			"Real Git directory could not be found in " + gitFile.getPath());
	}

	protected List<Branch> getRemoteBranches(Remote remote) {
		ExecutionResult executionResult = executeBashCommands(
			1, 1000 * 60,
			JenkinsResultsParserUtil.combine(
				"git ls-remote -h ", remote.getName()));

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branches from ", remote.toString(),
					"\n", executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		List<Branch> branches = new ArrayList<>();

		for (String line : input.split("\n")) {
			Matcher matcher = _gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				branches.add(
					new Branch(
						matcher.group("name"), remote, matcher.group("sha")));
			}
		}

		System.out.println(
			"getRemoteBranches found " + branches.size() + " branches.");

		return branches;
	}

	protected List<String> getRemoteBranchNames(Remote remote) {
		ExecutionResult executionResult = executeBashCommands(
			JenkinsResultsParserUtil.combine(
				"git ls-remote -h ", remote.getName()));

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branches from ", remote.getName(),
					"\n", executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		List<String> branchNames = new ArrayList<>();

		for (String line : input.split("\n")) {
			Matcher matcher = _gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				branchNames.add(matcher.group("name"));
			}
		}

		return branchNames;
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

	protected void setUpstreamRemoteToPrivateRepository() {
		Remote upstreamRemote = getRemote("upstream");

		String remoteURL = upstreamRemote.getRemoteURL();

		if (!remoteURL.contains("-ee") && !remoteURL.contains("-private")) {
			remoteURL = remoteURL.replace(".git", "-private.git");

			addRemote(true, "upstream", remoteURL);
		}
	}

	protected void setUpstreamRemoteToPublicRepository() {
		Remote upstreamRemote = getRemote("upstream");

		String remoteURL = upstreamRemote.getRemoteURL();

		if (remoteURL.contains("-ee") || remoteURL.contains("-private")) {
			remoteURL = remoteURL.replace("-ee", "");
			remoteURL = remoteURL.replace("-private", "");

			addRemote(true, "upstream", remoteURL);
		}
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

	protected class ExecutionResult {

		public int getExitValue() {
			return _exitValue;
		}

		public String getStandardError() {
			return _standardError;
		}

		public String getStandardOut() {
			return _standardOut;
		}

		protected ExecutionResult(
			int exitValue, String standardError, String standardOut) {

			_exitValue = exitValue;
			_standardError = standardError;

			if (standardOut.endsWith("\nFinished executing Bash commands.")) {
				_standardOut = standardOut.substring(
					0,
					standardOut.indexOf("\nFinished executing Bash commands."));
			}
			else {
				_standardOut = standardOut;
			}
		}

		private final int _exitValue;
		private final String _standardError;
		private final String _standardOut;

	};

	private static final Pattern _gitDirectoryPathPattern = Pattern.compile(
		"gitdir\\: (.*\\.git)");
	private static final Pattern _gitLsRemotePattern = Pattern.compile(
		"(?<sha>[^\\s]{40}+)[\\s]+refs/heads/(?<name>[^\\s]+)");
	private static final List<String> _privateOnlyRepositories = Arrays.asList(
		new String[] {
			"liferay-jenkins-ee", "liferay-jenkins-tools-private",
			"liferay-plugins-ee", "liferay-portal-ee",
			"liferay-qa-portal-legacy-ee", "liferay-release-tool-ee"
		});

	private File _gitDirectory;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}