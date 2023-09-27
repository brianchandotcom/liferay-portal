/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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