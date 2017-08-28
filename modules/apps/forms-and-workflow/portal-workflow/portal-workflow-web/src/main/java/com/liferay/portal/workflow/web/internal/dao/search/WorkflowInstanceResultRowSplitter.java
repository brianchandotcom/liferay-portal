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

package com.liferay.portal.workflow.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.workflow.WorkflowInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class WorkflowInstanceResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> workflowInstanceCompletedResultRows = new ArrayList<>();
		List<ResultRow> workflowInstancePendingResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			WorkflowInstance workflowInstance =
				(WorkflowInstance)resultRow.getObject();

			if (workflowInstance.isComplete()) {
				workflowInstanceCompletedResultRows.add(resultRow);
			}
			else {
				workflowInstancePendingResultRows.add(resultRow);
			}
		}

		if (!workflowInstancePendingResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"pending", workflowInstancePendingResultRows));
		}

		if (!workflowInstanceCompletedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"completed", workflowInstanceCompletedResultRows));
		}

		return resultRowSplitterEntries;
	}

}