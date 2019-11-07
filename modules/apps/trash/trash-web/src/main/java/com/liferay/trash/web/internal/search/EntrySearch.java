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

package com.liferay.trash.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.trash.util.comparator.EntryTypeComparator;
import com.liferay.trash.util.comparator.EntryUserNameComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * Provides a <code>SearchContainer</code> (in
 * <code>com.liferay.portal.kernel</code>) implementation for
 * <code>TrashEntry</code> (in <code>com.liferay.portal.kernel</code>) objects.
 * The search container is used to show the list of objects using the
 * <code>SearchIteratorTag</code> (in <code>com.liferay.taglib.ui</code>).
 *
 * @author Sergio González
 */
public class EntrySearch extends SearchContainer<TrashEntry> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"the-recycle-bin-is-empty";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("name");
			add("type");
			add("removed-date");
			add("removed-by");
		}
	};
	public static Map<String, String> orderableHeaders =
		HashMapBuilder.<String, String>put(
			"name", "name"
		).put(
			"removed-by", "removed-by"
		).put(
			"removed-date", "removed-date"
		).put(
			"type", "type"
		).build();

	public EntrySearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new EntryDisplayTerms(portletRequest),
			new EntrySearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String portletId = PortletProviderUtil.getPortletId(
				User.class.getName(), PortletProvider.Action.VIEW);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					portletId, "entries-order-by-col", orderByCol);
				preferences.setValue(
					portletId, "entries-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					portletId, "entries-order-by-col", "removed-date");
				orderByType = preferences.getValue(
					portletId, "entries-order-by-type", "asc");
			}

			OrderByComparator<TrashEntry> orderByComparator =
				_getEntryOrderByComparator(orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error("Unable to initialize entry search", e);
		}
	}

	private OrderByComparator<TrashEntry> _getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<TrashEntry> orderByComparator = null;

		if (orderByCol.equals("removed-by")) {
			orderByComparator = new EntryUserNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("removed-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new EntryTypeComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static final Log _log = LogFactoryUtil.getLog(EntrySearch.class);

}