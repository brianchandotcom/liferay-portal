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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.subscription.model.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		List<ResultRowSplitterEntry> resultRowSplitterEntries =
			new ArrayList<>();

		Map<String, List<ResultRow>> rowMap = new HashMap<>();

		String subscriptionClassName = StringPool.BLANK;
		String subscriptionHeader = StringPool.BLANK;

		for (ResultRow resultRow : resultRows) {
			Subscription subscription = (Subscription)resultRow.getObject();

			if (!subscriptionClassName.equals(subscription.getClassName())) {
				subscriptionClassName = subscription.getClassName();

				subscriptionHeader = ResourceActionsUtil.getModelResource(
					_locale, subscriptionClassName);

				rowMap.put(subscriptionHeader, new ArrayList<ResultRow>());
			}

			List<ResultRow> list = rowMap.get(subscriptionHeader);

			list.add(resultRow);
		}

		Iterator<Map.Entry<String, List<ResultRow>>> iterator =
			rowMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, List<ResultRow>> entry = iterator.next();

			resultRowSplitterEntries.add(
				new ResultRowSplitterEntry(entry.getKey(), entry.getValue()));
		}

		return resultRowSplitterEntries;
	}

	private final Locale _locale;

}