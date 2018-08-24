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

import org.apache.commons.lang.StringUtils;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseCommit implements Commit {

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
			"https://github.com/", _gitHubUserName, "/", _repositoryName,
			"/commit/", getSHA());
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

	@Override
	public void setStatus(
		Commit.Status status, String context, String description,
		String targetURL) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("state", StringUtils.lowerCase(status.toString()));

		if (context != null) {
			jsonObject.put("context", context);
		}

		if (description != null) {
			jsonObject.put("description", description);
		}

		if ((targetURL != null) && targetURL.matches("https?\\:\\/\\/.*")) {
			jsonObject.put("target_url", targetURL);
		}

		try {
			JenkinsResultsParserUtil.toJSONObject(
				getGitHubStatusURL(), jsonObject.toString());
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected BaseCommit(
		String gitHubUserName, String message, String repositoryName,
		String sha, Type type) {

		_gitHubUserName = gitHubUserName;
		_message = message;
		_repositoryName = repositoryName;
		_sha = sha;
		_type = type;
	}

	protected String getGitHubStatusURL() {
		return JenkinsResultsParserUtil.getGitHubApiUrl(
			_repositoryName, _gitHubUserName, "statuses/" + getSHA());
	}

	protected GitWorkingDirectory gitWorkingDirectory;

	private JSONObject _toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", _message);
		jsonObject.put("sha", _sha);

		return jsonObject;
	}

	private final String _gitHubUserName;
	private final String _message;
	private final String _repositoryName;
	private final String _sha;
	private final Type _type;

}