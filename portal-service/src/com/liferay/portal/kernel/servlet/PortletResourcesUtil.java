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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Michael Bradford
 */
public class PortletResourcesUtil {

	public static ServletContext getPathServletContext(String path) {
		for (ServletContext context : _instance._getPortletResourcesList()) {
			if (path.startsWith(context.getContextPath())) {
				return context;
			}
		}

		return null;
	}

	public static URL getResource(ServletContext servletContext, String path) {
		path = PortalWebResourcesUtil.stripContextPath(servletContext, path);

		try {
			URL url = servletContext.getResource(path);

			if (url != null) {
				return url;
			}
		}
		catch (MalformedURLException murle) {
		}

		return null;
	}

	public static URL getResource(String path) {
		ServletContext servletContext = getPathServletContext(path);

		if (servletContext != null) {
			return getResource(servletContext, path);
		}

		return null;
	}

	private PortletResourcesUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			Portlet.class, new PortletResourcesServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Collection<ServletContext> _getPortletResourcesList() {
		return _portletResourcesMap.values();
	}

	private static final PortletResourcesUtil _instance =
		new PortletResourcesUtil();

	private final Map<ServiceReference<Portlet>, ServletContext>
		_portletResourcesMap = new ConcurrentHashMap<>();
	private final ServiceTracker<Portlet, Portlet> _serviceTracker;

	private class PortletResourcesServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<Portlet, Portlet> {

		@Override
		public Portlet addingService(
			ServiceReference<Portlet> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Portlet portlet = registry.getService(serviceReference);

			PortletApp portletApp = portlet.getPortletApp();

			ServletContext servletContext = portletApp.getServletContext();

			if (portletApp.isWARFile()) {
				_portletResourcesMap.put(serviceReference, servletContext);
			}

			return portlet;
		}

		@Override
		public void modifiedService(
			ServiceReference<Portlet> serviceReference, Portlet portlet) {
		}

		@Override
		public void removedService(
			ServiceReference<Portlet> serviceReference, Portlet portlet) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_portletResourcesMap.remove(serviceReference);
		}

	}

}