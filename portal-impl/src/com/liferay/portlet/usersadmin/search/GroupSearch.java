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

package com.liferay.portlet.usersadmin.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.GroupDescriptiveNameComparator;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.kernel.util.comparator.GroupTypeComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupSearch extends SearchContainer<Group> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-sites-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("name");
			add("type");
		}
	};
	public static Map<String, String> orderableHeaders =
		HashMapBuilder.<String, String>put(
			"name", "name"
		).put(
			"type", "type"
		).build();

	public GroupSearch(PortletRequest portletRequest, PortletURL iteratorURL) {
		super(
			portletRequest, new GroupDisplayTerms(portletRequest),
			new GroupSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		GroupDisplayTerms displayTerms = (GroupDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			GroupDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			GroupDisplayTerms.NAME, displayTerms.getName());

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
					portletId, "groups-order-by-col", orderByCol);
				preferences.setValue(
					portletId, "groups-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					portletId, "groups-order-by-col", "name");
				orderByType = preferences.getValue(
					portletId, "groups-order-by-type", "asc");
			}

			Locale locale = LocaleUtil.getDefault();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				locale = themeDisplay.getLocale();
			}

			OrderByComparator<Group> orderByComparator =
				_getGroupOrderByComparator(orderByCol, orderByType, locale);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error("Unable to initialize group search", e);
		}
	}

	private OrderByComparator<Group> _getGroupOrderByComparator(
		String orderByCol, String orderByType, Locale locale) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		if (orderByCol.equals("descriptive-name")) {
			return new GroupDescriptiveNameComparator(orderByAsc, locale);
		}
		else if (orderByCol.equals("name")) {
			return new GroupNameComparator(orderByAsc, locale);
		}
		else if (orderByCol.equals("type")) {
			return new GroupTypeComparator(orderByAsc);
		}

		return new GroupNameComparator(orderByAsc, locale);
	}

	private static final Log _log = LogFactoryUtil.getLog(GroupSearch.class);

}