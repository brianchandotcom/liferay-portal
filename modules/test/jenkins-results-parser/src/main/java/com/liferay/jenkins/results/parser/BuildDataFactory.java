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

package com.liferay.jenkins.results.parser;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BuildDataFactory {

	public static BatchBuildData newBatchBuildData(JSONObject jsonObject) {
		if (PortalBatchBuildData.isValidJSONObject(jsonObject)) {
			return new PortalBatchBuildData(jsonObject);
		}

		throw new RuntimeException("Invalid JSONObject " + jsonObject);
	}

	public static BatchBuildData newBatchBuildData(
		Map<String, String> buildParameters) {

		return new PortalBatchBuildData(buildParameters);
	}

	public static BuildData newBuildData(JSONObject jsonObject) {
		if (PortalBatchBuildData.isValidJSONObject(jsonObject)) {
			return new PortalBatchBuildData(jsonObject);
		}
		else if (PortalTopLevelBuildData.isValidJSONObject(jsonObject)) {
			return new PortalTopLevelBuildData(jsonObject);
		}

		throw new RuntimeException("Invalid JSONObject " + jsonObject);
	}

	public static BuildData newBuildData(Map<String, String> buildParameters) {
		if (buildParameters.containsKey("RUN_ID")) {
			String runID = buildParameters.get("RUN_ID");

			BuildDatabase buildDatabase = BuildDatabaseUtil.getBuildDatabase();

			if (buildDatabase.hasBuildData(runID)) {
				return buildDatabase.getBuildData(runID);
			}
		}

		if (!buildParameters.containsKey("BUILD_URL")) {
			throw new RuntimeException("Please set BUILD_URL");
		}

		String buildURL = buildParameters.get("BUILD_URL");

		String jobName = BaseBuildData.getJobName(buildURL);

		if (jobName.endsWith("-batch")) {
			return newBatchBuildData(buildParameters);
		}

		return newTopLevelBuildData(buildParameters);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		JSONObject jsonObject) {

		if (PortalTopLevelBuildData.isValidJSONObject(jsonObject)) {
			return new PortalTopLevelBuildData(jsonObject);
		}

		throw new RuntimeException("Invalid JSONObject " + jsonObject);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		Map<String, String> buildParameters) {

		return new PortalTopLevelBuildData(buildParameters);
	}

}