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
		String jsonString, Map<String, String> buildParameters, String runID) {

		if (jsonString == null) {
			return new PortalBatchBuildData(buildParameters, runID);
		}

		return new PortalBatchBuildData(jsonString, buildParameters, runID);
	}

	public static BuildData newBuildData(Map<String, String> buildParameters) {
		return newBuildData(null, buildParameters, null);
	}

	public static BuildData newBuildData(
		String jsonString, Map<String, String> buildParameters, String runID) {

		String buildURL = buildParameters.get("BUILD_URL");

		String jobName = BaseBuildData.getJobName(buildURL);

		if (jobName == null) {
			throw new RuntimeException("Invalid BUILD_URL " + buildURL);
		}

		if (jobName.endsWith("-batch")) {
			if (jsonString == null) {
				return new PortalBatchBuildData(buildParameters, runID);
			}

			return new PortalBatchBuildData(jsonString, buildParameters, runID);
		}

		return newTopLevelBuildData(jsonString, buildParameters);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		String jsonString, Map<String, String> buildParameters) {

		if (jsonString == null) {
			return new PortalTopLevelBuildData(buildParameters);
		}

		return new PortalTopLevelBuildData(jsonString, buildParameters);
	}

}