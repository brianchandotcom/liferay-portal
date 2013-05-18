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

package com.liferay.portal.util;

import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ClassLoaderUtil {

	public static ClassLoader getAggregatedPluginsClassLoader(
		final String[] servletContextNames,
		final boolean addContextClassLoader) {

		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader> () {

				public ClassLoader run() {
					List<ClassLoader> classLoaders = new ArrayList<ClassLoader>(
						servletContextNames.length + 2);

					ClassLoader contextClassLoader = null;

					if (addContextClassLoader) {
						contextClassLoader = _getContextClassLoader();

						classLoaders.add(contextClassLoader);
					}

					for (String servletContextName : servletContextNames) {
						ClassLoader pluginClassLoader = _getPluginClassLoader(
							servletContextName);

						classLoaders.add(pluginClassLoader);
					}

					ClassLoader[] classloaders = classLoaders.toArray(
						new ClassLoader[classLoaders.size()]);

					return AggregateClassLoader.getAggregateClassLoader(
						classloaders);
				}
			}
		);
	}

	public static ClassLoader getClassLoader(final Class<?> clazz) {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					return clazz.getClassLoader();
				}

			}
		);
	}

	public static ClassLoader getContextClassLoader() {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					return _getContextClassLoader();
				}

			}
		);
	}

	public static ClassLoader getPluginClassLoader(
		final String servletContextName) {

		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader> () {

				public ClassLoader run() {
					ClassLoader pluginClassLoader = _getPluginClassLoader(
						servletContextName);

					return pluginClassLoader;
				}
			}
		);
	}

	public static ClassLoader getPortalClassLoader() {
		return AccessController.doPrivileged(
			new PrivilegedAction<ClassLoader>() {

				public ClassLoader run() {
					return PortalClassLoaderUtil.getClassLoader();
				}

			}
		);
	}

	public static void setContextClassLoader(final ClassLoader classLoader) {
		AccessController.doPrivileged(
			new PrivilegedAction<Void>() {

				public Void run() {
					Thread thread = Thread.currentThread();

					thread.setContextClassLoader(classLoader);

					return null;
				}

			}
		);
	}

	private static ClassLoader _getContextClassLoader() {
		Thread currentThread = Thread.currentThread();

		return currentThread.getContextClassLoader();
	}

	private static ClassLoader _getPluginClassLoader(
		String servletContextName) {

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		return (ClassLoader)servletContext.getAttribute(
			PluginContextListener.PLUGIN_CLASS_LOADER);
	}

}