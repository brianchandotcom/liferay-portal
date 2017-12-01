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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseCommit implements Commit {

	public BaseCommit(
		GitWorkingDirectory gitWorkingDirectory, String message, String sha) {

		this(gitWorkingDirectory, message, sha, null);
	}

	public BaseCommit(
		GitWorkingDirectory gitWorkingDirectory, String message, String sha,
		Type type) {

		this.gitWorkingDirectory = gitWorkingDirectory;
		_message = message;
		_sha = sha;
		_type = type;
	}

	@Override
	public boolean equals(Object object) {
		if (object.hashCode() == hashCode()) {
			return true;
		}

		return false;
	}

	@Override
	public String getAbbreviatedSHA() {
		return _sha.substring(0, 7);
	}

	@Override
	public String getGitHubCommitURL() {
		return JenkinsResultsParserUtil.combine(
			"https://github.com/",
			GitWorkingDirectory.getGitHubUserName(
				gitWorkingDirectory.getRemote("upstream")),
			"/", gitWorkingDirectory.getRepositoryName(), "/commit/", getSHA());
	}

	@Override
	public String getMessage() {
		return _message;
	}

	@Override
	public String getSHA() {
		return _sha;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		JSONObject jsonObject = _toJSONObject();

		String json = jsonObject.toString();

		return json.hashCode();
	}

	protected GitWorkingDirectory gitWorkingDirectory;

	private JSONObject _toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", _message);
		jsonObject.put("sha", _sha);

		return jsonObject;
	}

	private final String _message;
	private final String _sha;
	private final Type _type;

}