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

package com.liferay.portal.workflow.kaleo.designer.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class KaleoDefinitionVersionResultRowSplitter
	implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> kaleoDefinitionPublishedResultRows = new ArrayList<>();
		List<ResultRow> kaleoDefinitionNotPublishedResultRows =
			new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				(KaleoDefinitionVersion)resultRow.getObject();

			try {
				KaleoDefinition kaleoDefinition =
					kaleoDefinitionVersion.getKaleoDefinition();

				if (kaleoDefinition.isActive()) {
					kaleoDefinitionPublishedResultRows.add(resultRow);
				}
				else {
					kaleoDefinitionNotPublishedResultRows.add(resultRow);
				}
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				kaleoDefinitionNotPublishedResultRows.add(resultRow);
			}
		}

		if (!kaleoDefinitionPublishedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"published", kaleoDefinitionPublishedResultRows));
		}

		if (!kaleoDefinitionNotPublishedResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"not-published", kaleoDefinitionNotPublishedResultRows));
		}

		return resultRowSplitterEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionResultRowSplitter.class);

}