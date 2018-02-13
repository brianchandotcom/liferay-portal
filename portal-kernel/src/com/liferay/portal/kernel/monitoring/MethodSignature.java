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

package com.liferay.portal.kernel.monitoring;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class MethodSignature {

	public MethodSignature(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		_className = clazz.getName();

		_methodName = method.getName();

		Class<?>[] parameterTypes = method.getParameterTypes();

		_parameterTypeNames = new String[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			_parameterTypeNames[i] = parameterTypes[i].getName();
		}
	}

	public MethodSignature(
		String className, String methodName, String[] parameterTypeNames) {

		_className = className;
		_methodName = methodName;
		_parameterTypeNames = parameterTypeNames;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MethodSignature)) {
			return false;
		}

		MethodSignature methodSignature = (MethodSignature)obj;

		if (_className.equals(methodSignature._className) &&
			_methodName.equals(methodSignature._methodName) &&
			(_parameterTypeNames.length ==
				methodSignature._parameterTypeNames.length)) {

			for (int i = 0; i < _parameterTypeNames.length; i++) {
				if (!_parameterTypeNames[i].equals(
						methodSignature._parameterTypeNames[i])) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String[] getParameterTypeNames() {
		return _parameterTypeNames;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _className);

		hashCode = HashUtil.hash(hashCode, _methodName);

		for (String parameterTypeName : _parameterTypeNames) {
			hashCode = HashUtil.hash(hashCode, parameterTypeName);
		}

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(
			_parameterTypeNames.length * 2 + 5);

		sb.append("{className=");
		sb.append(_className);
		sb.append(", methodName=");
		sb.append(_methodName);
		sb.append(", parameterTypeNames=[");

		for (String parameterTypeName : _parameterTypeNames) {
			sb.append(parameterTypeName);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append("]}");

		return sb.toString();
	}

	private final String _className;
	private final String _methodName;
	private final String[] _parameterTypeNames;

}