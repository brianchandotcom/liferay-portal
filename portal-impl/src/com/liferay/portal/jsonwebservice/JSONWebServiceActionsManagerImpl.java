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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerImpl
	implements JSONWebServiceActionsManager {

	public JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request) {

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String servletContextPath = ContextPathUtil.getContextPath(
			servletContext);

		String path = GetterUtil.getString(request.getPathInfo());
		String method = GetterUtil.getString(request.getMethod());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Request JSON web service action with path " + path +
					" and method " + method + " for /" + servletContextPath);
		}

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

		int slashIndex = path.indexOf(CharPool.FORWARD_SLASH, 1);

		if (slashIndex != -1) {
			int dotIndex = path.lastIndexOf(CharPool.PERIOD, slashIndex);

			if (dotIndex != -1) {
				servletContextPath = path.substring(0, dotIndex);

				path = CharPool.FORWARD_SLASH + path.substring(dotIndex + 1);
			}
		}

		int jsonWebServiceActionConfigIndex =
			_getJSONWebServiceActionConfigIndex(
				servletContextPath, path, method,
				jsonWebServiceActionParameters.getParameterNames());

		if (jsonWebServiceActionConfigIndex == -1) {
			throw new RuntimeException(
				"No JSON web service action associated with path " + path +
					" and method " + method + " for /" + servletContextPath);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfigs.get(jsonWebServiceActionConfigIndex);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters);
	}

	public JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request, String path, String method,
		Map<String, Object> parameterMap) {

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			request, null, null, parameterMap);

		String[] parameterNames =
			jsonWebServiceActionParameters.getParameterNames();

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String servletContextPath = ContextPathUtil.getContextPath(
			servletContext);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Require JSON web service action with path " + path +
					" and method " + method + " for /" + servletContextPath);
		}

		int jsonWebServiceActionConfigIndex =
			_getJSONWebServiceActionConfigIndex(
				servletContextPath, path, method, parameterNames);

		if (jsonWebServiceActionConfigIndex == -1) {
			throw new RuntimeException(
				"No JSON web service action with path " + path +
					" and method " + method + " for /" + servletContextPath);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfigs.get(jsonWebServiceActionConfigIndex);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters);
	}

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

	public List<JSONWebServiceActionMapping> getJSONWebServiceActionMappings(
		String servletContextPath) {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			new ArrayList<JSONWebServiceActionMapping>(
				_jsonWebServiceActionConfigs.size());

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			String jsonWebServiceServletContextPath =
				jsonWebServiceActionConfig.getServletContextPath();

			if (servletContextPath.equals(jsonWebServiceServletContextPath)) {
				jsonWebServiceActionMappings.add(jsonWebServiceActionConfig);
			}
		}

		return jsonWebServiceActionMappings;
	}

	public void registerJSONWebServiceAction(
		String servletContextPath, Class<?> actionClass, Method actionMethod,
		String path, String method) {

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			new JSONWebServiceActionConfig(
				servletContextPath, actionClass, actionMethod, path, method);

		_jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);
	}

	public int unregisterJSONWebServiceActions(String servletContextPath) {
		int count = 0;

		Iterator<JSONWebServiceActionConfig> itr =
			_jsonWebServiceActionConfigs.iterator();

		while (itr.hasNext()) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig = itr.next();

			if (servletContextPath.equals(
				jsonWebServiceActionConfig.getServletContextPath())) {

				itr.remove();

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
		String servletContextPath, String path, String method,
		String[] parameterNames) {

		int hint = -1;

		int dotIndex = path.indexOf(CharPool.PERIOD);

		if (dotIndex != -1) {
			hint = GetterUtil.getInteger(path.substring(dotIndex + 1));

			path = path.substring(0, dotIndex);
		}

		path = servletContextPath + path;

		int firstIndex = _pathBinarySearch.findFirst(path);

		if (firstIndex < 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find JSON web service actions with path " +
						path + " for /" + servletContextPath);
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
					path + " in for /" + servletContextPath);
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
							servletContextPath);
			}
			else {
				_log.debug(
					"Matched parameters to a JSON web service action with " +
						"path " + path + " for /" + servletContextPath);
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

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceActionParameters.class);

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