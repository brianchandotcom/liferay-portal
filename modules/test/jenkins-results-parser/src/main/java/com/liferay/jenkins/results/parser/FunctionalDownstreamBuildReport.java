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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FunctionalDownstreamBuildReport extends BaseDownstreamBuildReport {

	@Override
	public long getTestExecutionDuration() {
		long testExecutionDuration = _getStopWatchRecordTestExecutionDuration();

		if (testExecutionDuration <= 0L) {
			return super.getTestExecutionDuration();
		}

		testExecutionDuration -= _getStopWatchRecordAppServerDuration();

		if (testExecutionDuration <= 0L) {
			return super.getTestExecutionDuration();
		}

		return testExecutionDuration;
	}

	protected FunctionalDownstreamBuildReport(
		String batchName, JSONObject buildReportJSONObject,
		TopLevelBuildReport topLevelBuildReport) {

		super(batchName, buildReportJSONObject, topLevelBuildReport);
	}

	private long _getStopWatchRecordAppServerDuration() {
		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup == null) {
			return 0L;
		}

		long appServerDuration = 0L;

		for (int i = 0; i < 5; i++) {
			StopWatchRecord startAppServerStopWatchRecord =
				stopWatchRecordsGroup.get("start.app.server." + i);

			if (startAppServerStopWatchRecord != null) {
				appServerDuration +=
					startAppServerStopWatchRecord.getDuration();
			}

			StopWatchRecord stopAppServerStopWatchRecord =
				stopWatchRecordsGroup.get("stop.app.server." + i);

			if (stopAppServerStopWatchRecord != null) {
				appServerDuration += stopAppServerStopWatchRecord.getDuration();
			}
		}

		return appServerDuration;
	}

	private long _getStopWatchRecordTestExecutionDuration() {
		StopWatchRecordsGroup stopWatchRecordsGroup =
			getStopWatchRecordsGroup();

		if (stopWatchRecordsGroup == null) {
			return 0L;
		}

		StopWatchRecord stopWatchRecord = stopWatchRecordsGroup.get(
			"test.execution.duration");

		if (stopWatchRecord == null) {
			return 0L;
		}

		return stopWatchRecord.getDuration();
	}

}