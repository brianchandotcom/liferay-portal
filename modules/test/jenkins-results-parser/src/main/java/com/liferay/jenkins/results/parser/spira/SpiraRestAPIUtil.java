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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRestAPIUtil {

	public static String request(
			String urlPath, Map<String, String> urlReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		if (urlReplacements != null) {
			for (Map.Entry<String, String> urlReplacement :
					urlReplacements.entrySet()) {

				urlPath = urlPath.replace(
					JenkinsResultsParserUtil.combine(
						"{", urlReplacement.getKey(), "}"),
					urlReplacement.getValue());
			}
		}

		if (!urlPath.startsWith("/")) {
			urlPath = "/" + urlPath;
		}

		return JenkinsResultsParserUtil.toString(
			_SPIRA_BASE_URL + urlPath, false, httpRequestMethod, requestData);
	}

	public static JSONArray requestJSONArray(
			String urlPath, Map<String, String> urlReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONArray(
			request(urlPath, urlReplacements, httpRequestMethod, requestData));
	}

	public static JSONObject requestJSONObject(
			String urlPath, Map<String, String> urlReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONObject(
			request(urlPath, urlReplacements, httpRequestMethod, requestData));
	}

	private static final String _SPIRA_BASE_URL =
		"https://liferay.spiraservice.net/services/v6_0/RestService.svc";

}