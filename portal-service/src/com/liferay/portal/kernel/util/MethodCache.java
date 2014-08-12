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

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class MethodCache {

	public static void reset() {
		_methods.clear();
	}

	/**
	 * @see MethodKey
	 */
	protected static Method get(MethodKey methodKey)
		throws NoSuchMethodException {

		Method method = _methods.get(methodKey);

		if (method == null) {
			Class<?> declaringClass = methodKey.getDeclaringClass();

			method = declaringClass.getDeclaredMethod(
				methodKey.getMethodName(), methodKey.getParameterTypes());

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			_methods.put(methodKey, method);
		}

		return method;
	}

	private static final Map<MethodKey, Method> _methods =
		new ConcurrentHashMap<MethodKey, Method>();

}