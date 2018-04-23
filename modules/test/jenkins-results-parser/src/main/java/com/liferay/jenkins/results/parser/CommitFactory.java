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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class CommitFactory {

	public static Commit newCommit(
		String gitHubUserName, String message, String repositoryName,
		String sha) {

		Commit.Type type = Commit.Type.MANUAL;

		if (message.startsWith("archive:ignore")) {
			type = Commit.Type.LEGACY_ARCHIVE;
		}

		String commitURL = _getCommitURL(gitHubUserName, repositoryName, sha);

		if (_commits.containsKey(commitURL)) {
			return _commits.get(commitURL);
		}

		Commit commit = new BaseCommit(
			gitHubUserName, message, repositoryName, sha, type);

		_commits.put(commitURL, commit);

		return commit;
	}

	private static String _getCommitURL(
		String gitHubUserName, String repositoryName, String sha) {

		return JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", gitHubUserName, "/",
			repositoryName, "/commits/", sha);
	}

	private static final Map<String, Commit> _commits = new HashMap<>();

}