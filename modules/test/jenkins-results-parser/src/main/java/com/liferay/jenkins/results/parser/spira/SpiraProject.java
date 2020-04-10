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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraProject extends BaseSpiraArtifact {

	public static SpiraProject getSpiraProjectByID(int projectID) {
		List<SpiraProject> spiraProjects = _getSpiraProjects(
			new SearchQuery.SearchParameter(ID_KEY, projectID));

		if (spiraProjects.size() > 1) {
			throw new RuntimeException("Duplicate project ID " + projectID);
		}

		if (spiraProjects.isEmpty()) {
			throw new RuntimeException("Missing project ID " + projectID);
		}

		return spiraProjects.get(0);
	}

	public static SpiraProject getSpiraProjectByName(String projectName) {
		List<SpiraProject> spiraProjects = _getSpiraProjects(
			new SearchQuery.SearchParameter("Name", projectName));

		if (spiraProjects.size() > 1) {
			throw new RuntimeException("Duplicate project name " + projectName);
		}

		if (spiraProjects.isEmpty()) {
			throw new RuntimeException("Missing project name " + projectName);
		}

		return spiraProjects.get(0);
	}

	public Integer getProjectTemplateID() {
		return jsonObject.getInt("ProjectTemplateId");
	}

	public SpiraRelease getSpiraReleaseByID(int releaseID) {
		List<SpiraRelease> spiraReleases = SpiraRelease.getSpiraReleases(
			this,
			new SearchQuery.SearchParameter(SpiraRelease.ID_KEY, releaseID));

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate release ID " + releaseID);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing release ID " + releaseID);
		}

		return spiraReleases.get(0);
	}

	public SpiraRelease getSpiraReleaseByPath(String releasePath) {
		List<SpiraRelease> spiraReleases = getSpiraReleasesByPath(releasePath);

		if (spiraReleases.size() > 1) {
			throw new RuntimeException("Duplicate release path " + releasePath);
		}

		if (spiraReleases.isEmpty()) {
			throw new RuntimeException("Missing release path " + releasePath);
		}

		return spiraReleases.get(0);
	}

	public List<SpiraRelease> getSpiraReleasesByPath(String releasePath) {
		return SpiraRelease.getSpiraReleases(
			this, new SearchQuery.SearchParameter("Path", releasePath));
	}

	public SpiraTestCaseObject getSpiraTestCaseByID(int testCaseID) {
		List<SpiraTestCaseObject> spiraTestCases =
			SpiraTestCaseObject.getSpiraTestCases(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseObject.ID_KEY, testCaseID));

		if (spiraTestCases.size() > 1) {
			throw new RuntimeException("Duplicate test case ID " + testCaseID);
		}

		if (spiraTestCases.isEmpty()) {
			throw new RuntimeException("Missing test case ID " + testCaseID);
		}

		return spiraTestCases.get(0);
	}

	public SpiraTestCaseObject getSpiraTestCaseByPath(String testCasePath) {
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

	public SpiraTestCaseFolder getSpiraTestCaseFolderByID(
		int testCaseFolderID) {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseFolder.ID_KEY, testCaseFolderID));

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
		String testCaseFolderPath) {

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
		String testCaseFolderPath) {

		return SpiraTestCaseFolder.getSpiraTestCaseFolders(
			this, new SearchQuery.SearchParameter("Path", testCaseFolderPath));
	}

	public List<SpiraTestCaseObject> getSpiraTestCasesByPath(
		String testCasePath) {

		return SpiraTestCaseObject.getSpiraTestCases(
			this, new SearchQuery.SearchParameter("Path", testCasePath));
	}

	public SpiraTestCaseType getSpiraTestCaseTypeByName(
		String testCaseTypeName) {

		List<SpiraTestCaseType> spiraTestCaseTypes =
			SpiraTestCaseType.getSpiraTestCaseTypes(
				this,
				new SearchQuery.SearchParameter("Name", testCaseTypeName));

		if (spiraTestCaseTypes.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case type name " + testCaseTypeName);
		}

		if (spiraTestCaseTypes.isEmpty()) {
			throw new RuntimeException(
				"Missing test case type name " + testCaseTypeName);
		}

		return spiraTestCaseTypes.get(0);
	}

	public SpiraTestSet getSpiraTestSetByID(int testSetID) {
		List<SpiraTestSet> spiraTestSets = SpiraTestSet.getSpiraTestSets(
			this,
			new SearchQuery.SearchParameter(SpiraTestSet.ID_KEY, testSetID));

		if (spiraTestSets.size() > 1) {
			throw new RuntimeException("Duplicate test set ID " + testSetID);
		}

		if (spiraTestSets.isEmpty()) {
			throw new RuntimeException("Missing test set ID " + testSetID);
		}

		return spiraTestSets.get(0);
	}

	public SpiraTestSet getSpiraTestSetByPath(String testSetPath) {
		List<SpiraTestSet> spiraTestSets = getSpiraTestSetsByPath(testSetPath);

		if (spiraTestSets.size() > 1) {
			throw new RuntimeException(
				"Duplicate test set path " + testSetPath);
		}

		if (spiraTestSets.isEmpty()) {
			throw new RuntimeException("Missing test set path " + testSetPath);
		}

		return spiraTestSets.get(0);
	}

	public SpiraTestSetFolder getSpiraTestSetFolderByID(int testSetFolderID) {
		List<SpiraTestSetFolder> spiraTestSetFolders =
			SpiraTestSetFolder.getSpiraTestSetFolders(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestSetFolder.ID_KEY, testSetFolderID));

		if (spiraTestSetFolders.size() > 1) {
			throw new RuntimeException(
				"Duplicate test set folder ID " + testSetFolderID);
		}

		if (spiraTestSetFolders.isEmpty()) {
			throw new RuntimeException(
				"Missing test set folder ID " + testSetFolderID);
		}

		return spiraTestSetFolders.get(0);
	}

	public SpiraTestSetFolder getSpiraTestSetFolderByPath(
		String testSetFolderPath) {

		List<SpiraTestSetFolder> spiraTestSetFolders =
			getSpiraTestSetFoldersByPath(testSetFolderPath);

		if (spiraTestSetFolders.size() > 1) {
			throw new RuntimeException(
				"Duplicate test set folder path " + testSetFolderPath);
		}

		if (spiraTestSetFolders.isEmpty()) {
			throw new RuntimeException(
				"Missing test set folder path " + testSetFolderPath);
		}

		return spiraTestSetFolders.get(0);
	}

	public List<SpiraTestSetFolder> getSpiraTestSetFoldersByPath(
		String testCaseSetPath) {

		return SpiraTestSetFolder.getSpiraTestSetFolders(
			this, new SearchQuery.SearchParameter("Path", testCaseSetPath));
	}

	public List<SpiraTestSet> getSpiraTestSetsByPath(String testSetPath) {
		return SpiraTestSet.getSpiraTestSets(
			this, new SearchQuery.SearchParameter("Path", testSetPath));
	}

	protected static final String ARTIFACT_TYPE_NAME = "project";

	protected static final String ID_KEY = "ProjectId";

	private static List<SpiraProject> _getSpiraProjects(
		SearchQuery.SearchParameter... searchParameters) {

		List<SpiraProject> spiraProjects = getSpiraArtifacts(
			SpiraProject.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraProjects();
				}

			},
			new Function<JSONObject, SpiraProject>() {

				@Override
				public SpiraProject apply(JSONObject jsonObject) {
					return new SpiraProject(jsonObject);
				}

			},
			searchParameters);

		return spiraProjects;
	}

	private static List<JSONObject> _requestSpiraProjectByID(
		Integer projectID) {

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put("project_id", String.valueOf(projectID));

		try {
			return Collections.singletonList(
				SpiraRestAPIUtil.requestJSONObject(
					"projects/{project_id}", null, urlPathReplacements,
					HttpRequestMethod.GET, null));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static List<JSONObject> _requestSpiraProjects() {
		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects", null, null, HttpRequestMethod.GET, null);

			List<JSONObject> spiraProjects = new ArrayList<>();

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraProjects.add(responseJSONArray.getJSONObject(i));
			}

			return spiraProjects;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraProject(JSONObject jsonObject) {
		super(jsonObject);
	}

}