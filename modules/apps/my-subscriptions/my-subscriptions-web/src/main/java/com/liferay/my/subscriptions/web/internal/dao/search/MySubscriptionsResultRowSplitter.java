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

package com.liferay.my.subscriptions.web.internal.dao.search;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
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
			String subscriptionHeader = ResourceActionsUtil.getModelResource(
				_locale, entry.getKey());

			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(
					subscriptionHeader, entry.getValue()));
		}

		return resultRowSplitterEntries;
	}

	private final Locale _locale;

}