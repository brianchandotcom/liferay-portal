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
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class RepositoryEntryBrowserTagUtil {

	public static String getOrderByCol(
		HttpServletRequest httpServletRequest,
		PortalPreferences portalPreferences) {

		return SearchOrderByUtil.getOrderByCol(
			httpServletRequest,
			_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE, "title");
	}

	public static String getOrderByType(
		HttpServletRequest httpServletRequest,
		PortalPreferences portalPreferences) {

		return SearchOrderByUtil.getOrderByType(
			httpServletRequest,
			_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE, "asc");
	}

	private static final String
		_TAGLIB_UI_REPOSITORY_ENTRY_BROWSER_PAGE_NAMESPACE =
			"taglib_ui_repository_entry_browse_page";

}