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
		throws FileNotFoundException, IOException {

		_centralUpstreamBranchName = centralUpstreamBranchName;

		_gitrepoProperties = new Properties();

		_gitrepoProperties.load(new FileInputStream(gitrepoFile));

		_subrepositoryName = _getSubrepositoryName();
		_subrepositoryUpstreamBranchName =
			_getSubrepositoryUpstreamBranchName();
		_subrepositoryUsername = _getSubrepositoryUsername();
	}

	public String getSubrepositoryName() {
		return _subrepositoryName;
	}

	public String getSubrepositoryUpstreamCommit()
		throws FileNotFoundException, IOException {

		if (_subrepositoryUpstreamCommit == null) {
			_subrepositoryUpstreamCommit = _getSubrepositoryUpstreamCommit();
		}

		return _subrepositoryUpstreamCommit;
	}

	public Boolean isCentralPullRequestCandidate()
		throws FileNotFoundException, IOException {

		if (_centralPullRequestCandidate == null) {
			_centralPullRequestCandidate = _isCentralPullRequestCandidate();
		}

		return _centralPullRequestCandidate;
	}

	private String _getMergePullRequestURL() throws IOException {
		String subrepositoryUpstreamCommit = getSubrepositoryUpstreamCommit();

		StringBuilder sb = new StringBuilder();

		sb.append("https://api.github.com/repos/");
		sb.append(_subrepositoryUsername);
		sb.append("/");
		sb.append(_subrepositoryName);
		sb.append("/commits/");
		sb.append(subrepositoryUpstreamCommit);
		sb.append("/statuses");

		JSONArray statusesJSONArray = new JSONArray(
			JenkinsResultsParserUtil.toString(sb.toString(), true));

		if (statusesJSONArray != null) {
			for (int i = 0; i < statusesJSONArray.length(); i++) {
				JSONObject statusesJSONObject = statusesJSONArray.getJSONObject(
					i);

				String context = statusesJSONObject.getString("context");

				if (context.equals("liferay/central-pull-request")) {
					return statusesJSONObject.getString("target_url");
				}
			}
		}

		return null;
	}

	private String _getSubrepositoryName()
		throws FileNotFoundException, IOException {

		String remote = _gitrepoProperties.getProperty("remote");

		int x = remote.indexOf("/") + 1;
		int y = remote.indexOf(".git");

		return remote.substring(x, y);
	}

	private String _getSubrepositoryUpstreamBranchName()
		throws FileNotFoundException, IOException {

		String remote = _gitrepoProperties.getProperty("remote");

		String subrepositoryUpstreamBranchName = _centralUpstreamBranchName;

		if (subrepositoryUpstreamBranchName.contains("7.0")) {
			subrepositoryUpstreamBranchName = "7.0.x";
		}

		if (remote.contains("-private")) {
			subrepositoryUpstreamBranchName += "-private";
		}

		return subrepositoryUpstreamBranchName;
	}

	private String _getSubrepositoryUpstreamCommit() throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("https://api.github.com/repos/");
		sb.append(_subrepositoryUsername);
		sb.append("/");
		sb.append(_subrepositoryName);
		sb.append("/git/refs/heads/");
		sb.append(_subrepositoryUpstreamBranchName);

		JSONObject branchJSONObject = JenkinsResultsParserUtil.toJSONObject(
			sb.toString());

		JSONObject objectJSONObject = branchJSONObject.getJSONObject("object");

		return objectJSONObject.getString("sha");
	}

	private String _getSubrepositoryUsername()
		throws FileNotFoundException, IOException {

		String remote = _gitrepoProperties.getProperty("remote");

		int x = remote.indexOf(":") + 1;
		int y = remote.indexOf("/");

		return remote.substring(x, y);
	}

	private Boolean _isCentralPullRequestCandidate()
		throws FileNotFoundException, IOException {

		String mode = _gitrepoProperties.getProperty("mode", "push");

		if (!mode.equals("pull")) {
			return false;
		}

		String autopull = _gitrepoProperties.getProperty("autopull", "false");

		if (!autopull.equals("true")) {
			return false;
		}

		String subrepositoryMergedCommit = _gitrepoProperties.getProperty(
			"commit", "");

		String subrepositoryUpstreamCommit = getSubrepositoryUpstreamCommit();

		if (subrepositoryMergedCommit.equals(subrepositoryUpstreamCommit)) {
			StringBuilder sb = new StringBuilder();

			sb.append("SKIPPED: ");
			sb.append(_subrepositoryName);
			sb.append(" already has merged commit https://github.com/");
			sb.append(_subrepositoryUsername);
			sb.append("/");
			sb.append(_subrepositoryName);
			sb.append("/commit/");
			sb.append(subrepositoryUpstreamCommit);

			System.out.println(sb.toString());

			return false;
		}

		String mergePullRequestURL = _getMergePullRequestURL();

		if (mergePullRequestURL != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("SKIPPED: ");
			sb.append(_subrepositoryName);
			sb.append(" already has open merge pull request ");
			sb.append(mergePullRequestURL);

			System.out.println(sb.toString());

			return false;
		}

		return true;
	}

	private Boolean _centralPullRequestCandidate;
	private final String _centralUpstreamBranchName;
	private final Properties _gitrepoProperties;
	private final String _subrepositoryName;
	private final String _subrepositoryUpstreamBranchName;
	private String _subrepositoryUpstreamCommit;
	private final String _subrepositoryUsername;

}