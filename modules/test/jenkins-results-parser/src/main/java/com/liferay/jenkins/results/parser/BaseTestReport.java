/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseTestReport implements TestReport {

	@Override
	public DownstreamBuildReport getDownstreamBuildReport() {
		return _downstreamBuildReport;
	}

	@Override
	public long getDuration() {
		return _jsonObject.getLong("duration");
	}

	@Override
	public String getErrorDetails() {
		return _jsonObject.optString("errorDetails");
	}

	@Override
	public String getErrorStackTrace() {
		return _jsonObject.optString("errorStackTrace");
	}

	@Override
	public String getStatus() {
		return _jsonObject.getString("status");
	}

	@Override
	public String getTestName() {
		return _jsonObject.getString("name");
	}

	@Override
	public String getTestClassName() {
		return getTestName();
	}

	@Override
	public String getTestTaskName() {
		return _jsonObject.optString("testTaskName");
	}

	@Override
	public boolean isFailing() {
		String status = getStatus();

		DownstreamBuildReport downstreamBuildReport =
			getDownstreamBuildReport();

		if (status.equals("PASSED") && downstreamBuildReport.isFailing()) {
			int failCount = downstreamBuildReport.getFailCount();
			int passCount = downstreamBuildReport.getPassCount();

			if ((failCount == 0) && (passCount == 1)) {
				return true;
			}
		}

		if (status.equals("FIXED") || status.equals("PASSED") ||
			status.equals("SKIPPED")) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isSkipped() {
		return Objects.equals("SKIPPED", getStatus());
	}

	protected BaseTestReport(
		DownstreamBuildReport downstreamBuildReport, JSONObject jsonObject) {

		_downstreamBuildReport = downstreamBuildReport;
		_jsonObject = jsonObject;
	}

	private final DownstreamBuildReport _downstreamBuildReport;
	private final JSONObject _jsonObject;

}