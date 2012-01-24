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

import java.io.Serializable;

import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class MethodKey implements Serializable {

	public MethodKey(Method method) {
		this(
			method.getDeclaringClass().getName(), method.getName(),
			method.getParameterTypes());
	}

	public MethodKey(
		String className, String methodName, Class<?>... parameterTypes) {

		_className = className;
		_methodName = methodName;
		_parameterTypes = parameterTypes;
	}

	public MethodKey(
			String className, String methodName, String[] parameterTypeNames)
		throws ClassNotFoundException {

		_className = className;
		_methodName = methodName;

		_parameterTypes = new Class[parameterTypeNames.length];

		ClassLoader classLoader = null;

		if (parameterTypeNames.length > 0) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
		}

		for (int i = 0; i < parameterTypeNames.length; i++) {
			String parameterTypeName = getWrapperType(parameterTypeNames[i]);

			_parameterTypes[i] = Class.forName(
				parameterTypeName, true, classLoader);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MethodKey methodKey = (MethodKey)obj;

		if (toString().equals(methodKey.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public Class<?>[] getParameterTypes() {
		return _parameterTypes;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return _toString();
	}

	protected String getWrapperType(String parameterTypeName) {
		String wrapperTypeName = parameterTypeName;

		if (parameterTypeName.equals("byte")) {
			wrapperTypeName = "Byte";
		}
		else if (parameterTypeName.equals("boolean")) {
			wrapperTypeName = "Boolean";
		}
		else if (parameterTypeName.equalsIgnoreCase("char")) {
			wrapperTypeName = "Character";
		}
		else if (parameterTypeName.equals("double")) {
			wrapperTypeName = "Double";
		}
		else if (parameterTypeName.equals("float")) {
			wrapperTypeName = "Float";
		}
		else if (parameterTypeName.equals("int")) {
			wrapperTypeName = "Integer";
		}
		else if (parameterTypeName.equals("long")) {
			wrapperTypeName = "Long";
		}
		else if (parameterTypeName.equals("short")) {
			wrapperTypeName = "Short";
		}

		return wrapperTypeName;
	}

	private String _toString() {
		if (_toString == null) {
			if ((_parameterTypes != null) && (_parameterTypes.length > 0)) {
				StringBundler sb = new StringBundler(
					3 + _parameterTypes.length);

				sb.append(_className);
				sb.append(_methodName);
				sb.append(StringPool.DASH);

				for (Class<?> parameterType : _parameterTypes) {
					sb.append(parameterType.getName());
				}

				_toString = sb.toString();
			}
			else {
				_toString = _className.concat(_methodName);
			}
		}

		return _toString;
	}

	private String _className;
	private String _methodName;
	private Class<?>[] _parameterTypes;
	private String _toString;

}