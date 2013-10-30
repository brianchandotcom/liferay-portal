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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.InputStream;
import java.io.OutputStream;

import java.lang.reflect.Method;

import java.util.Set;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceNaming {

	public String convertClassNameToPath(Class<?> clazz) {
		String className = clazz.getSimpleName();

		className = StringUtil.replace(className, "Impl", StringPool.BLANK);
		className = StringUtil.replace(className, "Service", StringPool.BLANK);

		return StringUtil.toLowerCase(className);
	}

	public String convertImplClassNameToUtilClassName(
		Class<?> implementationClass) {

		String implementationClassName = implementationClass.getName();

		if (implementationClassName.endsWith("Impl")) {
			implementationClassName = implementationClassName.substring(
				0, implementationClassName.length() - 4);
		}

		String utilClassName = implementationClassName + "Util";

		utilClassName = StringUtil.replace(
			utilClassName, ".impl.", StringPool.PERIOD);

		return utilClassName;
	}

	public String convertMethodNameToHttpMethod(Method method) {
		String methodName = method.getName();

		String methodNamePrefix = getMethodNamePrefix(methodName);

		if (prefixes.contains(methodNamePrefix)) {
			return HttpMethods.GET;
		}

		return HttpMethods.POST;
	}

	public String convertMethodNameToPath(Method method) {
		return CamelCaseUtil.fromCamelCase(method.getName());
	}

	public boolean isIncludedMethod(Method method) {
		if ((excludedMethodNames != null) &&
			excludedMethodNames.contains(method.getName())) {

			return false;
		}

		if (excludedTypes == null) {
			return true;
		}

		Class<?>[] parameterTypes = method.getParameterTypes();

		for (Class<?> parameterType : parameterTypes) {
			if (parameterType.isArray()) {
				parameterType = parameterType.getComponentType();
			}

			if (excludedTypes.contains(parameterType)) {
				return false;
			}
		}

		Class<?> returnType = method.getReturnType();

		if (returnType.isArray()) {
			returnType = returnType.getComponentType();
		}

		if (excludedTypes.contains(returnType)) {
			return false;
		}

		return true;
	}

	public boolean isIncludedPath(String contextPath, String path) {
		String portalContextPath = PortalUtil.getPathContext();

		if (!contextPath.equals(portalContextPath)) {
			path = contextPath + StringPool.PERIOD + path.substring(1);
		}

		if (includedPaths.length > 0) {
			boolean included = false;

			for (String includedPath : includedPaths) {
				if (StringUtil.wildcardMatches(
						path, includedPath, '?', '*', '\\', false)) {

					included = true;

					break;
				}
			}

			if (!included) {
				return false;
			}
		}

		for (String excludedPath : excludedPaths) {
			if (StringUtil.wildcardMatches(
					path, excludedPath, '?', '*', '\\', false)) {

				return false;
			}
		}

		return true;
	}

	public boolean isValidHttpMethod(String httpMethod) {
		if (invalidHttpMethods.contains(httpMethod)) {
			return false;
		}

		return true;
	}

	protected String getMethodNamePrefix(String methodName) {
		int i = 0;

		while (i < methodName.length()) {
			if (Character.isUpperCase(methodName.charAt(i))) {
				break;
			}

			i++;
		}

		return methodName.substring(0, i);
	}

	protected Set<String> excludedMethodNames = SetUtil.fromArray(
		new String[] {"getBeanIdentifier", "setBeanIdentifier"});
	protected String[] excludedPaths = PropsUtil.getArray(
		PropsKeys.JSONWS_WEB_SERVICE_PATHS_EXCLUDED);
	protected Set<Class<?>> excludedTypes = SetUtil.fromArray(
		new Class<?>[] {InputStream.class, OutputStream.class});
	protected String[] includedPaths = PropsUtil.getArray(
		PropsKeys.JSONWS_WEB_SERVICE_PATHS_INCLUDED);
	protected Set<String> invalidHttpMethods = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS));
	protected Set<String> prefixes = SetUtil.fromArray(
		new String[] {"get", "has", "is"});

}