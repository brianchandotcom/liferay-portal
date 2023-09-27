/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
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