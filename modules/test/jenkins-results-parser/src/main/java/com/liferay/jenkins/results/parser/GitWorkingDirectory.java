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

import java.nio.file.PathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

	public GitRemote addGitRemote(
		boolean force, String gitRemoteName, String remoteURL) {

		return addGitRemote(force, gitRemoteName, remoteURL, false);
	}

	public GitRemote addGitRemote(
		boolean force, String gitRemoteName, String remoteURL, boolean write) {

		if (gitRemoteExists(gitRemoteName)) {
			if (force) {
				removeGitRemote(getGitRemote(gitRemoteName));
			}
			else {
				throw new IllegalArgumentException(
					JenkinsResultsParserUtil.combine(
						"Git remote ", gitRemoteName, " already exists"));
			}
		}

		GitRemote newGitRemote = new GitRemote(this, gitRemoteName, remoteURL);

		_gitRemotes.put(gitRemoteName, newGitRemote);

		if (write) {
			String[] commands = {
				JenkinsResultsParserUtil.combine(
					"if [ \"$(git remote | grep ", gitRemoteName,
					")\" != \"\" ] ; then git remote remove ", gitRemoteName,
					" ; fi"),
				JenkinsResultsParserUtil.combine(
					"git remote add ", gitRemoteName, " ", remoteURL)
			};

			GitUtil.ExecutionResult executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				GitUtil.MILLIS_TIMEOUT, commands);

			if (executionResult.getExitValue() != 0) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to write Git remote ", gitRemoteName, "\n",
						executionResult.getStandardError()));
			}
		}

		return newGitRemote;
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

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			1000 * 60 * 10, sb.toString());

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
			catch (IOException ioException) {
				throw new RuntimeException(
					"Unable to read file " + headFile.getPath(), ioException);
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
				if (Objects.equals(branchName, getCurrentBranchName())) {
					return;
				}

				throw new RuntimeException(
					"Unable to checkout branch " + branchName);
			}
		}
	}

	public void checkoutUpstreamLocalGitBranch() {
		if (!Objects.equals(getCurrentBranchName(), getUpstreamBranchName())) {
			checkoutLocalGitBranch(getUpstreamLocalGitBranch());
		}
	}

	public void cherryPick(LocalGitCommit localGitCommit) {
		String cherryPickCommand = JenkinsResultsParserUtil.combine(
			"git cherry-pick " + localGitCommit.getSHA());

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, cherryPickCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to cherry pick commit ", localGitCommit.getSHA(),
					"\n", executionResult.getStandardError()));
		}
	}

	public void clean() {
		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			1000 * 60 * 10, "git clean -dfx");

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to clean Git repository\n",
					executionResult.getStandardError()));
		}
	}

	public void cleanTempBranches() {
		checkoutUpstreamLocalGitBranch();

		List<String> localGitBranchNames = getLocalGitBranchNames();

		List<String> tempBranchNames = new ArrayList<>(
			localGitBranchNames.size());

		String pattern = JenkinsResultsParserUtil.combine(
			".*", Pattern.quote(getUpstreamBranchName()), "-temp", ".*");

		for (String localGitBranchName : localGitBranchNames) {
			if (localGitBranchName.matches(pattern)) {
				tempBranchNames.add(localGitBranchName);
			}
		}

		if (!tempBranchNames.isEmpty()) {
			_deleteLocalGitBranches(tempBranchNames.toArray(new String[0]));
		}
	}

	public void commitFileToCurrentBranch(String fileName, String message) {
		String commitCommand = JenkinsResultsParserUtil.combine(
			"git commit -m \"", message, "\" ", fileName);

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, commitCommand);

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

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, commitCommand);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to commit staged files", "\n",
					executionResult.getStandardError()));
		}
	}

	public void configure(Map<String, String> configMap, String options) {
		String[] commands = new String[configMap.size()];

		int i = 0;

		for (Map.Entry<String, String> entry : configMap.entrySet()) {
			StringBuilder sb = new StringBuilder();

			sb.append("git config ");

			if ((options != null) && !options.isEmpty()) {
				sb.append(options);
				sb.append(" ");
			}

			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());

			commands[i] = sb.toString();

			i++;
		}

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, commands);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				"Unable to configure git repository.\n" +
					executionResult.getStandardError());
		}
	}

	public void configure(
		String configName, String configValue, String options) {

		Map<String, String> configMap = new HashMap<>();

		configMap.put(configName, configValue);

		configure(configMap, options);
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

			GitUtil.ExecutionResult executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				GitUtil.MILLIS_TIMEOUT, sb.toString());

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
			String senderUserName, String title)
		throws IOException {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("base", _upstreamBranchName);
		requestJSONObject.put("body", body);
		requestJSONObject.put(
			"head", senderUserName + ":" + pullRequestBranchName);
		requestJSONObject.put("title", title);

		String url = JenkinsResultsParserUtil.getGitHubApiUrl(
			_gitRepositoryName, receiverUserName, "pulls");

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
					_BRANCHES_DELETE_BATCH_SIZE)) {

			_deleteLocalGitBranches(branchNames.toArray(new String[0]));
		}
	}

	public void deleteRemoteGitBranch(RemoteGitBranch remoteGitBranch) {
		deleteRemoteGitBranches(Arrays.asList(remoteGitBranch));
	}

	public void deleteRemoteGitBranch(String branchName, GitRemote gitRemote) {
		deleteRemoteGitBranch(branchName, gitRemote.getRemoteURL());
	}

	public void deleteRemoteGitBranch(
		String branchName, RemoteGitRepository remoteGitRepository) {

		deleteRemoteGitBranch(branchName, remoteGitRepository.getRemoteURL());
	}

	public void deleteRemoteGitBranch(String branchName, String remoteURL) {
		deleteRemoteGitBranch(getRemoteGitBranch(branchName, remoteURL));
	}

	public void deleteRemoteGitBranches(
		List<RemoteGitBranch> remoteGitBranches) {

		Map<String, Set<String>> remoteURLGitBranchNameMap = new HashMap<>();

		for (RemoteGitBranch remoteGitBranch : remoteGitBranches) {
			RemoteGitRepository remoteGitRepository =
				remoteGitBranch.getRemoteGitRepository();

			String remoteURL = remoteGitRepository.getRemoteURL();

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
						_BRANCHES_DELETE_BATCH_SIZE)) {

				_deleteRemoteGitBranches(
					remoteURL, branchNames.toArray(new String[0]));
			}
		}
	}

	public void displayLog() {
		displayLog(1);
	}

	public void displayLog(int logNumber) {
		String command = "git log -n " + logNumber;

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 3,
			command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException("Unable to display log");
		}

		System.out.println();
		System.out.println(executionResult.getStandardOut());
		System.out.println();
	}

	public void fetch(GitRemote gitRemote) {
		fetch(gitRemote.getRemoteURL());
	}

	public void fetch(GitRemote gitRemote, boolean noTags) {
		fetch(gitRemote.getRemoteURL(), noTags);
	}

	public LocalGitBranch fetch(LocalGitBranch localGitBranch) {
		return fetch(null, localGitBranch);
	}

	public LocalGitBranch fetch(
		LocalGitBranch localGitBranch, boolean noTags,
		RemoteGitRef remoteGitRef) {

		if (remoteGitRef == null) {
			throw new IllegalArgumentException("Remote Git reference is null");
		}

		String remoteGitRefSHA = remoteGitRef.getSHA();

		if (localSHAExists(remoteGitRefSHA)) {
			System.out.println(
				remoteGitRefSHA + " already exists in Git repository");

			if (localGitBranch != null) {
				return createLocalGitBranch(
					localGitBranch.getName(), true, remoteGitRefSHA);
			}

			return null;
		}

		StringBuilder gitBranchesSHAReportStringBuilder = new StringBuilder();

		gitBranchesSHAReportStringBuilder.append(
			_getLocalGitBranchesSHAReport());
		gitBranchesSHAReportStringBuilder.append("\nRemote Git branch\n    ");
		gitBranchesSHAReportStringBuilder.append(remoteGitRef.getName());
		gitBranchesSHAReportStringBuilder.append(": ");
		gitBranchesSHAReportStringBuilder.append(remoteGitRef.getSHA());

		RemoteGitRepository remoteGitRepository =
			remoteGitRef.getRemoteGitRepository();

		String remoteURL = remoteGitRepository.getRemoteURL();

		if (JenkinsResultsParserUtil.isCINode() &&
			remoteURL.contains("github.com:liferay/")) {

			String gitHubDevRemoteURL = remoteURL.replace(
				"github.com:liferay/", "github-dev.liferay.com:liferay/");

			RemoteGitBranch gitHubDevRemoteGitBranch = getRemoteGitBranch(
				remoteGitRef.getName(), gitHubDevRemoteURL);

			if (gitHubDevRemoteGitBranch != null) {
				fetch(null, noTags, gitHubDevRemoteGitBranch);

				if (localSHAExists(remoteGitRefSHA)) {
					if (localGitBranch != null) {
						return createLocalGitBranch(
							localGitBranch.getName(), true, remoteGitRefSHA);
					}

					return null;
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git fetch -f ");

		if (noTags) {
			sb.append("--no-tags ");
		}
		else {
			sb.append("--tags ");
		}

		sb.append(remoteURL);

		String remoteGitRefName = remoteGitRef.getName();

		if ((remoteGitRefName != null) && !remoteGitRefName.isEmpty()) {
			sb.append(" ");
			sb.append(remoteGitRefName);

			if (localGitBranch != null) {
				sb.append(":");
				sb.append(localGitBranch.getName());
			}
		}

		long start = System.currentTimeMillis();

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 15, sb.toString());

		if (executionResult.getExitValue() != 0) {
			System.out.println(executionResult.getStandardOut());

			System.out.println(executionResult.getStandardError());

			System.out.println(gitBranchesSHAReportStringBuilder.toString());

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch remote branch ", remoteGitRefName, "\n",
					executionResult.getStandardError()));
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(duration));

		if (duration > (1000 * 60)) {
			System.out.println(gitBranchesSHAReportStringBuilder.toString());
		}

		if (localSHAExists(remoteGitRefSHA) && (localGitBranch != null)) {
			return createLocalGitBranch(
				localGitBranch.getName(), true, remoteGitRefSHA);
		}

		return null;
	}

	public LocalGitBranch fetch(
		LocalGitBranch localGitBranch, RemoteGitBranch remoteGitBranch) {

		return fetch(localGitBranch, true, remoteGitBranch);
	}

	public LocalGitBranch fetch(RemoteGitBranch remoteGitBranch) {
		return fetch(null, true, remoteGitBranch);
	}

	public void fetch(RemoteGitRepository remoteGitRepository) {
		fetch(remoteGitRepository.getRemoteURL());
	}

	public void fetch(RemoteGitRepository remoteGitRepository, boolean noTags) {
		fetch(remoteGitRepository.getRemoteURL(), noTags);
	}

	public void fetch(String remoteURL) {
		fetch(remoteURL, true);
	}

	public void fetch(String remoteURL, boolean noTags) {
		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		if (!GitUtil.isValidRemoteURL(remoteURL)) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		StringBuilder gitBranchesSHAReportStringBuilder = new StringBuilder();

		gitBranchesSHAReportStringBuilder.append(
			_getLocalGitBranchesSHAReport());
		gitBranchesSHAReportStringBuilder.append("\n");
		gitBranchesSHAReportStringBuilder.append(
			_getRemoteGitBranchesSHAReport(null, remoteURL));

		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f");

		if (noTags) {
			sb.append(" --no-tags");
		}
		else {
			sb.append(" --tags");
		}

		sb.append(" ");
		sb.append(remoteURL);
		sb.append(" refs/heads/*:refs/remotes/origin/*");

		long start = System.currentTimeMillis();

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 30, sb.toString());

		if (executionResult.getExitValue() != 0) {
			System.out.println(gitBranchesSHAReportStringBuilder.toString());

			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch from remote url ", remoteURL, "\n",
					executionResult.getStandardError()));
		}

		long duration = System.currentTimeMillis() - start;

		System.out.println(
			"Fetch completed in " +
				JenkinsResultsParserUtil.toDurationString(duration));

		if (duration > (1000 * 60)) {
			System.out.println(gitBranchesSHAReportStringBuilder.toString());
		}
	}

	public LocalGitBranch fetch(
		String branchName, LocalGitBranch localGitBranch) {

		if (localGitBranch == null) {
			throw new IllegalArgumentException("Local Git branch is null");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git fetch --progress -v -f --no-tags ");
		sb.append(String.valueOf(localGitBranch.getDirectory()));
		sb.append(" ");
		sb.append(localGitBranch.getName());

		if ((branchName != null) && !branchName.isEmpty()) {
			sb.append(":");
			sb.append(branchName);
		}

		long start = System.currentTimeMillis();

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 30, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to fetch from local Git repository ",
					String.valueOf(localGitBranch.getDirectory()), "\n",
					executionResult.getStandardError()));
		}

		String durationString = JenkinsResultsParserUtil.toDurationString(
			System.currentTimeMillis() - start);

		System.out.println("Fetch completed in " + durationString);

		return createLocalGitBranch(
			localGitBranch.getName(), true, localGitBranch.getSHA());
	}

	public void gc() {
		int retries = 0;

		while (true) {
			GitUtil.ExecutionResult executionResult = null;

			boolean exceptionThrown = false;

			try {
				executionResult = executeBashCommands(
					GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
					60 * 60 * 1000, "git gc");
			}
			catch (RuntimeException runtimeException) {
				exceptionThrown = true;
			}

			System.out.println(executionResult.getStandardOut());

			if (exceptionThrown || (executionResult.getExitValue() != 0)) {
				String standardError = executionResult.getStandardError();

				Matcher matcher = _badRefPattern.matcher(standardError);

				if (matcher.find()) {
					File badRefFile = new File(
						getWorkingDirectory(),
						".git/" + matcher.group("badRef"));

					badRefFile.delete();
				}

				if (retries > 1) {
					throw new RuntimeException(
						JenkinsResultsParserUtil.combine(
							"Unable to garbage collect Git\n", standardError));
				}
			}
			else {
				return;
			}

			retries++;

			JenkinsResultsParserUtil.sleep(GitUtil.MILLIS_RETRY_DELAY);

			System.out.println(
				JenkinsResultsParserUtil.combine(
					"Retry garbage collect Git in ",
					String.valueOf(GitUtil.MILLIS_RETRY_DELAY), "ms"));
		}
	}

	public List<String> getBranchNamesContainingSHA(String sha) {
		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 2,
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
			String branchName = line.trim();

			if (branchName.startsWith("* ")) {
				branchName = branchName.substring(2);
			}

			if (branchName.isEmpty()) {
				continue;
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

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, "git branch | grep \\*");

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

		currentBranchName = currentBranchName.trim();

		if (currentBranchName.isEmpty()) {
			return null;
		}

		return currentBranchName;
	}

	public LocalGitBranch getCurrentLocalGitBranch() {
		String currentBranchName = getCurrentBranchName();

		if (currentBranchName == null) {
			return null;
		}

		return getLocalGitBranch(currentBranchName);
	}

	public String getGitConfigProperty(String gitConfigPropertyName) {
		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, "git config " + gitConfigPropertyName);

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

	public GitRemote getGitRemote(String name) {
		if (name.equals("upstream")) {
			name = "upstream-temp";
		}

		if (_gitRemotes.isEmpty()) {
			getGitRemotes();
		}

		name = name.trim();

		return _gitRemotes.get(name);
	}

	public Set<String> getGitRemoteNames() {
		Map<String, GitRemote> gitRemotes = getGitRemotes();

		return gitRemotes.keySet();
	}

	public Map<String, GitRemote> getGitRemotes() {
		if (!_gitRemotes.isEmpty()) {
			return _gitRemotes;
		}

		int retries = 0;

		String standardOut = null;

		while (true) {
			if (retries > 1) {
				return _gitRemotes;
			}

			GitUtil.ExecutionResult executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				GitUtil.MILLIS_TIMEOUT, "git remote -v");

			if (executionResult.getExitValue() != 0) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get list of git remotes\n",
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

			sb.append("Found git remotes: ");

			for (int i = 0; i < lines.length; i = i + 2) {
				GitRemote gitRemote = new GitRemote(
					this, Arrays.copyOfRange(lines, i, i + 2));

				if (i > 0) {
					sb.append(", ");
				}

				sb.append(gitRemote.getName());

				_gitRemotes.put(gitRemote.getName(), gitRemote);
			}

			System.out.println(sb);
		}
		catch (Throwable t) {
			System.out.println("Unable to parse git remotes\n" + standardOut);

			throw t;
		}

		return _gitRemotes;
	}

	public String getGitRepositoryName() {
		return _gitRepositoryName;
	}

	public String getGitRepositoryUsername() {
		return _gitRepositoryUsername;
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
			if (!javaDirPath.contains(classPackagePath)) {
				continue;
			}

			File classFile = new File(javaDirPath, classFileName);

			if (!classFile.exists()) {
				continue;
			}

			String classFilePath = classFile.getPath();

			if (!classFilePath.contains(
					classPackagePath + "/" + classFileName)) {

				continue;
			}

			return classFile;
		}

		return null;
	}

	public LocalGitBranch getLocalGitBranch(String branchName) {
		return getLocalGitBranch(branchName, false);
	}

	public LocalGitBranch getLocalGitBranch(
		String branchName, boolean required) {

		if (branchName.equals(getUpstreamBranchName())) {
			return getUpstreamLocalGitBranch();
		}

		if (!localGitBranchExists(branchName)) {
			return null;
		}

		return _getLocalGitBranch(branchName, required);
	}

	public List<LocalGitBranch> getLocalGitBranches(String branchName) {
		String upstreamBranchName = getUpstreamBranchName();

		LocalGitRepository localGitRepository =
			GitRepositoryFactory.getLocalGitRepository(
				getGitRepositoryName(), upstreamBranchName);

		if (branchName != null) {
			try {
				return Arrays.asList(
					GitBranchFactory.newLocalGitBranch(
						localGitRepository, branchName,
						getLocalGitBranchSHA(branchName)));
			}
			catch (Exception exception) {
				if (!branchName.equals(upstreamBranchName)) {
					return null;
				}
			}
		}

		List<String> localGitBranchNames = getLocalGitBranchNames();

		List<LocalGitBranch> localGitBranches = new ArrayList<>(
			localGitBranchNames.size());

		Map<String, String> localGitBranchesShaMap =
			getLocalGitBranchesShaMap();

		for (String localGitBranchName : localGitBranchNames) {
			localGitBranches.add(
				GitBranchFactory.newLocalGitBranch(
					localGitRepository, localGitBranchName,
					localGitBranchesShaMap.get(localGitBranchName)));
		}

		return localGitBranches;
	}

	public String getLocalGitBranchSHA(String localGitBranchName) {
		if (localGitBranchName == null) {
			throw new IllegalArgumentException("Local branch name is null");
		}

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 2,
			"git rev-parse " + localGitBranchName);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to determine SHA of branch ", localGitBranchName,
					"\n", executionResult.getStandardError()));
		}

		return executionResult.getStandardOut();
	}

	public List<File> getModifiedDirsList(
		boolean checkUnstagedFiles, List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers) {

		return getModifiedDirsList(
			checkUnstagedFiles, excludesPathMatchers, includesPathMatchers,
			getWorkingDirectory());
	}

	public List<File> getModifiedDirsList(
		boolean checkUnstagedFiles, List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers, File rootDirectory) {

		List<File> subdirectories = getSubdirectoriesContainingFiles(
			1, getModifiedFilesList(checkUnstagedFiles, null, null),
			rootDirectory);

		return JenkinsResultsParserUtil.getIncludedFiles(
			excludesPathMatchers, includesPathMatchers, subdirectories);
	}

	public List<File> getModifiedFilesList() {
		return getModifiedFilesList(false, null, null);
	}

	public List<File> getModifiedFilesList(boolean checkUnstagedFiles) {
		return getModifiedFilesList(checkUnstagedFiles, null, null);
	}

	public List<File> getModifiedFilesList(
		boolean checkUnstagedFiles, List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers) {

		LocalGitBranch currentLocalGitBranch = getCurrentLocalGitBranch();

		if (currentLocalGitBranch == null) {
			throw new RuntimeException(
				"Unable to determine the current branch");
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git diff --diff-filter=ADMR --name-only ");

		sb.append(
			_getMergeBaseCommitSHA(
				currentLocalGitBranch,
				getLocalGitBranch(getUpstreamBranchName(), true)));

		if (!checkUnstagedFiles) {
			sb.append(" ");
			sb.append(currentLocalGitBranch.getSHA());
		}

		String gitDiffCommandString = sb.toString();

		List<File> modifiedFiles = _modifiedFilesMap.get(gitDiffCommandString);

		if (modifiedFiles == null) {
			GitUtil.ExecutionResult executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				GitUtil.MILLIS_TIMEOUT, gitDiffCommandString);

			if (executionResult.getExitValue() == 1) {
				return Collections.emptyList();
			}

			if (executionResult.getExitValue() != 0) {
				throw new RuntimeException(
					"Unable to get current branch modified files\n" +
						executionResult.getStandardError());
			}

			modifiedFiles = new ArrayList<>();

			String gitDiffOutput = executionResult.getStandardOut();

			for (String line : gitDiffOutput.split("\n")) {
				modifiedFiles.add(new File(_workingDirectory, line));
			}

			_modifiedFilesMap.put(gitDiffCommandString, modifiedFiles);
		}

		return JenkinsResultsParserUtil.getIncludedFiles(
			excludesPathMatchers, includesPathMatchers, modifiedFiles);
	}

	public List<File> getModifiedFilesList(
		List<PathMatcher> excludesPathMatchers,
		List<PathMatcher> includesPathMatchers) {

		return getModifiedFilesList(
			false, excludesPathMatchers, includesPathMatchers);
	}

	public LocalGitBranch getRebasedLocalGitBranch(PullRequest pullRequest) {
		return getRebasedLocalGitBranch(
			pullRequest.getLocalSenderBranchName(),
			pullRequest.getSenderBranchName(), pullRequest.getSenderRemoteURL(),
			pullRequest.getSenderSHA(), pullRequest.getUpstreamBranchName(),
			pullRequest.getLiferayRemoteBranchSHA());
	}

	public LocalGitBranch getRebasedLocalGitBranch(
		String rebasedLocalGitBranchName, String senderBranchName,
		String senderRemoteURL, String senderSHA, String upstreamBranchName,
		String upstreamBranchSHA) {

		String currentBranchName = getCurrentBranchName();

		LocalGitBranch tempLocalGitBranch = null;

		try {
			if ((currentBranchName == null) ||
				currentBranchName.equals(rebasedLocalGitBranchName)) {

				tempLocalGitBranch = createLocalGitBranch(
					"temp-" + System.currentTimeMillis());

				checkoutLocalGitBranch(tempLocalGitBranch);
			}

			RemoteGitBranch senderRemoteGitBranch = getRemoteGitBranch(
				senderBranchName, senderRemoteURL, true);

			fetch(senderRemoteGitBranch);

			LocalGitBranch rebasedLocalGitBranch = createLocalGitBranch(
				rebasedLocalGitBranchName, true, senderSHA);

			RemoteGitBranch upstreamRemoteGitBranch = getRemoteGitBranch(
				upstreamBranchName, getUpstreamGitRemote(), true);

			if (upstreamBranchSHA == null) {
				upstreamBranchSHA = upstreamRemoteGitBranch.getSHA();
			}

			if (!localSHAExists(upstreamBranchSHA)) {
				fetch(upstreamRemoteGitBranch);
			}

			LocalGitBranch upstreamLocalGitBranch = createLocalGitBranch(
				upstreamRemoteGitBranch.getName(), true, upstreamBranchSHA);

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

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, GitRemote gitRemote) {

		return getRemoteGitBranch(
			remoteGitBranchName, gitRemote.getRemoteURL(), false);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, GitRemote gitRemote, boolean required) {

		return getRemoteGitBranch(
			remoteGitBranchName, gitRemote.getRemoteURL(), required);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, RemoteGitRepository remoteGitRepository) {

		return getRemoteGitBranch(
			remoteGitBranchName, remoteGitRepository.getRemoteURL(), false);
	}

	public RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, RemoteGitRepository remoteGitRepository,
		boolean required) {

		return getRemoteGitBranch(
			remoteGitBranchName, remoteGitRepository.getRemoteURL(), required);
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

	public List<RemoteGitBranch> getRemoteGitBranches(GitRemote gitRemote) {
		return getRemoteGitBranches(null, gitRemote.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		RemoteGitRepository remoteGitRepository) {

		return getRemoteGitBranches(null, remoteGitRepository.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(String remoteURL) {
		return getRemoteGitBranches(null, remoteURL);
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, GitRemote gitRemote) {

		return getRemoteGitBranches(
			remoteGitBranchName, gitRemote.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, RemoteGitRepository remoteGitRepository) {

		return getRemoteGitBranches(
			remoteGitBranchName, remoteGitRepository.getRemoteURL());
	}

	public List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, String remoteURL) {

		return GitUtil.getRemoteGitBranches(
			remoteGitBranchName, _workingDirectory, remoteURL);
	}

	public List<String> getRemoteGitBranchNames(GitRemote gitRemote) {
		return getRemoteGitBranchNames(gitRemote.getRemoteURL());
	}

	public List<String> getRemoteGitBranchNames(
		RemoteGitRepository remoteGitRepository) {

		return getRemoteGitBranchNames(remoteGitRepository.getRemoteURL());
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
		String remoteGitBranchName, GitRemote gitRemote) {

		return getRemoteGitBranchSHA(
			remoteGitBranchName, gitRemote.getRemoteURL());
	}

	public String getRemoteGitBranchSHA(
		String remoteGitBranchName, RemoteGitRepository remoteGitRepository) {

		return getRemoteGitBranchSHA(
			remoteGitBranchName, remoteGitRepository.getRemoteURL());
	}

	public String getRemoteGitBranchSHA(
		String remoteGitBranchName, String remoteURL) {

		if (remoteGitBranchName == null) {
			throw new IllegalArgumentException("Remote branch name is null");
		}

		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		if (!GitUtil.isValidRemoteURL(remoteURL)) {
			throw new IllegalArgumentException(
				"Invalid remote url " + remoteURL);
		}

		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote -h ", remoteURL, " ", remoteGitBranchName);

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			3, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote branch SHA ", remoteURL, " ",
					remoteGitBranchName, "\n",
					executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		for (String line : input.split("\n")) {
			Matcher matcher = GitRemote.gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				return matcher.group("sha");
			}
		}

		return null;
	}

	public String getUpstreamBranchName() {
		return _upstreamBranchName;
	}

	public GitRemote getUpstreamGitRemote() {
		Map<String, GitRemote> gitRemotes = getGitRemotes();

		GitRemote gitRemote = gitRemotes.get("upstream");

		if (gitRemote == null) {
			gitRemote = addGitRemote(
				true, "upstream",
				JenkinsResultsParserUtil.combine(
					"git@github.com:liferay/", getGitRepositoryName(), ".git"));
		}

		return gitRemote;
	}

	public LocalGitBranch getUpstreamLocalGitBranch() {
		String upstreamBranchName = getUpstreamBranchName();

		if (localGitBranchExists(upstreamBranchName)) {
			return _getLocalGitBranch(upstreamBranchName, true);
		}

		RemoteGitBranch upstreamRemoteGitBranch = getRemoteGitBranch(
			upstreamBranchName, getGitRemote("upstream"));

		fetch(upstreamRemoteGitBranch);

		String currentBranchName = getCurrentBranchName();

		if (currentBranchName == null) {
			List<String> localGitBranchNames = getLocalGitBranchNames();

			List<LocalGitBranch> localGitBranches = getLocalGitBranches(
				localGitBranchNames.get(0));

			checkoutLocalGitBranch(localGitBranches.get(0));
		}

		return createLocalGitBranch(
			upstreamBranchName, true, upstreamRemoteGitBranch.getSHA());
	}

	public RemoteGitBranch getUpstreamRemoteGitBranch() {
		return getRemoteGitBranch(
			getUpstreamBranchName(),
			JenkinsResultsParserUtil.combine(
				"git@github.com:liferay/", getGitRepositoryName()));
	}

	public File getWorkingDirectory() {
		return _workingDirectory;
	}

	public boolean gitRemoteExists(String gitRemoteName) {
		if (getGitRemote(gitRemoteName) != null) {
			return true;
		}

		return false;
	}

	public boolean isRemoteGitRepositoryAlive(String remoteURL) {
		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote -h ", remoteURL, " HEAD");

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			System.out.println("Unable to connect to " + remoteURL);

			return false;
		}

		System.out.println(remoteURL + " is alive");

		return true;
	}

	public boolean localGitBranchExists(String branchName) {
		waitForIndexLock();

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT,
			"git branch | grep [\\s\\*]*" + branchName + "$");

		if (executionResult.getExitValue() == 0) {
			String standardOut = executionResult.getStandardOut();

			if (standardOut.isEmpty()) {
				return false;
			}

			return true;
		}

		return false;
	}

	public boolean localSHAExists(String sha) {
		String command = "git cat-file -t " + sha;

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 3,
			command);

		if (executionResult.getExitValue() == 0) {
			return true;
		}

		return false;
	}

	public List<LocalGitCommit> log(int num) {
		return _log(0, num, null, null);
	}

	public List<LocalGitCommit> log(int num, File file) {
		return _log(0, num, file, null);
	}

	public List<LocalGitCommit> log(int start, int num) {
		return _log(start, num, null, null);
	}

	public List<LocalGitCommit> log(int start, int num, String sha) {
		return _log(start, num, null, sha);
	}

	public RemoteGitBranch pushToRemoteGitRepository(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, GitRemote gitRemote) {

		return pushToRemoteGitRepository(
			force, localGitBranch, remoteGitBranchName,
			gitRemote.getRemoteURL());
	}

	public RemoteGitBranch pushToRemoteGitRepository(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, RemoteGitRepository remoteGitRepository) {

		return pushToRemoteGitRepository(
			force, localGitBranch, remoteGitBranchName,
			remoteGitRepository.getRemoteURL());
	}

	public RemoteGitBranch pushToRemoteGitRepository(
		boolean force, LocalGitBranch localGitBranch,
		String remoteGitBranchName, String remoteURL) {

		if (localGitBranch == null) {
			throw new IllegalArgumentException("Local Git branch is null");
		}

		if (remoteURL == null) {
			throw new IllegalArgumentException("Remote URL is null");
		}

		if (!GitUtil.isValidRemoteURL(remoteURL)) {
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
			GitUtil.ExecutionResult executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				1000 * 60 * 10, sb.toString());

			if (executionResult.getExitValue() != 0) {
				return null;
			}
		}
		catch (RuntimeException runtimeException) {
			runtimeException.printStackTrace();

			return null;
		}

		return (RemoteGitBranch)GitBranchFactory.newRemoteGitRef(
			GitRepositoryFactory.getRemoteGitRepository(remoteURL),
			remoteGitBranchName, localGitBranch.getSHA(), "heads");
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

		checkoutLocalGitBranch(baseLocalGitBranch);

		reset("--hard " + baseLocalGitBranch.getSHA());

		String rebaseCommand = JenkinsResultsParserUtil.combine(
			"git rebase ", baseLocalGitBranch.getName(), " ",
			localGitBranch.getName());

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			1000 * 60 * 10, rebaseCommand);

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
		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, "git rebase --abort");

		if (!ignoreFailure && (executionResult.getExitValue() != 0)) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to abort rebase\n",
					executionResult.getStandardError()));
		}
	}

	public boolean remoteGitBranchExists(
		String branchName, GitRemote gitRemote) {

		return remoteGitBranchExists(branchName, gitRemote.getRemoteURL());
	}

	public boolean remoteGitBranchExists(
		String branchName, RemoteGitRepository remoteGitRepository) {

		return remoteGitBranchExists(
			branchName, remoteGitRepository.getRemoteURL());
	}

	public boolean remoteGitBranchExists(String branchName, String remoteURL) {
		if (getRemoteGitBranch(branchName, remoteURL) != null) {
			return true;
		}

		return false;
	}

	public void removeGitRemote(GitRemote gitRemote) {
		if ((gitRemote == null) || !gitRemoteExists(gitRemote.getName())) {
			return;
		}

		_gitRemotes.remove(gitRemote.getName());
	}

	public void removeGitRemotes(List<GitRemote> gitRemotes) {
		for (GitRemote gitRemote : gitRemotes) {
			removeGitRemote(gitRemote);
		}
	}

	public void reset(String options) {
		String command = "git reset " + options;

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			2, GitUtil.MILLIS_RETRY_DELAY, 1000 * 60 * 5, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to reset\n", executionResult.getStandardError()));
		}
	}

	public void stageFileInCurrentLocalGitBranch(String fileName) {
		String command = "git stage " + fileName;

		GitUtil.ExecutionResult result = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, command);

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
			catch (RuntimeException runtimeException) {
				runtimeException.printStackTrace();

				JenkinsResultsParserUtil.sleep(1000);
			}
		}

		throw new RuntimeException("Unable to run: git status");
	}

	protected GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		this(upstreamBranchName, workingDirectoryPath, null);
	}

	protected GitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String gitRepositoryName)
		throws IOException {

		setWorkingDirectory(workingDirectoryPath);

		_upstreamBranchName = upstreamBranchName;

		GitRemote upstreamTempGitRemote = getGitRemote("upstream-temp");

		if (upstreamTempGitRemote != null) {
			removeGitRemote(upstreamTempGitRemote);
		}

		waitForIndexLock();

		if ((gitRepositoryName == null) || gitRepositoryName.equals("")) {
			gitRepositoryName = loadGitRepositoryName();
		}

		_gitRepositoryName = gitRepositoryName;

		if (_publicOnlyGitRepositoryNames.contains(_gitRepositoryName)) {
			setUpstreamGitRemoteToPublicGitRepository();
		}
		else {
			if (_privateOnlyGitRepositoryNames.contains(_gitRepositoryName)) {
				setUpstreamGitRemoteToPrivateGitRepository();
			}
			else {
				if (upstreamBranchName.equals("master")) {
					setUpstreamGitRemoteToPublicGitRepository();
				}
				else {
					setUpstreamGitRemoteToPrivateGitRepository();
				}
			}
		}

		_gitRepositoryUsername = loadGitRepositoryUsername();
	}

	protected GitUtil.ExecutionResult executeBashCommands(
		int maxRetries, long retryDelay, long timeout, String... commands) {

		return GitUtil.executeBashCommands(
			maxRetries, retryDelay, timeout, _workingDirectory, commands);
	}

	protected Map<String, String> getLocalGitBranchesShaMap() {
		File workingDirectory = getWorkingDirectory();

		String command = JenkinsResultsParserUtil.combine(
			"git ls-remote -h ",
			JenkinsResultsParserUtil.getCanonicalPath(workingDirectory));

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			1000 * 60 * 10, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get local Git branch SHAs\n",
					executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		String[] inputLines = input.split("\n");

		Map<String, String> localGitBranchesShaMap = new HashMap<>();

		for (String line : inputLines) {
			Matcher matcher = GitRemote.gitLsRemotePattern.matcher(line);

			if (matcher.find()) {
				localGitBranchesShaMap.put(
					matcher.group("name"), matcher.group("sha"));
			}
		}

		return localGitBranchesShaMap;
	}

	protected List<String> getLocalGitBranchNames() {
		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT,
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

	protected LocalGitCommit getLocalGitCommit(String gitLogEntity) {
		Matcher matcher = _gitLogEntityPattern.matcher(gitLogEntity);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("Unable to find Git SHA");
		}

		int unixTimestamp = Integer.valueOf(matcher.group("commitTime"));

		long epochTimestamp = (long)unixTimestamp * 1000;

		return GitCommitFactory.newLocalGitCommit(
			this, matcher.group("message"), matcher.group("sha"),
			epochTimestamp);
	}

	protected File getRealGitDirectory(File gitFile) {
		String gitFileContent = null;

		try {
			gitFileContent = JenkinsResultsParserUtil.read(gitFile);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Real .git directory could not be found", ioException);
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

	protected List<File> getSubdirectoriesContainingFiles(
		int depth, List<File> files, File rootDirectory) {

		List<File> subdirectories = JenkinsResultsParserUtil.getSubdirectories(
			depth, rootDirectory);

		return JenkinsResultsParserUtil.getDirectoriesContainingFiles(
			subdirectories, files);
	}

	protected String loadGitRepositoryName() {
		GitRemote upstreamGitRemote = getUpstreamGitRemote();

		String remoteURL = upstreamGitRemote.getRemoteURL();

		int x = remoteURL.lastIndexOf("/") + 1;

		int y = remoteURL.indexOf(".git");

		if (y == -1) {
			y = remoteURL.length();
		}

		String gitRepositoryName = remoteURL.substring(x, y);

		if (gitRepositoryName.equals("liferay-jenkins-tools-private")) {
			return gitRepositoryName;
		}

		if ((gitRepositoryName.equals("liferay-plugins-ee") ||
			 gitRepositoryName.equals("liferay-portal-ee")) &&
			_upstreamBranchName.equals("master")) {

			gitRepositoryName = gitRepositoryName.replace("-ee", "");
		}

		if (gitRepositoryName.contains("-private") &&
			!_upstreamBranchName.contains("-private")) {

			gitRepositoryName = gitRepositoryName.replace("-private", "");
		}

		return gitRepositoryName;
	}

	protected String loadGitRepositoryUsername() {
		GitRemote upstreamGitRemote = getUpstreamGitRemote();

		String remoteURL = upstreamGitRemote.getRemoteURL();

		int x = remoteURL.indexOf(":") + 1;
		int y = remoteURL.indexOf("/");

		return remoteURL.substring(x, y);
	}

	protected void setUpstreamGitRemoteToPrivateGitRepository() {
		GitRemote gitRemote = getUpstreamGitRemote();

		String privateGitRepositoryName = GitUtil.getPrivateRepositoryName(
			getGitRepositoryName());

		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github.com", privateGitRepositoryName,
				gitRemote.getUsername());

		addGitRemote(true, "upstream-temp", remoteGitRepository.getRemoteURL());
	}

	protected void setUpstreamGitRemoteToPublicGitRepository() {
		GitRemote gitRemote = getUpstreamGitRemote();

		String publicGitRepositoryName = GitUtil.getPublicRepositoryName(
			getGitRepositoryName());

		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				"github.com", publicGitRepositoryName, gitRemote.getUsername());

		addGitRemote(true, "upstream-temp", remoteGitRepository.getRemoteURL());
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

	private static List<String> _getBuildPropertyAsList(String key) {
		try {
			return JenkinsResultsParserUtil.getBuildPropertyAsList(true, key);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build property " + key, ioException);
		}
	}

	private boolean _deleteLocalGitBranches(String... branchNames) {
		StringBuilder sb = new StringBuilder();

		sb.append("git branch -D -f ");

		String joinedBranchNames = JenkinsResultsParserUtil.join(
			" ", branchNames);

		sb.append(joinedBranchNames);

		GitUtil.ExecutionResult executionResult = null;

		boolean exceptionThrown = false;

		try {
			executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				1000 * 60 * 10, sb.toString());
		}
		catch (RuntimeException runtimeException) {
			exceptionThrown = true;
		}

		if (exceptionThrown || (executionResult.getExitValue() != 0)) {
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

		GitUtil.ExecutionResult executionResult = null;

		boolean exceptionThrown = false;

		try {
			executionResult = executeBashCommands(
				GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
				1000 * 60 * 10, sb.toString());
		}
		catch (RuntimeException runtimeException) {
			exceptionThrown = true;
		}

		if (exceptionThrown || (executionResult.getExitValue() != 0)) {
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

	private LocalGitBranch _getLocalGitBranch(
		String branchName, boolean required) {

		List<LocalGitBranch> localGitBranches = getLocalGitBranches(branchName);

		if ((localGitBranches != null) && !localGitBranches.isEmpty()) {
			return localGitBranches.get(0);
		}

		if (required) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find required branch ", branchName, " from ",
					String.valueOf(getWorkingDirectory())));
		}

		return null;
	}

	private String _getLocalGitBranchesSHAReport() {
		StringBuilder sb = new StringBuilder("Local Git branches");

		for (LocalGitBranch localGitBranch : getLocalGitBranches(null)) {
			sb.append("\n    ");

			sb.append(localGitBranch.getName());
			sb.append(": ");
			sb.append(localGitBranch.getSHA());
		}

		return sb.toString();
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

		GitUtil.ExecutionResult executionResult = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, sb.toString());

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get merge base commit SHA\n",
					executionResult.getStandardError()));
		}

		return executionResult.getStandardOut();
	}

	private String _getRemoteGitBranchesSHAReport(
		String remoteGitBranchName, String remoteURL) {

		StringBuilder sb = new StringBuilder("Remote Git branches");

		for (RemoteGitBranch remoteGitBranch :
				getRemoteGitBranches(remoteGitBranchName, remoteURL)) {

			sb.append("\n    ");

			sb.append(remoteGitBranch.getName());
			sb.append(": ");
			sb.append(remoteGitBranch.getSHA());
		}

		return sb.toString();
	}

	private List<LocalGitCommit> _log(
		int start, int num, File file, String sha) {

		List<LocalGitCommit> localGitCommits = new ArrayList<>(num);

		String gitLog = _log(start, num, file, "%H %ct %s", sha);

		gitLog = gitLog.replaceAll("Finished executing Bash commands.", "");

		String[] gitLogEntities = gitLog.split("\n");

		for (String gitLogEntity : gitLogEntities) {
			localGitCommits.add(getLocalGitCommit(gitLogEntity));
		}

		return localGitCommits;
	}

	private String _log(
		int start, int num, File file, String format, String sha) {

		if ((sha == null) || sha.isEmpty()) {
			sha = "HEAD";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("git log ");

		if (file != null) {
			sb.append("-n ");
			sb.append(num);
			sb.append(" ");
		}
		else {
			sb.append(sha);
			sb.append("~");
			sb.append(start + num);
			sb.append("..");
			sb.append(sha);
			sb.append("~");
			sb.append(start);
		}

		sb.append(" --pretty=format:'");
		sb.append(format);
		sb.append("'");

		if (file != null) {
			sb.append(" ");
			sb.append(JenkinsResultsParserUtil.getCanonicalPath(file));
		}

		GitUtil.ExecutionResult result = executeBashCommands(
			5, 1000, 30 * 1000, sb.toString());

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to run: git log");
		}

		return result.getStandardOut();
	}

	private String _status() {
		String command = "git status";

		GitUtil.ExecutionResult result = executeBashCommands(
			GitUtil.RETRIES_SIZE_MAX, GitUtil.MILLIS_RETRY_DELAY,
			GitUtil.MILLIS_TIMEOUT, command);

		if (result.getExitValue() != 0) {
			throw new RuntimeException("Unable to run: git status");
		}

		return result.getStandardOut();
	}

	private static final int _BRANCHES_DELETE_BATCH_SIZE = 5;

	private static final Pattern _badRefPattern = Pattern.compile(
		"fatal: bad object (?<badRef>.+/HEAD)");
	private static final Pattern _gitDirectoryPathPattern = Pattern.compile(
		"gitdir\\: (.*)\\s*");
	private static final Pattern _gitLogEntityPattern = Pattern.compile(
		"(?<sha>[0-9a-f]{40}) (?<commitTime>\\d+) (?<message>.*)");
	private static final Map<String, List<File>> _modifiedFilesMap =
		new HashMap<>();
	private static final List<String> _privateOnlyGitRepositoryNames =
		_getBuildPropertyAsList(
			"git.working.directory.private.only.repository.names");
	private static final List<String> _publicOnlyGitRepositoryNames =
		_getBuildPropertyAsList(
			"git.working.directory.public.only.repository.names");

	private File _gitDirectory;
	private final Map<String, GitRemote> _gitRemotes = new HashMap<>();
	private final String _gitRepositoryName;
	private final String _gitRepositoryUsername;
	private Set<String> _javaDirPaths;
	private final String _upstreamBranchName;
	private File _workingDirectory;

}