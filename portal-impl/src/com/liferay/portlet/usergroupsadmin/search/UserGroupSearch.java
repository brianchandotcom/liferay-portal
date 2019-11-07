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

package com.liferay.portlet.usergroupsadmin.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Charles May
 */
public class UserGroupSearch extends SearchContainer<UserGroup> {

	public static final String EMPTY_RESULTS_MESSAGE =
		"no-user-groups-were-found";

	public static List<String> headerNames = new ArrayList<String>() {
		{
			add("name");
			add("description");
		}
	};
	public static Map<String, String> orderableHeaders =
		HashMapBuilder.<String, String>put(
			"description", "description"
		).put(
			"name", "name"
		).build();

	public UserGroupSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new UserGroupDisplayTerms(portletRequest),
			new UserGroupDisplayTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		UserGroupDisplayTerms displayTerms =
			(UserGroupDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			UserGroupDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			UserGroupDisplayTerms.NAME, displayTerms.getName());

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol");
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType");
			String portletId = PortletProviderUtil.getPortletId(
				User.class.getName(), PortletProvider.Action.VIEW);

			if (Validator.isNotNull(orderByCol) &&
				Validator.isNotNull(orderByType)) {

				preferences.setValue(
					portletId, "user-groups-order-by-col", orderByCol);
				preferences.setValue(
					portletId, "user-groups-order-by-type", orderByType);
			}
			else {
				orderByCol = preferences.getValue(
					portletId, "user-groups-order-by-col", "name");
				orderByType = preferences.getValue(
					portletId, "user-groups-order-by-type", "asc");
			}

			OrderByComparator<UserGroup> orderByComparator =
				UsersAdminUtil.getUserGroupOrderByComparator(
					orderByCol, orderByType);

			setOrderableHeaders(orderableHeaders);
			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error("Unable to initialize user group search", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupSearch.class);

}