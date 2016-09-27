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

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class JenkinsStopBuildUtil {

	public static void stopBuild(
			String buildURL, String username, String password)
		throws Exception {

		_stopDownstreamBuilds(buildURL, username, password);

		_stopBuild(buildURL, username, password);
	}

	public static void stopBuild(
			TopLevelBuild topLevelBuild, String username, String password)
		throws Exception {

		stopDownstreamBuilds(topLevelBuild, username, password);

		_stopBuild(topLevelBuild, username, password);
	}

	public static void stopDownstreamBuilds(
			TopLevelBuild topLevelBuild, String username, String password)
		throws Exception {

		List<Build> downstreamBuilds = topLevelBuild.getDownstreamBuilds(
			"running");

		for (Build downstreamBuild : downstreamBuilds) {
			_stopBuild(downstreamBuild, username, password);
		}
	}

	protected static String encodeAuthorizationFields(
		String username, String password) {

		String authorizationString = username + ":" + password;

		return new String(Base64.encodeBase64(authorizationString.getBytes()));
	}

	private static List<String> _getDownstreamURLs(String buildURL)
		throws Exception {

		List<String> downstreamURLs = new ArrayList<>();

		String consoleOutput = JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "/logText/progressiveText"));

		Matcher progressiveTextMatcher = _progressiveTextPattern.matcher(
			consoleOutput);

		while (progressiveTextMatcher.find()) {
			String urlString = progressiveTextMatcher.group("url");

			Matcher buildURLMatcher = _buildURLPattern.matcher(urlString);

			if (buildURLMatcher.find()) {
				downstreamURLs.add(urlString);
			}
		}

		return downstreamURLs;
	}

	private static void _stopBuild(
			Build build, String username, String password)
		throws Exception {

		_stopBuild(build.getBuildURL(), username, password);
	}

	private static void _stopBuild(
			String buildURL, String username, String password)
		throws Exception {

		String normalizedBuildURL = JenkinsResultsParserUtil.fixURL(
			JenkinsResultsParserUtil.getLocalURL(buildURL));

		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			normalizedBuildURL + "/api/json?tree=result", false);

		if (jsonObject.has("result") && jsonObject.isNull("result")) {
			URL urlObject = new URL(normalizedBuildURL + "/stop");

			HttpURLConnection httpConnection =
				(HttpURLConnection)urlObject.openConnection();

			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty(
				"Authorization",
				"Basic " + encodeAuthorizationFields(username, password));

			System.out.println(
				"Response from " + urlObject.toString() + ": " +
					httpConnection.getResponseCode() + " " +
						httpConnection.getResponseMessage());
		}
	}

	private static void _stopDownstreamBuilds(
			String buildURL, String username, String password)
		throws Exception {

		List<String> downstreamURLs = _getDownstreamURLs(buildURL);

		for (String downstreamURL : downstreamURLs) {
			_stopBuild(downstreamURL, username, password);
		}
	}

	private static final Pattern _buildURLPattern = Pattern.compile(
		".+://(?<hostName>[^.]+)(.liferay.com)?/job/(?<jobName>[^/]+).*/" +
			"(?<buildNumber>\\d+)/");
	private static final Pattern _progressiveTextPattern = Pattern.compile(
		"Build \\'.*\\' started at (?<url>.+)\\.");

}