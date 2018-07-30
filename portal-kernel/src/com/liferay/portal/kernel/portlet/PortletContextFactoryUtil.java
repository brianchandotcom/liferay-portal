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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Michael C. Han
 */
public class PortletContextFactoryUtil {

	public static PortletContext create(
		Portlet portlet, ServletContext servletContext) {

		return getPortletContextFactory().create(portlet, servletContext);
	}

	public static PortletContext createUntrackedInstance(
		Portlet portlet, ServletContext servletContext) {

		return getPortletContextFactory().createUntrackedInstance(
			portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		getPortletContextFactory().destroy(portlet);
	}

	public static PortletContextFactory getPortletContextFactory() {
		return _portletContextFactory;
	}

	public void setPortletContextFactory(
		PortletContextFactory portletContextFactory) {

		_portletContextFactory = portletContextFactory;
	}

	private static PortletContextFactory _portletContextFactory;

}