/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Set;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceNamingUtil {

	public static String convertMethodToHttpMethod(Method method) {
		String methodName = method.getName();

		if (_prefixes.contains(_getMethodNamePrefix(methodName))) {
			return HttpMethods.GET;
		}

		return HttpMethods.POST;
	}

	public static String convertMethodToPath(Method method) {
		return CamelCaseUtil.fromCamelCase(method.getName());
	}

	public static String convertModelClassToImplClassName(Class<?> clazz) {
		ImplementationClassName implementationClassName = clazz.getAnnotation(
			ImplementationClassName.class);

		if (implementationClassName != null) {
			return implementationClassName.value();
		}

		String className = clazz.getName();

		className = StringUtil.replace(className, ".kernel.", ".");
		className =
			StringUtil.replace(className, ".model.", ".model.impl.") + "Impl";

		return className;
	}

	public static String convertServiceClassToPath(Class<?> clazz) {
		String className = convertServiceClassToSimpleName(clazz);

		return StringUtil.toLowerCase(className);
	}

	public static String convertServiceClassToSimpleName(Class<?> clazz) {
		String className = clazz.getSimpleName();

		if (className.endsWith("ServiceImpl")) {
			className = className.substring(0, className.length() - 11);
		}
		else if (className.endsWith("Service")) {
			className = className.substring(0, className.length() - 7);
		}

		return className;
	}

	public static boolean isIncludedMethod(Method method) {
		if ((_excludedMethodNames != null) &&
			_excludedMethodNames.contains(method.getName())) {

			return false;
		}

		if (_EXCLUDED_TYPES_NAMES == null) {
			return true;
		}

		Class<?> returnType = method.getReturnType();

		if (returnType.isArray()) {
			returnType = returnType.getComponentType();
		}

		String returnTypeName = returnType.getName();

		for (String excludedTypesName : _EXCLUDED_TYPES_NAMES) {
			if (excludedTypesName.startsWith(returnTypeName)) {
				return false;
			}
		}

		Type[] types = method.getGenericParameterTypes();

		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			Type type = types[i];

			Class<?> parameterType = parameterTypes[i];

			if (parameterType.isArray()) {
				parameterType = parameterType.getComponentType();
			}

			String parameterTypeName = parameterType.getName();

			for (String excludedTypesName : _EXCLUDED_TYPES_NAMES) {
				if (parameterTypeName.startsWith(excludedTypesName)) {
					return false;
				}

				if (!(type instanceof ParameterizedType)) {
					continue;
				}

				ParameterizedType parameterizedType = (ParameterizedType)type;

				for (Type actualTypeArgument :
						parameterizedType.getActualTypeArguments()) {

					String typeName = actualTypeArgument.getTypeName();

					if (typeName.startsWith(excludedTypesName)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static boolean isIncludedPath(
		String contextName, String contextPath, String path) {

		String portalContextPath = PortalUtil.getPathContext();

		if (!contextPath.equals(portalContextPath)) {
			path = StringBundler.concat(
				StringPool.SLASH, contextName, StringPool.PERIOD,
				path.substring(1));
		}

		for (String excludedPath : _EXCLUDED_PATHS) {
			if (StringUtil.wildcardMatches(
					path, excludedPath, '?', '*', '\\', false)) {

				return false;
			}
		}

		if (_INCLUDED_PATHS.length == 0) {
			return true;
		}

		for (String includedPath : _INCLUDED_PATHS) {
			if (StringUtil.wildcardMatches(
					path, includedPath, '?', '*', '\\', false)) {

				return true;
			}
		}

		return false;
	}

	public static boolean isValidHttpMethod(String httpMethod) {
		if (_invalidHttpMethods.contains(httpMethod)) {
			return false;
		}

		return true;
	}

	private static String _getMethodNamePrefix(String methodName) {
		int i = 0;

		while (i < methodName.length()) {
			if (Character.isUpperCase(methodName.charAt(i))) {
				break;
			}

			i++;
		}

		return methodName.substring(0, i);
	}

	private static final String[] _EXCLUDED_PATHS = PropsUtil.getArray(
		PropsKeys.JSONWS_WEB_SERVICE_PATHS_EXCLUDES);

	private static final String[] _EXCLUDED_TYPES_NAMES = {
		InputStream.class.getName(), OutputStream.class.getName(), "javax."
	};

	private static final String[] _INCLUDED_PATHS = PropsUtil.getArray(
		PropsKeys.JSONWS_WEB_SERVICE_PATHS_INCLUDES);

	private static final Set<String> _excludedMethodNames = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSON_SERVICE_INVALID_METHOD_NAMES));
	private static final Set<String> _invalidHttpMethods = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS));
	private static final Set<String> _prefixes = SetUtil.fromArray(
		"get", "has", "is");

}