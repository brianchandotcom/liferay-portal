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

package com.liferay.calendar.web.internal.search;

import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.util.comparator.CalendarResourceCodeComparator;
import com.liferay.calendar.util.comparator.CalendarResourceNameComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 */
public class CalendarResourceSearch extends SearchContainer<CalendarResource> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-calendar-resources-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("code");
			add("name");
			add("description");
			add("active");
		}
	};
	public static Map<String, String> orderableHeaders =
		new HashMap<String, String>() {
			{
				put("code", "code");
				put("name", "name");
			}
		};

	public CalendarResourceSearch(
		PortletRequest portletRequest, String curParam,
		PortletURL iteratorURL) {

		super(
			portletRequest, new CalendarResourceDisplayTerms(portletRequest),
			new CalendarResourceDisplayTerms(portletRequest), curParam,
			DEFAULT_DELTA, iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		CalendarResourceDisplayTerms displayTerms =
			(CalendarResourceDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.ACTIVE,
			String.valueOf(displayTerms.isActive()));
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.CODE, displayTerms.getCode());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			CalendarResourceDisplayTerms.SCOPE,
			String.valueOf(displayTerms.getScope()));

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
					CalendarPortletKeys.CALENDAR,
					"users-resources-order-by-col", orderByCol);
				preferences.setValue(
					CalendarPortletKeys.CALENDAR,
					"users-resources-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					CalendarPortletKeys.CALENDAR,
					"users-resources-order-by-col", "last-name");
				orderByType = preferences.getValue(
					CalendarPortletKeys.CALENDAR,
					"users-resources-order-by-type", "asc");
			}

			OrderByComparator<CalendarResource> orderByComparator =
				_getOrderByComparator(orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error("Unable to initialize calendar resource search", e);
		}
	}

	private OrderByComparator<CalendarResource> _getOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<CalendarResource> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new CalendarResourceNameComparator(orderByAsc);
		}
		else {
			orderByComparator = new CalendarResourceCodeComparator(orderByAsc);
		}

		return orderByComparator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarResourceSearch.class);

}