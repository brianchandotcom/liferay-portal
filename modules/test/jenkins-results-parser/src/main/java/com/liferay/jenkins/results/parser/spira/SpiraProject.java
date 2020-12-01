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
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraProject extends BaseSpiraArtifact {

	public static int getID(String projectName) {
		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();

			return Integer.parseInt(
				buildProperties.getProperty(
					"spira.project.id[" + projectName + "]"));
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get build.properties", ioException);
		}
	}

	public static SpiraProject getSpiraProjectByID(int projectID) {
		List<SpiraProject> spiraProjects = _getSpiraProjects(
			new SearchQuery.SearchParameter(KEY_ID, projectID));

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

	public SpiraAutomationHost getSpiraAutomationHostByName(
		String automationHostName) {

		List<SpiraAutomationHost> spiraAutomationHosts =
			SpiraAutomationHost.getSpiraAutomationHosts(
				this,
				new SearchQuery.SearchParameter("Name", automationHostName));

		if (spiraAutomationHosts.size() > 1) {
			throw new RuntimeException(
				"Duplicate automation host name " + automationHostName);
		}

		if (spiraAutomationHosts.isEmpty()) {
			throw new RuntimeException(
				"Missing automation host name " + automationHostName);
		}

		return spiraAutomationHosts.get(0);
	}

	public List<SpiraAutomationHost> getSpiraAutomationHosts() {
		return SpiraAutomationHost.getSpiraAutomationHosts(this);
	}

	public SpiraCustomList getSpiraCustomListByName(
		Class<? extends SpiraArtifact> spiraArtifactClass,
		String customPropertyListName) {

		List<SpiraCustomList> spiraCustomLists =
			SpiraCustomList.getSpiraCustomLists(
				this, spiraArtifactClass,
				new SearchQuery.SearchParameter(
					"Name", customPropertyListName));

		if (spiraCustomLists.size() > 1) {
			throw new RuntimeException(
				"Duplicate custom list name " + customPropertyListName);
		}

		if (spiraCustomLists.isEmpty()) {
			throw new RuntimeException(
				"Missing custom list name " + customPropertyListName);
		}

		return spiraCustomLists.get(0);
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
			new SearchQuery.SearchParameter(SpiraRelease.KEY_ID, releaseID));

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

	public SpiraTestCaseComponent getSpiraTestCaseComponentByID(
		int componentID) {

		List<SpiraTestCaseComponent> spiraTestCaseComponents =
			SpiraTestCaseComponent.getSpiraTestCaseComponents(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseComponent.KEY_ID, componentID));

		if (spiraTestCaseComponents.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case component ID " + componentID);
		}

		if (spiraTestCaseComponents.isEmpty()) {
			throw new RuntimeException(
				"Missing test case component ID " + componentID);
		}

		return spiraTestCaseComponents.get(0);
	}

	public List<SpiraTestCaseComponent> getSpiraTestCaseComponents() {
		return SpiraTestCaseComponent.getSpiraTestCaseComponents(this);
	}

	public SpiraTestCaseFolder getSpiraTestCaseFolderByID(
		int testCaseFolderID) {

		List<SpiraTestCaseFolder> spiraTestCaseFolders =
			SpiraTestCaseFolder.getSpiraTestCaseFolders(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseFolder.KEY_ID, testCaseFolderID));

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

	public SpiraTestCaseObject getSpiraTestCaseObjectByID(int testCaseID) {
		List<SpiraTestCaseObject> spiraTestCaseObjects =
			SpiraTestCaseObject.getSpiraTestCaseObjects(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseObject.KEY_ID, testCaseID));

		if (spiraTestCaseObjects.size() > 1) {
			throw new RuntimeException("Duplicate test case ID " + testCaseID);
		}

		if (spiraTestCaseObjects.isEmpty()) {
			throw new RuntimeException("Missing test case ID " + testCaseID);
		}

		return spiraTestCaseObjects.get(0);
	}

	public List<SpiraTestCaseObject> getSpiraTestCaseObjects() {
		return getSpiraTestCaseObjects(35000, null);
	}

	public List<SpiraTestCaseObject> getSpiraTestCaseObjects(
		long numberOfRows) {

		return getSpiraTestCaseObjects(numberOfRows, null);
	}

	public List<SpiraTestCaseObject> getSpiraTestCaseObjects(
		long numberOfRows,
		SpiraTestCaseProductVersion spiraTestCaseProductVersion) {

		if (spiraTestCaseProductVersion == null) {
			return SpiraTestCaseObject.getSpiraTestCaseObjects(
				this, numberOfRows, false);
		}

		return SpiraTestCaseObject.getSpiraTestCaseObjects(
			this, numberOfRows, false,
			new SearchQuery.SearchParameter(spiraTestCaseProductVersion));
	}

	public List<SpiraTestCaseObject> getSpiraTestCaseObjects(
		SpiraTestCaseProductVersion spiraTestCaseProductVersion) {

		return getSpiraTestCaseObjects(35000, spiraTestCaseProductVersion);
	}

	public SpiraTestCasePriority getSpiraTestCasePriorityByID(int priorityID) {
		List<SpiraTestCasePriority> spiraTestCasePriorities =
			SpiraTestCasePriority.getSpiraTestCasePriorities(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCasePriority.KEY_ID, priorityID));

		if (spiraTestCasePriorities.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case priority ID " + priorityID);
		}

		if (spiraTestCasePriorities.isEmpty()) {
			throw new RuntimeException(
				"Missing test case priority ID " + priorityID);
		}

		return spiraTestCasePriorities.get(0);
	}

	public SpiraTestCasePriority getSpiraTestCasePriorityByScore(int score) {
		List<SpiraTestCasePriority> spiraTestCasePriorities =
			SpiraTestCasePriority.getSpiraTestCasePriorities(
				this, new SearchQuery.SearchParameter("Score", score));

		if (spiraTestCasePriorities.size() > 1) {
			throw new RuntimeException(
				"Duplicate test case priority score " + score);
		}

		if (spiraTestCasePriorities.isEmpty()) {
			throw new RuntimeException(
				"Missing test case priority score " + score);
		}

		return spiraTestCasePriorities.get(0);
	}

	public List<SpiraTestCaseProductVersion> getSpiraTestCaseProductVersions(
		Class<? extends SpiraArtifact> spiraArtifactClass) {

		SpiraCustomProperty spiraCustomProperty =
			SpiraCustomProperty.createSpiraCustomProperty(
				this, spiraArtifactClass,
				SpiraTestCaseProductVersion.CUSTOM_PROPERTY_NAME,
				SpiraCustomProperty.Type.LIST);

		SpiraCustomList spiraCustomList =
			spiraCustomProperty.getSpiraCustomList();

		List<SpiraTestCaseProductVersion> spiraTestCaseProductVersions =
			new ArrayList<>();

		for (SpiraCustomListValue spiraCustomListValue :
				spiraCustomList.getSpiraCustomListValues()) {

			SpiraCustomPropertyValue spiraCustomPropertyValue =
				SpiraCustomPropertyValue.createSpiraCustomPropertyValue(
					spiraCustomProperty, spiraCustomListValue.getName());

			if (!(spiraCustomPropertyValue instanceof
					SpiraTestCaseProductVersion)) {

				continue;
			}

			spiraTestCaseProductVersions.add(
				(SpiraTestCaseProductVersion)spiraCustomPropertyValue);
		}

		return spiraTestCaseProductVersions;
	}

	public SpiraTestCaseType getSpiraTestCaseTypeByID(int testCaseTypeID) {
		List<SpiraTestCaseType> spiraTestCaseTypes =
			SpiraTestCaseType.getSpiraTestCaseTypes(
				this,
				new SearchQuery.SearchParameter(
					SpiraTestCaseType.KEY_ID, testCaseTypeID));

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
			new SearchQuery.SearchParameter(SpiraTestSet.KEY_ID, testSetID));

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
					SpiraTestSetFolder.KEY_ID, testSetFolderID));

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

	@Override
	public String getURL() {
		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(getID()), "/Dev.aspx");
	}

	protected static final String ARTIFACT_TYPE_NAME = "project";

	protected static final String KEY_ID = "ProjectId";

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
				"project_id", String.valueOf(jsonObject.getInt(KEY_ID)));

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