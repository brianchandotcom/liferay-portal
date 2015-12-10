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

package com.liferay.bookmarks.web.dao.search;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class BookmarksResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<List<ResultRow>> split(List<ResultRow> resultRows) {
		List<List<ResultRow>> resultRowsList = new ArrayList<>();

		List<ResultRow> entryResultRows = new ArrayList<>();
		List<ResultRow> folderResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof BookmarksFolder) {
				folderResultRows.add(resultRow);
			}
			else {
				entryResultRows.add(resultRow);
			}
		}

		if (!folderResultRows.isEmpty()) {
			resultRowsList.add(folderResultRows);
		}

		if (!entryResultRows.isEmpty()) {
			resultRowsList.add(entryResultRows);
		}

		return resultRowsList;
	}

}