/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;

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

	public MethodKey getMethodKey() {
		return _methodKey;
	}

	public Object invoke() throws Exception {
		Method method = _methodKey.getMethod();

		Object targetObject = null;

		if (!Modifier.isStatic(method.getModifiers())) {
			Class<?> targetClass = _methodKey.getDeclaringClass();

			targetObject = targetClass.newInstance();
		}

		return method.invoke(targetObject, _arguments);
	}

	public Object invoke(Object target) throws Exception {
		Method method = _methodKey.getMethod();

		return method.invoke(target, _arguments);
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{arguments=", Arrays.toString(_arguments), ", methodKey=",
			_methodKey, "}");
	}

	private final Object[] _arguments;
	private final MethodKey _methodKey;

}