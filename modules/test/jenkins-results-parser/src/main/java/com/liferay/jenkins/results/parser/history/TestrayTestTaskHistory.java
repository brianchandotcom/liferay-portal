/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.history;

import com.liferay.jenkins.results.parser.TestTaskReport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class TestrayTestTaskHistory extends BaseTestTaskHistory {

	public void addTestTaskReport(TestTaskReport testTaskReport) {
		if ((testTaskReport == null) ||
			_testTaskReports.contains(testTaskReport)) {

			return;
		}

		_testTaskReports.add(testTaskReport);
	}

	@Override
	public long getAverageDuration() {
		if (_testTaskReports.isEmpty()) {
			return 0;
		}

		long totalDuration = 0;

		for (TestTaskReport testTaskReport : _testTaskReports) {
			totalDuration += testTaskReport.getOverheadDuration();
		}

		return totalDuration / _testTaskReports.size();
	}

	@Override
	public long getAverageTotalDuration() {
		if (_testTaskReports.isEmpty()) {
			return 0;
		}

		long totalDuration = 0;

		for (TestTaskReport testTaskReport : _testTaskReports) {
			totalDuration += testTaskReport.getDuration();
		}

		return totalDuration / _testTaskReports.size();
	}

	@Override
	public long getLongestDuration() {
		long longestDuration = 0L;

		for (TestTaskReport testTaskReport : _testTaskReports) {
			if (longestDuration <= testTaskReport.getDuration()) {
				longestDuration = testTaskReport.getDuration();
			}
		}

		return longestDuration;
	}

	@Override
	public long getTestTaskCount() {
		return _testTaskReports.size();
	}

	protected TestrayTestTaskHistory(
		BatchHistory batchHistory, String testTaskName) {

		super(batchHistory, testTaskName);
	}

	private final List<TestTaskReport> _testTaskReports = new ArrayList<>();

}