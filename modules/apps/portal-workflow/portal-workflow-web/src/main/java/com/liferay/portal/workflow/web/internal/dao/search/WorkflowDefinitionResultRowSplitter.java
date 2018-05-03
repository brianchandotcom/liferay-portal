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