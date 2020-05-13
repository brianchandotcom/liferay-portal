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
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRelease extends IndentLevelSpiraArtifact {

	public static SpiraRelease createSpiraRelease(
		SpiraProject spiraProject, String releaseName) {

		return createSpiraRelease(spiraProject, releaseName, null);
	}

	public static SpiraRelease createSpiraRelease(
		SpiraProject spiraProject, String releaseName,
		Integer parentReleaseID) {

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
		requestJSONObject.put("ReleaseStatusId", Status.PLANNED.getID());
		requestJSONObject.put("ReleaseTypeId", Type.MAJOR_RELEASE.getID());

		Calendar calendar = Calendar.getInstance();

		requestJSONObject.put("StartDate", toDateString(calendar));

		calendar.add(Calendar.MONTH, 1);

		requestJSONObject.put("EndDate", toDateString(calendar));

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				urlPath, null, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraProject.getSpiraReleaseByID(
				responseJSONObject.getInt(ID_KEY));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static SpiraRelease createSpiraReleaseByPath(
		SpiraProject spiraProject, String releasePath) {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		if (!spiraReleases.isEmpty()) {
			return spiraReleases.get(0);
		}

		String releaseName = getPathName(releasePath);
		String parentReleasePath = getParentPath(releasePath);

		if (parentReleasePath.isEmpty()) {
			return createSpiraRelease(spiraProject, releaseName);
		}

		SpiraRelease parentSpiraRelease = createSpiraReleaseByPath(
			spiraProject, parentReleasePath);

		return createSpiraRelease(
			spiraProject, releaseName, parentSpiraRelease.getID());
	}

	public static void deleteSpiraReleaseByID(
		SpiraProject spiraProject, int releaseID) {

		List<SpiraRelease> spiraReleases = getSpiraReleases(
			spiraProject, new SearchQuery.SearchParameter(ID_KEY, releaseID));

		if (spiraReleases.isEmpty()) {
			return;
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put("release_id", String.valueOf(releaseID));

		try {
			SpiraRestAPIUtil.request(
				"projects/{project_id}/releases/{release_id}", null,
				urlPathReplacements, HttpRequestMethod.DELETE, null);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		removeCachedSpiraArtifacts(SpiraRelease.class, spiraReleases);
	}

	public static void deleteSpiraReleasesByPath(
		SpiraProject spiraProject, String releasePath) {

		List<SpiraRelease> spiraReleases = spiraProject.getSpiraReleasesByPath(
			releasePath);

		for (SpiraRelease spiraRelease : spiraReleases) {
			deleteSpiraReleaseByID(spiraProject, spiraRelease.getID());
		}
	}

	public SpiraRelease getParentSpiraRelease() {
		if (_parentSpiraRelease != null) {
			return _parentSpiraRelease;
		}

		String indentLevel = getIndentLevel();

		if (indentLevel.length() <= 3) {
			return null;
		}

		String parentIndentLevel = indentLevel.substring(
			0, indentLevel.length() - 3);

		_parentSpiraRelease = _getSpiraReleaseByIndentLevel(parentIndentLevel);

		return _parentSpiraRelease;
	}

	public SpiraReleaseBuild getSpiraReleaseBuildByID(int releaseBuildID) {
		List<SpiraReleaseBuild> spiraReleaseBuilds =
			SpiraReleaseBuild.getSpiraReleaseBuilds(
				getSpiraProject(), this,
				new SearchQuery.SearchParameter(
					SpiraReleaseBuild.ID_KEY, releaseBuildID));

		if (spiraReleaseBuilds.size() > 1) {
			throw new RuntimeException(
				"Duplicate release build id " + releaseBuildID);
		}

		if (spiraReleaseBuilds.isEmpty()) {
			throw new RuntimeException(
				"Missing release build id " + releaseBuildID);
		}

		return spiraReleaseBuilds.get(0);
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()), "/Release/",
			String.valueOf(getID()), ".aspx");
	}

	public static enum Status {

		CANCELED(5), CLOSED(3), DEFERRED(4), IN_PROGRESS(2), PLANNED(1);

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	public static enum Type {

		MAJOR_RELEASE(1), MINOR_RELEASE(2), PHASE(4), SPRINT(3);

		public Integer getID() {
			return _id;
		}

		private Type(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static List<SpiraRelease> getSpiraReleases(
		final SpiraProject spiraProject,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraRelease.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraReleases(
						spiraProject.getID(), searchParameters);
				}

			},
			new Function<JSONObject, SpiraRelease>() {

				@Override
				public SpiraRelease apply(JSONObject jsonObject) {
					return new SpiraRelease(jsonObject);
				}

			},
			searchParameters);
	}

	@Override
	protected PathSpiraArtifact getParentSpiraArtifact() {
		return getParentSpiraRelease();
	}

	protected static final Integer ARTIFACT_TYPE_ID = 2;

	protected static final String ARTIFACT_TYPE_NAME = "release";

	protected static final String ID_KEY = "ReleaseId";

	private static List<JSONObject> _requestSpiraReleases(
		int spiraProjectID, SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_rows", String.valueOf(15000));
		urlParameters.put("start_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put("project_id", String.valueOf(spiraProjectID));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/releases/search", urlParameters,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			List<JSONObject> spiraReleases = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraReleases.add(responseJSONArray.getJSONObject(i));
			}

			return spiraReleases;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraRelease(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraRelease.class, this);
	}

	private SpiraRelease _getSpiraReleaseByIndentLevel(String indentLevel) {
		List<SpiraRelease> spiraReleases = getSpiraReleases(
			getSpiraProject(),
			new SearchQuery.SearchParameter("IndentLevel", indentLevel));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate indent level " + indentLevel);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing indent level " + indentLevel);
		}

		return spiraReleases.get(0);
	}

	private SpiraRelease _parentSpiraRelease;

}