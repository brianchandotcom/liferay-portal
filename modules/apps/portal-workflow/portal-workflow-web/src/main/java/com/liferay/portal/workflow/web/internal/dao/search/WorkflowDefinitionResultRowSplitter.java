/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class WorkflowDefinitionResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> workflowDefinitionPublishedResultRows =
			new ArrayList<>();
		List<ResultRow> workflowDefinitionNotPublishedResultRows =
			new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			WorkflowDefinition workflowDefinition =
				(WorkflowDefinition)resultRow.getObject();

			if (workflowDefinition.isActive()) {
				workflowDefinitionPublishedResultRows.add(resultRow);
			}
			else {
				workflowDefinitionNotPublishedResultRows.add(resultRow);
			}
		}

		if (!workflowDefinitionPublishedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"published", workflowDefinitionPublishedResultRows));
		}

		if (!workflowDefinitionNotPublishedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"not-published", workflowDefinitionNotPublishedResultRows));
		}

		return resultRowSplitterEntries;
	}

}