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

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Alejandro Tardín
 */
public class RedirectNotFoundEntrySearch
	extends SearchContainer<RedirectNotFoundEntry> {

	public RedirectNotFoundEntrySearch(
		PortletRequest portletRequest, PortletResponse portletResponse,
		PortletURL iteratorURL, String searchContainerId) {

		super(portletRequest, iteratorURL, null, _EMPTY_RESULTS_MESSAGE);

		setId(searchContainerId);
		setOrderByCol(
			SearchOrderByUtil.getOrderByCol(
				portletRequest, RedirectPortletKeys.REDIRECT,
				"redirect-not-found-entries-order-by-col", "requests"));
		setOrderByType(
			SearchOrderByUtil.getOrderByType(
				portletRequest, RedirectPortletKeys.REDIRECT,
				"redirect-not-found-entries-order-by-type", "asc"));
		setRowChecker(new EmptyOnClickRowChecker(portletResponse));
	}

	private static final String _EMPTY_RESULTS_MESSAGE =
		"all-your-pages-are-connected-or-redirected";

}