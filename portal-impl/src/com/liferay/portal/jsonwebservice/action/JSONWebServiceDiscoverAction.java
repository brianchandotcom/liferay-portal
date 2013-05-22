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

package com.liferay.portal.jsonwebservice.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.Wildcard;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceDiscoverAction implements JSONWebServiceAction {

	public JSONWebServiceDiscoverAction(HttpServletRequest request) {
		_basePath = request.getServletPath();
		_baseURL = request.getRequestURL().toString();
		_contextPath = request.getContextPath();

		String discover = request.getParameter("discover");

		_discover = StringUtil.split(discover);
	}

	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	public Object invoke() throws Exception {
		Map<String, Object> resultsMap = new LinkedHashMap<String, Object>();

		Map<String, Object> jsonWebServiceActionMappingMaps =
			_buildJsonWebServiceActionMappingMaps();

		resultsMap.put("actions", jsonWebServiceActionMappingMaps);

		resultsMap.put("context", _contextPath);
		resultsMap.put("basePath", _basePath);
		resultsMap.put("baseURL", _baseURL);

		if (_discover.length > 0) {
			resultsMap.put("discover", Arrays.toString(_discover));
		}

		return resultsMap;
	}

	private Map<String, Object> _buildJsonWebServiceActionMappingMaps()
		throws PortalException {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappingMaps =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings(
				_contextPath);

		Map<String, Object> jsonWebServiceActionMappingsMap =
			new LinkedHashMap<String, Object>(
				jsonWebServiceActionMappingMaps.size());

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappingMaps) {

			String path = jsonWebServiceActionMapping.getPath();

			if (!_isAcceptPath(path)) {
				continue;
			}

			Map<String, Object> jsonWebServiceActionMappingMap =
				new LinkedHashMap<String, Object>();

			jsonWebServiceActionMappingMap.put(
				"method", jsonWebServiceActionMapping.getMethod());

			MethodParameter[] methodParameters =
				jsonWebServiceActionMapping.getMethodParameters();

			Map<String, Object> parameterMap =
				new LinkedHashMap<String, Object>(methodParameters.length);

			for (MethodParameter methodParameter : methodParameters) {
				Class<?>[] genericTypes = null;

				try {
					genericTypes = methodParameter.getGenericTypes();
				}
				catch (ClassNotFoundException cnfe) {
					throw new PortalException(cnfe);
				}

				parameterMap.put(
					methodParameter.getName(),
					_formatType(methodParameter.getType(), genericTypes));
			}

			jsonWebServiceActionMappingMap.put("parameters", parameterMap);

			jsonWebServiceActionMappingMap.put("path", path);

			Method actionMethod = jsonWebServiceActionMapping.getActionMethod();

			jsonWebServiceActionMappingMap.put(
				"response", _formatType(actionMethod.getReturnType(), null));

			jsonWebServiceActionMappingsMap.put(
				path, jsonWebServiceActionMappingMap);
		}

		return jsonWebServiceActionMappingsMap;
	}

	private String _formatType(Class<?> type, Class<?>[] genericTypes) {
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();

			return _formatType(componentType, genericTypes) + "[]";
		}

		if (type.isPrimitive()) {
			return type.getSimpleName();
		}
		else if (type.equals(Date.class)) {
			return "long";
		}
		else if (type.equals(Locale.class) || type.equals(String.class)) {
			return "string";
		}
		else if (type.equals(Object.class) || type.equals(Serializable.class)) {
			return "object";
		}

		String typeName = type.getName();

		if (type.equals(List.class)) {
			typeName = "list";
		}
		else if (type.equals(Map.class)) {
			typeName = "map";
		}
		else {
			_types.add(type);
		}

		if (genericTypes == null) {
			return "object<" + typeName + ">";
		}

		StringBundler sb = new StringBundler(genericTypes.length * 2 + 1);

		sb.append(StringPool.LESS_THAN);

		for (int i = 0; i < genericTypes.length; i++) {
			Class<?> genericType = genericTypes[i];

			if (i != 0) {
				sb.append(StringPool.COMMA);
			}

			sb.append(_formatType(genericType, null));
		}

		sb.append(StringPool.GREATER_THAN);

		return typeName + sb.toString();
	}

	private boolean _isAcceptPath(String path) {
		if (_discover.length == 0) {
			return true;
		}

		if (Wildcard.matchOne(path, _discover) != -1) {
			return true;
		}
		else {
			return false;
		}
	}

	private String _basePath;
	private String _baseURL;
	private String _contextPath;
	private String[] _discover;
	private List<Class<?>> _types = new ArrayList<Class<?>>();

}