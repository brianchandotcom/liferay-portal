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

package com.liferay.portal.bean;

import com.liferay.portal.util.ClassLoaderUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class VelocityBeanHandler implements InvocationHandler {

	public VelocityBeanHandler(Object bean, ClassLoader classLoader) {
		_bean = bean;
		_classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			if ((_classLoader != null) &&
				(_classLoader != contextClassLoader)) {

				ClassLoaderUtil.setContextClassLoader(_classLoader);
			}

			return method.invoke(_bean, arguments);
		}
		catch (InvocationTargetException ite) {
			return null;
		}
		finally {
			if ((_classLoader != null) &&
				(_classLoader != contextClassLoader)) {

				ClassLoaderUtil.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private final Object _bean;
	private final ClassLoader _classLoader;

}