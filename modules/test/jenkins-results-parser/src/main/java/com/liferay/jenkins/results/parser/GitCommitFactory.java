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

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitCommitFactory {

	public static GitHubRemoteGitCommit newGitHubRemoteGitCommit(
		String gitHubUsername, String gitRepositoryName, String sha) {

		String gitHubCommitURL = _getGitHubCommitURL(
			gitHubUsername, gitRepositoryName, sha);

		if (_gitHubRemoteGitCommits.containsKey(gitHubCommitURL)) {
			return _gitHubRemoteGitCommits.get(gitHubCommitURL);
		}

		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				gitHubCommitURL);

			JSONObject commitJSONObject = jsonObject.getJSONObject("commit");

			String message = commitJSONObject.getString("message");

			JSONObject committerJSONObject = commitJSONObject.getJSONObject(
				"committer");

			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");

				Date date = simpleDateFormat.parse(
					committerJSONObject.getString("date"));

				GitHubRemoteGitCommit remoteGitCommit =
					new GitHubRemoteGitCommit(
						gitHubUsername, gitRepositoryName, message, sha,
						_getGitCommitType(message), date.getTime());

				_gitHubRemoteGitCommits.put(gitHubCommitURL, remoteGitCommit);

				return remoteGitCommit;
			}
			catch (ParseException pe) {
				throw new RuntimeException(pe);
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get commit details", ioe);
		}
	}

	public static LocalGitCommit newLocalGitCommit(
		GitWorkingDirectory gitWorkingDirectory, String message, String sha,
		long commitTime) {

		return new DefaultLocalGitCommit(
			gitWorkingDirectory, message, sha, _getGitCommitType(message),
			commitTime);
	}

	private static GitCommit.Type _getGitCommitType(String message) {
		if (message.startsWith("archive:ignore")) {
			return GitCommit.Type.LEGACY_ARCHIVE;
		}

		return GitCommit.Type.MANUAL;
	}

	private static String _getGitHubCommitURL(
		String gitHubUsername, String gitRepositoryName, String sha) {

		return JenkinsResultsParserUtil.getGitHubApiUrl(
			gitRepositoryName, gitHubUsername, "commits/" + sha);
	}

	private static final Map<String, GitHubRemoteGitCommit>
		_gitHubRemoteGitCommits = new HashMap<>();

}