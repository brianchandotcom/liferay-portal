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

package com.liferay.staging.processes.web.internal.dao.search;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Máté Thurzó
 */
public class PublishResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> publishCompletedResultRows = new ArrayList<>();
		List<ResultRow> publishInProgressResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			BackgroundTask backgroundTask =
				(BackgroundTask)resultRow.getObject();

			if (backgroundTask.isInProgress()) {
				publishInProgressResultRows.add(resultRow);
			}
			else {
				publishCompletedResultRows.add(resultRow);
			}
		}

		if (!publishInProgressResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"current", publishInProgressResultRows));
		}

		if (!publishCompletedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"previous", publishCompletedResultRows));
		}

		return resultRowSplitterEntries;
	}

}