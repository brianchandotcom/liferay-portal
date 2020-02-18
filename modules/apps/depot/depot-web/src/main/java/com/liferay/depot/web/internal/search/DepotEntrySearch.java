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

package com.liferay.depot.web.internal.search;

import com.liferay.depot.model.DepotEntry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class DepotEntrySearch extends SearchContainer<DepotEntry> {

	public DepotEntrySearch(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortletURL iteratorURL, String searchContainerId) {

		super(
			portletRequest, iteratorURL, _headerNames, _EMPTY_RESULTS_MESSAGE);

		PortalPreferences preferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String portletId = PortletProviderUtil.getPortletId(
			User.class.getName(), PortletProvider.Action.VIEW);

		String orderByCol = ParamUtil.getString(portletRequest, "orderByCol");
		String orderByType = ParamUtil.getString(portletRequest, "orderByType");

		if (Validator.isNotNull(orderByCol) &&
			Validator.isNotNull(orderByType)) {

			preferences.setValue(
				portletId, "depot-entries-order-by-col", orderByCol);
			preferences.setValue(
				portletId, "depot-entries-order-by-type", orderByType);
		}
		else {
			orderByCol = preferences.getValue(
				portletId, "depot-entries-order-by-col", "name");
			orderByType = preferences.getValue(
				portletId, "depot-entries-order-by-type", "asc");
		}

		setId(searchContainerId);
		setOrderableHeaders(_orderableHeaders);
		setOrderByCol(orderByCol);
		setOrderByType(orderByType);
		setRowChecker(new EmptyOnClickRowChecker(portletResponse));
	}

	private static final String _EMPTY_RESULTS_MESSAGE =
		"no-asset-libraries-were-found";

	private static List<String> _headerNames = Arrays.asList("name");
	private static Map<String, String> _orderableHeaders = HashMapBuilder.put(
		"name", "name"
	).build();

}