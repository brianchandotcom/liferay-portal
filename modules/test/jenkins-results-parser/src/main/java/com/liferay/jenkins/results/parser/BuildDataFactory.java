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

/**
 * @author Michael Hashimoto
 */
public class BuildDataFactory {

	public static BatchBuildData newBatchBuildData(String jobName) {
		return newBatchBuildData(jobName, null, null);
	}

	public static BatchBuildData newBatchBuildData(
		String jobName, String runID) {

		return newBatchBuildData(jobName, runID, null);
	}

	public static BatchBuildData newBatchBuildData(
		String jobName, String runID, String buildURL) {

		if (jobName.contains("git-bisect-tool") || jobName.contains("portal")) {
			return new PortalBatchBuildData(runID, buildURL);
		}

		return new DefaultBatchBuildData(runID, buildURL);
	}

	public static BuildData newBuildData(
		String jobName, String runID, String buildURL) {

		if (jobName.endsWith("-batch")) {
			return newBatchBuildData(jobName, runID, buildURL);
		}

		return newTopLevelBuildData(jobName, runID, buildURL);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		String jobName, String runID) {

		return newTopLevelBuildData(jobName, runID, null);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		String jobName, String runID, String buildURL) {

		if (jobName.contains("git-bisect-tool") || jobName.contains("portal")) {
			return new PortalTopLevelBuildData(runID, buildURL);
		}

		return new DefaultTopLevelBuildData(runID, buildURL);
	}

}