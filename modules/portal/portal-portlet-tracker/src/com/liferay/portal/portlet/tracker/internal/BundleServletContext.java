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

package com.liferay.portal.portlet.tracker.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.model.PortletApp;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class BundleServletContext {

	public BundleServletContext(
		ServletContext servletContext,
		ServletContextHelper servletContextHelper, ClassLoader classLoader) {

		_servletContext = servletContext;
		_servletContextHelper = servletContextHelper;
		_classLoader = classLoader;
	}

	public Object getAttribute(String name) {
		if (PluginContextListener.PLUGIN_CLASS_LOADER.equals(name)) {
			return _classLoader;
		}

		return _servletContext.getAttribute(name);
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		URL url = _servletContextHelper.getResource(path);

		if (url != null) {
			PortletApp portletApp = (PortletApp)_servletContextHelper;

			path = portletApp.getContextPath() + path;
		}

		return _servletContext.getRequestDispatcher(path);
	}

	public URL getResource(String name) {
		return _servletContextHelper.getResource(name);
	}

	public InputStream getResourceAsStream(String path) {
		URL url = getResource(path);

		if (url != null) {
			try {
				return url.openStream();
			}
			catch (IOException e) {
				// ignore
			}
		}

		return null;
	}

	public Set<String> getResourcePaths(String path) {
		return _servletContextHelper.getResourcePaths(path);
	}

	private static Log _log = LogFactoryUtil.getLog(BundleServletContext.class);

	private ClassLoader _classLoader;
	private ServletContextHelper _servletContextHelper;
	private ServletContext _servletContext;

}