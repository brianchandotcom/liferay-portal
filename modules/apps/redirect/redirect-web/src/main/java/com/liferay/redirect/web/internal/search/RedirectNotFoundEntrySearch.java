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

package com.liferay.redirect.web.internal.search;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntrySearch
	extends SearchContainer<RedirectNotFoundEntry> {

	public RedirectNotFoundEntrySearch(
		PortletRequest portletRequest, PortletURL iteratorURL,
		String searchContainerId) {

		super(portletRequest, iteratorURL, null, _EMPTY_RESULTS_MESSAGE);

		PortalPreferences preferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String portletId = RedirectPortletKeys.REDIRECT;

		String orderByCol = ParamUtil.getString(portletRequest, "orderByCol");
		String orderByType = ParamUtil.getString(portletRequest, "orderByType");

		if (Validator.isNotNull(orderByCol)) {
			preferences.setValue(
				portletId, "redirect-not-found-entries-order-by-col",
				orderByCol);
		}
		else {
			orderByCol = preferences.getValue(
				portletId, "redirect-not-found-entries-order-by-col",
				"requests");
		}

		if (Validator.isNotNull(orderByType)) {
			preferences.setValue(
				portletId, "redirect-not-found-entries-order-by-type",
				orderByType);
		}
		else {
			orderByType = preferences.getValue(
				portletId, "redirect-not-found-entries-order-by-type", "asc");
		}

		setId(searchContainerId);
		setOrderByCol(orderByCol);
		setOrderByType(orderByType);
	}

	private static final String _EMPTY_RESULTS_MESSAGE =
		"all-your-pages-are-connected-or-redirected";

}