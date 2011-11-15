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

import java.io.Serializable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

/**
 * @author Shuyang Zhou
 */
public class MethodHandler implements Serializable {

	public MethodHandler(Method method, Object... arguments) {
		this(new MethodKey(method), arguments);
	}

	public MethodHandler(MethodKey methodKey, Object... arguments) {
		_methodKey = methodKey;
		_arguments = arguments;
	}

	public Object[] getArguments() {
		Object[] arguments = new Object[_arguments.length];

		System.arraycopy(_arguments, 0, arguments, 0, _arguments.length);

		return arguments;
	}

	public Class<?>[] getArgumentsClasses() {
		return _methodKey.getParameterTypes();
	}

	public String getClassName() {
		return _methodKey.getClassName();
	}

	public MethodKey getMethodKey() {
		return _methodKey;
	}

	public String getMethodName() {
		return _methodKey.getMethodName();
	}

	public Object invoke(boolean newInstance) throws Exception {
		Method method = MethodCache.get(_methodKey);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Object targetObject = null;

		if (newInstance && !Modifier.isStatic(method.getModifiers())) {
			Class<?> targetClass = contextClassLoader.loadClass(getClassName());

			targetObject = targetClass.newInstance();
		}

		return method.invoke(targetObject, _arguments);
	}

	public Object invoke(Object target) throws Exception {
		Method method = MethodCache.get(_methodKey);

		return method.invoke(target, _arguments);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{arguments=");
		sb.append(Arrays.toString(_arguments));
		sb.append(", methodKey=");
		sb.append(_methodKey);
		sb.append("}");

		return sb.toString();
	}

	private Object[] _arguments;
	private MethodKey _methodKey;

}