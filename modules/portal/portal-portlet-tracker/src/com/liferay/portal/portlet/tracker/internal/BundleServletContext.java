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

import com.liferay.portal.kernel.servlet.PluginContextListener;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class BundleServletContext {

	public BundleServletContext(
		ServletContext servletContext, BundlePortletApp bundlePortletApp,
		ClassLoader classLoader) {

		_servletContext = servletContext;
		_bundlePortletApp = bundlePortletApp;
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
		URL url = _bundlePortletApp.getResource(path);

		if (url != null) {
			String portletAppContextPath = _bundlePortletApp.getContextPath();
			String servletContextContextPath = _servletContext.getContextPath();

			int index = servletContextContextPath.length();

			path = portletAppContextPath.substring(index) + path;
		}

		return _servletContext.getRequestDispatcher(path);
	}

	public URL getResource(String name) {
		return _bundlePortletApp.getResource(name);
	}

	public InputStream getResourceAsStream(String path) {
		URL url = getResource(path);

		if (url != null) {
			try {
				return url.openStream();
			}
			catch (IOException ioe) {
			}
		}

		return null;
	}

	public Set<String> getResourcePaths(String path) {
		return _bundlePortletApp.getResourcePaths(path);
	}

	public String getServletContextName() {
		return _bundlePortletApp.getServletContextName();
	}

	private final BundlePortletApp _bundlePortletApp;
	private final ClassLoader _classLoader;
	private final ServletContext _servletContext;

}