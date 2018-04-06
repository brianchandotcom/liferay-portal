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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CentralSubrepository {

	public CentralSubrepository(
			File gitrepoFile, String centralUpstreamBranchName)
		throws IOException {

		_centralUpstreamBranchName = centralUpstreamBranchName;

		_ciProperties = new Properties();

		_gitrepoProperties = new Properties();

		_gitrepoProperties.load(new FileInputStream(gitrepoFile));

		_subrepositoryName = _getSubrepositoryName();

		StringBuilder sb = new StringBuilder();

		sb.append("/opt/dev/projects/github/");
		sb.append(_subrepositoryName);

		if (!_subrepositoryName.endsWith("-private")) {
			sb.append("-private");
		}

		_subrepositoryDirectory = sb.toString();

		_subrepositoryUpstreamBranchName =
			_getSubrepositoryUpstreamBranchName();
		_subrepositoryUsername = _getSubrepositoryUsername();

		String tempBranchName = "temp-" + System.currentTimeMillis();

		GitWorkingDirectory gitWorkingDirectory = new GitWorkingDirectory(
			_subrepositoryUpstreamBranchName, _subrepositoryDirectory,
			_subrepositoryName);

		GitWorkingDirectory.Branch localUpstreamBranch = null;
		GitWorkingDirectory.Branch tempBranch = null;

		try {
			tempBranch = gitWorkingDirectory.createLocalBranch(tempBranchName);

			gitWorkingDirectory.checkoutBranch(tempBranch);

			GitWorkingDirectory.Remote upstreamRemote =
				gitWorkingDirectory.getRemote("upstream");

			localUpstreamBranch = gitWorkingDirectory.getBranch(
				_subrepositoryUpstreamBranchName, null);

			gitWorkingDirectory.fetch(
				localUpstreamBranch,
				gitWorkingDirectory.getBranch(
					_subrepositoryUpstreamBranchName, upstreamRemote));
		}
		finally {
			if ((localUpstreamBranch != null) && (tempBranch != null) &&
				gitWorkingDirectory.branchExists(tempBranch.getName(), null)) {

				gitWorkingDirectory.checkoutBranch(localUpstreamBranch);

				gitWorkingDirectory.deleteBranch(tempBranch);
			}
		}

		try {
			File ciPropertiesFile = new File(
				_subrepositoryDirectory, "ci.properties");

			_ciProperties.load(new FileInputStream(ciPropertiesFile));
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(
				"Unable to find ci.properties in " + _subrepositoryDirectory);
		}
	}

	public String getCIProperty(String key) {
		return _ciProperties.getProperty(key);
	}

	public String getSubrepositoryName() {
		return _subrepositoryName;
	}

	public String getSubrepositoryUpstreamCommit() throws IOException {
		if (_subrepositoryUpstreamCommit == null) {
			_subrepositoryUpstreamCommit = _getSubrepositoryUpstreamCommit();
		}

		return _subrepositoryUpstreamCommit;
	}

	public boolean isAutoPullEnabled() {
		String mode = _gitrepoProperties.getProperty("mode", "push");

		if (!mode.equals("pull")) {
			return false;
		}

		return Boolean.parseBoolean(
			_gitrepoProperties.getProperty("autopull", "false"));
	}

	public boolean isCentralPullRequestCandidate() throws IOException {
		if (_centralPullRequestCandidate == null) {
			_centralPullRequestCandidate = _isCentralPullRequestCandidate();
		}

		return _centralPullRequestCandidate;
	}

	public boolean isSubrepositoryUpstreamCommitMerged() throws IOException {
		String subrepositoryMergedCommit = _gitrepoProperties.getProperty(
			"commit", "");

		String subrepositoryUpstreamCommit = getSubrepositoryUpstreamCommit();

		if (subrepositoryMergedCommit.equals(subrepositoryUpstreamCommit)) {
			return true;
		}

		return false;
	}

	private String _getMergePullRequestURL() throws IOException {
		String subrepositoryUpstreamCommit = getSubrepositoryUpstreamCommit();

		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", _subrepositoryUsername, "/",
			_subrepositoryName, "/commits/", subrepositoryUpstreamCommit,
			"/statuses");

		for (int i = 0; i < 15; i++) {
			JSONArray statusesJSONArray = new JSONArray(
				JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.combine(
						url, "?page=", String.valueOf(i + 1)),
					true));

			if ((statusesJSONArray == null) ||
				(statusesJSONArray.length() == 0)) {

				break;
			}

			for (int j = 0; j < statusesJSONArray.length(); j++) {
				JSONObject statusesJSONObject = statusesJSONArray.getJSONObject(
					j);

				String context = statusesJSONObject.getString("context");

				if (context.equals("liferay/central-pull-request")) {
					return statusesJSONObject.getString("target_url");
				}
			}
		}

		return null;
	}

	private String _getSubrepositoryName() {
		String remote = _gitrepoProperties.getProperty("remote");

		int x = remote.indexOf("/") + 1;
		int y = remote.indexOf(".git");

		return remote.substring(x, y);
	}

	private String _getSubrepositoryUpstreamBranchName() {
		String remote = _gitrepoProperties.getProperty("remote");

		String subrepositoryUpstreamBranchName = _centralUpstreamBranchName;

		if (subrepositoryUpstreamBranchName.contains("7.0")) {
			subrepositoryUpstreamBranchName = "7.0.x";
		}
		else if (subrepositoryUpstreamBranchName.contains("master")) {
			subrepositoryUpstreamBranchName = "master";
		}

		if (remote.contains("-private")) {
			subrepositoryUpstreamBranchName += "-private";
		}

		return subrepositoryUpstreamBranchName;
	}

	private String _getSubrepositoryUpstreamCommit() throws IOException {
		String url = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", _subrepositoryUsername, "/",
			_subrepositoryName, "/git/refs/heads/",
			_subrepositoryUpstreamBranchName);

		JSONObject branchJSONObject = JenkinsResultsParserUtil.toJSONObject(
			url, false);

		JSONObject objectJSONObject = branchJSONObject.getJSONObject("object");

		return objectJSONObject.getString("sha");
	}

	private String _getSubrepositoryUsername() {
		String remote = _gitrepoProperties.getProperty("remote");

		int x = remote.indexOf(":") + 1;
		int y = remote.indexOf("/");

		return remote.substring(x, y);
	}

	private Boolean _isCentralPullRequestCandidate() throws IOException {
		if (!isAutoPullEnabled()) {
			return false;
		}

		if (isSubrepositoryUpstreamCommitMerged()) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"SKIPPED: ", _subrepositoryName,
					" contains merged commit https://github.com/",
					_subrepositoryUsername, "/", _subrepositoryName, "/commit/",
					getSubrepositoryUpstreamCommit()));

			return false;
		}

		String mergePullRequestURL = _getMergePullRequestURL();

		if (mergePullRequestURL != null) {
			System.out.println(
				JenkinsResultsParserUtil.combine(
					"SKIPPED: ", _subrepositoryName,
					" contains an open merge pull request ",
					mergePullRequestURL));

			return false;
		}

		return true;
	}

	private Boolean _centralPullRequestCandidate;
	private final String _centralUpstreamBranchName;
	private final Properties _ciProperties;
	private final Properties _gitrepoProperties;
	private final String _subrepositoryDirectory;
	private final String _subrepositoryName;
	private final String _subrepositoryUpstreamBranchName;
	private String _subrepositoryUpstreamCommit;
	private final String _subrepositoryUsername;

}