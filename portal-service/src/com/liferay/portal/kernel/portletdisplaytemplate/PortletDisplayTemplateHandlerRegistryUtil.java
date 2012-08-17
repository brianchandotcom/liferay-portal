/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class PortletDisplayTemplateHandlerRegistryUtil {

	public static long[] getClassNameIds() {
		return getPortletDisplayTemplateRegistry().getClassNameIds();
	}

	public static PortletDisplayTemplateHandler
		getPortletDisplayTemplateHandler(long classNameId) {

		return getPortletDisplayTemplateRegistry().
			getPortletDisplayTemplateHandler(classNameId);
	}

	public static PortletDisplayTemplateHandler
		getPortletDisplayTemplateHandler(String className) {

		return getPortletDisplayTemplateRegistry().
			getPortletDisplayTemplateHandler(className);
	}

	public static List<PortletDisplayTemplateHandler>
		getPortletDisplayTemplateHandlers() {

		return getPortletDisplayTemplateRegistry().
			getPortletDisplayTemplateHandlers();
	}

	public static PortletDisplayTemplateHandlerRegistry
		getPortletDisplayTemplateRegistry() {

		PortalRuntimePermission.checkGetBeanProperty(
			PortletDisplayTemplateHandlerRegistryUtil.class);

		return _portletDisplayTemplateHandlerRegistry;
	}

	public static void register(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler) {

		getPortletDisplayTemplateRegistry().register(
			portletDisplayTemplateHandler);
	}

	public static void unregister(
		PortletDisplayTemplateHandler portletDisplayTemplateHandler) {

		getPortletDisplayTemplateRegistry().unregister(
			portletDisplayTemplateHandler);
	}

	public void setPortletDisplayTemplateHandlerRegistry(
		PortletDisplayTemplateHandlerRegistry
			portletDisplayTemplateHandlerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletDisplayTemplateHandlerRegistry =
			portletDisplayTemplateHandlerRegistry;
	}

	private static PortletDisplayTemplateHandlerRegistry
		_portletDisplayTemplateHandlerRegistry;

}