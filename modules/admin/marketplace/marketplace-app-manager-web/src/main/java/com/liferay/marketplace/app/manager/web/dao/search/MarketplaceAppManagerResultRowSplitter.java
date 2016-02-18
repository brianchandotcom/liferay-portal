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

package com.liferay.marketplace.app.manager.web.dao.search;

import com.liferay.marketplace.app.manager.web.util.AppDisplay;
import com.liferay.marketplace.app.manager.web.util.ModuleGroupDisplay;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Park
 */
public class MarketplaceAppManagerResultRowSplitter
	implements ResultRowSplitter {

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		List<ResultRow> appSuiteResultRows = new ArrayList<>();
		List<ResultRow> appResultRows = new ArrayList<>();
		List<ResultRow> moduleResultRows = new ArrayList<>();

		for (ResultRow resultRow : resultRows) {
			Object object = resultRow.getObject();

			if (object instanceof AppDisplay) {
				AppDisplay appDisplay = (AppDisplay)object;

				if (appDisplay.hasModuleGroups()) {
					appSuiteResultRows.add(resultRow);
				}
				else {
					appResultRows.add(resultRow);
				}
			}
			else if (object instanceof ModuleGroupDisplay) {
				appResultRows.add(resultRow);
			}
			else {
				moduleResultRows.add(resultRow);
			}
		}

		if (!appSuiteResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("app-suites", appSuiteResultRows));
		}

		if (!appResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("apps", appResultRows));
		}

		if (!moduleResultRows.isEmpty()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry("modules", moduleResultRows));
		}

		return resultRowSplitterEntries;
	}

}