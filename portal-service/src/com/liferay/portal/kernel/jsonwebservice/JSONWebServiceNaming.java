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

import java.lang.reflect.Method;

import java.util.Set;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceNaming {

	public boolean acceptHttpMethod(String httpMethod) {
		if (invalidHttpMethods.contains(httpMethod)) {
			return false;
		}

		return true;
	}

	public boolean acceptMethod(Method method) {
		if ((excludedMethodNames != null) &&
			excludedMethodNames.contains(method.getName())) {

			return false;
		}

		return true;
	}

	public String classNameToPath(Class<?> clazz) {
		String className = clazz.getSimpleName();

		className = StringUtil.replace(className, "Impl", StringPool.BLANK);
		className = StringUtil.replace(className, "Service", StringPool.BLANK);

		return className.toLowerCase();
	}

	public String implClassNameToUtilClassName(Class implementationClass) {
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

	public String methodNameToHttpMethod(Method method) {
		String methodName = method.getName();

		String methodNamePrefix = cutPrefix(methodName);

		if (prefixes.contains(methodNamePrefix)) {
			return HttpMethods.GET;
		}

		return HttpMethods.POST;
	}

	public String methodNameToPath(Method method) {
		return CamelCaseUtil.fromCamelCase(method.getName());
	}

	protected String cutPrefix(String methodName) {
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
	protected Set<String> invalidHttpMethods = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS));
	protected Set<String> prefixes = SetUtil.fromArray(
		new String[] {"get", "has", "is"});

}