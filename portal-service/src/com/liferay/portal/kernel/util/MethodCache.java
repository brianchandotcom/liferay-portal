/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
		_methodsMap.clear();
	}

	/**
	 * No public accessing is allowed, used only by {@link MethodKey}
	 */
	protected static Method get(MethodKey methodKey)
		throws NoSuchMethodException {

		Method method = _methodsMap.get(methodKey);

		if (method == null) {
			Class<?> declaringClass = methodKey.getDeclaringClass();
			String methodName = methodKey.getMethodName();
			Class<?>[] parameterTypes = methodKey.getParameterTypes();

			method = declaringClass.getDeclaredMethod(
				methodName, parameterTypes);

			_methodsMap.put(methodKey, method);
		}

		return method;
	}

	private static Map<MethodKey, Method> _methodsMap =
		new ConcurrentHashMap<MethodKey, Method>();;

}