/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceMappingResolverUtil {

	public static String resolveHttpMethod(Method method) {
		JSONWebService annotationJSONWebService = method.getAnnotation(
			JSONWebService.class);

		String httpMethod = null;

		if (annotationJSONWebService != null) {
			httpMethod = StringUtil.trim(annotationJSONWebService.method());
		}

		if ((httpMethod != null) && (httpMethod.length() != 0)) {
			return httpMethod;
		}

		return JSONWebServiceNamingUtil.convertMethodToHttpMethod(method);
	}

	public static String resolvePath(Class<?> clazz, Method method) {
		JSONWebService annotationJSONWebService = method.getAnnotation(
			JSONWebService.class);

		String path = null;

		if (annotationJSONWebService != null) {
			path = StringUtil.trim(annotationJSONWebService.value());
		}

		if ((path == null) || (path.length() == 0)) {
			path = JSONWebServiceNamingUtil.convertMethodToPath(method);
		}

		if (path.startsWith(StringPool.SLASH)) {
			return path;
		}

		path = StringPool.SLASH + path;

		String pathFromClass = null;

		annotationJSONWebService = clazz.getAnnotation(JSONWebService.class);

		if (annotationJSONWebService != null) {
			pathFromClass = StringUtil.trim(annotationJSONWebService.value());
		}

		if ((pathFromClass == null) || (pathFromClass.length() == 0)) {
			pathFromClass = JSONWebServiceNamingUtil.convertServiceClassToPath(
				clazz);
		}

		if (!pathFromClass.startsWith(StringPool.SLASH)) {
			pathFromClass = StringPool.SLASH + pathFromClass;
		}

		return pathFromClass + path;
	}

}