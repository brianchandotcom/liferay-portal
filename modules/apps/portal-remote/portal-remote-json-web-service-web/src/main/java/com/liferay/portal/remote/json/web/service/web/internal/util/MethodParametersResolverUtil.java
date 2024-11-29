/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.objectweb.asm.ClassReader;

/**
 * @author Igor Spasic
 */
public class MethodParametersResolverUtil {

	public static MethodParameter[] resolveMethodParameters(Method method) {
		MethodParameter[] methodParameters = _methodParameters.get(method);

		if (methodParameters != null) {
			return methodParameters;
		}

		try {
			methodParameters = _resolveMethodParameters(
				method.getDeclaringClass(), method);

			_methodParameters.put(method, methodParameters);
		}
		catch (PortalException portalException) {
			_log.error("Unable to resolve method parameters", portalException);
		}

		return methodParameters;
	}

	private static MethodParameter[] _resolveMethodParameters(
			Class<?> clazz, Method method)
		throws PortalException {

		String path = StringUtil.replace(
			clazz.getName(), CharPool.PERIOD, CharPool.SLASH);

		String resourcePath = path.concat(".class");

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

		if (inputStream == null) {
			throw new IllegalArgumentException(
				"Class not found: " + clazz.getName());
		}

		ClassReader classReader = null;

		try {
			classReader = new ClassReader(inputStream);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to read class from path " + resourcePath, ioException);
		}

		MethodParameterClassVisitor methodParameterClassVisitor =
			new MethodParameterClassVisitor(classLoader, method);

		classReader.accept(
			methodParameterClassVisitor, ClassReader.SKIP_FRAMES);

		List<MethodParameter> methodParameters =
			methodParameterClassVisitor.getMethodParameters();

		return methodParameters.toArray(new MethodParameter[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodParametersResolverUtil.class);

	private static final ConcurrentMap<AccessibleObject, MethodParameter[]>
		_methodParameters = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}