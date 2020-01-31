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

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraProject {

	public static SpiraProject getSpiraProjectById(int projectID) {
		if (_spiraProjects.containsKey(projectID)) {
			return _spiraProjects.get(projectID);
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put("project_id", String.valueOf(projectID));

		try {
			SpiraProject spiraProject = new SpiraProject(
				SpiraRestAPIUtil.requestJSONObject(
					"projects/{project_id}", null, urlPathReplacements,
					HttpRequestMethod.GET, null));

			if (spiraProject != null) {
				_spiraProjects.put(spiraProject.getID(), spiraProject);
			}
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _spiraProjects.get(projectID);
	}

	public int getID() {
		return _jsonObject.getInt("ProjectId");
	}

	public String getName() {
		return _jsonObject.getString("Name");
	}

	public SpiraRelease getSpiraReleaseByID(int releaseID) throws IOException {
		List<SpiraRelease> spiraReleases = SpiraRelease.getSpiraReleases(
			this, new SpiraRelease.SearchParameter("ReleaseId", releaseID));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate Release ID " + releaseID);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing Release ID " + releaseID);
		}

		return spiraReleases.get(0);
	}

	public SpiraRelease getSpiraReleaseByPath(String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = getSpiraReleasesByPath(releasePath);

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate Release Path " + releasePath);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing Release Path " + releasePath);
		}

		return spiraReleases.get(0);
	}

	public JSONObject toJSONObject() {
		return _jsonObject;
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	protected SpiraProject(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	protected SpiraRelease getSpiraReleaseByIndentLevel(String indentLevel)
		throws IOException {

		List<SpiraRelease> spiraReleases = SpiraRelease.getSpiraReleases(
			this, new SpiraRelease.SearchParameter("IndentLevel", indentLevel));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate Indent Level " + indentLevel);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing Indent Level " + indentLevel);
		}

		return spiraReleases.get(0);
	}

	protected List<SpiraRelease> getSpiraReleasesByName(String releaseName)
		throws IOException {

		return SpiraRelease.getSpiraReleases(
			this, new SpiraRelease.SearchParameter("Name", releaseName));
	}

	private static final Map<Integer, SpiraProject> _spiraProjects =
		new HashMap<>();

	private final JSONObject _jsonObject;

}