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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 10,
				JenkinsResultsParserUtil.combine(
					"git remote add ", remoteName, " ", remoteURL));
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException("Unable to add remote " + remoteName, e);
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
					"Unable to add remote ", remoteName));
		}

		return getGitRemote(remoteName);
	}

	public boolean branchExists(String branchName, GitRemote gitRemote) {
		List<String> branchNames = null;

		if (gitRemote == null) {
			branchNames = getLocalBranchNames();
		}
		else {
			branchNames = getRemoteBranchNames(gitRemote);
		}

		return branchNames.contains(branchName);
	}

	public void checkoutBranch(String branchName) {
		checkoutBranch(branchName, "-f");
	}

	public void checkoutBranch(String branchName, String options) {
		String currentBranchName = getCurrentBranch();

		List<String> localBranchNames = getLocalBranchNames();

		if (!branchName.contains("/") &&
			!localBranchNames.contains(branchName)) {

			throw new IllegalArgumentException(
				JenkinsResultsParserUtil.combine(
					"Unable to checkout ", branchName,
					" because it does not exist"));
		}

		if (currentBranchName.equals(branchName)) {
			System.out.println(branchName + " is already checked out");

			return;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"The current branch is ", currentBranchName,
				". Checking out branch ", branchName, "."));

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		sb.append(branchName);

		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 60 * 10, sb.toString());
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to checkout branch " + branchName, e);
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
					"Unable to checkout branch ", branchName));
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

		Process process;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, workingDirectory, 1000 * 10, "git clean -dfx");
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to clean directory " + workingDirectory.getPath(), e);
		}

		if (process.exitValue() != 0) {
			try {
				String error = JenkinsResultsParserUtil.readInputStream(
					process.getErrorStream());

				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to clean directory ",
						workingDirectory.getPath(), "\n", error));
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	public void commitFileToCurrentBranch(String fileName, String message) {
		System.out.println("Committing file to current branch " + fileName);

		try {
			String commitCommand = JenkinsResultsParserUtil.combine(
				"git commit -m \'", message, "\' ", fileName);

			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 5, commitCommand);

			if (process.exitValue() != 0) {
				String errorMessage = JenkinsResultsParserUtil.combine(
					"Unable to commit ", fileName, "\n",
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException(errorMessage);
			}

			System.out.println(
				JenkinsResultsParserUtil.readInputStream(
					process.getInputStream()));
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException("Unable to commit " + fileName);
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

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 5, sb.toString());

			if (process.exitValue() != 0) {
				String errorMessage = JenkinsResultsParserUtil.combine(
					"Unable to create branch ", localBranchName, " at ",
					startPoint, "\n",
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException(errorMessage);
			}
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to create branch " + localBranchName);
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

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 1000 * 5,
				"git branch -f -D" + localBranchName);

			if (process.exitValue() != 0) {
				String errorMessage = JenkinsResultsParserUtil.combine(
					"Unable to delete branch ", localBranchName, "\n",
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException(errorMessage);
			}
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to delete branch " + localBranchName);
		}
	}

	public void deleteRemoteBranch(
		String remoteBranchName, GitRemote gitRemote) {

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleting remote branch ", remoteBranchName, " from ",
				gitRemote.getRemoteURL()));

		pushToRemote(true, "", remoteBranchName, gitRemote);
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

		int retries = 0;
		long start = System.currentTimeMillis();

		while (true) {
			try {
				Process process = JenkinsResultsParserUtil.executeBashCommands(
					true, getWorkingDirectory(), 1000 * 60 * 30, sb.toString());

				if ((process != null) && (process.exitValue() != 0)) {
					try {
						System.out.println(
							JenkinsResultsParserUtil.readInputStream(
								process.getErrorStream()));
					}
					catch (IOException ioe) {
						ioe.printStackTrace();
					}

					throw new RuntimeException("Unable to fetch");
				}

				if (process == null) {
					throw new RuntimeException("Process failed to run");
				}

				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream()));
			}
			catch (InterruptedException | IOException e) {
				if (retries < 3) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Fetch attempt ", Integer.toString(retries),
							" failed with an exception. ", e.getMessage(),
							"\nRetrying."));

					retries++;

					JenkinsResultsParserUtil.sleep(30000);
				}
				else {
					throw new RuntimeException(e);
				}
			}

			break;
		}

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
		String command = "git branch --contains " + sha;

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, getWorkingDirectory(), 1000 * 60 * 2, command);

			String output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			if (output.contains("no such commit")) {
				return Collections.emptyList();
			}

			System.out.println(output);

			String[] outputLines = output.split("\n");

			List<String> branchNamesList = new ArrayList<>(
				outputLines.length - 1);

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
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to find branches with SHA " + sha, e);
		}
	}

	public List<Ref> getBranchRefs() {
		ListBranchCommand listBranchCommand = _git.branchList();

		listBranchCommand.setListMode(ListMode.ALL);

		return listBranchCommand.call();
	}

	public String getBranchSHA(String branchName) {
		String command = "git rev-parse " + branchName;

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, getWorkingDirectory(), 1000 * 60 * 2, command);

			String output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			String firstLine = output.substring(0, output.indexOf("\n"));

			return firstLine.trim();
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to get SHA of branch " + branchName);
		}
	}

	public String getBranchSHA(String branchName, GitRemote gitRemote) {
		if (gitRemote == null) {
			return getBranchSHA(branchName);
		}

		String remoteURL = getRemoteURL(gitRemote);

		if (remoteURL.contains("git@github.com")) {
			return getGitHubBranchSHA(branchName, gitRemote);
		}

		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		lsRemoteCommand.setHeads(true);
		lsRemoteCommand.setRemote(remoteURL);
		lsRemoteCommand.setTags(false);

		Collection<Ref> remoteRefs = lsRemoteCommand.call();

		for (Ref remoteRef : remoteRefs) {
			String completeBranchName = "refs/heads/" + branchName;

			if (completeBranchName.equals(remoteRef.getName())) {
				return remoteRef.getObjectId().getName();
			}
		}

		return null;
	}

	public String getCurrentBranch() {
		waitForIndexLock();

		try {
			return _repository.getBranch();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get current branch name from repository", ioe);
		}
	}

	public Git getGit() {
		return _git;
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		Process process = null;

		try {
			process = JenkinsResultsParserUtil.executeBashCommands(
				true, _workingDirectory, 10 * 1000,
				"git config " + gitConfigPropertyName);
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(
				"Unable to get git config property " + gitConfigPropertyName,
				e);
		}

		if (process.exitValue() != 0) {
			return null;
		}

		try {
			String configProperty = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			configProperty = configProperty.trim();

			if (configProperty.isEmpty()) {
				return null;
			}

			return configProperty;
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get git config property " + gitConfigPropertyName,
				ioe);
		}
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

	public String getGitHubBranchSHA(String branchName, GitRemote gitRemote) {
		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote ", getRemoteURL(gitRemote), " ", branchName);

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				command);

			if (process.exitValue() != 0) {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getErrorStream()));

				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get branch sha for ", branchName, " on ",
						getRemoteURL(gitRemote)));
			}

			String output = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			for (String line : output.split("\n")) {
				if (line.endsWith("refs/heads/" + branchName)) {
					return line.substring(0, line.indexOf("\t"));
				}
			}
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}

		return null;
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

		try {
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, getWorkingDirectory(), 1000 * 5, "git remote -v");

			if (process.exitValue() != 0) {
				String errorOutput = JenkinsResultsParserUtil.readInputStream(
					process.getErrorStream());

				throw new RuntimeException(
					"Unable to get remotes\n" + errorOutput);
			}

			String input = JenkinsResultsParserUtil.readInputStream(
				process.getInputStream());

			String[] lines = input.split("\n");

			for (int i = 0; i < lines.length; i = i + 2) {
				GitRemote gitRemote = new GitRemote(
					this, lines[i], lines[i + 1]);

				gitRemotes.put(gitRemote.getName(), gitRemote);
			}

			return gitRemotes;
		}
		catch (InterruptedException | IOException e) {
			throw new RuntimeException("Unable to get remotes", e);
		}
	}

	public List<String> getLocalBranchNames() {
		List<Ref> allLocalBranchRefs = new ArrayList<>();

		for (Ref branchRef : getBranchRefs()) {
			String branchName = branchRef.getName();

			if (branchName.startsWith("refs/heads")) {
				allLocalBranchRefs.add(branchRef);
			}
		}

		return toShortNameList(allLocalBranchRefs);
	}

	public List<String> getRemoteBranchNames(GitRemote gitRemote) {
		LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository();

		lsRemoteCommand.setHeads(true);
		lsRemoteCommand.setRemote(getRemoteURL(gitRemote));
		lsRemoteCommand.setTags(false);

		List<String> remoteBranchNames = toShortNameList(
			lsRemoteCommand.call());

		Collections.sort(remoteBranchNames);

		return remoteBranchNames;
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

	public boolean pushToRemote(boolean force, GitRemote gitRemote) {
		return pushToRemote(force, getCurrentBranch(), gitRemote);
	}

	public boolean pushToRemote(
		boolean force, String remoteBranchName, GitRemote gitRemote) {

		return pushToRemote(
			force, getCurrentBranch(), remoteBranchName, gitRemote);
	}

	public boolean pushToRemote(
		boolean force, String remoteBranchName, String remoteURL) {

		GitRemote gitRemote = null;

		try {
			gitRemote = addRemote(true, "temp", remoteURL);

			return pushToRemote(force, remoteBranchName, gitRemote);
		}
		finally {
			removeRemote(gitRemote);
		}
	}

	public boolean pushToRemote(
		boolean force, String localBranchName, String remoteBranchName,
		GitRemote gitRemote) {

		String remoteURL = getRemoteURL(gitRemote);

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Pushing ", localBranchName, " to ", remoteURL, " ",
				remoteBranchName));

		PushCommand pushCommand = null;

		synchronized (_git) {
			pushCommand = _git.push();
		}

		String remoteRefName = "refs/heads/" + remoteBranchName;

		RefSpec refSpec = new RefSpec(
			JenkinsResultsParserUtil.combine(
				localBranchName, ":", remoteRefName));

		synchronized (pushCommand) {
			pushCommand.setForce(force);
			pushCommand.setRefSpecs(refSpec);
			pushCommand.setRemote(remoteURL);

			for (PushResult pushResult : pushCommand.call()) {
				for (RemoteRefUpdate remoteRefUpdate :
						pushResult.getRemoteUpdates()) {

					if ((remoteRefUpdate != null) &&
						(remoteRefUpdate.getStatus() !=
							RemoteRefUpdate.Status.OK)) {

						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Unable to push ", localBranchName, " to ",
								getRemoteURL(gitRemote), ".\nPush response: ",
								remoteRefUpdate.toString()));

						return false;
					}
				}
			}

			return true;
		}
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
			Process process = JenkinsResultsParserUtil.executeBashCommands(
				true, getWorkingDirectory(), 1000 * 60 * 10, rebaseCommand);

			if ((process != null) && (process.exitValue() != 0)) {
				try {
					System.out.println(
						JenkinsResultsParserUtil.readInputStream(
							process.getErrorStream()));
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}

				throw new RuntimeException("Unable to rebase");
			}

			if (process != null) {
				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream()));
			}

			int i = 0;

			while (i < 10) {
				List<String> branchNamesContainingSourceBranchSHA =
					getBranchNamesContainingSHA(sourceBranchSHA);

				if (!branchNamesContainingSourceBranchSHA.contains(
						targetBranchName)) {

					i++;

					JenkinsResultsParserUtil.sleep(1000 * 30);

					continue;
				}

				break;
			}
		}
		catch (Exception e) {
			RepositoryState repositoryState = _repository.getRepositoryState();

			try {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to rebase ", targetBranchName, " to ",
						sourceBranchName, ". Repository is in the ",
						repositoryState.toString(), " state."),
					e);
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

	protected List<String> toShortNameList(Collection<Ref> refs) {
		List<String> shortNames = new ArrayList<>(refs.size());

		for (Ref ref : refs) {
			String refName = ref.getName();

			shortNames.add(refName.substring(refName.lastIndexOf("/") + 1));
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

}