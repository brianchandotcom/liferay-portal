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
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT,
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

	public void checkoutLocalGitBranch(LocalGitBranch localGitBranch) {
		checkoutLocalGitBranch(localGitBranch, "-f");
	}

	public void checkoutLocalGitBranch(
		LocalGitBranch localGitBranch, String options) {

		waitForIndexLock();

		StringBuilder sb = new StringBuilder();

		sb.append("git checkout ");

		if (options != null) {
			sb.append(options);
			sb.append(" ");
		}

		String branchName = localGitBranch.getName();

		sb.append(branchName);

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, sb.toString());

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
					"HEAD file content is: ", headContent,
					". Waiting for branch to be updated."));

			JenkinsResultsParserUtil.sleep(5000);

			timeout++;

			if (timeout >= 59) {
				LocalGitBranch currentLocalGitBranch =
					getCurrentLocalGitBranch();

				if ((currentLocalGitBranch != null) &&
					branchName.equals(currentLocalGitBranch.getName())) {

					return;
				}

				throw new RuntimeException(
					"Unable to checkout branch " + branchName);
			}
		}
	}

	public void cherryPick(Commit commit) {
		String cherryPickCommand = JenkinsResultsParserUtil.combine(
			"git cherry-pick " + commit.getSHA());

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, cherryPickCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to cherry pick commit ", commit.getSHA(), "\n",
					executionResult.getStandardError()));
		}
	}

	public void clean() {
		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, "git clean -dfx");

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

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, commitCommand);

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

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, commitCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to commit staged files", "\n",
					executionResult.getStandardError()));
		}
	}

	public LocalGitBranch createLocalGitBranch(LocalGitBranch localGitBranch) {
		return createLocalGitBranch(
			localGitBranch.getName(), false, localGitBranch.getSHA());
	}

	public LocalGitBranch createLocalGitBranch(
		LocalGitBranch localGitBranch, boolean force) {

		return createLocalGitBranch(
			localGitBranch.getName(), force, localGitBranch.getSHA());
	}

	public LocalGitBranch createLocalGitBranch(String localGitBranchName) {
		return createLocalGitBranch(localGitBranchName, false, null);
	}

	public LocalGitBranch createLocalGitBranch(
		String localGitBranchName, boolean force) {

		return createLocalGitBranch(localGitBranchName, force, null);
	}

	public LocalGitBranch createLocalGitBranch(
		String localGitBranchName, boolean force, String startPoint) {

		LocalGitBranch currentLocalGitBranch = getCurrentLocalGitBranch();

		LocalGitBranch tempLocalGitBranch = null;

		try {
			if ((currentLocalGitBranch == null) ||
				localGitBranchName.equals(currentLocalGitBranch.getName())) {

				tempLocalGitBranch = createLocalGitBranch(
					"temp-" + System.currentTimeMillis());

				checkoutLocalGitBranch(tempLocalGitBranch);
			}

			StringBuilder sb = new StringBuilder();

			sb.append("git branch ");

			if (force) {
				sb.append("-f ");
			}

			sb.append(localGitBranchName);

			if (startPoint != null) {
				sb.append(" ");
				sb.append(startPoint);
			}

			ExecutionResult executionResult = executeBashCommands(
				_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, sb.toString());

			if (executionResult.getExitValue() != 0) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to create local branch ", localGitBranchName,
						" at ", startPoint, "\n",
						executionResult.getStandardError()));
			}
		}
		finally {
			if (tempLocalGitBranch != null) {
				checkoutLocalGitBranch(currentLocalGitBranch);

				deleteLocalGitBranch(tempLocalGitBranch);
			}
		}

		return getLocalGitBranch(localGitBranchName, true);
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

		String url = JenkinsResultsParserUtil.getGitHubApiUrl(
			_repositoryName, receiverUserName, "pulls");

		JSONObject responseJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, requestJSONObject.toString());

		String pullRequestURL = responseJSONObject.getString("html_url");

		System.out.println("Created a pull request at " + pullRequestURL);

		return pullRequestURL;
	}

	public void deleteLocalGitBranch(LocalGitBranch localGitBranch) {
		if (localGitBranch == null) {
			return;
		}

		deleteLocalGitBranches(Arrays.asList(localGitBranch));
	}

	public void deleteLocalGitBranch(String branchName) {
		deleteLocalGitBranch(getLocalGitBranch(branchName));
	}

	public void deleteLocalGitBranches(List<LocalGitBranch> localGitBranches) {
		if (localGitBranches.isEmpty()) {
			return;
		}

		Set<String> localGitBranchNames = new HashSet<>();

		for (LocalGitBranch localGitBranch : localGitBranches) {
			localGitBranchNames.add(localGitBranch.getName());
		}

		for (List<String> branchNames :
				Lists.partition(
					new ArrayList<>(localGitBranchNames),
					_DELETE_BRANCHES_BATCH_SIZE)) {

			_deleteLocalGitBranches(
				branchNames.toArray(new String[branchNames.size()]));
		}
	}

	public void deleteRemoteGitBranch(RemoteGitBranch remoteGitBranch) {
		deleteRemoteGitBranches(Arrays.asList(remoteGitBranch));
	}

	public void deleteRemoteGitBranch(String branchName, Remote remote) {
		deleteRemoteGitBranch(branchName, remote.getRemoteURL());
	}

	public void deleteRemoteGitBranch(
		String branchName, RemoteRepository remoteRepository) {

		deleteRemoteGitBranch(branchName, remoteRepository.getRemoteURL());
	}

	public void deleteRemoteGitBranch(String branchName, String remoteURL) {
		deleteRemoteGitBranch(getRemoteGitBranch(branchName, remoteURL));
	}

	public void deleteRemoteGitBranches(
		List<RemoteGitBranch> remoteGitBranches) {

		Map<String, Set<String>> remoteURLGitBranchNameMap = new HashMap<>();

		for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
			RemoteRepository remoteRepository =
				remoteGitBranch.getRemoteRepository();

			String remoteURL = remoteRepository.getRemoteURL();

			if (!remoteURLGitBranchNameMap.containsKey(remoteURL)) {
				remoteURLGitBranchNameMap.put(remoteURL, new HashSet<String>());
			}

			Set<String> remoteGitBranchNames = remoteURLGitBranchNameMap.get(
				remoteURL);

			remoteGitBranchNames.add(remoteGitBranch.getName());

			remoteURLGitBranchNameMap.put(remoteURL, remoteGitBranchNames);
		}

		for (Map.Entry<String, Set<String>> remoteURLBranchNamesEntry :
				remoteURLGitBranchNameMap.entrySet()) {

			String remoteURL = remoteURLBranchNamesEntry.getKey();

			for (List<String> branchNames :
					Lists.partition(
						new ArrayList<String>(
							remoteURLBranchNamesEntry.getValue()),
							_DELETE_BRANCHES_BATCH_SIZE)) {

				_deleteRemoteGitBranches(
					remoteURL,
					branchNames.toArray(new String[branchNames.size()]));
			}
		}
	}

	public void displayLog() {
		displayLog(1);
	}

	public void displayLog(int logNumber) {
		String command = "git log -n " + logNumber;

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 3, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException("Unable to display log");
		}

		System.out.println();
		System.out.println(executionResult.getStandardOut());
		System.out.println();
	}

	public LocalGitBranch fetch(
		LocalGitBranch localGitBranch, boolean noTags,
		RemoteGitBranch remoteGitBranch) {

		if (remoteGitBranch == null) {
			throw new IllegalArgumentException("Remote Git branch is null");
		}

		String remoteGitBranchSHA = remoteGitBranch.getSHA();

		if (localSHAExists(remoteGitBranchSHA)) {
			System.out.println(
				remoteGitBranchSHA + " already exists in repository");

			if (localGitBranch != null) {
				return createLocalGitBranch(
					localGitBranch.getName(), true, remoteGitBranchSHA);
			}

			return null;
		}

		RemoteRepository remoteRepository =
			remoteGitBranch.getRemoteRepository();

		String remoteURL = remoteRepository.getRemoteURL();

		if (JenkinsResultsParserUtil.isCINode()) {
			if (remoteURL.contains("github-dev.liferay.com")) {
				executeBashCommands(
					_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT,
					"rm -f ~/.ssh/known_hosts");
			}

			if (remoteURL.contains("github.com:liferay/")) {
				String gitHubDevRemoteURL = remoteURL.replace(
					"github.com:liferay/", "github-dev.liferay.com:liferay/");

				RemoteGitBranch gitHubDevRemoteGitBranch = getRemoteGitBranch(
					remoteGitBranch.getName(), gitHubDevRemoteURL);

				if (gitHubDevRemoteGitBranch != null) {
					fetch(null, noTags, gitHubDevRemoteGitBranch);

					if (localSHAExists(remoteGitBranchSHA)) {
						if (localGitBranch != null) {
							return createLocalGitBranch(
								localGitBranch.getName(), true,
								remoteGitBranchSHA);
						}

						return null;
					}
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f ");

		if (noTags) {
			sb.append(" --no-tags ");
		}

		sb.append(remoteURL);

		String remoteGitBranchName = remoteGitBranch.getName();

		if ((remoteGitBranchName != null) && !remoteGitBranchName.isEmpty()) {
			sb.append(" ");
			sb.append(remoteGitBranchName);

			if (localGitBranch != null) {
				sb.append(":");
				sb.append(localGitBranch.getName());
			}
		}

		long start = System.currentTimeMillis();

		ExecutionResult executionResult = executeBashCommands(
			3, _RETRY_DELAY, 1000 * 60 * 30, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch remote branch ", remoteGitBranchName, "\n",
					executionResult.getStandardError()));
		}

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));

		if (localSHAExists(remoteGitBranchSHA) && (localGitBranch != null)) {
			return createLocalGitBranch(
				localGitBranch.getName(), true, remoteGitBranchSHA);
		}

		return null;
	}

	public LocalGitBranch fetch(
		LocalGitBranch localGitBranch, RemoteGitBranch remoteGitBranch) {

		return fetch(localGitBranch, true, remoteGitBranch);
	}

	public void fetch(Remote remote) {
		fetch(remote.getRemoteURL());
	}

	public LocalGitBranch fetch(RemoteGitBranch remoteGitBranch) {
		return fetch(null, true, remoteGitBranch);
	}

	public void fetch(RemoteRepository remoteRepository) {
		fetch(remoteRepository.getRemoteURL());
	}

	public void fetch(String remoteURL) {
		fetch(remoteURL, true);
	}

	public void fetch(String remoteURL, boolean noTags) {
		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		Matcher remoteURLMatcher = _remoteURLPattern.matcher(remoteURL);

		if (!remoteURLMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f ");

		if (noTags) {
			sb.append(" --no-tags ");
		}

		sb.append(remoteURL);
		sb.append("refs/heads/*:refs/remotes/origin/*");

		long start = System.currentTimeMillis();

		ExecutionResult executionResult = executeBashCommands(
			3, _RETRY_DELAY, 1000 * 60 * 30, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch from remote url ", remoteURL, "\n",
					executionResult.getStandardError()));
		}

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(
					System.currentTimeMillis() - start));
	}

	public List<String> getBranchNamesContainingSHA(String sha) {
		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 2,
			"git branch --contains " + sha);

		if (executionResult.getExitValue() != 0) {
			String standardError = executionResult.getStandardError();

			if (standardError.contains("no such commit")) {
				return Collections.emptyList();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get branches with SHA ", sha, "\n",
					standardError));
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

	public String getCurrentBranchName() {
		return getCurrentBranchName(false);
	}

	public String getCurrentBranchName(boolean required) {
		waitForIndexLock();

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, "git branch | grep \\*");

		if (executionResult.getExitValue() != 0) {
			System.out.println(executionResult.getStandardError());

			if (required) {
				throw new RuntimeException(
					"Unable to find required local branch HEAD");
			}

			return null;
		}

		String currentBranchName = executionResult.getStandardOut();

		currentBranchName = currentBranchName.replaceFirst("\\*\\s*", "");

		return currentBranchName.trim();
	}

	public LocalGitBranch getCurrentLocalGitBranch() {
		String currentBranchName = getCurrentBranchName();

		if (currentBranchName != null) {
			LocalGitBranch currentLocalGitBranch = getLocalGitBranch(
				currentBranchName);

			return currentLocalGitBranch;
		}

		LocalGitBranch currentLocalGitBranch = getLocalGitBranch(
			getUpstreamBranchName());

		checkoutLocalGitBranch(currentLocalGitBranch);

		return currentLocalGitBranch;
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT,
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

	public File getJavaFileFromFullClassName(String fullClassName) {
		if (_javaDirPaths == null) {
			List<File> javaFiles = JenkinsResultsParserUtil.findFiles(
				getWorkingDirectory(), ".*\\.java");

			_javaDirPaths = new HashSet<>();

			for (File javaFile : javaFiles) {
				File parentFile = javaFile.getParentFile();

				_javaDirPaths.add(parentFile.getPath());
			}
		}

		String classFileName =
			fullClassName.replaceAll(".*\\.([^\\.]+)", "$1") + ".java";
		String classPackageName = fullClassName.substring(
			0, fullClassName.lastIndexOf("."));

		String classPackagePath = classPackageName.replaceAll("\\.", "/");

		for (String javaDirPath : _javaDirPaths) {
			if (javaDirPath.contains(classPackagePath)) {
				File classFile = new File(javaDirPath, classFileName);

				if (classFile.exists()) {
					return classFile;
				}
			}
		}

		return null;
	}

	public LocalGitBranch getLocalGitBranch(String branchName) {
		return getLocalGitBranch(branchName, false);
	}

	public LocalGitBranch getLocalGitBranch(
		String branchName, boolean required) {

		if ((branchName != null) && !branchName.isEmpty()) {
			List<LocalGitBranch> localGitBranches = getLocalGitBranches(
				branchName);

			if (localGitBranches.isEmpty()) {
				return null;
			}

			return localGitBranches.get(0);
		}

		for (LocalGitBranch localGitBranch : getLocalGitBranches(null)) {
			if (branchName.equals(localGitBranch.getName())) {
				return localGitBranch;
			}
		}

		if (required) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find required branch ", branchName, " from ",
					String.valueOf(getWorkingDirectory())));
		}

		return null;
	}

	public List<LocalGitBranch> getLocalGitBranches(String branchName) {
		List<String> localGitBranchNames = getLocalGitBranchNames();

		List<LocalGitBranch> localGitBranches = new ArrayList<>(
			localGitBranchNames.size());

		LocalRepository localRepository = RepositoryFactory.getLocalRepository(
			getRepositoryName(), getUpstreamBranchName());

		if (branchName != null) {
			if (localGitBranchNames.contains(branchName)) {
				localGitBranches.add(
					GitBranchFactory.newLocalGitBranch(
						localRepository, branchName,
						getLocalGitBranchSHA(branchName)));
			}

			return localGitBranches;
		}

		for (String localGitBranchName : localGitBranchNames) {
			localGitBranches.add(
				GitBranchFactory.newLocalGitBranch(
					localRepository, localGitBranchName,
					getLocalGitBranchSHA(localGitBranchName)));
		}

		return localGitBranches;
	}

	public String getLocalGitBranchSHA(String localGitBranchName) {
		if (localGitBranchName == null) {
			throw new IllegalArgumentException("Local branch name is null");
		}

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 2,
			"git rev-parse " + localGitBranchName);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to determine SHA of branch ", localGitBranchName,
					"\n", executionResult.getStandardError()));
		}

		return executionResult.getStandardOut();
	}

	public List<File> getModifiedFilesList() {
		return getModifiedFilesList(null, false);
	}

	public List<File> getModifiedFilesList(boolean checkUnstagedFiles) {
		return getModifiedFilesList(null, checkUnstagedFiles);
	}

	public List<File> getModifiedFilesList(String grepPredicateString) {
		return getModifiedFilesList(grepPredicateString, false);
	}

	public List<File> getModifiedFilesList(
		String grepPredicateString, boolean checkUnstagedFiles) {

		List<File> modifiedFiles = new ArrayList<>();

		LocalGitBranch currentLocalGitBranch = getCurrentLocalGitBranch();

		if (currentLocalGitBranch == null) {
			throw new RuntimeException(
				"Unable to determine the current branch");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git diff --diff-filter=AM --name-only ");

		sb.append(
			_getMergeBaseCommitSHA(
				currentLocalGitBranch,
				getLocalGitBranch(getUpstreamBranchName(), true)));

		if (!checkUnstagedFiles) {
			sb.append(" ");
			sb.append(currentLocalGitBranch.getSHA());
		}

		if ((grepPredicateString != null) && !grepPredicateString.isEmpty()) {
			sb.append(" | grep ");
			sb.append(grepPredicateString);
		}

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, sb.toString());

		if (executionResult.getExitValue() == 1) {
			return modifiedFiles;
		}

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				"Unable to get current branch modified files\n" +
					executionResult.getStandardError());
		}

		String gitDiffOutput = executionResult.getStandardOut();

		for (String line : gitDiffOutput.split("\n")) {
			modifiedFiles.add(new File(_workingDirectory, line));
		}

		return modifiedFiles;
	}

	public LocalGitBranch getRebasedLocalGitBranch(PullRequest pullRequest) {
		LocalGitBranch currentLocalGitBranch = getCurrentLocalGitBranch();

		String currentBranchName = null;

		if (currentLocalGitBranch != null) {
			currentBranchName = currentLocalGitBranch.getName();
		}

		LocalGitBranch tempLocalGitBranch = null;

		try {
			if ((currentBranchName == null) ||
				currentBranchName.equals(
					pullRequest.getLocalSenderBranchName())) {

				tempLocalGitBranch = createLocalGitBranch(
					"temp-" + System.currentTimeMillis());

				checkoutLocalGitBranch(tempLocalGitBranch);
			}

			RemoteGitBranch senderRemoteGitBranch = getRemoteGitBranch(
				pullRequest.getSenderBranchName(),
				pullRequest.getSenderRemoteURL(), true);

			fetch(senderRemoteGitBranch);

			LocalGitBranch rebasedLocalGitBranch = createLocalGitBranch(
				pullRequest.getLocalSenderBranchName(), true,
				pullRequest.getSenderSHA());

			RemoteGitBranch upstreamRemoteGitBranch = getRemoteGitBranch(
				pullRequest.getUpstreamBranchName(), getUpstreamRemote(), true);

			if (!localSHAExists(upstreamRemoteGitBranch.getSHA())) {
				fetch(upstreamRemoteGitBranch);
			}

			LocalGitBranch upstreamLocalGitBranch = createLocalGitBranch(
				upstreamRemoteGitBranch.getName(), true,
				upstreamRemoteGitBranch.getSHA());

			rebasedLocalGitBranch = rebase(
				true, upstreamLocalGitBranch, rebasedLocalGitBranch);

			clean();

			reset("--hard");

			return rebasedLocalGitBranch;
		}
		finally {
			if (tempLocalGitBranch != null) {
				deleteLocalGitBranch(tempLocalGitBranch);
			}
		}
	}

	public Remote getRemote(String name) {
		if (name.equals("upstream")) {
			name = "upstream-temp";
		}

		Map<String, Remote> remotes = getRemotes();

		name = name.trim();

		Remote remote = remotes.get(name);

		if ((remote == null) && name.equals("upstream-temp")) {
			JenkinsResultsParserUtil.sleep(1000);

			remotes = getRemotes();

			return remotes.get(name);
		}

		return remote;
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, Remote remote) {

		return getRemoteGitBranch(
			remoteGitBranchName, remote.getRemoteURL(), false);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, Remote remote, boolean required) {

		return getRemoteGitBranch(
			remoteGitBranchName, remote.getRemoteURL(), required);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, RemoteRepository remoteRepository) {

		return getRemoteGitBranch(
			remoteGitBranchName, remoteRepository.getRemoteURL(), false);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, RemoteRepository remoteRepository,
		boolean required) {

		return getRemoteGitBranch(
			remoteGitBranchName, remoteRepository.getRemoteURL(), required);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, String remoteURL) {

		return getRemoteGitBranch(remoteGitBranchName, remoteURL, false);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, String remoteURL, boolean required) {

		List<RemoteGitBranch> remoteGitBranches = getRemoteGitBranches(
			remoteGitBranchName, remoteURL);

		for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
			if (remoteGitBranchName.equals(remoteGitBranch.getName())) {
				return remoteGitBranch;
			}
		}

		if (required) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find required branch ", remoteGitBranchName,
					" from remote URL ", remoteURL));
		}

		return null;
	}

	public List<RemoteGitBranch> getRemoteGitBranches(Remote remote) {
		return getRemoteGitBranches(null, remote.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		RemoteRepository remoteRepository) {

		return getRemoteGitBranches(null, remoteRepository.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(String remoteURL) {
		return getRemoteGitBranches(null, remoteURL);
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, Remote remote) {

		return getRemoteGitBranches(remoteGitBranchName, remote.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, RemoteRepository remoteRepository) {

		return getRemoteGitBranches(
			remoteGitBranchName, remoteRepository.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, String remoteURL) {

		Matcher remoteURLMatcher = _remoteURLPattern.matcher(remoteURL);

		if (!remoteURLMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		String command = null;

		if (remoteGitBranchName != null) {
			command = JenkinsResultsParserUtil.combine(
				"git ls-remote -h ", remoteURL, " ", remoteGitBranchName);
		}
		else {
			command = JenkinsResultsParserUtil.combine(
				"git ls-remote -h ", remoteURL);
		}

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branches from ", remoteURL, "\n",
					executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		List<RemoteGitBranch> remoteGitBranches = new ArrayList<>();

		RemoteRepository remoteRepository =
			RepositoryFactory.getRemoteRepository(
				remoteURLMatcher.group("hostname"),
				remoteURLMatcher.group("repositoryName"),
				remoteURLMatcher.group("username"));

		for (String line : input.split("\n")) {
			Matcher gitLsRemoteMatcher = _gitLsRemotePattern.matcher(line);

			if (gitLsRemoteMatcher.find()) {
				remoteGitBranches.add(
					GitBranchFactory.newRemoteGitBranch(
						remoteRepository, gitLsRemoteMatcher.group("name"),
						gitLsRemoteMatcher.group("sha")));
			}
		}

		System.out.println(
			"getRemoteGitBranches found " + remoteGitBranches.size() +
				" branches at " + remoteURL + ".");

		return remoteGitBranches;
	}

	public List<String> getRemoteGitBranchNames(Remote remote) {
		return getRemoteGitBranchNames(remote.getRemoteURL());
	}

	public List<String> getRemoteGitBranchNames(
		RemoteRepository remoteRepository) {

		return getRemoteGitBranchNames(remoteRepository.getRemoteURL());
	}

	public List<String> getRemoteGitBranchNames(String remoteURL) {
		List<String> remoteGitBranchNames = new ArrayList<>();

		List<RemoteGitBranch> remoteGitBranches = getRemoteGitBranches(
			remoteURL);

		for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
			remoteGitBranchNames.add(remoteGitBranch.getName());
		}

		return remoteGitBranchNames;
	}

	public String getRemoteGitBranchSHA(
		String remoteGitBranchName, Remote remote) {

		return getRemoteGitBranchSHA(
			remoteGitBranchName, remote.getRemoteURL());
	}

	public String getRemoteGitBranchSHA(
		String remoteGitBranchName, RemoteRepository remoteRepository) {

		return getRemoteGitBranchSHA(
			remoteGitBranchName, remoteRepository.getRemoteURL());
	}

	public String getRemoteGitBranchSHA(
		String remoteGitBranchName, String remoteURL) {

		if (remoteGitBranchName == null) {
			throw new IllegalArgumentException("Remote branch name is null");
		}

		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		Matcher remoteURLMatcher = _remoteURLPattern.matcher(remoteURL);

		if (!remoteURLMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote -h ", remoteURL, " ", remoteGitBranchName);

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branch SHA ", remoteURL, " ",
					remoteGitBranchName, "\n",
					executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		for (String line : input.split("\n")) {
			Matcher matcher = _gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				return matcher.group("sha");
			}
		}

		return null;
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
				_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, "git remote -v");

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
			StringBuilder sb = new StringBuilder();

			sb.append("Found remotes: ");

			for (int i = 0; i < lines.length; i = i + 2) {
				Remote remote = new Remote(
					this, Arrays.copyOfRange(lines, i, i + 2));

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(remote.getName());

				remotes.put(remote.getName(), remote);
			}

			System.out.println(sb);
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

	public boolean isRemoteRepositoryAlive(String remoteURL) {
		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote -h ", remoteURL, " HEAD");

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			System.out.println("Unable to connect to " + remoteURL);

			return false;
		}

		System.out.println(remoteURL + " is alive");

		return true;
	}

	public boolean localGitBranchExists(String branchName) {
		if (getLocalGitBranch(branchName) != null) {
			return true;
		}

		return false;
	}

	public boolean localSHAExists(String sha) {
		String command = "git cat-file -t " + sha;

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 3, command);

		if (executionResult.getExitValue() == 0) {
			return true;
		}

		return false;
	}

	public List<Commit> log(int num) {
		return log(num, null);
	}

	public List<Commit> log(int num, File file) {
		List<Commit> commits = new ArrayList<>(num);

		String gitLog = _log(num, file, "%H %s");

		gitLog = gitLog.replaceAll("Finished executing Bash commands.", "");

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			commits.add(getCommit(gitLogEntity));
		}

		return commits;
	}

	public RemoteGitBranch pushToRemote(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, Remote remote) {

		return pushToRemote(
			force, localGitBranch, remoteGitBranchName, remote.getRemoteURL());
	}

	public RemoteGitBranch pushToRemote(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, RemoteRepository remoteRepository) {

		return pushToRemote(
			force, localGitBranch, remoteGitBranchName,
			remoteRepository.getRemoteURL());
	}

	public RemoteGitBranch pushToRemote(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, String remoteURL) {

		if (localGitBranch == null) {
			throw new IllegalArgumentException("Local Git branch is null");
		}

		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		Matcher remoteURLMatcher = _remoteURLPattern.matcher(remoteURL);

		if (!remoteURLMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git push ");

		if (force) {
			sb.append("-f ");
		}

		sb.append(remoteURL);
		sb.append(" ");
		sb.append(localGitBranch.getName());

		if (remoteGitBranchName != null) {
			sb.append(":");
			sb.append(remoteGitBranchName);
		}

		try {
			executeBashCommands(
				_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, sb.toString());
		}
		catch (RuntimeException re) {
			return null;
		}

		if (remoteGitBranchName != null) {
			return getRemoteGitBranch(remoteGitBranchName, remoteURL);
		}

		return null;
	}

	public LocalGitBranch rebase(
		boolean abortOnFail, LocalGitBranch baseLocalGitBranch,
		LocalGitBranch localGitBranch) {

		List<String> branchNamesContainingSHA = getBranchNamesContainingSHA(
			baseLocalGitBranch.getSHA());

		if (branchNamesContainingSHA.contains(localGitBranch.getName())) {
			checkoutLocalGitBranch(localGitBranch);

			return localGitBranch;
		}

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", baseLocalGitBranch.getName(), " ",
			localGitBranch.getName());

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, rebaseCommand);

		if (executionResult.getExitValue() != 0) {
			if (abortOnFail) {
				rebaseAbort();
			}

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to rebase ", localGitBranch.getName(), " to ",
					baseLocalGitBranch.getName(), "\n",
					executionResult.getStandardError()));
		}

		return getCurrentLocalGitBranch();
	}

	public void rebaseAbort() {
		rebaseAbort(true);
	}

	public void rebaseAbort(boolean ignoreFailure) {
		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, "git rebase --abort");

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

	public boolean remoteGitBranchExists(String branchName, Remote remote) {
		return remoteGitBranchExists(branchName, remote.getRemoteURL());
	}

	public boolean remoteGitBranchExists(
		String branchName, RemoteRepository remoteRepository) {

		return remoteGitBranchExists(
			branchName, remoteRepository.getRemoteURL());
	}

	public boolean remoteGitBranchExists(String branchName, String remoteURL) {
		if (getRemoteGitBranch(branchName, remoteURL) != null) {
			return true;
		}

		return false;
	}

	public void removeRemote(Remote remote) {
		if ((remote == null) || !remoteExists(remote.getName())) {
			return;
		}

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT,
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
			2, _RETRY_DELAY, 1000 * 60 * 2, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to reset\n", executionResult.getStandardError()));
		}
	}

	public void stageFileInCurrentLocalGitBranch(String fileName) {
		String command = "git stage " + fileName;

		ExecutionResult result = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, command);

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to stage file " + fileName);
		}
	}

	public String status() {
		for (int i = 0; i < 5; i++) {
			try {
				String gitStatus = _status();

				gitStatus = gitStatus.replaceAll(
					"Finished executing Bash commands.", "");

				if (!gitStatus.startsWith("On branch")) {
					throw new RuntimeException("Unable to run: git status");
				}

				return gitStatus;
			}
			catch (RuntimeException re) {
				re.printStackTrace();

				JenkinsResultsParserUtil.sleep(1000);
			}
		}

		throw new RuntimeException("Unable to run: git status");
	}

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

		public String getHostname() {
			return _hostname;
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

		public String getRepositoryName() {
			return _repositoryName;
		}

		public String getUsername() {
			return _username;
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

			Matcher remoteURLMatcher = _remoteURLMultiPattern.matches(
				_fetchRemoteURL);

			if (remoteURLMatcher == null) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"fetch remote URL ", _fetchRemoteURL,
						" is not a valid remote URL"));
			}

			_hostname = remoteURLMatcher.group("hostname");
			_username = remoteURLMatcher.group("username");
			_repositoryName = remoteURLMatcher.group("repositoryName");
		}

		private static final Pattern _remotePattern = Pattern.compile(
			JenkinsResultsParserUtil.combine(
				"(?<name>[^\\s]+)[\\s]+(?<remoteURL>[^\\s]+)[\\s]+\\(",
				"(?<type>[^\\s]+)\\)"));
		private static final MultiPattern _remoteURLMultiPattern =
			new MultiPattern(
				"git@(?<hostname>[^:]+):(?<username>[^/]+)" +
					"/(?<repositoryName>[^\\.^\\s]+)(\\.git)?+\\s*",
				"https://(?<hostname>[^/]+)/(?<username>[^/]+)" +
					"/(?<repositoryName>[^\\.^\\s]+)(\\.git)?+\\s*");

		private final String _fetchRemoteURL;
		private final GitWorkingDirectory _gitWorkingDirectory;
		private final String _hostname;
		private final String _name;
		private final String _pushRemoteURL;
		private final String _repositoryName;
		private final String _username;

	}

	protected GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		this(upstreamBranchName, workingDirectoryPath, null);
	}

	protected GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String repositoryName)
		throws IOException {

		setWorkingDirectory(workingDirectoryPath);

		_upstreamBranchName = upstreamBranchName;

		Remote upstreamTempRemote = getRemote("upstream-temp");

		if (upstreamTempRemote != null) {
			removeRemote(upstreamTempRemote);
		}

		waitForIndexLock();

		if ((repositoryName == null) || repositoryName.equals("")) {
			repositoryName = loadRepositoryName();
		}

		_repositoryName = repositoryName;

		if (_publicOnlyRepositoryNames.contains(_repositoryName)) {
			setUpstreamRemoteToPublicRepository();
		}
		else {
			if (_privateOnlyRepositoryNames.contains(_repositoryName)) {
				setUpstreamRemoteToPrivateRepository();
			}
			else {
				if (upstreamBranchName.equals("master")) {
					setUpstreamRemoteToPublicRepository();
				}
				else {
					setUpstreamRemoteToPrivateRepository();
				}
			}
		}

		_repositoryUsername = loadRepositoryUsername();
	}

	protected ExecutionResult executeBashCommands(
		int maxRetries, long retryDelay, long timeout, String... commands) {

		Process process = null;

		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				process = JenkinsResultsParserUtil.executeBashCommands(
					true, _workingDirectory, timeout, commands);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to execute bash commands: " +
							Arrays.toString(commands),
						e);
				}

				System.out.println(
					"Unable to execute bash commands retrying... ");

				e.printStackTrace();

				JenkinsResultsParserUtil.sleep(retryDelay);
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

	protected Commit getCommit(String gitLogEntity) {
		Matcher matcher = _gitLogEntityPattern.matcher(gitLogEntity);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("Unable to find Git SHA");
		}

		String gitHubUserName = getGitHubUserName(getRemote("upstream"));
		String message = matcher.group("message");
		String repositoryName = getRepositoryName();
		String sha = matcher.group("sha");

		return CommitFactory.newCommit(
			gitHubUserName, message, repositoryName, sha);
	}

	protected List<String> getLocalGitBranchNames() {
		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT,
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
		String gitFileContent = null;

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

		throw new IllegalArgumentException(
			"Real Git directory could not be found in " + gitFile.getPath());
	}

	protected Remote getUpstreamRemote() {
		Map<String, Remote> remotes = getRemotes();

		Remote remote = remotes.get("upstream");

		return remote;
	}

	protected String loadRepositoryName() {
		Remote upstreamRemote = getUpstreamRemote();

		String remoteURL = upstreamRemote.getRemoteURL();

		int x = remoteURL.lastIndexOf("/") + 1;

		int y = remoteURL.indexOf(".git");

		if (y == -1) {
			y = remoteURL.length();
		}

		String repositoryName = remoteURL.substring(x, y);

		if (repositoryName.equals("liferay-jenkins-tools-private")) {
			return repositoryName;
		}

		if ((repositoryName.equals("liferay-plugins-ee") ||
			 repositoryName.equals("liferay-portal-ee")) &&
			_upstreamBranchName.equals("master")) {

			repositoryName = repositoryName.replace("-ee", "");
		}

		if (repositoryName.contains("-private") &&
			!_upstreamBranchName.contains("-private")) {

			repositoryName = repositoryName.replace("-private", "");
		}

		return repositoryName;
	}

	protected String loadRepositoryUsername() {
		Remote upstreamRemote = getUpstreamRemote();

		String remoteURL = upstreamRemote.getRemoteURL();

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setUpstreamRemoteToPrivateRepository() {
		Remote upstreamRemote = getUpstreamRemote();

		addRemote(true, "upstream-temp", upstreamRemote.getRemoteURL());
	}

	protected void setUpstreamRemoteToPublicRepository() {
		Remote upstreamRemote = getUpstreamRemote();

		addRemote(true, "upstream-temp", upstreamRemote.getRemoteURL());
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
			shortNames.add(fullName.substring("refs/heads/".length()));
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

	}

	private static List<String> _getBuildPropertyAsList(String key) {
		try {
			return JenkinsResultsParserUtil.getBuildPropertyAsList(key);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get build property " + key, ioe);
		}
	}

	private boolean _deleteLocalGitBranches(String... branchNames) {
		StringBuilder sb = new StringBuilder();

		sb.append("git branch -D -f ");

		String joinedBranchNames = JenkinsResultsParserUtil.join(
			" ", branchNames);

		sb.append(joinedBranchNames);

		ExecutionResult executionResult = null;

		boolean exceptionThrown = false;

		try {
			executionResult = executeBashCommands(
				_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, sb.toString());
		}
		catch (RuntimeException re) {
			exceptionThrown = true;
		}

		if (exceptionThrown || (executionResult._exitValue != 0)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete local branches:", "\n    ",
					joinedBranchNames.replaceAll("\\s", "\n    "), "\n",
					executionResult.getStandardError()));

			return false;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleted local branches:", "\n    ",
				joinedBranchNames.replaceAll("\\s", "\n    ")));

		return true;
	}

	private boolean _deleteRemoteGitBranches(
		String remoteURL, String... branchNames) {

		StringBuilder sb = new StringBuilder();

		sb.append("git push --delete ");
		sb.append(remoteURL);
		sb.append(" ");

		String joinedBranchNames = JenkinsResultsParserUtil.join(
			" ", branchNames);

		sb.append(joinedBranchNames);

		ExecutionResult executionResult = null;

		boolean exceptionThrown = false;

		try {
			executionResult = executeBashCommands(
				_MAX_RETRIES, _RETRY_DELAY, 1000 * 60 * 10, sb.toString());
		}
		catch (RuntimeException re) {
			exceptionThrown = true;
		}

		if (exceptionThrown || (executionResult._exitValue != 0)) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Unable to delete ", remoteURL, " branches:\n    ",
					joinedBranchNames.replaceAll("\\s", "\n    "), "\n",
					executionResult.getStandardError()));

			return false;
		}

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Deleted ", remoteURL, " branches:", "\n    ",
				joinedBranchNames.replaceAll("\\s", "\n    ")));

		return true;
	}

	private String _getMergeBaseCommitSHA(LocalGitBranch... localGitBranches) {
		if (localGitBranches.length < 2) {
			throw new IllegalArgumentException(
				"Unable to perform merge-base with less than two branches");
		}

		StringBuilder sb = new StringBuilder("git merge-base");

		for (LocalGitBranch localGitBranch : localGitBranches) {
			sb.append(" ");
			sb.append(localGitBranch.getName());
		}

		ExecutionResult executionResult = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get merge base commit SHA\n",
					executionResult.getStandardError()));
		}

		return executionResult.getStandardOut();
	}

	private String _log(int num, File file, String format) {
		StringBuilder sb = new StringBuilder();

		sb.append("git log -n ");
		sb.append(num);
		sb.append(" --pretty=format:'");
		sb.append(format);
		sb.append("'");

		if (file != null) {
			sb.append(" ");

			try {
				sb.append(file.getCanonicalPath());
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		ExecutionResult result = executeBashCommands(
			5, 1000, 30 * 1000, sb.toString());

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to run: git log");
		}

		return result.getStandardOut();
	}

	private String _status() {
		String command = "git status";

		ExecutionResult result = executeBashCommands(
			_MAX_RETRIES, _RETRY_DELAY, _TIMEOUT, command);

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to run: git status");
		}

		return result.getStandardOut();
	}

	private static final int _DELETE_BRANCHES_BATCH_SIZE = 5;

	private static final int _MAX_RETRIES = 1;

	private static final long _RETRY_DELAY = 1000;

	private static final long _TIMEOUT = 30 * 1000;

	private static final Pattern _gitDirectoryPathPattern = Pattern.compile(
		"gitdir\\: (.*)\\s*");
	private static final Pattern _gitLogEntityPattern = Pattern.compile(
		"(?<sha>[0-9a-f]{40}) (?<message>.*)");
	private static final Pattern _gitLsRemotePattern = Pattern.compile(
		"(?<sha>[^\\s]{40}+)[\\s]+refs/heads/(?<name>[^\\s]+)");
	private static final List<String> _privateOnlyRepositoryNames =
		_getBuildPropertyAsList(
			"git.working.directory.private.only.repository.names");
	private static final List<String> _publicOnlyRepositoryNames =
		_getBuildPropertyAsList(
			"git.working.directory.public.only.repository.names");
	private static final Pattern _remoteURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"git@(?<hostname>[^:]+):(?<username>[^/]+)/",
			"(?<repositoryName>[^\\.]+)(.git)?"));

	private File _gitDirectory;
	private Set<String> _javaDirPaths;
	private final String _repositoryName;
	private final String _repositoryUsername;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}