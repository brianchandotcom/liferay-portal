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

package com.liferay.commerce.bom.admin.web.internal.dao.search;

import com.liferay.commerce.bom.model.CommerceBOMFolder;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceBOMAdminResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> commerceBOMDefinitionResultRows = new ArrayList<>();
		List<ResultRow> commerceBOMFolderResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof CommerceBOMFolder) {
				commerceBOMFolderResultRows.add(resultRow);
			}
			else {
				commerceBOMDefinitionResultRows.add(resultRow);
			}
		}

		if (!commerceBOMFolderResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"folders", commerceBOMFolderResultRows));
		}

		if (!commerceBOMDefinitionResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"definitions", commerceBOMDefinitionResultRows));
		}

		return resultRowSplitterEntries;
	}

}