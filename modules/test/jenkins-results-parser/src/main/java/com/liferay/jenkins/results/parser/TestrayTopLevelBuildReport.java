/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import com.liferay.jenkins.results.parser.testray.TestrayBuild;
import com.liferay.jenkins.results.parser.testray.TestrayS3Object;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestrayTopLevelBuildReport extends BaseTopLevelBuildReport {

	@Override
	public JSONObject getBuildReportJSONObject() {
		return _buildReportJSONObject;
	}

	protected TestrayTopLevelBuildReport(TestrayBuild testrayBuild) {
		super(String.valueOf(testrayBuild.getTopLevelBuildURL()));

		_startYearMonth = testrayBuild.getStartYearMonth();

		TestrayS3Object buildReportTestrayS3Object =
			getBuildReportTestrayS3Object();

		if (buildReportTestrayS3Object == null) {
			throw new RuntimeException("Unable to find build-report.json.gz");
		}

		String value = buildReportTestrayS3Object.getValue();

		if (JenkinsResultsParserUtil.isNullOrEmpty(value)) {
			throw new RuntimeException("Invalid build-report.json.gz");
		}

		_buildReportJSONObject = new JSONObject(value);

		initialize(_buildReportJSONObject);
	}

	@Override
	protected String getStartYearMonth() {
		return _startYearMonth;
	}

	private final JSONObject _buildReportJSONObject;
	private final String _startYearMonth;

}