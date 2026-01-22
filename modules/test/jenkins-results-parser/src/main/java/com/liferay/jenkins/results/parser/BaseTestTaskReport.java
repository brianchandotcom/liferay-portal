/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseTestTaskReport implements TestTaskReport {

	@Override
	public DownstreamBuildReport getDownstreamBuildReport() {
		return _downstreamBuildReport;
	}

	@Override
	public long getDuration() {
		return _duration;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public long getOverheadDuration() {
		long overheadDuration = getDuration();

		DownstreamBuildReport downstreamBuildReport =
			getDownstreamBuildReport();

		for (TestClassReport testClassReport :
				downstreamBuildReport.getTestClassReports()) {

			if (!Objects.equals(getName(), testClassReport.getTestTaskName())) {
				continue;
			}

			overheadDuration -= testClassReport.getDuration();
		}

		if (overheadDuration <= 0) {
			return 0L;
		}

		return overheadDuration;
	}

	@Override
	public TopLevelBuildReport getTopLevelBuildReport() {
		return _downstreamBuildReport.getTopLevelBuildReport();
	}

	@Override
	public boolean isMissing() {
		if (getDuration() <= 0) {
			return true;
		}

		return false;
	}

	protected BaseTestTaskReport(
		DownstreamBuildReport downstreamBuildReport, JSONObject jsonObject) {

		_downstreamBuildReport = downstreamBuildReport;

		_duration = jsonObject.optLong("duration");
		_name = jsonObject.getString("name");
	}

	private final DownstreamBuildReport _downstreamBuildReport;
	private final long _duration;
	private final String _name;

}