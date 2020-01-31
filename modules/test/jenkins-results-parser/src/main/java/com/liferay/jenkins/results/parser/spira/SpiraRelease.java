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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRelease {

	public int getID() {
		return _jsonObject.getInt("ReleaseId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	public String getPath() {
		String name = getName();

		String parentPath = "";

		SpiraRelease parentSpiraRelease = _getParentSpiraRelease();

		if (parentSpiraRelease != null) {
			parentPath = parentSpiraRelease.getPath();
		}

		return JenkinsResultsParserUtil.combine(
			parentPath, "/", name.replace("/", "\\/"));
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject(_jsonObject.toString());

		jsonObject.put("Path", getPath());

		return jsonObject;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toString();
	}

	public static class SearchParameter {

		public SearchParameter(String name, Object value) {
			_name = name;
			_value = value;
		}

		public String getName() {
			return _name;
		}

		public Object getValue() {
			return _value;
		}

		public boolean matches(JSONObject jsonObject) {
			if (!Objects.equals(getValue(), jsonObject.get(getName()))) {
				return false;
			}

			return true;
		}

		public JSONObject toFilterJSONObject() {
			JSONObject filterJSONObject = new JSONObject();

			filterJSONObject.put("PropertyName", _name);

			if (_value instanceof Integer) {
				Integer intValue = (Integer)_value;

				filterJSONObject.put("IntValue", intValue);
			}
			else if (_value instanceof String) {
				String stringValue = (String)_value;

				stringValue = stringValue.replaceAll("\\[", "[[]");

				filterJSONObject.put("StringValue", stringValue);
			}
			else {
				throw new RuntimeException("Invalid value type");
			}

			return filterJSONObject;
		}

		private final String _name;
		private final Object _value;

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

		urlParameters.put("number_rows", String.valueOf(_NUMBER_ROWS));
		urlParameters.put("start_row", String.valueOf(_START_ROW));

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

		String indentLevel = getIndentLevel();

		int parentSpiraReleaseCount = (indentLevel.length() / 3) - 1;

		_parentSpiraReleases = new SpiraRelease[parentSpiraReleaseCount];

		for (int i = 1; i <= parentSpiraReleaseCount; i++) {
			String parentIndentLevel = indentLevel.substring(0, i * 3);

			try {
				SpiraRelease parentSpiraRelease =
					_spiraProject.getSpiraReleaseByIndentLevel(
						parentIndentLevel);

				_parentSpiraReleases[i - 1] = parentSpiraRelease;
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}
	}

	protected String getIndentLevel() {
		return _jsonObject.getString("IndentLevel");
	}

	protected boolean isParentSpiraRelease(SpiraRelease parentSpiraRelease) {
		String indentLevel = getIndentLevel();

		return indentLevel.startsWith(parentSpiraRelease.getIndentLevel());
	}

	protected boolean matches(SearchParameter... searchParameters) {
		JSONObject jsonObject = toJSONObject();

		for (SearchParameter searchParameter : searchParameters) {
			if (!searchParameter.matches(jsonObject)) {
				return false;
			}
		}

		return true;
	}

	private static String _createSpiraReleaseKey(
		Integer projectID, Integer releaseID) {

		return projectID + "-" + releaseID;
	}

	private SpiraRelease _getParentSpiraRelease() {
		if (_parentSpiraReleases.length > 0) {
			return _parentSpiraReleases[_parentSpiraReleases.length - 1];
		}

		return null;
	}

	private static final int _NUMBER_ROWS = 15000;

	private static final int _START_ROW = 1;

	private static final Map<String, SpiraRelease> _spiraReleases =
		new HashMap<>();

	private final JSONObject _jsonObject;
	private final SpiraRelease[] _parentSpiraReleases;
	private final SpiraProject _spiraProject;

}