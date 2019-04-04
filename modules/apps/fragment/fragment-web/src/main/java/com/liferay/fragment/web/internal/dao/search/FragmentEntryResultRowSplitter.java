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

package com.liferay.fragment.web.internal.dao.search;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryResultRowSplitter implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> fragmentEntrySectionsResultRows = new ArrayList<>();

		List<ResultRow> fragmentEntryComponentsResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			FragmentEntry fragmentEntry = (FragmentEntry)resultRow.getObject();

			if (fragmentEntry.getType() == FragmentConstants.TYPE_COMPONENT) {
				fragmentEntryComponentsResultRows.add(resultRow);
			}
			else {
				fragmentEntrySectionsResultRows.add(resultRow);
			}
		}

		if (!fragmentEntrySectionsResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"sections", fragmentEntrySectionsResultRows));
		}

		if (!fragmentEntryComponentsResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					"components", fragmentEntryComponentsResultRows));
		}

		return resultRowSplitterEntries;
	}

}