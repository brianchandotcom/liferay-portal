/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseArgs {

	public BuildDatabaseArgs() {
		_jsonObject.put(
			"builds", new JSONObject()
		).put(
			"jobs", new JSONObject()
		).put(
			"portal_fixpack_releases", new JSONObject()
		).put(
			"portal_hotfix_releases", new JSONObject()
		).put(
			"portal_releases", new JSONObject()
		).put(
			"properties", new JSONObject()
		).put(
			"pull_requests", new JSONObject()
		).put(
			"workspace_git_repositories", new JSONObject()
		).put(
			"workspaces", new JSONObject()
		);
	}

	public void addModifiedFile(String relativePath) {
		JSONObject jobsJSONObject = _jsonObject.getJSONObject("jobs");

		JSONObject jobJSONObject = jobsJSONObject.optJSONObject(jobKey);

		if (jobJSONObject == null) {
			jobJSONObject = new JSONObject();

			jobsJSONObject.put(jobKey, jobJSONObject);
		}

		JSONObject branchJSONObject = jobJSONObject.optJSONObject("branch");

		if (branchJSONObject == null) {
			branchJSONObject = new JSONObject();

			jobJSONObject.put("branch", branchJSONObject);
		}

		JSONArray modifiedFilesJSONArray = branchJSONObject.optJSONArray(
			"modified_files");

		if (modifiedFilesJSONArray == null) {
			modifiedFilesJSONArray = new JSONArray();

			branchJSONObject.put("modified_files", modifiedFilesJSONArray);
		}

		modifiedFilesJSONArray.put(relativePath);
	}

	public JSONObject getJSONObject() {
		return new JSONObject(_jsonObject.toString());
	}

	public void setProperty(String key, String name, String value) {
		JSONObject propertiesJSONObject = _jsonObject.getJSONObject(
			"properties");

		JSONArray propertyJSONArray = propertiesJSONObject.optJSONArray(key);

		if (propertyJSONArray == null) {
			propertyJSONArray = new JSONArray();

			propertiesJSONObject.put(key, propertyJSONArray);
		}

		for (int i = 0; i < propertyJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertyJSONArray.getJSONObject(i);

			if (name.equals(propertyJSONObject.getString("name"))) {
				propertyJSONObject.put("value", value);

				return;
			}
		}

		JSONObject propertyJSONObject = new JSONObject();

		propertyJSONObject.put(
			"name", name
		).put(
			"value", value
		);

		propertyJSONArray.put(propertyJSONObject);
	}

	public String jobKey = "PortalAcceptancePullRequestJob";

	@FunctionalInterface
	public interface Consumer
		extends java.util.function.Consumer<BuildDatabaseArgs> {
	}

	private final JSONObject _jsonObject = new JSONObject();

}