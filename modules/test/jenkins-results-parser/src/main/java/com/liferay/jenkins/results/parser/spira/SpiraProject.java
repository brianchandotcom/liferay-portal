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
public class SpiraProject extends BaseSpiraArtifact {

	public static SpiraProject getSpiraProjectByID(int projectID) {
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

			_spiraProjects.put(spiraProject.getID(), spiraProject);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return _spiraProjects.get(projectID);
	}

	@Override
	public int getID() {
		return jsonObject.getInt("ProjectId");
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

	public SpiraTestCaseObject getSpiraTestCaseByID(int testCaseID)
		throws IOException {

		List<SpiraTestCaseObject> spiraTestCases =
			SpiraTestCaseObject.getSpiraTestCases(
				this, new SearchParameter("TestCaseId", testCaseID));

		if (spiraTestCases.size() > 1) {
			throw new RuntimeException("Duplicate test case ID " + testCaseID);
		}

		if (spiraTestCases.isEmpty()) {
			throw new RuntimeException("Missing test case ID " + testCaseID);
		}

		return spiraTestCases.get(0);
	}

	public SpiraTestCaseObject getSpiraTestCaseByPath(String testCasePath)
		throws IOException {

		List<SpiraTestCaseObject> spiraTestCases = getSpiraTestCasesByPath(
			testCasePath);

		if (spiraTestCases.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case path " + testCasePath);
		}

		if (spiraTestCases.isEmpty()) {
			throw new RuntimeException(
				"Missing test case path " + testCasePath);
		}

		return spiraTestCases.get(0);
	}

	public SpiraTestCaseFolder getSpiraTestCaseFolderByID(int testCaseFolderID)
		throws IOException {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this,
				new SearchParameter("TestCaseFolderId", testCaseFolderID));

		if (spiraTestCaseFolders.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case folder ID " + testCaseFolderID);
		}

		if (spiraTestCaseFolders.isEmpty()) {
			throw new RuntimeException(
				"Missing test case folder ID " + testCaseFolderID);
		}

		return spiraTestCaseFolders.get(0);
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

	public List<SpiraTestCaseObject> getSpiraTestCasesByPath(
			String testCasePath)
		throws IOException {

		return SpiraTestCaseObject.getSpiraTestCases(
			this, new SearchParameter("Path", testCasePath));
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

	private SpiraProject(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Map<Integer, SpiraProject> _spiraProjects =
		new HashMap<>();

}