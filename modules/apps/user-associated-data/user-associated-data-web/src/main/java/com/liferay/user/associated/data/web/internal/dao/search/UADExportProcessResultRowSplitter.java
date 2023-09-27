/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.dao.search;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
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

				resultRow.setCssClass(
					resultRow.getCssClass() + "export-process-status-failed");

				failedProcesses.add(resultRow);
			}
			else if (backgroundTask.getStatus() ==
						BackgroundTaskConstants.STATUS_SUCCESSFUL) {

				resultRow.setCssClass(
					resultRow.getCssClass() +
						"export-process-status-successful");

				successfulProcesses.add(resultRow);
			}
			else {
				resultRow.setCssClass(
					resultRow.getCssClass() +
						"export-process-status-in-progress");

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