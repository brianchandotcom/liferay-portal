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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.portlet.PortletContextFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletContextFactoryImpl implements PortletContextFactory {

	@Override
	public PortletContext create(
		Portlet portlet, ServletContext servletContext) {

		Map<String, PortletContext> portletContexts = _pool.get(
			portlet.getRootPortletId());

		if (portletContexts == null) {
			portletContexts = new ConcurrentHashMap<>();

			_pool.put(portlet.getRootPortletId(), portletContexts);
		}

		PortletContext portletContext = null;

		if (portlet.isUndeployedPortlet()) {
			portletContexts.remove(portlet.getPortletId());
		}
		else {
			portletContext = portletContexts.get(portlet.getPortletId());
		}

		if (portletContext != null) {
			return portletContext;
		}

		PortletApp portletApp = portlet.getPortletApp();

		if (!portlet.isUndeployedPortlet() && portletApp.isWARFile()) {
			servletContext = portletApp.getServletContext();
		}

		portletContext = new PortletContextImpl(portlet, servletContext);

		portletContexts.put(portlet.getPortletId(), portletContext);

		return portletContext;
	}

	@Override
	public PortletContext createUntrackedInstance(
		Portlet portlet, ServletContext servletContext) {

		return new PortletContextImpl(portlet, servletContext);
	}

	@Override
	public void destroy(Portlet portlet) {
		_pool.remove(portlet.getRootPortletId());
	}

	private final Map<String, Map<String, PortletContext>> _pool =
		new ConcurrentHashMap<>();

}