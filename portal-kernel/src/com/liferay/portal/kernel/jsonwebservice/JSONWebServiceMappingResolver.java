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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceMappingResolver {

	public JSONWebServiceMappingResolver(
		JSONWebServiceNaming jsonWebServiceNaming) {

		_jsonWebServiceNaming = jsonWebServiceNaming;
	}

	public String resolveHttpMethod(Method method) {
		JSONWebService jsonWebServiceAnnotation = method.getAnnotation(
			JSONWebService.class);

		String httpMethod = null;

		if (jsonWebServiceAnnotation != null) {
			httpMethod = StringUtil.trim(jsonWebServiceAnnotation.method());
		}

		if ((httpMethod != null) && (httpMethod.length() != 0)) {
			return httpMethod;
		}

		return _jsonWebServiceNaming.convertMethodToHttpMethod(method);
	}

	public String resolvePath(Class<?> clazz, Method method) {
		JSONWebService jsonWebServiceAnnotation = method.getAnnotation(
			JSONWebService.class);

		String path = null;

		if (jsonWebServiceAnnotation != null) {
			path = StringUtil.trim(jsonWebServiceAnnotation.value());
		}

		if ((path == null) || (path.length() == 0)) {
			path = _jsonWebServiceNaming.convertMethodToPath(method);
		}

		if (path.startsWith(StringPool.SLASH)) {
			return path;
		}

		path = StringPool.SLASH + path;

		String pathFromClass = null;

		jsonWebServiceAnnotation = clazz.getAnnotation(JSONWebService.class);

		if (jsonWebServiceAnnotation != null) {
			pathFromClass = StringUtil.trim(jsonWebServiceAnnotation.value());
		}

		if ((pathFromClass == null) || (pathFromClass.length() == 0)) {
			pathFromClass = _jsonWebServiceNaming.convertServiceClassToPath(
				clazz);
		}

		if (!pathFromClass.startsWith(StringPool.SLASH)) {
			pathFromClass = StringPool.SLASH + pathFromClass;
		}

		return pathFromClass + path;
	}

	private final JSONWebServiceNaming _jsonWebServiceNaming;

}