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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.liferay.jenkins.results.parser.GitWorkingDirectory.Remote;

/**
 * @author Peter Yoo
 */
public class GitHubRemoteRepository extends RemoteRepository {

	public List<Label> getLabels() {
		String labelsRequestURL = JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/", getUsername(), "/", getName(),
			"/labels");

		if (_repositoryLabels.containsKey(labelsRequestURL)) {
			return _repositoryLabels.get(labelsRequestURL);
		}

		JSONArray labelsJSONArray;

		List<Label> labels = new ArrayList<>();

		int page = 1;

		while (true) {
			try {
				labelsJSONArray = JenkinsResultsParserUtil.toJSONArray(
					JenkinsResultsParserUtil.combine(
						labelsRequestURL, "?page=", String.valueOf(page)));
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to get labels for ", getName(), " repository"),
					ioe);
			}

			if (labelsJSONArray.length() == 0) {
				break;
			}

			for (int i = 0; i < labelsJSONArray.length(); i++) {
				labels.add(new Label((JSONObject)labelsJSONArray.get(i)));
			}

			page++;
		}

		_repositoryLabels.put(labelsRequestURL, labels);

		return labels;
	}

	protected GitHubRemoteRepository(Remote remote) {
		super(remote);

		if (!hostname.equals("github.com")) {
			throw new IllegalArgumentException(
				name + " is not a GitHub repository");
		}
	}

	protected GitHubRemoteRepository(String repositoryName, String username) {
		super("github.com", repositoryName, username);
	}

	private static final Map<String, List<Label>> _repositoryLabels =
		new ConcurrentHashMap<>();

}