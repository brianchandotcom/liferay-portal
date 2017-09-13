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

	public static String getGitHubUserName(GitRemote gitRemote) {
		String remoteURL = gitRemote.getRemoteURL();

		if (!remoteURL.contains("github.com")) {
			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					gitRemote.getName(),
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
			String upstreamBranchName, String workingDirectory)
		throws IOException {

		this(upstreamBranchName, workingDirectory, null);
	}

	public GitWorkingDirectory(
			String upstreamBranchName, String workingDirectory,
			String repositoryName)
		throws IOException {

		_upstreamBranchName = upstreamBranchName;

		setWorkingDirectory(workingDirectory);

		waitForIndexLock();

		if ((repositoryName == null) || repositoryName.equals("")) {
			repositoryName = loadRepositoryName();
		}

		_repositoryName = repositoryName;

		_repositoryUsername = loadRepositoryUsername();
	}

	public GitRemote addRemote(
		boolean force, String remoteName, String remoteURL) {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Adding remote ", remoteName, " with url: ", remoteURL));

		GitRemote gitRemote = getGitRemote(remoteName);

		if (gitRemote != null) {
			if (force) {
				removeRemote(gitRemote);
			}
			else {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Remote ", remoteName, " already exists"));
			}
		}

		executeBashCommands(
			JenkinsResultsParserUtil.combine(
				"git remote add ", remoteName, " ", remoteURL));

		return getGitRemote(remoteName);
	}

	public boolean branchExists(String branchName, GitRemote gitRemote) {
		List<String> branchNames = null;

		if (gitRemote == null) {
			branchNames = getLocalBranchNames();
		}
		else {
			GitBranch gitBranch = gitRemote.getGitBranch(branchName);

			if (gitBranch != null) {
				return true;
			}
		}

		return branchNames.contains(branchName);
	}

	public void checkoutBranch(String branchName) {
		checkoutBranch(branchName, "-f");
	}

	public void checkoutBranch(String branchName, String options) {
		GitBranch currentBranch = getCurrentBranch();

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

		executeBashCommands(1, 1000 * 60 * 10, sb.toString());

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

			expectedContent = getBranchSHA(
				remoteBranchName, getGitRemote(remoteName));
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

		executeBashCommands("git clean -dfx");
	}

	public void commitFileToCurrentBranch(String fileName, String message) {
		System.out.println("Committing file to current branch " + fileName);

		String commitCommand = JenkinsResultsParserUtil.combine(
			"git commit -m \'", message, "\' ", fileName);

		System.out.println(executeBashCommands(commitCommand));
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

		executeBashCommands(sb.toString());
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

		executeBashCommands("git branch -f -D " + localBranchName);
	}

	public void deleteRemoteBranch(GitBranch remoteBranch) {
		GitRemote gitRemote = remoteBranch._gitRemote;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleting remote branch ", remoteBranch._name, " from ",
				gitRemote.getRemoteURL()));

		pushToRemote(true, null, remoteBranch);
	}

	public void fetch(String refSpec, GitRemote gitRemote) {
		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f ");
		sb.append(gitRemote.getRemoteURL());

		if (refSpec == null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Fetching from ", gitRemote.getRemoteURL()));

			sb.append(" ");
			sb.append(gitRemote.getFetchRefSpec());
		}
		else {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Fetching from ", gitRemote.getRemoteURL(), " ",
					refSpec.toString()));

			sb.append(" ");
			sb.append(refSpec.toString());
		}

		long start = System.currentTimeMillis();

		System.out.println(
			executeBashCommands(3, 1000 * 60 * 30, sb.toString()));

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));
	}

	public void fetch(
		String localBranchName, String remoteBranchName, GitRemote gitRemote) {

		String refSpec = JenkinsResultsParserUtil.combine(
			"refs/heads/", remoteBranchName, ":", "refs/heads/",
			localBranchName);

		fetch(refSpec, gitRemote);
	}

	public List<String> getBranchNamesContainingSHA(String sha) {
		String output = executeBashCommands(
			1, 1000 * 60 * 2, "git branch --contains " + sha);

		if (output.contains("no such commit")) {
			return Collections.emptyList();
		}

		System.out.println(output);

		String[] outputLines = output.split("\n");

		List<String> branchNamesList = new ArrayList<>(outputLines.length - 1);

		for (String outputLine : outputLines) {
			if (branchNamesList.size() == (outputLines.length - 1)) {
				break;
			}

			String branchName = outputLine.trim();

			if (branchName.startsWith("* ")) {
				branchName = branchName.substring(2);
			}

			branchNamesList.add(branchName);
		}

		return branchNamesList;
	}

	public String getBranchSHA(String branchName) {
		String output = executeBashCommands(
			1, 1000 * 60 * 2, "git rev-parse " + branchName);

		String firstLine = output.substring(0, output.indexOf("\n"));

		return firstLine.trim();
	}

	public String getBranchSHA(String branchName, GitRemote gitRemote) {
		if (gitRemote == null) {
			return getBranchSHA(branchName);
		}

		GitBranch gitBranch = gitRemote.getGitBranch(branchName);

		if (gitBranch == null) {
			return null;
		}

		return gitBranch._sha;
	}

	public GitBranch getCurrentBranch() {
		waitForIndexLock();

		String branchName = executeBashCommands(
			"git rev-parse --abbrev-ref HEAD");

		String sha = executeBashCommands("git rev-parse");

		return new GitBranch(null, branchName, sha);
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		String configProperty = executeBashCommands(
			"git config " + gitConfigPropertyName);

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

	public GitRemote getGitRemote(String name) {
		Map<String, GitRemote> gitRemotes = getGitRemotes();

		GitRemote gitRemote = gitRemotes.get(name);

		if ((gitRemote == null) && name.equals("upstream-public")) {
			GitRemote upstreamGitRemote = gitRemotes.get("upstream");

			String upstreamRemoteURL = upstreamGitRemote.getRemoteURL();

			upstreamRemoteURL = upstreamRemoteURL.replace("-ee", "");
			upstreamRemoteURL = upstreamRemoteURL.replace("-private", "");

			return addRemote(true, "upstream-public", upstreamRemoteURL);
		}

		if (name.equals("upstream")) {
			String upstreamRemoteURL = gitRemote.getRemoteURL();

			if (upstreamRemoteURL.contains(_repositoryName + ".git")) {
				return gitRemote;
			}

			return getGitRemote("upstream-public");
		}

		return gitRemote;
	}

	public Map<String, GitRemote> getGitRemotes() {
		Map<String, GitRemote> gitRemotes = new HashMap<>();

		String input = executeBashCommands("git remote -v");

		String[] lines = input.split("\n");

		for (int i = 0; i < lines.length; i = i + 2) {
			GitRemote gitRemote = new GitRemote(this, lines[i], lines[i + 1]);

			gitRemotes.put(gitRemote.getName(), gitRemote);
		}

		return gitRemotes;
	}

	public List<String> getLocalBranchNames() {
		String input = executeBashCommands(
			"git for-each-ref refs/heads --format=\'%(refname)\'");

		return toShortNameList(Arrays.asList(input.split("\n")));
	}

	public Set<String> getRemoteNames() {
		Map<String, GitRemote> gitRemotes = getGitRemotes();

		return gitRemotes.keySet();
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

	public boolean pushToRemote(boolean force, GitBranch remoteBranch) {
		return pushToRemote(force, getCurrentBranch(), remoteBranch);
	}

	public boolean pushToRemote(
		boolean force, GitBranch localBranch, GitBranch remoteBranch) {

		String localBranchName = "";

		if (localBranch != null) {
			localBranchName = localBranch._name;
		}

		GitRemote gitRemote = remoteBranch._gitRemote;

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushing ", localBranchName, " to ", gitRemote.getRemoteURL(),
				" ", remoteBranch._name));

		StringBuilder sb = new StringBuilder();

		sb.append("git push ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(gitRemote.getName());
		sb.append(" ");
		sb.append(localBranchName);
		sb.append(":");
		sb.append(remoteBranch._name);

		try {
			executeBashCommands(sb.toString());
		} catch (RuntimeException re) {
			return false;
		}

		return true;
	}

	public boolean pushToRemote(boolean force, GitRemote gitRemote) {
		GitBranch currentBranch = getCurrentBranch();

		return pushToRemote(
			force, currentBranch, gitRemote.getGitBranch(currentBranch._name));
	}

	public void rebase(
		boolean abortOnFail, String sourceBranchName, String targetBranchName) {

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", sourceBranchName, " ", targetBranchName);

		String sourceBranchSHA = getBranchSHA(sourceBranchName);

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
		if (!_rebaseRepositoryStates.contains(
				_repository.getRepositoryState())) {

			return;
		}

		RebaseCommand rebaseCommand = _git.rebase();

		rebaseCommand.setOperation(RebaseCommand.Operation.ABORT);

		System.out.println(
			"Aborting rebase " + RebaseCommand.Operation.ABORT.toString());

		rebaseCommand.call();
	}

	public boolean remoteExists(String remoteName) {
		Set<String> remoteNames = getRemoteNames();

		return remoteNames.contains(remoteName);
	}

	public void removeRemote(GitRemote gitRemote) {
		if (!remoteExists(gitRemote.getName())) {
			return;
		}

		System.out.println("Removing remote " + gitRemote.getName());

		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 60,
				"git remote rm " + gitRemote.getName());
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to remove remote " + gitRemote.getName(), e);
		}

		if ((process != null) && (process.exitValue() != 0)) {
			try {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to remove remote", gitRemote.getName()));
		}
	}

	public void removeRemotes(List<GitRemote> gitRemotes) {
		for (GitRemote gitRemote : gitRemotes) {
			removeRemote(gitRemote);
		}
	}

	public void reset(String ref, ResetCommand.ResetType resetType) {
		if ((ref != null) && (ref.equals("head") || ref.equals("HEAD"))) {
			ref = null;
		}

		ResetCommand resetCommand = _git.reset();

		resetCommand.setMode(resetType);

		if (ref != null) {
			resetCommand.setRef(ref);
		}
		else {
			ref = Constants.HEAD;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Resetting ", resetType.toString(), " to ", ref));

		resetCommand.call();
	}

	public static class GitRemote implements Comparable<GitRemote> {

		@Override
		public int compareTo(GitRemote otherGitRemote) {
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

		public GitBranch getGitBranch(String branchName) {
			List<GitBranch> gitBranches = _getGitLsResults(branchName, "-h");

			if (gitBranches.isEmpty()) {
				return null;
			}

			return gitBranches.get(0);
		}

		public List<GitBranch> getGitBranches() {
			return _getGitLsResults(null, "-h");
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

		private GitRemote(
			GitWorkingDirectory gitWorkingDirectory, String gitRemoteInputLine1,
			String gitRemoteInputLine2) {

			_gitWorkingDirectory = gitWorkingDirectory;

			if (gitRemoteInputLine1.equals(gitRemoteInputLine2)) {
				throw new IllegalArgumentException(
					"Duplicate remote input lines detected. " +
						gitRemoteInputLine1);
			}

			Matcher matcher = _gitRemotePattern.matcher(gitRemoteInputLine1);

			if (!matcher.matches()) {
				throw new IllegalArgumentException(
					"Invalid git remote input line " + gitRemoteInputLine1);
			}

			_name = matcher.group("name");

			String remoteURL = matcher.group("remoteURL");
			String type = matcher.group("type");

			_fetchRemoteURL = null;
			_pushRemoteURL = null;

			if (type.equals("fetch")) {
				_fetchRemoteURL = remoteURL;
			}

			if (type.equals("push")) {
				_pushRemoteURL = remoteURL;
			}

			matcher = _gitRemotePattern.matcher(gitRemoteInputLine2);

			if (!matcher.matches()) {
				throw new IllegalArgumentException(
					"Invalid git remote input line " + gitRemoteInputLine2);
			}

			String name2 = matcher.group("name");

			if (!_name.equals(name2)) {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Names do not match ", _name, " " + name2));
			}

			remoteURL = matcher.group("remoteURL");
			type = matcher.group("type");

			if (type.equals("fetch")) {
				_fetchRemoteURL = remoteURL;
			}

			if (type.equals("push")) {
				_pushRemoteURL = remoteURL;
			}
		}

		private List<GitBranch> _getGitLsResults(
			String branchName, String options) {

			try {
				Process process = JenkinsResultsParserUtil.executeBashCommands(
					true, _gitWorkingDirectory._workingDirectory, 1000 * 5,
					JenkinsResultsParserUtil.combine(
						"git ls-remote ", options, " ", getName(), " ",
						branchName));

				if (process.exitValue() != 0) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to get details of remote branch ",
							getName(), " ", branchName));
				}

				String input = JenkinsResultsParserUtil.readInputStream(
					process.getInputStream());

				List<GitBranch> branches = new ArrayList<>();

				for (String line : input.split("\n")) {
					Matcher matcher = _gitLsRemotePattern.matcher(line);

					if (matcher.find()) {
						branches.add(
							new GitBranch(
								this, matcher.group("name"),
								matcher.group("sha")));
					}
				}

				return branches;
			}
			catch (InterruptedException | IOException e) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get details of remote branch ", getName(),
						" ", branchName));
			}
		}

		private static final Pattern _gitLsRemotePattern = Pattern.compile(
			"(?<SHA>[^\\s]{40}+)[\\s]+(?<name>[^\\s]+)");
		private static final Pattern _gitRemotePattern = Pattern.compile(
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

	protected String executeBashCommands(
		int maxRetries, long timeout, String... commands) {

		Process process = null;

		int retries = 0;

		while (retries < maxRetries) {
			try {
				process = JenkinsResultsParserUtil.executeBashCommands(
					true, _workingDirectory, timeout, commands);
			}
			catch (InterruptedException | IOException e) {
				throw new RuntimeException(
					"Unable to execute bash commands: " + commands, e);
			}
			catch (TimeoutException te) {
				retries++;

				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to execute bash commands: " + commands, te);
				}
			}
		}

		if (process.exitValue() != 0) {
			String errorInput = "";

			try {
				errorInput = JenkinsResultsParserUtil.readInputStream(
					process.getErrorStream());
			}
			catch (IOException ioe) {
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to execute bash commands: ", commands.toString(),
					"\n", errorInput));
		}

		try {
			return JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to read process input stream", ioe);
		}
	}

	protected String executeBashCommands(String... commands) {
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
		String remoteURL = getRemoteURL(_getGitRemote("upstream"));

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
		String remoteURL = getRemoteURL(_getGitRemote("upstream"));

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setWorkingDirectory(String workingDirectory)
		throws IOException {

		_workingDirectory = new File(workingDirectory);

		if (!_workingDirectory.exists()) {
			throw new FileNotFoundException(
				_workingDirectory.getPath() + " is unavailable");
		}

		_gitDirectory = new File(workingDirectory, ".git");

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

	private File _gitDirectory;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

	private static class GitBranch {

		public GitBranch(GitRemote gitRemote, String name, String sha) {
			_gitRemote = gitRemote;
			_name = name;
			_sha = sha;
		}

		public GitRemote getGitRemote() {
			return _gitRemote;
		}

		public String getName() {
			return _name;
		}

		public String getSha() {
			return _sha;
		}

		private final GitRemote _gitRemote;
		private String _name;
		private final String _sha;

	};

}