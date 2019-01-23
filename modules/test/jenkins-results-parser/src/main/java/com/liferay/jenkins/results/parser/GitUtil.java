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
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class GitUtil {

	public static final int MAX_RETRIES = 1;

	public static final long RETRY_DELAY = 1000;

	public static final long TIMEOUT = 30 * 1000;

	public static RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		RemoteGitRef remoteGitRef = getRemoteGitRef(
			remoteGitBranchName, workingDirectory, remoteURL);

		if (!(remoteGitRef instanceof RemoteGitBranch)) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find remote Git branch ", remoteGitBranchName,
					" on remote URL ", remoteURL));
		}

		return (RemoteGitBranch)remoteGitRef;
	}

	public static List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		List<RemoteGitBranch> remoteGitBranches = new ArrayList<>();

		for (RemoteGitRef remoteGitRef :
				getRemoteGitRefs(
					remoteGitBranchName, workingDirectory, remoteURL)) {

			if (remoteGitRef instanceof RemoteGitBranch) {
				remoteGitBranches.add((RemoteGitBranch)remoteGitRef);
			}
		}

		return remoteGitBranches;
	}

	public static RemoteGitRef getRemoteGitRef(String gitHubURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid GitHub URL " + gitHubURL);
		}

		String remoteGitRepositoryURL = JenkinsResultsParserUtil.combine(
			"git@github.com:", matcher.group("username"), "/",
			matcher.group("gitRepositoryName"), ".git");

		return getRemoteGitRef(
			matcher.group("refName"), new File("."), remoteGitRepositoryURL);
	}

	public static RemoteGitRef getRemoteGitRef(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		List<RemoteGitRef> remoteGitRefs = null;

		if (remoteURL.contains(_GITHUB_CACHE_PROXY_HOSTNAME)) {
			List<String> usedGitHubCacheHostnames = new ArrayList<>(3);

			while ((usedGitHubCacheHostnames.size() < 3) &&
				   ((remoteGitRefs == null) || remoteGitRefs.isEmpty())) {

				String gitHubCacheHostname =
					JenkinsResultsParserUtil.getRandomGitHubCacheHostname(
						usedGitHubCacheHostnames);

				String gitHubCacheRemoteURL = remoteURL.replace(
					_GITHUB_CACHE_PROXY_HOSTNAME, gitHubCacheHostname);

				try {
					remoteGitRefs = getRemoteGitRefs(
						remoteGitBranchName, workingDirectory,
						gitHubCacheRemoteURL);
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				usedGitHubCacheHostnames.add(gitHubCacheHostname);
			}
		}
		else {
			remoteGitRefs = getRemoteGitRefs(
				remoteGitBranchName, workingDirectory, remoteURL);
		}

		if ((remoteGitRefs == null) || remoteGitRefs.isEmpty()) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to find remote Git ref ", remoteGitBranchName,
					" on remote URL ", remoteURL));
		}

		return remoteGitRefs.get(0);
	}

	public static List<RemoteGitRef> getRemoteGitRefs(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		Matcher remoteURLMatcher = GitRemote.remoteURLPattern.matcher(
			remoteURL);

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
			GitUtil.MAX_RETRIES, GitUtil.RETRY_DELAY, 1000 * 60 * 10,
			workingDirectory, command);

		if (executionResult.getExitValue() != 0) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to get remote refs from ", remoteURL, "\n",
					executionResult.getStandardError()));
		}

		String input = executionResult.getStandardOut();

		List<RemoteGitRef> remoteGitRefs = new ArrayList<>();

		RemoteGitRepository remoteGitRepository =
			GitRepositoryFactory.getRemoteGitRepository(
				remoteURLMatcher.group("hostname"),
				remoteURLMatcher.group("gitRepositoryName"),
				remoteURLMatcher.group("username"));

		for (String line : input.split("\n")) {
			Pattern gitLsRemotePattern = GitRemote.gitLsRemotePattern;

			Matcher gitLsRemoteMatcher = gitLsRemotePattern.matcher(line);

			if (!gitLsRemoteMatcher.find()) {
				continue;
			}

			remoteGitRefs.add(
				GitBranchFactory.newRemoteGitRef(
					remoteGitRepository, gitLsRemoteMatcher.group("name"),
					gitLsRemoteMatcher.group("sha"),
					gitLsRemoteMatcher.group("type")));
		}

		System.out.println(
			"getRemoteGitRefs found " + remoteGitRefs.size() + " refs at " +
				remoteURL + ".");

		return remoteGitRefs;
	}

	public static boolean isValidGitHubRefURL(String gitHubURL) {
		Matcher matcher = _gitHubRefURLPattern.matcher(gitHubURL);

		if (!matcher.find()) {
			return false;
		}

		return true;
	}

	public static class ExecutionResult {

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

	protected static ExecutionResult executeBashCommands(
		int maxRetries, long retryDelay, long timeout, File workingDirectory,
		String... commands) {

		Process process = null;

		int retries = 0;
		List<String> usedGitHubCacheHostnames = new ArrayList<>(maxRetries);

		while (retries < maxRetries) {
			String[] modifiedCommands = Arrays.copyOf(
				commands, commands.length);

			String gitHubCacheHostname =
				JenkinsResultsParserUtil.getRandomGitHubCacheHostname(
					usedGitHubCacheHostnames);

			for (int i = 0; i < modifiedCommands.length; i++) {
				modifiedCommands[i] = modifiedCommands[i].replace(
					_GITHUB_CACHE_PROXY_HOSTNAME, gitHubCacheHostname);
			}

			try {
				retries++;

				process = JenkinsResultsParserUtil.executeBashCommands(
					true, workingDirectory, timeout, modifiedCommands);

				break;
			}
			catch (IOException | TimeoutException e) {
				if (retries == maxRetries) {
					throw new RuntimeException(
						"Unable to execute bash commands: " +
							Arrays.toString(commands),
						e);
				}

				usedGitHubCacheHostnames.add(gitHubCacheHostname);

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

	private static final String _GITHUB_CACHE_PROXY_HOSTNAME =
		"github-dev.liferay.com";

	private static final Pattern _gitHubRefURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<username>[^/]+)/",
			"(?<gitRepositoryName>[^/]+)/tree/(?<refName>[^/]+)"));

}