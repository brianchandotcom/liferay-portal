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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRelease {

	public static SpiraRelease createSpiraRelease(
			SpiraProject spiraProject, String releaseName)
		throws IOException {

		return createSpiraRelease(spiraProject, releaseName, null);
	}

	public static SpiraRelease createSpiraRelease(
			SpiraProject spiraProject, String releaseName,
			Integer parentReleaseID)
		throws IOException {

		String urlPath = "projects/{project_id}/releases{parent_release_id}";

		Map<String, String> urlPathReplacements = new HashMap<>();

		if ((parentReleaseID == null) || (parentReleaseID == 0)) {
			urlPathReplacements.put("parent_release_id", "");
		}
		else {
			urlPathReplacements.put("parent_release_id", "/" + parentReleaseID);
		}

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"Name", StringEscapeUtils.unescapeJava(releaseName));
		requestJSONObject.put("ReleaseStatusId", SpiraRelease.STATUS_PLANNED);
		requestJSONObject.put("ReleaseTypeId", SpiraRelease.TYPE_MAJOR_RELEASE);

		Calendar calendar = Calendar.getInstance();

		requestJSONObject.put("StartDate", _toDateString(calendar));

		calendar.add(Calendar.MONTH, 1);

		requestJSONObject.put("EndDate", _toDateString(calendar));

		JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
			urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
			requestJSONObject.toString());

		return spiraProject.getSpiraReleaseByID(
			responseJSONObject.getInt("ReleaseId"));
	}

	public static SpiraRelease createSpiraReleaseByPath(
			SpiraProject spiraProject, String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		if (!spiraReleases.isEmpty()) {
			return spiraReleases.get(0);
		}

		String[] releasePathNames = releasePath.split("(?<!\\\\)\\/");

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < (releasePathNames.length - 2); i++) {
			sb.append("/");
			sb.append(releasePathNames[i]);
		}

		String releaseName = releasePathNames[releasePathNames.length - 1];

		String parentReleasePath = sb.toString();

		if (parentReleasePath.isEmpty()) {
			return createSpiraRelease(spiraProject, releaseName);
		}

		SpiraRelease parentSpiraRelease = createSpiraReleaseByPath(
			spiraProject, parentReleasePath);

		return createSpiraRelease(
			spiraProject, releaseName, parentSpiraRelease.getID());
	}

	public static void deleteSpiraReleaseById(
			SpiraProject spiraProject, int releaseID)
		throws IOException {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put("release_id", String.valueOf(releaseID));

		SpiraRestAPIUtil.request(
			"projects/{project_id}/releases/{release_id}", null,
			urlPathReplacements, HttpRequestMethod.DELETE, null);

		_spiraReleases.remove(
			_createSpiraReleaseKey(spiraProject.getID(), releaseID));
	}

	public static void deleteSpiraReleasesByPath(
			SpiraProject spiraProject, String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		for (SpiraRelease spiraRelease : spiraReleases) {
			deleteSpiraReleaseById(spiraProject, spiraRelease.getID());
		}
	}

	public int getID() {
		return _jsonObject.getInt("ReleaseId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	public String getPath() {
		String name = getName();

		name = name.replace("/", "\\/");

		if (_parentSpiraRelease == null) {
			return "/" + name;
		}

		return JenkinsResultsParserUtil.combine(
			_parentSpiraRelease.getPath(), "/", name.replace("/", "\\/"));
	}

	public JSONObject toJSONObject() {
		return _jsonObject;
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	protected static List<SpiraRelease> getSpiraReleases(
			SpiraProject spiraProject, SearchParameter... searchParameters)
		throws IOException {

		List<SpiraRelease> spiraReleases = new ArrayList<>();

		for (SpiraRelease spiraRelease : _spiraReleases.values()) {
			if (spiraRelease.matches(searchParameters)) {
				spiraReleases.add(spiraRelease);
			}
		}

		if (!spiraReleases.isEmpty()) {
			return spiraReleases;
		}

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_rows", String.valueOf(15000));
		urlParameters.put("start_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
			"projects/{project_id}/releases/search", urlParameters,
			urlPathReplacements, HttpRequestMethod.POST,
			requestJSONArray.toString());

		for (int i = 0; i < responseJSONArray.length(); i++) {
			SpiraRelease spiraRelease = new SpiraRelease(
				responseJSONArray.getJSONObject(i));

			_spiraReleases.put(
				_createSpiraReleaseKey(
					spiraProject.getID(), spiraRelease.getID()),
				spiraRelease);

			if (spiraRelease.matches(searchParameters)) {
				spiraReleases.add(spiraRelease);
			}
		}

		return spiraReleases;
	}

	protected SpiraRelease(JSONObject jsonObject) {
		_jsonObject = jsonObject;
		_spiraProject = SpiraProject.getSpiraProjectById(
			jsonObject.getInt("ProjectId"));

		SpiraRelease parentSpiraRelease = null;

		String indentLevel = getIndentLevel();

		if (indentLevel.length() > 3) {
			String parentIndentLevel = indentLevel.substring(
				0, indentLevel.length() - 3);

			try {
				parentSpiraRelease = _spiraProject.getSpiraReleaseByIndentLevel(
					parentIndentLevel);
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		_parentSpiraRelease = parentSpiraRelease;

		_jsonObject.put("Path", getPath());
	}

	protected String getIndentLevel() {
		return _jsonObject.getString("IndentLevel");
	}

	protected boolean matches(SearchParameter... searchParameters) {
		return SearchParameter.matches(toJSONObject(), searchParameters);
	}

	protected static final int STATUS_CANCELED = 5;

	protected static final int STATUS_CLOSED = 3;

	protected static final int STATUS_DEFERRED = 4;

	protected static final int STATUS_IN_PROGRESS = 2;

	protected static final int STATUS_PLANNED = 1;

	protected static final int TYPE_MAJOR_RELEASE = 1;

	protected static final int TYPE_MINOR_RELEASE = 2;

	protected static final int TYPE_PHASE = 4;

	protected static final int TYPE_SPRINT = 3;

	private static String _createSpiraReleaseKey(int projectID, int releaseID) {
		return projectID + "-" + releaseID;
	}

	private static String _toDateString(Calendar calendar) {
		return JenkinsResultsParserUtil.combine(
			"/Date(", String.valueOf(calendar.getTimeInMillis()), ")/");
	}

	private static final Map<String, SpiraRelease> _spiraReleases =
		new HashMap<>();

	private final JSONObject _jsonObject;
	private final SpiraRelease _parentSpiraRelease;
	private final SpiraProject _spiraProject;

}