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
			this, new SearchParameter("ReleaseId", releaseID));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate release ID " + releaseID);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing release ID " + releaseID);
		}

		return spiraReleases.get(0);
	}

	public SpiraRelease getSpiraReleaseByPath(String releasePath)
		throws IOException {

		List<SpiraRelease> spiraReleases = getSpiraReleasesByPath(releasePath);

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate release path " + releasePath);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing release path " + releasePath);
		}

		return spiraReleases.get(0);
	}

	public List<SpiraRelease> getSpiraReleasesByPath(String releasePath)
		throws IOException {

		return SpiraRelease.getSpiraReleases(
			this, new SearchParameter("Path", releasePath));
	}

	public SpiraTestCaseFolder getSpiraTestCaseFolderByPath(
			String testCaseFolderPath)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			getSpiraTestCaseFoldersByPath(testCaseFolderPath);

		if (spiraTestCaseFolders.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case folder path " + testCaseFolderPath);
		}

		if (spiraTestCaseFolders.isEmpty()) {
			throw new RuntimeException(
				"Missing test case folder path " + testCaseFolderPath);
		}

		return spiraTestCaseFolders.get(0);
	}

	public List<SpiraTestCaseFolder> getSpiraTestCaseFoldersByPath(
			String testCaseFolderPath)
		throws IOException {

		return SpiraTestCaseFolder.getSpiraTestCaseFolders(
			this, new SearchParameter("Path", testCaseFolderPath));
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
			this, new SearchParameter("IndentLevel", indentLevel));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate indent level " + indentLevel);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing indent level " + indentLevel);
		}

		return spiraReleases.get(0);
	}

	protected SpiraTestCaseFolder getSpiraTestCaseFolderByIndentLevel(
			String indentLevel)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this, new SearchParameter("IndentLevel", indentLevel));

		if (spiraTestCaseFolders.size() > 1) {
			throw new RuntimeException("Duplicate indent level " + indentLevel);
		}

		if (spiraTestCaseFolders.isEmpty()) {
			throw new RuntimeException("Missing indent level " + indentLevel);
		}

		return spiraTestCaseFolders.get(0);
	}

	private static final Map<Integer, SpiraProject> _spiraProjects =
		new HashMap<>();

	private final JSONObject _jsonObject;

}