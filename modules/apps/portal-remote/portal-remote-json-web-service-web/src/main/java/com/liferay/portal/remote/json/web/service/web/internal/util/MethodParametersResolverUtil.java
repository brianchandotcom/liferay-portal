/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodParameter;

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
			List<MethodParameter> extractedParameterList =
				_resolveMethodParameters(method.getDeclaringClass(), method);

			methodParameters =
				new MethodParameter[extractedParameterList.size()];

			_methodParameters.put(
				method, extractedParameterList.toArray(methodParameters));
		}
		catch (PortalException portalException) {
			_log.error("Error extracting parameters ", portalException);
		}

		return methodParameters;
	}

	private static List<MethodParameter> _resolveMethodParameters(
			Class<?> clazz, Method method)
		throws PortalException {

		String resourceName = clazz.getName(
		).replace(
			StringPool.PERIOD, StringPool.SLASH
		);

		resourceName = resourceName + ".class";

		InputStream classInputStream = clazz.getClassLoader(
		).getResourceAsStream(
			resourceName
		);

		if (classInputStream == null) {
			throw new IllegalArgumentException(
				"Class not found: " + clazz.getName());
		}

		ClassReader classReader = null;

		try {
			classReader = new ClassReader(classInputStream);
		}
		catch (IOException ioException) {
			throw new PortalException(
				"Unable to readClass to extract parameters ", ioException);
		}

		MethodParameterClassVisitor classVisitor =
			new MethodParameterClassVisitor(clazz.getClassLoader(), method);

		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);

		return classVisitor.getMethodParameters();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MethodParametersResolverUtil.class);

	private static final ConcurrentMap<AccessibleObject, MethodParameter[]>
		_methodParameters = new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}