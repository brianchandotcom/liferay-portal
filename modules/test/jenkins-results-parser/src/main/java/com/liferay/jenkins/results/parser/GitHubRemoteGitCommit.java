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

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubRemoteGitCommit extends BaseGitCommit {

	public String getGitHubCommitURL() {
		return JenkinsResultsParserUtil.combine(
			"https://github.com/", _gitHubUsername, "/", getGitRepositoryName(),
			"/commit/", getSHA());
	}

	public List<String> getModifiedFilenames() {
		if (modifiedFilenames == null) {
			init();
		}

		return modifiedFilenames;
	}

	public List<String> getStatusDescriptions() {
		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				getGitHubStatusURL());

			JSONArray statusesJSONArray = jsonObject.getJSONArray("statuses");

			List<String> statusDescriptions = new ArrayList<>(
				statusesJSONArray.length());

			for (int i = 0; i < statusesJSONArray.length(); i++) {
				JSONObject statusJSONObject = statusesJSONArray.getJSONObject(
					i);

				statusDescriptions.add(
					statusJSONObject.optString("description"));
			}

			return statusDescriptions;
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get status data", ioException);
		}
	}

	public void setStatus(
		Status status, String context, String description, String targetURL) {

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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public enum Status {

		ERROR, FAILURE, PENDING, SUCCESS

	}

	protected GitHubRemoteGitCommit(
		String emailAddress, String gitHubUsername, String gitRepositoryName,
		String message, List<String> modifiedFilenames, String sha, Type type,
		long commitTime) {

		super(emailAddress, gitRepositoryName, message, sha, type, commitTime);

		_gitHubUsername = gitHubUsername;
		this.modifiedFilenames = modifiedFilenames;
	}

	protected GitHubRemoteGitCommit(
		String gitHubUsername, String gitRepositoryName, String sha,
		Type type) {

		super(gitRepositoryName, sha, type);

		_gitHubUsername = gitHubUsername;
	}

	protected String getGitHubStatusURL() {
		return JenkinsResultsParserUtil.getGitHubApiUrl(
			getGitRepositoryName(), _gitHubUsername, "statuses/" + getSHA());
	}

	protected void init() {
		try {
			JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
				getGitHubCommitURL(), false);

			JSONObject commitJSONObject = jsonObject.getJSONObject("commit");

			message = commitJSONObject.getString("message");

			JSONObject committerJSONObject = commitJSONObject.getJSONObject(
				"committer");

			modifiedFilenames = Collections.emptyList();

			JSONArray filesJSONArray = jsonObject.getJSONArray("files");

			if (filesJSONArray != null) {
				modifiedFilenames = new ArrayList<>(filesJSONArray.length());

				for (int i = 0; i < filesJSONArray.length(); i++) {
					JSONObject fileJSONObject = filesJSONArray.getJSONObject(i);

					modifiedFilenames.add(fileJSONObject.getString("filename"));
				}
			}

			try {
				DateFormat gitHubDateFormat =
					JenkinsResultsParserUtil.getGitHubDateFormat();

				Date commitDate = gitHubDateFormat.parse(
					committerJSONObject.getString("date"));

				commitTime = commitDate.getTime();
			}
			catch (ParseException parseException) {
				throw new RuntimeException(
					"Unable to parse committer date " +
						committerJSONObject.getString("date"),
					parseException);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get GitHub API JSON for commit " + getSHA(),
				ioException);
		}
	}

	@Override
	protected void initCommitTime() {
		init();
	}

	@Override
	protected void initEmailAddress() {
		init();
	}

	@Override
	protected void initMessage() {
		init();
	}

	protected List<String> modifiedFilenames;

	private final String _gitHubUsername;

}