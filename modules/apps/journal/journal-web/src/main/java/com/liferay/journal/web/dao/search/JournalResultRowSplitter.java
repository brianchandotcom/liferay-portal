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

package com.liferay.journal.web.dao.search;

import com.liferay.journal.model.JournalFolder;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class JournalResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<List<ResultRow>> split(List<ResultRow> resultRows) {
		List<List<ResultRow>> resultRowsList = new ArrayList<>();

		List<ResultRow> journalArticleResultRows = new ArrayList<>();
		List<ResultRow> journalFolderResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof JournalFolder) {
				journalFolderResultRows.add(resultRow);
			}
			else {
				journalArticleResultRows.add(resultRow);
			}
		}

		if (!journalFolderResultRows.isEmpty()) {
			resultRowsList.add(journalFolderResultRows);
		}

		if (!journalArticleResultRows.isEmpty()) {
			resultRowsList.add(journalArticleResultRows);
		}

		return resultRowsList;
	}

}