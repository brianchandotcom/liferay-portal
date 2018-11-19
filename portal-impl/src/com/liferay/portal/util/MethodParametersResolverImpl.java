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

package com.liferay.portal.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.MethodParametersResolver;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.concurrent.ConcurrentMap;

import jodd.paramo.Paramo;

/**
 * @author Igor Spasic
 */
public class MethodParametersResolverImpl implements MethodParametersResolver {

	@Override
	public MethodParameter[] resolveMethodParameters(Method method) {
		MethodParameter[] methodParameters = _methodParameters.get(method);

		if (methodParameters != null) {
			return methodParameters;
		}

		Class<?> clazz = method.getDeclaringClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Class<?>[] methodParameterTypes = method.getParameterTypes();

		jodd.paramo.MethodParameter[] joddMethodParameters =
			Paramo.resolveParameters(method);

		methodParameters = new MethodParameter[joddMethodParameters.length];

		for (int i = 0; i < joddMethodParameters.length; i++) {
			methodParameters[i] = new MethodParameter(
				classLoader, joddMethodParameters[i].getName(),
				joddMethodParameters[i].getSignature(),
				methodParameterTypes[i]);
		}

		_methodParameters.put(method, methodParameters);

		return methodParameters;
	}

	private static final ConcurrentMap<AccessibleObject, MethodParameter[]>
		_methodParameters = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}