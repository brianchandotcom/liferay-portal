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

import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import javax.servlet.ServletContext;

/**
 * @author Michael Bradford
 */
public class ServletContextUtil {

	public static InfoItemServiceRegistry getInfoItemServiceRegistry() {
		return _infoItemServiceRegistrySnapshot.get();
	}

	public static PortletDisplayTemplate getPortletDisplayTemplate() {
		return _portletDisplayTemplateSnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	public static SiteNavigationMenuItemType getSiteNavigationMenuItemType(
		String type) {

		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry =
			_siteNavigationMenuItemTypeRegistrySnapshot.get();

		return siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
			type);
	}

	private static final Snapshot<InfoItemServiceRegistry>
		_infoItemServiceRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, InfoItemServiceRegistry.class);
	private static final Snapshot<PortletDisplayTemplate>
		_portletDisplayTemplateSnapshot = new Snapshot<>(
			ServletContextUtil.class, PortletDisplayTemplate.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.site.navigation.taglib)");
	private static final Snapshot<SiteNavigationMenuItemTypeRegistry>
		_siteNavigationMenuItemTypeRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, SiteNavigationMenuItemTypeRegistry.class);

}