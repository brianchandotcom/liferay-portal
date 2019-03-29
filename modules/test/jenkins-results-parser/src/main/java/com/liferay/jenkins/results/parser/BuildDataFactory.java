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

	public static BatchBuildData newBatchBuildData(
		String runID, String jobName, String buildURL) {

		if (jobName.contains("portal") ||
			jobName.contains("root-cause-analysis-tool")) {

			return new PortalBatchBuildData(runID, jobName, buildURL);
		}

		return new DefaultBatchBuildData(runID, jobName, buildURL);
	}

	public static BuildData newBuildData(
		String runID, String jobName, String buildURL) {

		if (jobName.endsWith("-batch")) {
			return newBatchBuildData(runID, jobName, buildURL);
		}

		return newTopLevelBuildData(runID, jobName, buildURL);
	}

	public static TopLevelBuildData newTopLevelBuildData(
		String runID, String jobName, String buildURL) {

		if (jobName.contains("test-portal-testsuite-upstream-controller")) {
			return new PortalTestSuiteUpstreamControllerBuildData(
				runID, jobName, buildURL);
		}
		else if (jobName.contains("portal") ||
				 jobName.contains("root-cause-analysis-tool")) {

			return new PortalTopLevelBuildData(runID, jobName, buildURL);
		}

		return new DefaultTopLevelBuildData(runID, jobName, buildURL);
	}

}