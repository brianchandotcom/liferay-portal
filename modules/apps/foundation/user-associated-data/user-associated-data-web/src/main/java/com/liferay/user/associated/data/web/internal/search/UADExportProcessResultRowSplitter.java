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

package com.liferay.user.associated.data.web.internal.search;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pei-Jung Lan
 */
public class UADExportProcessResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> failedProcesses = new ArrayList<>();
		List<ResultRow> inProgressProcesses = new ArrayList<>();
		List<ResultRow> successfulProcesses = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			BackgroundTask backgroundTask =
				(BackgroundTask)resultRow.getObject();

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_FAILED) {

				failedProcesses.add(resultRow);
			}
			else if (backgroundTask.getStatus() ==
						BackgroundTaskConstants.STATUS_SUCCESSFUL) {

				successfulProcesses.add(resultRow);
			}
			else {
				inProgressProcesses.add(resultRow);
			}
		}

		if (!inProgressProcesses.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("in-progress", inProgressProcesses));
		}

		if (!successfulProcesses.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("successful", successfulProcesses));
		}

		if (!failedProcesses.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("failed", failedProcesses));
		}

		return resultRowSplitterEntries;
	}

}