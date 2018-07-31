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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class PortalBeanLocatorUtil {

	public static BeanLocator getBeanLocator() {
		return _beanLocator;
	}

	public static <T> Map<String, T> locate(Class<T> clazz) {
		BeanLocator beanLocator = getBeanLocator();

		if (beanLocator == null) {
			_log.error("BeanLocator is null");

			throw new BeanLocatorException("BeanLocator is not set");
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader beanClassLoader = beanLocator.getClassLoader();

		try {
			if (contextClassLoader != beanClassLoader) {
				currentThread.setContextClassLoader(beanClassLoader);
			}

			return beanLocator.locate(clazz);
		}
		finally {
			if (contextClassLoader != beanClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public static Object locate(String name) {
		BeanLocator beanLocator = getBeanLocator();

		if (beanLocator == null) {
			_log.error("BeanLocator is null");

			throw new BeanLocatorException("BeanLocator is not set");
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		ClassLoader beanClassLoader = beanLocator.getClassLoader();

		try {
			if (contextClassLoader != beanClassLoader) {
				currentThread.setContextClassLoader(beanClassLoader);
			}

			return beanLocator.locate(name);
		}
		finally {
			if (contextClassLoader != beanClassLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	public static void reset() {
		setBeanLocator(null);
	}

	public static void setBeanLocator(BeanLocator beanLocator) {
		if (_log.isDebugEnabled()) {
			if (beanLocator == null) {
				_log.debug("Setting BeanLocator " + beanLocator);
			}
			else {
				_log.debug("Setting BeanLocator " + beanLocator.hashCode());
			}
		}

		_beanLocator = beanLocator;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public ClassLoader getBeanLocatorClassLoader(BeanLocator beanLocator);

		public ClassLoader getContextClassLoader(Thread currentThread);

		public void setContextClassLoader(
			Thread currentThread, ClassLoader classLoader);

	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalBeanLocatorUtil.class);

	private static BeanLocator _beanLocator;

}