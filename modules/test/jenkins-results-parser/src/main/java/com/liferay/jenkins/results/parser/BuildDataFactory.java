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

/**
 * @author Michael Hashimoto
 */
public class BuildDataFactory {

	public static BatchBuildData newBatchBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		return new PortalBatchBuildData(
			buildParameters, jenkinsJSONObject, runID);
	}

	public static BuildData newBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		String buildURL = buildParameters.get("BUILD_URL");

		String jobName = BaseBuildData.getJobName(buildURL);

		if (jobName.endsWith("-batch")) {
			return newBatchBuildData(buildParameters, jenkinsJSONObject, runID);
		}

		return newTopLevelBuildData(buildParameters, jenkinsJSONObject, runID);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		Map<String, String> buildParameters,
		JenkinsJSONObject jenkinsJSONObject, String runID) {

		return new PortalTopLevelBuildData(
			buildParameters, jenkinsJSONObject, runID);
	}

}