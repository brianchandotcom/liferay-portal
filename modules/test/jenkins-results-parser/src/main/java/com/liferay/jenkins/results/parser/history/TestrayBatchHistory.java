/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class TestrayBatchHistory extends BaseBatchHistory {

	public void addBuildReport(DownstreamBuildReport downstreamBuildReport) {
		if ((downstreamBuildReport == null) ||
			_downstreamBuildReports.contains(downstreamBuildReport)) {

			return;
		}

		_downstreamBuildReports.add(downstreamBuildReport);
	}

	public long getAverageDuration() {
		long count = 0;
		long totalDuration = 0;

		for (DownstreamBuildReport downstreamBuildReport :
				_downstreamBuildReports) {

			long duration = downstreamBuildReport.getDuration();

			if (duration > _MAXIMUM_BATCH_DURATION) {
				continue;
			}

			count++;
			totalDuration = totalDuration + duration;
		}

		if (count == 0) {
			return 0;
		}

		return totalDuration / count;
	}

	protected TestrayBatchHistory(String batchName, JobHistory jobHistory) {
		super(batchName, jobHistory);
	}

	private static final long _MAXIMUM_BATCH_DURATION = 24 * 60 * 60 * 1000;

	private final List<DownstreamBuildReport> _downstreamBuildReports =
		new ArrayList<>();

}