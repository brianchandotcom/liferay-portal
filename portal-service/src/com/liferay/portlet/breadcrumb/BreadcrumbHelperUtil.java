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

package com.liferay.portlet.breadcrumb;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author José Manuel Navarro
 */
public class BreadcrumbHelperUtil {

	public static final long ENTRY_TYPE_ANY = BreadcrumbEntry.ENTRY_TYPE_ANY;

	public static final long ENTRY_TYPE_CURRENT_GROUP =
		BreadcrumbEntry.ENTRY_TYPE_CURRENT_GROUP;

	public static final long ENTRY_TYPE_GUEST_GROUP =
		BreadcrumbEntry.ENTRY_TYPE_GUEST_GROUP;

	public static final long ENTRY_TYPE_LAYOUT =
		BreadcrumbEntry.ENTRY_TYPE_LAYOUT;

	public static final long ENTRY_TYPE_PARENT_GROUP =
		BreadcrumbEntry.ENTRY_TYPE_PARENT_GROUP;

	public static final long ENTRY_TYPE_PORTLET =
		BreadcrumbEntry.ENTRY_TYPE_PORTLET;

	public static List<BreadcrumbEntry> getBreadcrumbEntries(
			HttpServletRequest request, long typeMask)
		throws Exception {

		return getBreadcrumbHelper().getBreadcrumbEntries(request, typeMask);
	}

	public static BreadcrumbHelper getBreadcrumbHelper() {
		PortalRuntimePermission.checkGetBeanProperty(
			BreadcrumbHelperUtil.class);

		return _breadcrumbHelper;
	}

	public static BreadcrumbEntry getGuestGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumbHelper().getGuestGroupBreadcrumbEntry(themeDisplay);
	}

	public static List<BreadcrumbEntry> getLayoutBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumbHelper().getLayoutBreadcrumbEntries(themeDisplay);
	}

	public static List<BreadcrumbEntry> getParentGroupBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumbHelper().getParentGroupBreadcrumbEntries(
			themeDisplay);
	}

	public static List<BreadcrumbEntry> getPortletBreadcrumbEntries(
		HttpServletRequest request) {

		return getBreadcrumbHelper().getPortletBreadcrumbEntries(request);
	}

	public static BreadcrumbEntry getScopeGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception {

		return getBreadcrumbHelper().getScopeGroupBreadcrumbEntry(themeDisplay);
	}

	public void setBreadcrumbHelper(BreadcrumbHelper breadcrumbHelper) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_breadcrumbHelper = breadcrumbHelper;
	}

	private static BreadcrumbHelper _breadcrumbHelper;

}