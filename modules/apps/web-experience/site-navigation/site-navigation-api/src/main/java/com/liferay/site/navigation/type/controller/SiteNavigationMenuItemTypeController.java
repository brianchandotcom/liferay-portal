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

package com.liferay.site.navigation.type.controller;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public interface SiteNavigationMenuItemTypeController {

	public JSONObject getEditContext(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception;

	public String getIcon();

	public String getLabel(SiteNavigationMenuItem siteNavigationMenuItem);

	public String getType();

	public String getURL(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception;

	public JSONObject getViewContext(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception;

	public boolean isBrowsable();

	public boolean isCheckViewPermission();

	public boolean isFullPageDisplayable();

	public boolean isParentable();

	public boolean isSitemapable();

	public boolean isURLFriendliable();

}