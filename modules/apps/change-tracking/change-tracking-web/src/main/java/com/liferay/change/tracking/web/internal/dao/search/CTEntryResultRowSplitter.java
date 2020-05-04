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

package com.liferay.change.tracking.web.internal.dao.search;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.util.StringComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Samuel Trong Tran
 */
public class CTEntryResultRowSplitter implements ResultRowSplitter {

	public CTEntryResultRowSplitter(
		CTDisplayRendererRegistry ctDisplayRendererRegistry, Locale locale) {

		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_locale = locale;
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		for (ResultRow resultRow : resultRows) {
			CTEntry ctEntry = (CTEntry)resultRow.getObject();

			List<ResultRow> classResultRows = _resultRowsMap.computeIfAbsent(
				ctEntry.getModelClassNameId(), key -> new ArrayList<>());

			classResultRows.add(resultRow);
		}

		for (Map.Entry<Long, List<ResultRow>> entry :
				_resultRowsMap.entrySet()) {

			long classNameId = entry.getKey();

			String typeName = _ctDisplayRendererRegistry.getTypeName(
				_locale, classNameId);

			_resultRowSplitterEntryMap.put(
				typeName,
				new ResultRowSplitterEntry(typeName, entry.getValue()));
		}

		List<String> typeNames = new ArrayList<>(
			_resultRowSplitterEntryMap.keySet());

		typeNames.sort(new StringComparator());

		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		for (String typeName : typeNames) {
			resultRowSplitterEntries.add(
				_resultRowSplitterEntryMap.get(typeName));
		}

		return resultRowSplitterEntries;
	}

	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final Locale _locale;
	private final Map<Long, List<ResultRow>> _resultRowsMap = new HashMap<>();
	private final Map<String, ResultRowSplitterEntry>
		_resultRowSplitterEntryMap = new HashMap<>();

}