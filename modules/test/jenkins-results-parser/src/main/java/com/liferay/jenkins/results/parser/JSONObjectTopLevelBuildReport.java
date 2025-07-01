/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JSONObjectTopLevelBuildReport extends BaseTopLevelBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		return _buildReportJSONObject;
	}

	protected JSONObjectTopLevelBuildReport(JSONObject buildReportJSONObject) {
		super(buildReportJSONObject.getString("buildURL"));

		_buildReportJSONObject = buildReportJSONObject;

		JSONArray batchesJSONArray = buildReportJSONObject.optJSONArray(
			"batches");

		if (batchesJSONArray != null) {
			for (int i = 0; i < batchesJSONArray.length(); i++) {
				JSONObject batchJSONObject = batchesJSONArray.getJSONObject(i);

				String batchName = batchJSONObject.optString("batchName");
				JSONArray buildsJSONArray = batchJSONObject.optJSONArray(
					"builds");

				if (JenkinsResultsParserUtil.isNullOrEmpty(batchName) ||
					(buildsJSONArray == null)) {

					continue;
				}

				for (int j = 0; j < buildsJSONArray.length(); j++) {
					addDownstreamBuildReport(
						BuildReportFactory.newDownstreamBuildReport(
							batchName, buildsJSONArray.getJSONObject(j), this));
				}
			}
		}

		JSONObject controllerJSONObject = buildReportJSONObject.optJSONObject(
			"controller");

		if (controllerJSONObject != null) {
			setControllerBuildReport(
				BuildReportFactory.newControllerBuildReport(
					controllerJSONObject, this));
		}
	}

	private final JSONObject _buildReportJSONObject;

}