/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseTestUtil {

	public static BuildDatabase addPortalAcceptancePullRequest() {
		return addPortalAcceptancePullRequest(new BuildDatabaseArgs());
	}

	public static BuildDatabase addPortalAcceptancePullRequest(
		BuildDatabaseArgs buildDatabaseArgs) {

		File buildDir = _newBuildDir();

		File buildDatabaseFile = new File(
			buildDir, BuildDatabase.FILE_NAME_BUILD_DATABASE_JSON);

		try {
			JenkinsResultsParserUtil.write(
				buildDatabaseFile,
				_newBuildDatabaseJSONObject(
					buildDatabaseArgs
				).toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return new DefaultBuildDatabase(buildDir);
	}

	private static JSONObject _newBuildDatabaseJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONObject jsonObject = new JSONObject();

		int index = _index.getAndIncrement();

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
			"pull_requests", _newPullRequestsJSONObject(index)
		).put(
			"workspace_git_repositories", new JSONObject()
		).put(
			"workspaces", _newWorkspacesJSONObject(index)
		);

		return jsonObject;
	}

	private static File _newBuildDir() {
		try {
			File buildDir = File.createTempFile("build-database-", null);

			buildDir.delete();

			buildDir.mkdir();

			return buildDir;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static JSONObject _newJobsJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONObject jobsJSONObject = new JSONObject();
		JSONObject jobJSONObject = new JSONObject();
		JSONObject branchJSONObject = new JSONObject();

		JSONArray modifiedFilesJSONArray = new JSONArray();

		for (String modifiedFile : buildDatabaseArgs.modifiedFiles) {
			modifiedFilesJSONArray.put(modifiedFile);
		}

		branchJSONObject.put("modified_files", modifiedFilesJSONArray);

		jobJSONObject.put("branch", branchJSONObject);

		jobsJSONObject.put(buildDatabaseArgs.jobKey, jobJSONObject);

		return jobsJSONObject;
	}

	private static JSONObject _newPropertiesJSONObject(
		BuildDatabaseArgs buildDatabaseArgs) {

		JSONObject propertiesJSONObject = new JSONObject();

		for (Map.Entry<String, Map<String, String>> entry :
				buildDatabaseArgs.properties.entrySet()) {

			JSONArray propertyJSONArray = new JSONArray();

			for (Map.Entry<String, String> propertyEntry :
					entry.getValue(
					).entrySet()) {

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

	private static JSONObject _newPullRequestsJSONObject(int index) {
		JSONObject pullRequestsJSONObject = new JSONObject();
		JSONObject pullRequestJSONObject = new JSONObject();
		JSONObject baseJSONObject = new JSONObject();
		JSONObject repositoryJSONObject = new JSONObject();

		JSONObject ownerJSONObject = new JSONObject();

		ownerJSONObject.put("login", "test-owner-" + index);

		repositoryJSONObject.put(
			"name", "test-repository-" + index
		).put(
			"owner", ownerJSONObject
		);

		baseJSONObject.put("repo", repositoryJSONObject);

		String htmlURL =
			"https://github.com/test-owner-" + index + "/test-repository-" +
				index + "/pull/" + index;

		pullRequestJSONObject.put(
			"base", baseJSONObject
		).put(
			"html_url", htmlURL
		).put(
			"number", index
		);

		pullRequestsJSONObject.put(htmlURL, pullRequestJSONObject);

		return pullRequestsJSONObject;
	}

	private static JSONObject _newWorkspacesJSONObject(int index) {
		JSONObject workspacesJSONObject = new JSONObject();

		JSONObject workspaceJSONObject = new JSONObject();

		workspaceJSONObject.put(
			"primary_repository_dir_name", "test-repository-" + index
		).put(
			"primary_repository_name", "test-repository-" + index
		);

		workspacesJSONObject.put(
			"test-repository-" + index, workspaceJSONObject);

		return workspacesJSONObject;
	}

	private static final AtomicInteger _index = new AtomicInteger(1);

}