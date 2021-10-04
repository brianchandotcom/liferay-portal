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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PullRequestFactory {

	public static PullRequest newPullRequest(String gitHubURL) {
		PullRequest pullRequest = _pullRequests.get(gitHubURL);

		if (pullRequest != null) {
			return pullRequest;
		}

		pullRequest = new PullRequest(gitHubURL);

		_pullRequests.put(gitHubURL, pullRequest);

		return pullRequest;
	}

	private static final Map<String, PullRequest> _pullRequests =
		new HashMap<>();

}