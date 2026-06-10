/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseTestUtil {

	public static BuildDatabase addPortalAcceptancePR(
		BuildDatabaseArgs.Consumer... consumers) {

		BuildDatabaseArgs buildDatabaseArgs = new BuildDatabaseArgs();

		for (BuildDatabaseArgs.Consumer consumer : consumers) {
			consumer.accept(buildDatabaseArgs);
		}

		return _addBuildDatabase(buildDatabaseArgs);
	}

	private static BuildDatabase _addBuildDatabase(
		BuildDatabaseArgs buildDatabaseArgs) {

		StagedBuildDatabase stagedBuildDatabase = new StagedBuildDatabase(
			_newBuildDir());

		stagedBuildDatabase.setJSONObject(
			_newBuildDatabaseJSONObject(buildDatabaseArgs));

		stagedBuildDatabase.write();

		return stagedBuildDatabase;
	}

	private static JSONObject _newBuildDatabaseJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"builds", new JSONObject()
		).put(
			"jobs", _newJobsJSONObject(buildDatabaseArgs)
		).put(
			"portal_fixpack_releases", new JSONObject()
		).put(
			"portal_hotfix_releases", new JSONObject()
		).put(
			"portal_releases", new JSONObject()
		).put(
			"properties", _newPropertiesJSONObject(buildDatabaseArgs)
		).put(
			"pull_requests", _newPullRequestsJSONObject()
		).put(
			"workspace_git_repositories", new JSONObject()
		).put(
			"workspaces", _newWorkspacesJSONObject()
		);

		return jsonObject;
	}

	private static File _newBuildDir() {
		try {
			File buildDir = Files.createTempDirectory(
				"build-database"
			).toFile();

			buildDir.deleteOnExit();

			return buildDir;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static JSONObject _newJobsJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONArray modifiedFilesJSONArray = new JSONArray();

		for (String modifiedFile : buildDatabaseArgs.getModifiedFiles()) {
			modifiedFilesJSONArray.put(modifiedFile);
		}

		JSONObject branchJSONObject = new JSONObject();

		branchJSONObject.put("modified_files", modifiedFilesJSONArray);

		JSONObject jobJSONObject = new JSONObject();

		jobJSONObject.put("branch", branchJSONObject);

		JSONObject jobsJSONObject = new JSONObject();

		jobsJSONObject.put(buildDatabaseArgs.jobKey, jobJSONObject);

		return jobsJSONObject;
	}

	private static JSONObject _newPropertiesJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONObject propertiesJSONObject = new JSONObject();

		Map<String, Map<String, String>> properties =
			buildDatabaseArgs.getProperties();

		for (Map.Entry<String, Map<String, String>> entry :
				properties.entrySet()) {

			JSONArray propertyJSONArray = new JSONArray();

			Map<String, String> propertyMap = entry.getValue();

			for (Map.Entry<String, String> propertyEntry :
					propertyMap.entrySet()) {

				JSONObject propertyJSONObject = new JSONObject();

				propertyJSONObject.put(
					"name", propertyEntry.getKey()
				).put(
					"value", propertyEntry.getValue()
				);

				propertyJSONArray.put(propertyJSONObject);
			}

			propertiesJSONObject.put(entry.getKey(), propertyJSONArray);
		}

		return propertiesJSONObject;
	}

	private static JSONObject _newPullRequestsJSONObject() {
		int number = _pullRequestNumber.getAndIncrement();

		String htmlURL =
			"https://github.com/test-owner/test-repo/pull/" + number;

		JSONObject ownerJSONObject = new JSONObject();

		ownerJSONObject.put("login", "test-owner");

		JSONObject repositoryJSONObject = new JSONObject();

		repositoryJSONObject.put(
			"name", "test-repo"
		).put(
			"owner", ownerJSONObject
		);

		JSONObject baseJSONObject = new JSONObject();

		baseJSONObject.put("repo", repositoryJSONObject);

		JSONObject pullRequestJSONObject = new JSONObject();

		pullRequestJSONObject.put(
			"base", baseJSONObject
		).put(
			"html_url", htmlURL
		).put(
			"number", number
		);

		JSONObject pullRequestsJSONObject = new JSONObject();

		pullRequestsJSONObject.put(htmlURL, pullRequestJSONObject);

		return pullRequestsJSONObject;
	}

	private static JSONObject _newWorkspacesJSONObject() {
		JSONObject workspaceJSONObject = new JSONObject();

		workspaceJSONObject.put(
			"primary_repository_dir_name", "liferay-portal"
		).put(
			"primary_repository_name", "liferay-portal"
		);

		JSONObject workspacesJSONObject = new JSONObject();

		workspacesJSONObject.put("liferay-portal", workspaceJSONObject);

		return workspacesJSONObject;
	}

	private static final AtomicInteger _pullRequestNumber = new AtomicInteger(
		1);

	private static class StagedBuildDatabase extends DefaultBuildDatabase {

		@Override
		public FilePropagator rsyncBuildDatabaseFile(
			List<String> distNodes, String distPath, String preDistCommand,
			String postDistCommand, int threadCount) {

			throw new UnsupportedOperationException(_MESSAGE);
		}

		@Override
		public void rsyncBuildDatabaseFileToJenkinsMaster(
			String destinationDirPath, JenkinsMaster jenkinsMaster) {

			throw new UnsupportedOperationException(_MESSAGE);
		}

		@Override
		public void uploadBuildDatabaseFileToCloudBucket() {
			throw new UnsupportedOperationException(_MESSAGE);
		}

		@Override
		public void uploadBuildDatabaseFileToCloudBucket(String path) {
			throw new UnsupportedOperationException(_MESSAGE);
		}

		@Override
		public void writeFilteredPropertiesToFile(
			String destFilePath, Pattern pattern, String key) {

			throw new UnsupportedOperationException(_MESSAGE);
		}

		@Override
		public void writePropertiesToFile(String destFilePath, String key) {
			throw new UnsupportedOperationException(_MESSAGE);
		}

		private StagedBuildDatabase(File buildDir) {
			super(buildDir);
		}

		private static final String _MESSAGE =
			"BuildDatabaseTestUtil does not support file distribution";

	}

}
