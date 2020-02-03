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

package com.liferay.item.selector.taglib.servlet.taglib.util;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class RepositoryEntryBrowserTagUtil {

	public static String getOrderByCol(
		HttpServletRequest httpServletRequest,
		PortalPreferences portalPreferences) {

		String orderByCol = ParamUtil.getString(
			httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			portalPreferences.setValue(
				_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE,
				"order-by-col", orderByCol);
		}
		else {
			orderByCol = portalPreferences.getValue(
				_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE,
				"order-by-col", "title");
		}

		return orderByCol;
	}

	public static String getOrderByType(
		HttpServletRequest httpServletRequest,
		PortalPreferences portalPreferences) {

		String orderByType = ParamUtil.getString(
			httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			portalPreferences.setValue(
				_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE,
				"order-by-type", orderByType);
		}
		else {
			orderByType = portalPreferences.getValue(
				_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE,
				"order-by-type", "asc");
		}

		return orderByType;
	}

	private static final String
		_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE =
			"taglib_ui_repository_entry_browse_page";

}