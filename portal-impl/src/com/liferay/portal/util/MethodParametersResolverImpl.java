/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.MethodParametersResolver;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

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

		try {
			Class<?>[] methodParameterTypes = method.getParameterTypes();

			jodd.paramo.MethodParameter[] joddMethodParameters =
				Paramo.resolveParameters(method);

			methodParameters = new MethodParameter[joddMethodParameters.length];

			for (int i = 0; i < joddMethodParameters.length; i++) {
				methodParameters[i] = new MethodParameter(
					joddMethodParameters[i].getName(),
					joddMethodParameters[i].getSignature(),
					methodParameterTypes[i]);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}

		_methodParameters.put(method, methodParameters);

		return methodParameters;
	}

	private static Log _log = LogFactoryUtil.getLog(
		MethodParametersResolverImpl.class);

	private Map<AccessibleObject, MethodParameter[]> _methodParameters =
		new HashMap<AccessibleObject, MethodParameter[]>();

}