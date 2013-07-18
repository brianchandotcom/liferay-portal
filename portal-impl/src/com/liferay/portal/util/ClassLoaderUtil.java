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

import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.security.lang.SecurityManagerUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class ClassLoaderUtil {

	public static ClassLoader getAggregatePluginsClassLoader(
		String[] servletContextNames, boolean addContextClassLoader) {

		return _classLoaderUtilProvider.getAggregatePluginsClassLoader(
			servletContextNames, addContextClassLoader);
	}

	public static ClassLoader getClassLoader(Class<?> clazz) {
		return _classLoaderUtilProvider.getClassLoader(clazz);
	}

	public static ClassLoader getContextClassLoader() {
		return _classLoaderUtilProvider.getContextClassLoader();
	}

	public static ClassLoader getPluginClassLoader(String servletContextName) {
		return _classLoaderUtilProvider.getPluginClassLoader(
			servletContextName);
	}

	public static ClassLoader getPortalClassLoader() {
		return _classLoaderUtilProvider.getPortalClassLoader();
	}

	public static void setContextClassLoader(ClassLoader classLoader) {
		_classLoaderUtilProvider.setContextClassLoader(classLoader);
	}

	private static final ClassLoaderUtilProvider _classLoaderUtilProvider;

	static {
		if (SecurityManagerUtil.isNone()) {
			_classLoaderUtilProvider = new ClassLoaderUtilProvider();
		}
		else {
			_classLoaderUtilProvider = new PACLClassLoaderUtilProvider();
		}
	}

	private static class ClassLoaderUtilProvider {

		public ClassLoader getAggregatePluginsClassLoader(
			String[] servletContextNames, boolean addContextClassLoader) {

			ClassLoader[] classLoaders = null;

			int offset = 0;

			if (addContextClassLoader) {
				classLoaders = new ClassLoader[servletContextNames.length + 1];

				Thread currentThread = Thread.currentThread();

				classLoaders[0] = currentThread.getContextClassLoader();

				offset = 1;
			}

			for (int i = 0; i < servletContextNames.length; i++) {
				classLoaders[offset + i] = ClassLoaderPool.getClassLoader(
					servletContextNames[i]);
			}

			return AggregateClassLoader.getAggregateClassLoader(classLoaders);
		}

		public ClassLoader getClassLoader(Class<?> clazz) {
			return clazz.getClassLoader();
		}

		public ClassLoader getContextClassLoader() {
			Thread thread = Thread.currentThread();

			return thread.getContextClassLoader();
		}

		public ClassLoader getPluginClassLoader(String servletContextName) {
			return ClassLoaderPool.getClassLoader(servletContextName);
		}

		public ClassLoader getPortalClassLoader() {
			return PortalClassLoaderUtil.getClassLoader();
		}

		public void setContextClassLoader(ClassLoader classLoader) {
			Thread thread = Thread.currentThread();

			thread.setContextClassLoader(classLoader);
		}

	}

	private static class PACLClassLoaderUtilProvider
		extends ClassLoaderUtilProvider {

		@Override
		public ClassLoader getAggregatePluginsClassLoader(
			final String[] servletContextNames,
			final boolean addContextClassLoader) {

			if (!SecurityManagerUtil.isPACLDisabled()) {
				return super.getAggregatePluginsClassLoader(
					servletContextNames, addContextClassLoader);
			}

			return AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return PACLClassLoaderUtilProvider.super.
							getAggregatePluginsClassLoader(
								servletContextNames, addContextClassLoader);
					}

				}
			);
		}

		@Override
		public ClassLoader getClassLoader(final Class<?> clazz) {
			if (!SecurityManagerUtil.isPACLDisabled()) {
				return super.getClassLoader(clazz);
			}

			return AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return PACLClassLoaderUtilProvider.super.getClassLoader(
							clazz);
					}

				}
			);
		}

		@Override
		public ClassLoader getContextClassLoader() {
			if (!SecurityManagerUtil.isPACLDisabled()) {
				return super.getContextClassLoader();
			}

			return AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return PACLClassLoaderUtilProvider.super.
							getContextClassLoader();
					}

				}
			);
		}

		@Override
		public ClassLoader getPluginClassLoader(
			final String servletContextName) {

			if (!SecurityManagerUtil.isPACLDisabled()) {
				return super.getPluginClassLoader(servletContextName);
			}

			return AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return PACLClassLoaderUtilProvider.super.
							getPluginClassLoader(servletContextName);
					}

				}
			);
		}

		@Override
		public ClassLoader getPortalClassLoader() {
			if (!SecurityManagerUtil.isPACLDisabled()) {
				return super.getPortalClassLoader();
			}

			return AccessController.doPrivileged(
				new PrivilegedAction<ClassLoader>() {

					@Override
					public ClassLoader run() {
						return PACLClassLoaderUtilProvider.super.
							getPortalClassLoader();
					}

				}
			);
		}

		@Override
		public void setContextClassLoader(final ClassLoader classLoader) {
			if (!SecurityManagerUtil.isPACLDisabled()) {
				super.setContextClassLoader(classLoader);
			}

			AccessController.doPrivileged(
				new PrivilegedAction<Void>() {

					@Override
					public Void run() {
						PACLClassLoaderUtilProvider.super.setContextClassLoader(
							classLoader);

						return null;
					}

				}
			);
		}
	}

}