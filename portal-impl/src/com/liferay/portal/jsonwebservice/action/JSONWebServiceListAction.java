/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
public class JSONWebServiceListAction implements JSONWebServiceAction {

	public JSONWebServiceListAction(HttpServletRequest request) {
		String list = request.getParameter("list");

		if (list.length() == 0) {
			_filters = null;
		}
		else {
			_filters = StringUtil.split(list, ',');
		}

		_contextPath = request.getContextPath();

		_basePath = request.getServletPath();

		_baseURL = request.getRequestURL().toString();

		_types = new ArrayList<Class>();
	}

	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	public Object invoke() throws Exception {

		final Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("context", _contextPath);
		result.put("basePath", _basePath);
		result.put("baseUrl", _baseURL);

		if (_filters != null) {
			result.put("filters", Arrays.toString(_filters));
		}

		Map<String, Object> servicesMap = _buildServicesMap();

		result.put("services", servicesMap);

		return result;
	}

	private boolean _acceptPath(String path) {
		if (_filters == null) {
			return true;
		}

		return Wildcard.matchOne(path, _filters) != -1;
	}

	private Map<String, Object> _buildServicesMap() throws PortalException {
		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings(
				_contextPath);

		Map<String, Object> services =
			new LinkedHashMap<String, Object>(
				jsonWebServiceActionMappings.size());

		for (JSONWebServiceActionMapping
			jsonWebServiceActionMapping : jsonWebServiceActionMappings) {

			String path = jsonWebServiceActionMapping.getPath();

			if (!_acceptPath(path)) {
				continue;
			}

			Map<String, Object> action = new LinkedHashMap<String, Object>();

			Method actionMethod = jsonWebServiceActionMapping.getActionMethod();

			MethodParameter[] methodParameters =
				jsonWebServiceActionMapping.getMethodParameters();

			Map<String, Object> parameters = new LinkedHashMap<String, Object>(
				methodParameters.length);

			for (MethodParameter methodParameter : methodParameters) {

				Class[] genericTypes = null;

				try {
					genericTypes = methodParameter.getGenericTypes();
				}
				catch (ClassNotFoundException e) {
					throw new PortalException(e);
				}

				parameters.put(
					methodParameter.getName(),
					_formatType(methodParameter.getType(), genericTypes));
			}

			action.put("path", path);

			action.put("parameters", parameters);

			action.put("httpMethod", jsonWebServiceActionMapping.getMethod());

			action.put("response", _formatType(
				actionMethod.getReturnType(), null));

			services.put(path, action);
		}

		return services;
	}

	private String _formatType(Class<?> type, Class<?>[] genericTypes) {
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();

			return _formatType(componentType, genericTypes) + "[]";
		}

		if (type.isPrimitive()) {
			return type.getSimpleName();
		}

		if (type.equals(String.class)) {
			return "string";
		}

		if (type.equals(Locale.class)) {
			return "string";
		}

		if (type.equals(Date.class)) {
			return "long";
		}

		if (type.equals(Serializable.class)) {
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

		if (genericTypes != null) {
			StringBundler genericTypesString = new StringBundler(
				2 + genericTypes.length * 2);

			genericTypesString.append(StringPool.LESS_THAN);

			for (int i = 0; i < genericTypes.length; i++) {
				Class<?> genericType = genericTypes[i];

				if (i != 0) {
					genericTypesString.append(StringPool.COMMA);
				}

				genericTypesString.append(_formatType(genericType, null));
			}

			genericTypesString.append(StringPool.GREATER_THAN);

			return typeName + genericTypesString.toString();
		}

		return "object<" + typeName + ">";
	}

	private String _basePath;
	private String _baseURL;
	private String _contextPath;
	private String[] _filters;
	private List<Class> _types;

}