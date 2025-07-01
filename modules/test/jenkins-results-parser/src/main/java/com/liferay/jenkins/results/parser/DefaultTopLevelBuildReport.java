/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultTopLevelBuildReport extends BaseTopLevelBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		JSONObject buildReportJSONObject =
			_topLevelBuild.getBuildReportJSONObject();

		JSONArray batchesJSONArray = new JSONArray();

		for (String batchName : getBatchNames()) {
			JSONArray buildsJSONArray = new JSONArray();

			for (DownstreamBuildReport downstreamBuildReport :
					getDownstreamBuildReports(batchName)) {

				buildsJSONArray.put(
					downstreamBuildReport.getBuildReportJSONObject());
			}

			JSONObject batchJSONObject = new JSONObject();

			batchJSONObject.put(
				"batchName", batchName
			).put(
				"builds", buildsJSONArray
			);

			batchesJSONArray.put(batchJSONObject);
		}

		buildReportJSONObject.put("batches", batchesJSONArray);

		ControllerBuildReport controllerBuildReport =
			getControllerBuildReport();

		if (controllerBuildReport != null) {
			buildReportJSONObject.put(
				"controller", controllerBuildReport.getBuildReportJSONObject());
		}

		return buildReportJSONObject;
	}

	@Override
	public Date getStartDate() {
		return new Date(_topLevelBuild.getStartTime());
	}

	protected DefaultTopLevelBuildReport(TopLevelBuild topLevelBuild) {
		super(topLevelBuild.getBuildURL());

		_topLevelBuild = topLevelBuild;

		Build controllerBuild = topLevelBuild.getControllerBuild();

		if ((controllerBuild != null) && controllerBuild.isCompleted()) {
			setControllerBuildReport(
				BuildReportFactory.newControllerBuildReport(
					controllerBuild, this));
		}

		for (Build build : topLevelBuild.getDownstreamBuilds()) {
			if (!build.isCompleted()) {
				continue;
			}

			if (build instanceof DownstreamBuild) {
				addDownstreamBuildReport(
					BuildReportFactory.newDownstreamBuildReport(
						(DownstreamBuild)build));
			}
		}
	}

	private final TopLevelBuild _topLevelBuild;

}