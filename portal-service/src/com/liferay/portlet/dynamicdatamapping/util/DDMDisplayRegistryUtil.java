/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Eduardo Garcia
 */
public class DDMDisplayRegistryUtil {

	public static DDMDisplay getDDMDisplay(String portletId) {
		return getDDMDisplayRegistry().getDDMDisplay(portletId);
	}

	public static DDMDisplayRegistry getDDMDisplayRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMDisplayRegistryUtil.class);

		return _ddmDisplayRegistry;
	}

	public static List<DDMDisplay> getDDMDisplays() {
		return getDDMDisplayRegistry().getDDMDisplays();
	}

	public static String[] getPortletIds() {
		return getDDMDisplayRegistry().getPortletIds();
	}

	public static void register(DDMDisplay ddmDisplay) {
		getDDMDisplayRegistry().register(ddmDisplay);
	}

	public static void unregister(DDMDisplay ddmDisplay) {
		getDDMDisplayRegistry().unregister(ddmDisplay);
	}

	public void setDDMDisplayRegistry(DDMDisplayRegistry ddmDisplayRegistry) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmDisplayRegistry = ddmDisplayRegistry;
	}

	private static DDMDisplayRegistry _ddmDisplayRegistry;

}