/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.my.subscriptions.web.internal.dao.search;

import com.liferay.my.subscriptions.web.internal.util.MySubscriptionsUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.subscription.model.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Ambrín Chaudhary
 */
public class MySubscriptionsResultRowSplitter implements ResultRowSplitter {

	public MySubscriptionsResultRowSplitter(Locale locale) {
		_locale = locale;
	}

	@Override
	public List<ResultRowSplitterEntry> split(List<ResultRow> resultRows) {
		Map<String, List<ResultRow>> rowMap = new HashMap<>();

		for (ResultRow resultRow : resultRows) {
			Subscription subscription = (Subscription)resultRow.getObject();

			List<ResultRow> list = rowMap.computeIfAbsent(
				subscription.getClassName(), className -> new ArrayList<>());

			list.add(resultRow);
		}

		List<ResultRowSplitterEntry> resultRowSplitterEntries = new ArrayList<>(
			rowMap.size());

		for (Map.Entry<String, List<ResultRow>> entry : rowMap.entrySet()) {
			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					MySubscriptionsUtil.getAssetTypeDescription(
						_locale, entry.getKey()),
					entry.getValue()));
		}

		return resultRowSplitterEntries;
	}

	private final Locale _locale;

}