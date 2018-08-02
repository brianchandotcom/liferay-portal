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

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class Ref {

	public static boolean isValidHtmlURL(String htmlURL) {
		Matcher matcher = _htmlURLPattern.matcher(htmlURL);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public Ref(String htmlURL) {
		Matcher matcher = _htmlURLPattern.matcher(htmlURL);

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + htmlURL);
		}

		JSONObject jsonObject = null;

		for (Type type : Type.values()) {
			try {
				jsonObject = _getJSONObject(matcher, type);
			}
			catch (IOException ioe) {
				continue;
			}

			break;
		}

		if (jsonObject == null) {
			throw new RuntimeException("Could not find github ref");
		}

		_jsonObject = jsonObject;
	}

	public Ref(URL url) {
		this(url.toString());
	}

	public String getHtmlURL() {
		return JenkinsResultsParserUtil.combine(
			"https://github.com/", getUsername(), "/", getRepositoryName(),
			"/tree/", getName());
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	public String getName() {
		Matcher matcher = _urlPattern.matcher(getURL());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + getURL());
		}

		return matcher.group("refName");
	}

	public String getRepositoryName() {
		Matcher matcher = _urlPattern.matcher(getURL());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + getURL());
		}

		return matcher.group("repositoryName");
	}

	public String getSHA() {
		JSONObject objectJSONObject = _jsonObject.getJSONObject("object");

		return objectJSONObject.getString("sha");
	}

	public Type getType() {
		Matcher matcher = _urlPattern.matcher(getURL());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + getURL());
		}

		String refType = matcher.group("refType");

		for (Type type : Type.values()) {
			if (refType.equals(type.getURLPath())) {
				return type;
			}
		}

		throw new RuntimeException("Invalid ref type " + refType);
	}

	public String getUpstreamBranchName() {
		return getName();
	}

	public String getURL() {
		return _jsonObject.getString("url");
	}

	public String getUsername() {
		Matcher matcher = _urlPattern.matcher(getURL());

		if (!matcher.find()) {
			throw new RuntimeException("Invalid URL " + getURL());
		}

		return matcher.group("username");
	}

	public static enum Type {

		BRANCH("heads"), TAG("tags");

		public String getURLPath() {
			return _urlPath;
		}

		private Type(String urlPath) {
			_urlPath = urlPath;
		}

		private final String _urlPath;

	}

	private static JSONObject _getJSONObject(Matcher matcher, Type type)
		throws IOException {

		return JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.combine(
				"https://api.github.com/repos/", matcher.group("username"), "/",
				matcher.group("repositoryName"), "/git/refs/",
				type.getURLPath(), "/", matcher.group("refName")),
			false, 0, 5, 0);
	}

	private static final Pattern _htmlURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://github.com/(?<username>[^/]+)/(?<repositoryName>[^/]+)/",
			"tree/(?<refName>[^/]+)"));
	private static final Pattern _urlPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"https://api.github.com/repos/(?<username>[^/]+)/",
			"(?<repositoryName>[^/]+)/git/refs/(?<refType>[^/]+)/",
			"(?<refName>[^/]+)"));

	private final JSONObject _jsonObject;

}