/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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