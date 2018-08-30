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
		BuildDataJSONObject buildDataJSONObject,
		Map<String, String> buildParameters, String runID) {

		return new PortalBatchBuildData(
			buildDataJSONObject, buildParameters, runID);
	}

	public static BuildData newBuildData(
		BuildDataJSONObject buildDataJSONObject,
		Map<String, String> buildParameters, String runID) {

		String buildURL = buildParameters.get("BUILD_URL");

		String jobName = BaseBuildData.getJobName(buildURL);

		if (jobName == null) {
			throw new RuntimeException("Invalid BUILD_URL " + buildURL);
		}

		if (jobName.endsWith("-batch")) {
			return new PortalBatchBuildData(
				buildDataJSONObject, buildParameters, runID);
		}

		return newTopLevelBuildData(buildDataJSONObject, buildParameters);
	}

	public static BuildData newBuildData(Map<String, String> buildParameters) {
		return newBuildData(null, buildParameters, null);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		BuildDataJSONObject buildDataJSONObject,
		Map<String, String> buildParameters) {

		return new PortalTopLevelBuildData(
			buildDataJSONObject, buildParameters);
	}

}