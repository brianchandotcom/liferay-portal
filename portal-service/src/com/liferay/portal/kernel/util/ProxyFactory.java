/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;

/**
 * @author Brian Wing Shun Chan
 */
public class ProxyFactory {

	public static Object newInstance(
			ClassLoader classLoader, Class<?> interfaceClass,
			String implClassName)
		throws Exception {

		return newInstance(
			classLoader, new Class[] {interfaceClass}, implClassName);
	}

	public static Object newInstance(
			ClassLoader classLoader, Class<?>[] interfaceClasses,
			String implClassName)
		throws Exception {

		Object instance = InstanceFactory.newInstance(
			classLoader, implClassName);

		return ProxyUtil.newProxyInstance(
			classLoader, interfaceClasses,
			new ClassLoaderBeanHandler(instance, classLoader));
	}

}