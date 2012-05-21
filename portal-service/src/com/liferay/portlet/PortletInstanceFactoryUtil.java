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

package com.liferay.portlet;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Portlet;

import javax.portlet.PortletException;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletInstanceFactoryUtil {

	public static void clear(Portlet portlet) {
		getPortletInstanceFactory().clear(portlet);
	}

	public static void clear(Portlet portlet, boolean resetRemotePortletBag) {
		getPortletInstanceFactory().clear(portlet, resetRemotePortletBag);
	}

	public static InvokerPortlet create(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		return getPortletInstanceFactory().create(portlet, servletContext);
	}

	public static void delete(Portlet portlet) {
		getPortletInstanceFactory().delete(portlet);
	}

	public static void destroy(Portlet portlet) {
		getPortletInstanceFactory().destroy(portlet);
	}

	public static PortletInstanceFactory getPortletInstanceFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletInstanceFactoryUtil.class);

		return _portletInstanceFactory;
	}

	public void destroy() {

		// LPS-10473

	}

	public void setPortletInstanceFactory(
		PortletInstanceFactory portletInstanceFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletInstanceFactory = portletInstanceFactory;
	}

	private static PortletInstanceFactory _portletInstanceFactory;

}