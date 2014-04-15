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

package com.liferay.portal.jsonwebservice.action;

import com.liferay.portal.json.transformer.FlexjsonBeanAnalyzerTransformer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.javadoc.JavadocManagerUtil;
import com.liferay.portal.kernel.javadoc.JavadocMethod;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import flexjson.JSONContext;
import flexjson.PathExpression;

import java.io.File;
import java.io.Serializable;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ReflectUtil;
import jodd.util.Wildcard;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceDiscoverAction implements JSONWebServiceAction {

	public JSONWebServiceDiscoverAction(HttpServletRequest request) {
		_basePath = request.getServletPath();
		_baseURL = String.valueOf(request.getRequestURL());
		_contextPath = ParamUtil.getString(
			request, "contextPath", request.getContextPath());
		_discover = StringUtil.split(request.getParameter("discover"));
	}

	@Override
	public JSONWebServiceActionMapping getJSONWebServiceActionMapping() {
		return null;
	}

	@Override
	public Object invoke() throws Exception {
		Map<String, Object> resultsMap = new LinkedHashMap<String, Object>();

		List<Map<String, Object>> jsonWebServiceActionMappingMaps =
			_buildJsonWebServiceActionMappingMaps();

		resultsMap.put("services", jsonWebServiceActionMappingMaps);

		List<Map> types = _buildTypesList(_types);

		resultsMap.put("types", types);

		resultsMap.put("context", _contextPath);
		resultsMap.put("basePath", _basePath);
		resultsMap.put("baseURL", _baseURL);
		resultsMap.put("version", "7.0");	// todo add portal version

		if (_discover.length > 0) {
			resultsMap.put("discover", _discover);
		}

		return new DiscoveryContent(resultsMap);
	}

	public static class DiscoveryContent implements JSONSerializable {

		public DiscoveryContent(Map<String, Object> resultsMap) {
			_resultsMap = resultsMap;
		}

		@Override
		public String toJSONString() {
			JSONSerializer jsonSerializer =
				JSONFactoryUtil.createJSONSerializer();

			jsonSerializer.include("types");

			return jsonSerializer.serializeDeep(_resultsMap);
		}

		private Map<String, Object> _resultsMap;

	}

	private List<Map<String, Object>> _buildJsonWebServiceActionMappingMaps()
		throws PortalException {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings(
				_contextPath);

		List<Map<String, Object>> jsonWebServiceActionMappingMaps =
			new ArrayList<Map<String, Object>>(
				jsonWebServiceActionMappings.size());

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			String path = jsonWebServiceActionMapping.getPath();

			if (!_isAcceptPath(path)) {
				continue;
			}

			Map<String, Object> jsonWebServiceActionMappingMap =
				new LinkedHashMap<String, Object>();

			jsonWebServiceActionMappingMap.put(
				"method", jsonWebServiceActionMapping.getMethod());

			JavadocMethod javadocMethod =
				JavadocManagerUtil.lookupJavadocMethod(
					jsonWebServiceActionMapping.getRealActionMethod());

			if (javadocMethod != null) {
				String methodComment = javadocMethod.getComment();

				if (methodComment != null) {
					jsonWebServiceActionMappingMap.put(
						"description", javadocMethod.getComment());
				}
			}

			MethodParameter[] methodParameters =
				jsonWebServiceActionMapping.getMethodParameters();

			List<Map<String, String>> parametersList =
				new ArrayList<Map<String, String>>(methodParameters.length);

			for (MethodParameter methodParameter : methodParameters) {
				Class<?>[] genericTypes = null;

				try {
					genericTypes = methodParameter.getGenericTypes();
				}
				catch (ClassNotFoundException cnfe) {
					throw new PortalException(cnfe);
				}

				Map<String, String> parameterMap =
					new HashMap<String, String>();

				parameterMap.put("name", methodParameter.getName());

				parameterMap.put(
						"type", _formatType(
							methodParameter.getType(), genericTypes));

				parametersList.add(parameterMap);
			}

			jsonWebServiceActionMappingMap.put("parameters", parametersList);

			jsonWebServiceActionMappingMap.put("path", path);

			Method actionMethod = jsonWebServiceActionMapping.getActionMethod();

			Map<String, String> returnsMap =
				new LinkedHashMap<String, String>();

			Class<?> realActionClass =
				jsonWebServiceActionMapping.getActionClass();

			Method realActionMethod =
				jsonWebServiceActionMapping.getRealActionMethod();

			Class[] genericReturnTypes = null;

			Type genericReturnType = realActionMethod.getGenericReturnType();

			if (genericReturnType instanceof ParameterizedType) {
				ParameterizedType parameterizedType =
					(ParameterizedType)genericReturnType;

				Type[] generics = parameterizedType.getActualTypeArguments();

				genericReturnTypes = new Class[generics.length];

				for (int i = 0; i < generics.length; i++) {
					Type generic = generics[i];

					genericReturnTypes[i] = ReflectUtil.getRawType(
						generic, realActionClass);
				}
			}

			returnsMap.put(
				"type", _formatType(
					actionMethod.getReturnType(), genericReturnTypes));

			jsonWebServiceActionMappingMap.put("returns", returnsMap);

			jsonWebServiceActionMappingMaps.add(jsonWebServiceActionMappingMap);
		}

		return jsonWebServiceActionMappingMaps;
	}

	private Map<String, Map> _buildPropertiesMap(Class type) {
		if (type.getPackage().getName().startsWith("java.")) {
			return null;
		}

		if (type.isInterface()) {
			try {
				String modelImplClassName = type.getName();

				modelImplClassName =
						StringUtil.replace(
							modelImplClassName, ".model.", ".model.impl.");

				modelImplClassName += "ModelImpl";

				ClassLoader classLoader = this.getClass().getClassLoader();

				type = classLoader.loadClass(modelImplClassName);
			}
			catch (ClassNotFoundException e) {
			}
		}

		// scan properties

		try {
			final JSONContext context = JSONContext.get();

			final List<PathExpression> pathExpressions =
				new ArrayList<PathExpression>();

			context.setPathExpressions(pathExpressions);

			FlexjsonBeanAnalyzerTransformer flexjsonBeanAnalyzerTransformer =
					new FlexjsonBeanAnalyzerTransformer(pathExpressions) {
						@Override
						protected String getTypeName(Class<?> type) {
							return _formatType(type, null);
						}
					};

			flexjsonBeanAnalyzerTransformer.transform(type);

			Map<String, Map> propertiesMap =
				flexjsonBeanAnalyzerTransformer.getPropertiesMap();

			return propertiesMap;
		}
		catch (Exception e) {
			return null;
		}
	}

	private List<Map> _buildTypesList(List<Class<?>> allTypes) {
		List<Map> types = new ArrayList<Map>();

		for (int i = 0; i < allTypes.size(); i++) {
			Class<?> type = allTypes.get(i);

			Map<String, Object> map = new LinkedHashMap<String, Object>();
			types.add(map);

			map.put("type", type.getName());

			// properties

			Map<String, Map> properties = _buildPropertiesMap(type);

			if (properties != null) {
				map.put("properties", properties);
			}
		}

		return types;
	}

	private String _formatType(Class<?> type, Class<?>[] genericTypes) {
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();

			return _formatType(componentType, genericTypes) + "[]";
		}

		if (type.isPrimitive()) {
			return type.getSimpleName();
		}

		if (type.equals(File.class)) {
			return "file";
		}
		else if (type.equals(Boolean.class)) {
			return "boolean";
		}
		else if (type.equals(Date.class)) {
			return "long";
		}
		else if (type.equals(Locale.class)) {
			return "string";
		}
		else if (type.equals(TimeZone.class)) {
			return "string";
		}
		else if (type.equals(String.class)) {
			return "string";
		}
		else if (type.equals(Object.class) || type.equals(Serializable.class)) {
			return "map";
		}
		else if (ReflectUtil.isSubclass(type, Number.class)) {
			String typeName = null;

			if (type == Integer.class) {
				typeName = "int";
			}
			else if (type == Character.class) {
				typeName = "char";
			}
			else {
				typeName = StringUtil.toLowerCase(type.getSimpleName());
			}

			return typeName;
		}

		String typeName = type.getName();

		if ((type == List.class) || ReflectUtil.isSubclass(type, List.class)) {
			typeName = "list";
		}
		else if ((type == Map.class) ||
				 ReflectUtil.isSubclass(type, Map.class)) {

			typeName = "map";
		}
		else if (type == Collection.class) {
			typeName = "list";
		}
		else {
			if (!_types.contains(type)) {
				_types.add(type);
			}
		}

		if (genericTypes == null) {
			return typeName;
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