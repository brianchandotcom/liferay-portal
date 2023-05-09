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

package com.liferay.user.associated.data.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Samuel Trong Tran
 */
public class UADHierarchyResultRowSplitter implements ResultRowSplitter {

	public UADHierarchyResultRowSplitter(
		Locale locale, UADDisplay<?>[] uadDisplays) {

		_locale = locale;
		_uadDisplays = uadDisplays;
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		Map<String, List<ResultRow>> classResultRowsMap = new HashMap<>();

		for (UADDisplay<?> uadDisplay : _uadDisplays) {
			classResultRowsMap.put(uadDisplay.getTypeKey(), new ArrayList<>());
		}

		for (ResultRow resultRow : resultRows) {
			UADEntity<?> uadEntity = (UADEntity<?>)resultRow.getObject();

			if (classResultRowsMap.containsKey(uadEntity.getTypeKey())) {
				List<ResultRow> classResultRows = classResultRowsMap.get(
					uadEntity.getTypeKey());

				classResultRows.add(resultRow);
			}
		}

		for (UADDisplay<?> uadDisplay : _uadDisplays) {
			List<ResultRow> classResultRows = classResultRowsMap.get(
				uadDisplay.getTypeKey());

			if (!classResultRows.isEmpty()) {
				resultRowSplitterEntries.add(
					new ResultRowSplitterEntry(
						uadDisplay.getTypeName(_locale), classResultRows));
			}
		}

		return resultRowSplitterEntries;
	}

	private final Locale _locale;
	private final UADDisplay<?>[] _uadDisplays;

}