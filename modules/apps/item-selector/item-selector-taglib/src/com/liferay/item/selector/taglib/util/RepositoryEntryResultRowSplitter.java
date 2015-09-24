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

package com.liferay.item.selector.taglib.util;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.repository.model.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergio González
 */
public class RepositoryEntryResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<List<ResultRow>> split(List<ResultRow> resultRows) {
		List<List<ResultRow>> resultRowList = new ArrayList<>();

		List<ResultRow> fileEntriesResultRows = new ArrayList<>();
		List<ResultRow> folderResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof Folder) {
				folderResultRows.add(resultRow);
			}
			else {
				fileEntriesResultRows.add(resultRow);
			}
		}

		if (!folderResultRows.isEmpty()) {
			resultRowList.add(folderResultRows);
		}

		if (!fileEntriesResultRows.isEmpty()) {
			resultRowList.add(fileEntriesResultRows);
		}

		return resultRowList;
	}

}