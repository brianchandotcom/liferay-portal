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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.BinarySearch;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
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

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
@DoPrivileged
public class JSONWebServiceActionsManagerImpl
	implements JSONWebServiceActionsManager {

	@Override
	public Set<String> getContextNames() {
		Set<String> contextNames = new TreeSet<String>();

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			String contextName = jsonWebServiceActionConfig.getContextName();

			contextNames.add(contextName);
		}

		return contextNames;
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest request)
		throws NoSuchJSONWebServiceException {

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

		if (jsonWebServiceActionParameters.getServiceContext() != null) {
			ServiceContextThreadLocal.pushServiceContext(
				jsonWebServiceActionParameters.getServiceContext());
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_findJSONWebServiceAction(
				request, path, method, jsonWebServiceActionParameters);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters,
			_jsonWebServiceNaming);
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest request, String path, String method,
			Map<String, Object> parameterMap)
		throws NoSuchJSONWebServiceException {

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			request, null, null, parameterMap);

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_findJSONWebServiceAction(
				request, path, method, jsonWebServiceActionParameters);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters,
			_jsonWebServiceNaming);
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
		String contextName) {

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			new ArrayList<JSONWebServiceActionMapping>(
				_jsonWebServiceActionConfigs.size());

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			if (contextName.equals(
					jsonWebServiceActionConfig.getContextName())) {

				jsonWebServiceActionMappings.add(jsonWebServiceActionConfig);
			}
		}

		return jsonWebServiceActionMappings;
	}

	@Override
	public int getJSONWebServiceActionsCount(String contextName) {
		int count = 0;

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_jsonWebServiceActionConfigs) {

			if (contextName.equals(
					jsonWebServiceActionConfig.getContextName())) {

				count++;
			}
		}

		return count;
	}

	@Override
	public JSONWebServiceNaming getJSONWebServiceNaming() {
		return _jsonWebServiceNaming;
	}

	@Override
	public void registerJSONWebServiceAction(
		String contextName, String contextPath, Class<?> actionClass,
		Method actionMethod, String path, String method) {

		try {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				new JSONWebServiceActionConfig(
					contextName, contextPath, actionClass, actionMethod, path,
					method);

			if (_jsonWebServiceActionConfigs.contains(
					jsonWebServiceActionConfig)) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"A JSON web service action is already registered at " +
							path);
				}

				return;
			}

			_jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(14);

				sb.append("Unable to register service method {actionClass=");
				sb.append(actionClass);
				sb.append(", actionMethod=");
				sb.append(actionMethod);
				sb.append(", contextName=");
				sb.append(contextName);
				sb.append(", contextPath=");
				sb.append(contextPath);
				sb.append(", method=");
				sb.append(method);
				sb.append(", path=");
				sb.append(path);
				sb.append("} due to ");
				sb.append(e.getMessage());

				_log.warn(sb.toString());
			}
		}
	}

	@Override
	public void registerJSONWebServiceAction(
		String contextName, String contextPath, Object actionObject,
		Class<?> actionClass, Method actionMethod, String path, String method) {

		try {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				new JSONWebServiceActionConfig(
					contextName, contextPath, actionObject, actionClass,
					actionMethod, path, method);

			if (_jsonWebServiceActionConfigs.contains(
					jsonWebServiceActionConfig)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"A JSON web service action is already registered at " +
							path);
				}

				return;
			}

			_jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);
		}
		catch (Exception e) {
			StringBundler sb = new StringBundler(17);

			sb.append("Something went wrong attempting to register service ");
			sb.append("method {contextName=");
			sb.append(contextName);
			sb.append(",contextPath=");
			sb.append(contextPath);
			sb.append(",actionObject=");
			sb.append(actionObject);
			sb.append(",actionClass=");
			sb.append(actionClass);
			sb.append(",actionMethod=");
			sb.append(actionMethod);
			sb.append(",path=");
			sb.append(path);
			sb.append(",method=");
			sb.append(method);
			sb.append("} due to ");
			sb.append(e.getMessage());

			_log.warn(sb.toString());
		}
	}

	@Override
	public int registerService(String contextPath, Object service) {
		return registerService(StringPool.BLANK, contextPath, service);
	}

	@Override
	public int registerService(
		String contextName, String contextPath, Object service) {

		JSONWebServiceRegistrator jsonWebServiceRegistrator =
			new JSONWebServiceRegistrator();

		jsonWebServiceRegistrator.processBean(
			contextName, contextPath, service);

		int count = getJSONWebServiceActionsCount(contextPath);

		if (_log.isInfoEnabled()) {
			_log.info("Configured " + count + " actions for " + contextPath);
		}

		return count;
	}

	@Override
	public int registerServletContext(ServletContext servletContext) {
		BeanLocator beanLocator = null;

		String contextName = servletContext.getServletContextName();
		String contextPath = servletContext.getContextPath();

		if (contextPath.equals(
				PortalContextLoaderListener.getPortalServletContextPath()) ||
			contextPath.isEmpty()) {

			beanLocator = PortalBeanLocatorUtil.getBeanLocator();
		}
		else {
			beanLocator = PortletBeanLocatorUtil.getBeanLocator(contextName);
		}

		if (beanLocator == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Bean locator not available for " + contextPath);
			}

			return -1;
		}

		JSONWebServiceRegistrator jsonWebServiceRegistrator =
			new JSONWebServiceRegistrator();

		jsonWebServiceRegistrator.processAllBeans(
			contextName, contextPath, beanLocator);

		int count = getJSONWebServiceActionsCount(contextPath);

		if (_log.isInfoEnabled()) {
			_log.info("Configured " + count + " actions for " + contextPath);
		}

		return count;
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

	@Override
	public int unregisterServletContext(ServletContext servletContext) {
		String contextPath = ContextPathUtil.getContextPath(servletContext);

		return unregisterJSONWebServiceActions(contextPath);
	}

	private int _countMatchedParameters(
		String[] parameterNames, MethodParameter[] methodParameters) {

		int matched = 0;

		for (MethodParameter methodParameter : methodParameters) {
			String methodParameterName = methodParameter.getName();

			methodParameterName = StringUtil.toLowerCase(methodParameterName);

			for (String parameterName : parameterNames) {
				if (StringUtil.equalsIgnoreCase(
						parameterName, methodParameterName)) {

					matched++;
				}
			}
		}

		return matched;
	}

	private JSONWebServiceActionConfig _findJSONWebServiceAction(
			HttpServletRequest request, String path, String method,
			JSONWebServiceActionParameters jsonWebServiceActionParameters)
		throws NoSuchJSONWebServiceException {

		String[] paths = _resolvePaths(request, path);

		String contextName = paths[0];

		path = paths[1];

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Request JSON web service action with path " + path +
					" and method " + method + " for " + contextName);
		}

		String[] parameterNames =
			jsonWebServiceActionParameters.getParameterNames();

		int jsonWebServiceActionConfigIndex =
			_getJSONWebServiceActionConfigIndex(
				contextName, path, method, parameterNames);

		if (jsonWebServiceActionConfigIndex == -1) {
			if (jsonWebServiceActionParameters.includeDefaultParameters()) {
				parameterNames =
					jsonWebServiceActionParameters.getParameterNames();

				jsonWebServiceActionConfigIndex =
					_getJSONWebServiceActionConfigIndex(
						contextName, path, method, parameterNames);
			}
		}

		if (jsonWebServiceActionConfigIndex == -1) {
			throw new NoSuchJSONWebServiceException(
				"No JSON web service action with path " + path +
					" and method " + method + " for " + contextName);
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_jsonWebServiceActionConfigs.get(jsonWebServiceActionConfigIndex);

		return jsonWebServiceActionConfig;
	}

	private int _getJSONWebServiceActionConfigIndex(
		String contextName, String path, String method,
		String[] parameterNames) {

		int hint = -1;

		int offset = 0;

		if (Validator.isNotNull(contextName)) {
			String pathPrefix = StringPool.SLASH.concat(contextName).concat(
				StringPool.PERIOD);

			if (path.startsWith(pathPrefix)) {
				offset = pathPrefix.length();
			}
		}

		int dotIndex = path.indexOf(CharPool.PERIOD, offset);

		if (dotIndex != -1) {
			hint = GetterUtil.getInteger(path.substring(dotIndex + 1), -1);

			if (hint != -1) {
				path = path.substring(0, dotIndex);
			}
		}

		int firstIndex = _pathBinarySearch.findFirst(path);

		if (firstIndex < 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find JSON web service actions with path " +
						path + " for " + contextName);
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
					path + " in for " + contextName);
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

			int count = _countMatchedParameters(
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
						"action with path " + path + " for " + contextName);
			}
			else {
				_log.debug(
					"Matched parameters to a JSON web service action with " +
						"path " + path + " for " + contextName);
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
		String contextName = null;

		int index = path.indexOf(CharPool.FORWARD_SLASH, 1);

		if (index != -1) {
			index = path.lastIndexOf(CharPool.PERIOD, index);

			if (index != -1) {
				contextName = path.substring(1, index);
			}
		}

		if (contextName == null) {
			ServletContext servletContext = request.getServletContext();

			contextName = servletContext.getServletContextName();

			if (Validator.isNotNull(contextName)) {
				path = StringPool.SLASH.concat(contextName).concat(
					StringPool.PERIOD).concat(path.substring(1));
			}
		}

		return new String[] {contextName, path};
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceActionsManagerImpl.class);

	private SortedArrayList<JSONWebServiceActionConfig>
		_jsonWebServiceActionConfigs =
			new SortedArrayList<JSONWebServiceActionConfig>();
	private JSONWebServiceNaming _jsonWebServiceNaming =
		new JSONWebServiceNaming();
	private BinarySearch<String> _pathBinarySearch = new PathBinarySearch();

	private class PathBinarySearch extends BinarySearch<String> {

		@Override
		protected int compare(int index, String element) {
			JSONWebServiceActionConfig jsonWebServiceActionConfig =
				_jsonWebServiceActionConfigs.get(index);

			String path = jsonWebServiceActionConfig.getPath();

			return path.compareTo(element);
		}

		@Override
		protected int getLastIndex() {
			return _jsonWebServiceActionConfigs.size() - 1;
		}

	}

}