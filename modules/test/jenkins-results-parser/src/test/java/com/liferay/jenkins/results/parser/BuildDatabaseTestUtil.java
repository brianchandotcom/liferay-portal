/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseTestUtil {

	public static BuildDatabase newBuildDatabaseWithPullRequest() {
		File buildDir = _newBuildDir();

		File buildDatabaseFile = new File(
			buildDir, BuildDatabase.FILE_NAME_BUILD_DATABASE_JSON);

		JSONObject buildDatabaseJSONObject = new JSONObject();

		buildDatabaseJSONObject.put(
			"pull_requests",
			_newPullRequestsJSONObject(_index.getAndIncrement()));

		try {
			JenkinsResultsParserUtil.write(
				buildDatabaseFile, buildDatabaseJSONObject.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return new DefaultBuildDatabase(buildDir);
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

	private static JSONObject _newPullRequestsJSONObject(int index) {
		JSONObject ownerJSONObject = new JSONObject();

		ownerJSONObject.put("login", "test-owner-" + index);

		JSONObject repositoryJSONObject = new JSONObject();

		repositoryJSONObject.put(
			"name", "test-repository-" + index
		).put(
			"owner", ownerJSONObject
		);

		JSONObject baseJSONObject = new JSONObject();

		baseJSONObject.put("repo", repositoryJSONObject);

		String htmlURL =
			"https://github.com/test-owner-" + index + "/test-repository-" +
				index + "/pull/" + index;

		JSONObject pullRequestJSONObject = new JSONObject();

		pullRequestJSONObject.put(
			"base", baseJSONObject
		).put(
			"html_url", htmlURL
		).put(
			"number", index
		);

		JSONObject pullRequestsJSONObject = new JSONObject();

		pullRequestsJSONObject.put(htmlURL, pullRequestJSONObject);

		return pullRequestsJSONObject;
	}

	private static final AtomicInteger _index = new AtomicInteger(1);

}