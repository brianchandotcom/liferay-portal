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

import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author José Manuel Navarro
 */
public interface Breadcrumb {

	public BreadcrumbEntry getCurrentGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception;

	public BreadcrumbEntry getGuestGroupBreadcrumbEntry(
			ThemeDisplay themeDisplay)
		throws Exception;

	public List<BreadcrumbEntry> getLayoutBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception;

	public List<BreadcrumbEntry> getParentGroupBreadcrumbEntries(
			ThemeDisplay themeDisplay)
		throws Exception;

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries(
		HttpServletRequest request);

}