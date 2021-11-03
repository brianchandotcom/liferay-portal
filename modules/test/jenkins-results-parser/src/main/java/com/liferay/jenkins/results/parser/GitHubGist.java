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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubGist {

	public GitHubGist(String gistID) {
		try {
			_jsonObject = JenkinsResultsParserUtil.toJSONObject(
				"https://api.github.com/gists/" + gistID);
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Could not find gist https://gist.github.com/" + gistID,
				ioException);
		}
	}

	public String getContent() {
		JSONObject filesJSONObject = _jsonObject.getJSONObject("files");

		for (String key : filesJSONObject.keySet()) {
			JSONObject fileJSONObject = filesJSONObject.getJSONObject(key);

			return fileJSONObject.getString("content");
		}

		return null;
	}

	public Map<String, String> getEnvironmentVariables() {
		Map<String, String> environmentVariables = new HashMap<>();

		String content = getContent();

		for (String line : content.split("\\n")) {
			if (JenkinsResultsParserUtil.isNullOrEmpty(line) ||
				!line.contains("=")) {

				continue;
			}

			int x = line.indexOf("=");

			environmentVariables.put(
				line.substring(0, x), line.substring(x + 1));
		}

		return environmentVariables;
	}

	private final JSONObject _jsonObject;

}