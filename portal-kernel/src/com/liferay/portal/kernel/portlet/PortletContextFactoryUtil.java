/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return _portletContextFactory.create(portlet, servletContext);
	}

	public static PortletContext createUntrackedInstance(
		Portlet portlet, ServletContext servletContext) {

		return _portletContextFactory.createUntrackedInstance(
			portlet, servletContext);
	}

	public static void destroy(Portlet portlet) {
		_portletContextFactory.destroy(portlet);
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