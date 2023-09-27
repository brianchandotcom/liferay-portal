/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.dao.search;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Máté Thurzó
 */
public class ExportImportResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> exportCompletedResultRows = new ArrayList<>();
		List<ResultRow> exportInProgressResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			BackgroundTask backgroundTask =
				(BackgroundTask)resultRow.getObject();

			if (backgroundTask.isInProgress()) {
				exportInProgressResultRows.add(resultRow);
			}
			else {
				exportCompletedResultRows.add(resultRow);
			}
		}

		if (!exportInProgressResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"current", exportInProgressResultRows));
		}

		if (!exportCompletedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"previous", exportCompletedResultRows));
		}

		return resultRowSplitterEntries;
	}

}