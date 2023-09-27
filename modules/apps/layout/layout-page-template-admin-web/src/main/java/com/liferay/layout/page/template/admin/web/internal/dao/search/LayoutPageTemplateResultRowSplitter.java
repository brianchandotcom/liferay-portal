/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.dao.search;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yurena Cabrera
 */
public class LayoutPageTemplateResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> layoutPageTemplateCollectionResultRows =
			new ArrayList<>();
		List<ResultRow> layoutPageTemplateEntryResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof LayoutPageTemplateCollection) {
				layoutPageTemplateCollectionResultRows.add(resultRow);
			}
			else {
				layoutPageTemplateEntryResultRows.add(resultRow);
			}
		}

		if (!layoutPageTemplateCollectionResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"folders", layoutPageTemplateCollectionResultRows));
		}

		if (!layoutPageTemplateEntryResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"display-pages", layoutPageTemplateEntryResultRows));
		}

		return resultRowSplitterEntries;
	}

}