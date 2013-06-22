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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Igor Spasic
 */
@DoPrivileged
public class JSONWebServiceActionsManagerImpl
	implements JSONWebServiceActionsManager {

	@Override
	public Set<String> getContextPaths() {
		Set<String> contextPaths = new TreeSet<String>();

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			String contextPath = jsonWebServiceActionConfig.getContextPath();

			contextPaths.add(contextPath);
		}

		return contextPaths;
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request) {

		String path = GetterUtil.getString(request.getPathInfo());

		String method = GetterUtil.getString(request.getMethod());

		String parameterPath = null;

		JSONRPCRequest jsonRPCRequest = null;

		int parameterPathIndex = _getParameterPathIndex(path);

		if (parameterPathIndex != -1) {
			parameterPath = path.substring(parameterPathIndex);

			path = path.substring(0, parameterPathIndex);
		}
		else {
			if (method.equals(HttpMethods.POST) &&
				!PortalUtil.isMultipartRequest(request)) {

				jsonRPCRequest = JSONRPCRequest.detectJSONRPCRequest(request);

				if (jsonRPCRequest != null) {
					path += StringPool.SLASH + jsonRPCRequest.getMethod();

					method = null;
				}
			}
		}

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			request, parameterPath, jsonRPCRequest, null);

		String[] paths = _resolvePaths(request, path);

		String contextPath = paths[0];

		path = paths[1];

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Request JSON web service action with path " + path +
					" and method " + method + " for /" + contextPath);
		}

		int jsonWebServiceActionConfigIndex =
			_getJSONWebServiceActionConfigIndex(
				contextPath, path, method,
				jsonWebServiceActionParameters.getParameterNames());

		if (jsonWebServiceActionConfigIndex == -1) {
			throw new RuntimeException(
				"No JSON web service action associated with path " + path +
					" and method " + method + " for /" + contextPath);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfigs.get(jsonWebServiceActionConfigIndex);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters);
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request, String path, String method,
		Map<String, Object> parameterMap) {

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			request, null, null, parameterMap);

		String[] parameterNames =
			jsonWebServiceActionParameters.getParameterNames();

		String[] paths = _resolvePaths(request, path);

		String contextPath = paths[0];

		path = paths[1];

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Request JSON web service action with path " + path +
					" and method " + method + " for /" + contextPath);
		}

		int jsonWebServiceActionConfigIndex =
			_getJSONWebServiceActionConfigIndex(
				contextPath, path, method, parameterNames);

		if (jsonWebServiceActionConfigIndex == -1) {
			throw new RuntimeException(
				"No JSON web service action with path " + path +
					" and method " + method + " for /" + contextPath);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfigs.get(jsonWebServiceActionConfigIndex);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters);
	}

	@Override
	public JSONWebServiceActionMapping getJSONWebServiceActionMapping(
		String signature) {

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			if (signature.equals(jsonWebServiceActionConfig.getSignature())) {
				return jsonWebServiceActionConfig;
			}
		}

		return null;
	}

	@Override
	public List<JSONWebServiceActionMapping> getJSONWebServiceActionMappings(
		String contextPath) {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			new ArrayList<JSONWebServiceActionMapping>(
				_jsonWebServiceActionConfigs.size());

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			String jsonWebServiceContextPath =
				jsonWebServiceActionConfig.getContextPath();

			if (contextPath.equals(jsonWebServiceContextPath)) {
				jsonWebServiceActionMappings.add(jsonWebServiceActionConfig);
			}
		}

		return jsonWebServiceActionMappings;
	}

	@Override
	public int getJSONWebServiceActionsCount(String contextPath) {
		int count = 0;

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			if (contextPath.equals(
					jsonWebServiceActionConfig.getContextPath())) {

				count++;
			}
		}

		return count;
	}

	@Override
	public void registerJSONWebServiceAction(
		String contextPath, Class<?> actionClass, Method actionMethod,
		String path, String method) {

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			new JSONWebServiceActionConfig(
				contextPath, actionClass, actionMethod, path, method);

		if (_jsonWebServiceActionConfigs.contains(jsonWebServiceActionConfig)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"A JSON web service action is already registered at " +
						path);
			}

			return;
		}

		_jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);
	}

	@Override
	public void registerJSONWebServiceAction(
		String contextPath, Object actionObject, Class<?> actionClass,
		Method actionMethod, String path, String method) {

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			new JSONWebServiceActionConfig(
				contextPath, actionObject, actionClass, actionMethod, path,
				method);

		if (_jsonWebServiceActionConfigs.contains(jsonWebServiceActionConfig)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"A JSON web service action is already registered at " +
						path);
			}

			return;
		}

		_jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);
	}

	@Override
	public int unregisterJSONWebServiceActions(Object actionObject) {
		int count = 0;

		Iterator<JSONWebServiceActionConfig> iterator =
			_jsonWebServiceActionConfigs.iterator();

		while (iterator.hasNext()) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				iterator.next();

			if (actionObject.equals(
					jsonWebServiceActionConfig.getActionObject())) {

				iterator.remove();

				count++;
			}
		}

		return count;
	}

	@Override
	public int unregisterJSONWebServiceActions(String contextPath) {
		int count = 0;

		Iterator<JSONWebServiceActionConfig> iterator =
			_jsonWebServiceActionConfigs.iterator();

		while (iterator.hasNext()) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				iterator.next();

			if (contextPath.equals(
					jsonWebServiceActionConfig.getContextPath())) {

				iterator.remove();

				count++;
			}
		}

		return count;
	}

	private int _countMatchedElements(
		String[] parameterNames, MethodParameter[] methodParameters) {

		int matched = 0;

		for (MethodParameter methodParameter : methodParameters) {
			String methodParameterName = methodParameter.getName();

			methodParameterName = CamelCaseUtil.normalizeCamelCase(
				methodParameterName);

			if (ArrayUtil.contains(parameterNames, methodParameterName)) {
				matched++;
			}
		}

		return matched;
	}

	private int _getJSONWebServiceActionConfigIndex(
		String contextPath, String path, String method,
		String[] parameterNames) {

		int hint = -1;

		int dotIndex = path.indexOf(CharPool.PERIOD);

		if (dotIndex != -1) {
			hint = GetterUtil.getInteger(path.substring(dotIndex + 1));

			path = path.substring(0, dotIndex);
		}

		path = contextPath + path;

		int firstIndex = _pathBinarySearch.findFirst(path);

		if (firstIndex < 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find JSON web service actions with path " +
						path + " for /" + contextPath);
			}

			return -1;
		}

		int lastIndex = _pathBinarySearch.findLast(path, firstIndex);

		if (lastIndex < 0) {
			lastIndex = firstIndex;
		}

		int index = -1;

		int max = -1;

		if (_log.isDebugEnabled()) {
			int total = lastIndex - firstIndex + 1;

			_log.debug(
				"Found " + total + " JSON web service actions with path " +
					path + " in for /" + contextPath);
		}

		for (int i = firstIndex; i <= lastIndex; i++) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				_jsonWebServiceActionConfigs.get(i);

			String jsonWebServiceActionConfigMethod =
				jsonWebServiceActionConfig.getMethod();

			if (PropsValues.JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD &&
				(method != null)) {

				if ((jsonWebServiceActionConfigMethod != null) &&
					!jsonWebServiceActionConfigMethod.equals(method)) {

					continue;
				}
			}

			MethodParameter[] jsonWebServiceActionConfigMethodParameters =
				jsonWebServiceActionConfig.getMethodParameters();

			int methodParametersCount =
				jsonWebServiceActionConfigMethodParameters.length;

			if ((hint != -1) && (methodParametersCount != hint)) {
				continue;
			}

			int count = _countMatchedElements(
				parameterNames, jsonWebServiceActionConfigMethodParameters);

			if (count > max) {
				if ((hint != -1) || (count >= methodParametersCount)) {
					max = count;

					index = i;
				}
			}
		}

		if (_log.isDebugEnabled()) {
			if (index == -1) {
				_log.debug(
					"Unable to match parameters to a JSON web service " +
						"action with path " + path + " for /" +
							contextPath);
			}
			else {
				_log.debug(
					"Matched parameters to a JSON web service action with " +
						"path " + path + " for /" + contextPath);
			}
		}

		return index;
	}

	private int _getParameterPathIndex(String path) {
		int index = path.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			index = path.indexOf(CharPool.SLASH, index + 1);
		}

		return index;
	}

	private String[] _resolvePaths(HttpServletRequest request, String path) {
		String contextPath = null;

		int index = path.indexOf(CharPool.FORWARD_SLASH, 1);

		if (index != -1) {
			index = path.lastIndexOf(CharPool.PERIOD, index);

			if (index != -1) {
				contextPath = path.substring(0, index);

				path = CharPool.FORWARD_SLASH + path.substring(index + 1);
			}
		}

		if (contextPath == null) {
			HttpSession session = request.getSession();

			ServletContext servletContext = session.getServletContext();

			contextPath = ContextPathUtil.getContextPath(servletContext);
		}

		return new String[] {contextPath, path};
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceActionsManagerImpl.class);

	private SortedArrayList<JSONWebServiceActionConfig>
		_jsonWebServiceActionConfigs =
			new SortedArrayList<JSONWebServiceActionConfig>();
	private BinarySearch<String> _pathBinarySearch = new PathBinarySearch();

	private class PathBinarySearch extends BinarySearch<String> {

		@Override
		protected int compare(int index, String element) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				_jsonWebServiceActionConfigs.get(index);

			String fullPath = jsonWebServiceActionConfig.getFullPath();

			return fullPath.compareTo(element);
		}

		@Override
		protected int getLastIndex() {
			return _jsonWebServiceActionConfigs.size() - 1;
		}

	}

}