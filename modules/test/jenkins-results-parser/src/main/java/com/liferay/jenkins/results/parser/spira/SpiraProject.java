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
			new SearchQuery.SearchParameter(
				"Name", StringEscapeUtils.unescapeJava(projectName)));

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

	public SpiraCustomProperty getSpiraCustomPropertyByName(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		String customPropertyName) {

		List<SpiraCustomProperty> spiraCustomProperties =
			SpiraCustomProperty.getSpiraCustomProperties(
				this, spiraArtifactClass,
				new SearchQuery.SearchParameter("Name", customPropertyName));

		if (spiraCustomProperties.size() > 1) {
			throw new RuntimeException(
				"Duplicate custom property name " + customPropertyName);
		}

		if (spiraCustomProperties.isEmpty()) {
			throw new RuntimeException(
				"Missing custom property name " + customPropertyName);
		}

		return spiraCustomProperties.get(0);
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
		List<SpiraRelease> spiraReleases = SpiraRelease.getSpiraReleases(
			this, new SearchQuery.SearchParameter("Path", releasePath));

		if (!spiraReleases.isEmpty()) {
			return spiraReleases;
		}

		String releaseName = StringEscapeUtils.unescapeJava(
			PathSpiraArtifact.getPathName(releasePath));

		List<SpiraRelease> nameSpiraReleases = SpiraRelease.getSpiraReleases(
			this, new SearchQuery.SearchParameter("Name", releaseName));

		for (SpiraRelease spiraRelease : nameSpiraReleases) {
			if (!releasePath.equals(spiraRelease.getPath())) {
				continue;
			}

			spiraReleases.add(spiraRelease);
		}

		return spiraReleases;
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

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this,
				new SearchQuery.SearchParameter("Path", testCaseFolderPath));

		if (!spiraTestCaseFolders.isEmpty()) {
			return spiraTestCaseFolders;
		}

		String testCaseFolderName = StringEscapeUtils.unescapeJava(
			PathSpiraArtifact.getPathName(testCaseFolderPath));

		List<SpiraTestCaseFolder> spiraTestCaseFoldersByName =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this,
				new SearchQuery.SearchParameter("Name", testCaseFolderName));

		for (SpiraTestCaseFolder spiraTestCaseFolder :
				spiraTestCaseFoldersByName) {

			if (!testCaseFolderPath.equals(spiraTestCaseFolder.getPath())) {
				continue;
			}

			spiraTestCaseFolders.add(spiraTestCaseFolder);
		}

		return spiraTestCaseFolders;
	}

	public List<SpiraTestCaseObject> getSpiraTestCasesByPath(
		String testCasePath) {

		List<SpiraTestCaseObject> spiraTestCases =
			SpiraTestCaseObject.getSpiraTestCases(
				this, new SearchQuery.SearchParameter("Path", testCasePath));

		if (!spiraTestCases.isEmpty()) {
			return spiraTestCases;
		}

		String testCaseName = StringEscapeUtils.unescapeJava(
			PathSpiraArtifact.getPathName(testCasePath));

		List<SpiraTestCaseObject> spiraTestCasesByName =
			SpiraTestCaseObject.getSpiraTestCases(
				this, new SearchQuery.SearchParameter("Name", testCaseName));

		for (SpiraTestCaseObject spiraTestCase : spiraTestCasesByName) {
			if (!testCasePath.equals(spiraTestCase.getPath())) {
				continue;
			}

			spiraTestCases.add(spiraTestCase);
		}

		return spiraTestCases;
	}

	public SpiraTestCaseType getSpiraTestCaseTypeByID(int testCaseTypeID) {
		List<SpiraTestCaseType> spiraTestCaseTypes =
			SpiraTestCaseType.getSpiraTestCaseTypes(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseType.ID_KEY, testCaseTypeID));

		if (spiraTestCaseTypes.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case type id " + testCaseTypeID);
		}

		if (spiraTestCaseTypes.isEmpty()) {
			throw new RuntimeException(
				"Missing test case type id " + testCaseTypeID);
		}

		return spiraTestCaseTypes.get(0);
	}

	public SpiraTestCaseType getSpiraTestCaseTypeByName(
		String testCaseTypeName) {

		List<SpiraTestCaseType> spiraTestCaseTypes =
			SpiraTestCaseType.getSpiraTestCaseTypes(
				this,
				new SearchQuery.SearchParameter(
					"Name", StringEscapeUtils.unescapeJava(testCaseTypeName)));

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
		String testSetFolderPath) {

		List<SpiraTestSetFolder> spiraTestSetFolders =
			SpiraTestSetFolder.getSpiraTestSetFolders(
				this,
				new SearchQuery.SearchParameter("Path", testSetFolderPath));

		if (!spiraTestSetFolders.isEmpty()) {
			return spiraTestSetFolders;
		}

		String testSetFolderName = StringEscapeUtils.unescapeJava(
			PathSpiraArtifact.getPathName(testSetFolderPath));

		List<SpiraTestSetFolder> spiraTestSetFoldersByName =
			SpiraTestSetFolder.getSpiraTestSetFolders(
				this,
				new SearchQuery.SearchParameter("Name", testSetFolderName));

		for (SpiraTestSetFolder spiraTestSetFolder :
				spiraTestSetFoldersByName) {

			if (!testSetFolderPath.equals(spiraTestSetFolder.getPath())) {
				continue;
			}

			spiraTestSetFolders.add(spiraTestSetFolder);
		}

		return spiraTestSetFolders;
	}

	public List<SpiraTestSet> getSpiraTestSetsByPath(String testSetPath) {
		List<SpiraTestSet> spiraTestSets = SpiraTestSet.getSpiraTestSets(
			this, new SearchQuery.SearchParameter("Path", testSetPath));

		if (!spiraTestSets.isEmpty()) {
			return spiraTestSets;
		}

		String testSetName = StringEscapeUtils.unescapeJava(
			PathSpiraArtifact.getPathName(testSetPath));

		List<SpiraTestSet> spiraTestSetsByName = SpiraTestSet.getSpiraTestSets(
			this, new SearchQuery.SearchParameter("Name", testSetName));

		for (SpiraTestSet spiraTestSet : spiraTestSetsByName) {
			if (!testSetPath.equals(spiraTestSet.getPath())) {
				continue;
			}

			spiraTestSets.add(spiraTestSet);
		}

		return spiraTestSets;
	}

	protected static final String ARTIFACT_TYPE_NAME = "project";

	protected static final String ID_KEY = "ProjectId";

	private static List<SpiraProject> _getSpiraProjects(
		SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
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
	}

	private static JSONObject _requestSpiraProject(JSONObject jsonObject) {
		try {
			Map<String, String> urlPathReplacements = new HashMap<>();

			urlPathReplacements.put(
				"project_id", String.valueOf(jsonObject.getInt(ID_KEY)));

			return SpiraRestAPIUtil.requestJSONObject(
				"projects/{project_id}", null, urlPathReplacements,
				HttpRequestMethod.GET, null);
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
				spiraProjects.add(
					_requestSpiraProject(responseJSONArray.getJSONObject(i)));
			}

			return spiraProjects;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraProject(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraProject.class, this);
	}

}