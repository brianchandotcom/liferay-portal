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

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.ClassLoaderPool;

/**
 * @author     Raymond Augé
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ClassLoaderUtil {

	public static ClassLoader getAggregatePluginsClassLoader(
		String[] servletContextNames, boolean addContextClassLoader) {

		ClassLoader[] classLoaders = null;

		int offset = 0;

		if (addContextClassLoader) {
			classLoaders = new ClassLoader[servletContextNames.length + 1];

			Thread currentThread = Thread.currentThread();

			classLoaders[0] = currentThread.getContextClassLoader();

			offset = 1;
		}
		else {
			classLoaders = new ClassLoader[servletContextNames.length];
		}

		for (int i = 0; i < servletContextNames.length; i++) {
			classLoaders[offset + i] = ClassLoaderPool.getClassLoader(
				servletContextNames[i]);
		}

		return AggregateClassLoader.getAggregateClassLoader(classLoaders);
	}

	public static ClassLoader getClassLoader(Class<?> clazz) {
		return clazz.getClassLoader();
	}

	public static ClassLoader getContextClassLoader() {
		Thread currentThread = Thread.currentThread();

		return currentThread.getContextClassLoader();
	}

	public static ClassLoader getPluginClassLoader(String servletContextName) {
		return ClassLoaderPool.getClassLoader(servletContextName);
	}

	public static ClassLoader getPortalClassLoader() {
		return PortalClassLoaderUtil.getClassLoader();
	}

	public static void setContextClassLoader(ClassLoader classLoader) {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(classLoader);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static class NoPACL implements PACL {

		@Override
		public ClassLoader getAggregatePluginsClassLoader(
			String[] servletContextNames, boolean addContextClassLoader) {

			return ClassLoaderUtil.getAggregatePluginsClassLoader(
				servletContextNames, addContextClassLoader);
		}

		@Override
		public ClassLoader getClassLoader(Class<?> clazz) {
			return ClassLoaderUtil.getClassLoader(clazz);
		}

		@Override
		public ClassLoader getContextClassLoader() {
			return ClassLoaderUtil.getContextClassLoader();
		}

		@Override
		public ClassLoader getPluginClassLoader(String servletContextName) {
			return ClassLoaderUtil.getPluginClassLoader(servletContextName);
		}

		@Override
		public ClassLoader getPortalClassLoader() {
			return ClassLoaderUtil.getPortalClassLoader();
		}

		@Override
		public void setContextClassLoader(ClassLoader classLoader) {
			ClassLoaderUtil.setContextClassLoader(classLoader);
		}

	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public ClassLoader getAggregatePluginsClassLoader(
			String[] servletContextNames, boolean addContextClassLoader);

		public ClassLoader getClassLoader(Class<?> clazz);

		public ClassLoader getContextClassLoader();

		public ClassLoader getPluginClassLoader(String servletContextName);

		public ClassLoader getPortalClassLoader();

		public void setContextClassLoader(ClassLoader classLoader);

	}

}