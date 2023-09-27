/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.util.MethodParameter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.concurrent.ConcurrentMap;

import jodd.paramo.Paramo;

/**
 * @author Igor Spasic
 */
public class MethodParametersResolverUtil {

	public static MethodParameter[] resolveMethodParameters(Method method) {
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