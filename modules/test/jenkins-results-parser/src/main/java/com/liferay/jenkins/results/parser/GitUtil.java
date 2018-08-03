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
	
	public static RemoteGitBranch getRemoteGitBranch(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		List<RemoteGitBranch> remoteGitBranches = getRemoteGitBranches(
			remoteGitBranchName, workingDirectory, remoteURL);

		return remoteGitBranches.get(0);
	}

	public static List<RemoteGitBranch> getRemoteGitBranches(
		String remoteGitBranchName, File workingDirectory, String remoteURL) {

		Matcher remoteURLMatcher = BaseGitRemote.remoteURLPattern.matcher(
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
			Pattern gitLsRemotePattern = BaseGitRemote.gitLsRemotePattern;

			Matcher gitLsRemoteMatcher = gitLsRemotePattern.matcher(line);

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
	
	protected static ExecutionResult executeBashCommands(
		int maxRetries, long retryDelay, long timeout, File workingDirectory,
		String... commands) {

		Process process = null;

		int retries = 0;

		while (retries < maxRetries) {
			try {
				retries++;

				process = JenkinsResultsParserUtil.executeBashCommands(
					true, workingDirectory, timeout, commands);

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

	public static final int MAX_RETRIES = 1;
	public static final long RETRY_DELAY = 1000;
	public static final long TIMEOUT = 30 * 1000;

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
}
