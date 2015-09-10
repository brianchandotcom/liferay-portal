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

package com.liferay.dynamic.data.lists.form.web.search;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormAdminPortletUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class RecordSearch extends SearchContainer<DDLRecord> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-entries-were-found";

	public static Map<String, String> orderableHeaders = new HashMap<>();

	static {
		orderableHeaders.put("modified-date", "modified-date");
	}

	public RecordSearch(
		PortletRequest portletRequest, PortletURL iteratorURL,
		List<String> headerNames) {

		super(
			portletRequest, new DisplayTerms(portletRequest), null,
			DEFAULT_CUR_PARAM, DEFAULT_DELTA, iteratorURL, headerNames,
			EMPTY_RESULTS_MESSAGE);

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
					"view-entries-order-by-col", orderByCol);
				preferences.setValue(
					DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
					"view-entries-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
					"view-entries-order-by-col", "id");
				orderByType = preferences.getValue(
					DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
					"view-entries-order-by-type", "asc");
			}

			OrderByComparator<DDLRecord> orderByComparator =
				DDLFormAdminPortletUtil.getRecordOrderByComparator(
					orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(RecordSearch.class);

}