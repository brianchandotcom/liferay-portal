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

package com.liferay.site.navigation.taglib.internal.servlet;

import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Bradford
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static final String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static final ServletContext getServletContext() {
		return _servletContext;
	}

	public static final SiteNavigationMenuItemType
		getSiteNavigationMenuItemType(String type) {

		return _siteNavigationMenuItemTypeRegistry.
			getSiteNavigationMenuItemType(type);
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Reference(unbind = "-")
	protected void setSiteNavigationMenuItemTypeRegistry(
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry) {

		_siteNavigationMenuItemTypeRegistry =
			siteNavigationMenuItemTypeRegistry;
	}

	private static ServletContext _servletContext;
	private static SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

}