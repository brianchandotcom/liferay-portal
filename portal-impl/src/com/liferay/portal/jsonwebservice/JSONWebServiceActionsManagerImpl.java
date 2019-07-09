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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceRegistrator;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodParameter;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceActionsManagerImpl
	implements JSONWebServiceActionsManager {

	@Override
	public Set<String> getContextNames() {
		return new TreeSet<>(
			_contextNameIndexedJSONWebServiceActionConfigs.keySet());
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest httpServletRequest)
		throws NoSuchJSONWebServiceException {

		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		String method = GetterUtil.getString(httpServletRequest.getMethod());

		String parameterPath = null;

		JSONRPCRequest jsonRPCRequest = null;

		int parameterPathIndex = _getParameterPathIndex(path);

		if (parameterPathIndex != -1) {
			parameterPath = path.substring(parameterPathIndex);

			path = path.substring(0, parameterPathIndex);
		}
		else {
			if (method.equals(HttpMethods.POST) &&
				!PortalUtil.isMultipartRequest(httpServletRequest)) {

				jsonRPCRequest = JSONRPCRequest.detectJSONRPCRequest(
					httpServletRequest);

				if (jsonRPCRequest != null) {
					path += StringPool.SLASH + jsonRPCRequest.getMethod();

					method = null;
				}
			}
		}

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			httpServletRequest, parameterPath, jsonRPCRequest, null);

		if (jsonWebServiceActionParameters.getServiceContext() != null) {
			ServiceContextThreadLocal.pushServiceContext(
				jsonWebServiceActionParameters.getServiceContext());
		}

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_findJSONWebServiceAction(
				httpServletRequest, path, method,
				jsonWebServiceActionParameters);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters,
			_jsonWebServiceNaming);
	}

	@Override
	public JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest httpServletRequest, String path, String method,
			Map<String, Object> parameterMap)
		throws NoSuchJSONWebServiceException {

		JSONWebServiceActionParameters jsonWebServiceActionParameters =
			new JSONWebServiceActionParameters();

		jsonWebServiceActionParameters.collectAll(
			httpServletRequest, null, null, parameterMap);

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_findJSONWebServiceAction(
				httpServletRequest, path, method,
				jsonWebServiceActionParameters);

		return new JSONWebServiceActionImpl(
			jsonWebServiceActionConfig, jsonWebServiceActionParameters,
			_jsonWebServiceNaming);
	}

	@Override
	public JSONWebServiceActionMapping getJSONWebServiceActionMapping(
		String signature) {

		return _signatureIndexedJSONWebServiceActionConfigs.get(signature);
	}

	@Override
	public List<JSONWebServiceActionMapping> getJSONWebServiceActionMappings(
		String contextName) {

		List<JSONWebServiceActionConfig> jsonWebServiceActionConfigs =
			_contextNameIndexedJSONWebServiceActionConfigs.get(contextName);

		if (jsonWebServiceActionConfigs == null) {
			return Collections.emptyList();
		}

		return new ArrayList<JSONWebServiceActionMapping>(
			jsonWebServiceActionConfigs);
	}

	@Override
	public int getJSONWebServiceActionsCount(String contextName) {
		List<JSONWebServiceActionConfig> jsonWebServiceActionConfigs =
			_contextNameIndexedJSONWebServiceActionConfigs.get(contextName);

		if (jsonWebServiceActionConfigs == null) {
			return 0;
		}

		return jsonWebServiceActionConfigs.size();
	}

	@Override
	public JSONWebServiceNaming getJSONWebServiceNaming() {
		return _jsonWebServiceNaming;
	}

	@Override
	public synchronized void registerJSONWebServiceAction(
		String contextName, String contextPath, Class<?> actionClass,
		Method actionMethod, String path, String method) {

		try {
			if (!_addJSONWebServiceActionConfig(
					new JSONWebServiceActionConfig(
						contextName, contextPath, actionClass, actionMethod,
						path, method))) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"A JSON web service action is already registered at " +
							path);
				}
			}
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
	public synchronized void registerJSONWebServiceAction(
		String contextName, String contextPath, Object actionObject,
		Class<?> actionClass, Method actionMethod, String path, String method) {

		try {
			if (!_addJSONWebServiceActionConfig(
					new JSONWebServiceActionConfig(
						contextName, contextPath, actionObject, actionClass,
						actionMethod, path, method))) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"A JSON web service action is already registered at " +
							path);
				}
			}
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
			new DefaultJSONWebServiceRegistrator();

		return registerService(
			contextName, contextPath, service, jsonWebServiceRegistrator);
	}

	@Override
	public int registerService(
		String contextName, String contextPath, Object service,
		JSONWebServiceRegistrator jsonWebServiceRegistrator) {

		jsonWebServiceRegistrator.processBean(
			contextName, contextPath, service);

		int count = getJSONWebServiceActionsCount(contextPath);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Configured ", count, " actions for ", contextPath));
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

		DefaultJSONWebServiceRegistrator defaultJSONWebServiceRegistrator =
			new DefaultJSONWebServiceRegistrator();

		defaultJSONWebServiceRegistrator.processAllBeans(
			contextName, contextPath, beanLocator);

		int count = getJSONWebServiceActionsCount(contextPath);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Configured ", count, " actions for ", contextPath));
		}

		return count;
	}

	@Override
	public synchronized int unregisterJSONWebServiceActions(
		Object actionObject) {

		int count = 0;

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_signatureIndexedJSONWebServiceActionConfigs.values()) {

			if ((actionObject ==
					jsonWebServiceActionConfig.getActionObject()) &&
				_removeJSONWebServiceActionConfig(jsonWebServiceActionConfig)) {

				count++;
			}
		}

		return count;
	}

	@Override
	public synchronized int unregisterJSONWebServiceActions(
		String contextPath) {

		int count = 0;

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				_signatureIndexedJSONWebServiceActionConfigs.values()) {

			if (contextPath.equals(
					jsonWebServiceActionConfig.getContextPath()) &&
				_removeJSONWebServiceActionConfig(jsonWebServiceActionConfig)) {

				count++;
			}
		}

		return count;
	}

	@Override
	public int unregisterServletContext(ServletContext servletContext) {
		return unregisterJSONWebServiceActions(servletContext.getContextPath());
	}

	private boolean _addJSONWebServiceActionConfig(
		JSONWebServiceActionConfig jsonWebServiceActionConfig) {

		JSONWebServiceActionConfig oldJSONWebServiceActionConfig =
			_signatureIndexedJSONWebServiceActionConfigs.putIfAbsent(
				jsonWebServiceActionConfig.getSignature(),
				jsonWebServiceActionConfig);

		if (oldJSONWebServiceActionConfig != null) {
			return false;
		}

		String contextName = jsonWebServiceActionConfig.getContextName();

		List<JSONWebServiceActionConfig> jsonWebServiceActionConfigs =
			_contextNameIndexedJSONWebServiceActionConfigs.get(contextName);

		if (jsonWebServiceActionConfigs == null) {
			jsonWebServiceActionConfigs = new CopyOnWriteArrayList<>();

			_contextNameIndexedJSONWebServiceActionConfigs.put(
				contextName, jsonWebServiceActionConfigs);
		}

		jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);

		jsonWebServiceActionConfigs =
			_pathIndexedJSONWebServiceActionConfigs.get(
				jsonWebServiceActionConfig.getPath());

		if (jsonWebServiceActionConfigs == null) {
			jsonWebServiceActionConfigs = new CopyOnWriteArrayList<>();

			_pathIndexedJSONWebServiceActionConfigs.put(
				jsonWebServiceActionConfig.getPath(),
				jsonWebServiceActionConfigs);
		}

		jsonWebServiceActionConfigs.add(jsonWebServiceActionConfig);

		return true;
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
			HttpServletRequest httpServletRequest, String path, String method,
			JSONWebServiceActionParameters jsonWebServiceActionParameters)
		throws NoSuchJSONWebServiceException {

		String[] paths = _resolvePaths(httpServletRequest, path);

		String contextName = paths[0];

		path = paths[1];

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Request JSON web service action with path ", path,
					" and method ", method, " for ", contextName));
		}

		String[] parameterNames =
			jsonWebServiceActionParameters.getParameterNames();

		JSONWebServiceActionConfig jsonWebServiceActionConfig =
			_getJSONWebServiceActionConfig(
				contextName, path, method, parameterNames);

		if ((jsonWebServiceActionConfig == null) &&
			jsonWebServiceActionParameters.includeDefaultParameters()) {

			parameterNames = jsonWebServiceActionParameters.getParameterNames();

			jsonWebServiceActionConfig = _getJSONWebServiceActionConfig(
				contextName, path, method, parameterNames);
		}

		if (jsonWebServiceActionConfig == null) {
			throw new NoSuchJSONWebServiceException(
				StringBundler.concat(
					"No JSON web service action with path ", path,
					" and method ", method, " for ", contextName));
		}

		return jsonWebServiceActionConfig;
	}

	private JSONWebServiceActionConfig _getJSONWebServiceActionConfig(
		String contextName, String path, String method,
		String[] parameterNames) {

		int hint = -1;

		int offset = 0;

		if (Validator.isNotNull(contextName)) {
			String pathPrefix = StringPool.SLASH.concat(
				contextName
			).concat(
				StringPool.PERIOD
			);

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

		List<JSONWebServiceActionConfig> jsonWebServiceActionConfigs =
			_pathIndexedJSONWebServiceActionConfigs.get(path);

		if ((jsonWebServiceActionConfigs == null) ||
			jsonWebServiceActionConfigs.isEmpty()) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to find JSON web service actions with path ",
						path, " for ", contextName));
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Found ", jsonWebServiceActionConfigs.size(),
					" JSON web service actions with path ", path, " for ",
					contextName));
		}

		jsonWebServiceActionConfigs = new ArrayList<>(
			jsonWebServiceActionConfigs);

		Collections.sort(jsonWebServiceActionConfigs);

		int max = -1;

		JSONWebServiceActionConfig matchedJSONWebServiceActionConfig = null;

		for (JSONWebServiceActionConfig jsonWebServiceActionConfig :
				jsonWebServiceActionConfigs) {

			if (PropsValues.JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD &&
				(method != null)) {

				String jsonWebServiceActionConfigMethod =
					jsonWebServiceActionConfig.getMethod();

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

			if ((count > max) &&
				((hint != -1) || (count >= methodParametersCount))) {

				max = count;

				matchedJSONWebServiceActionConfig = jsonWebServiceActionConfig;
			}
		}

		if (_log.isDebugEnabled()) {
			if (matchedJSONWebServiceActionConfig == null) {
				_log.debug(
					StringBundler.concat(
						"Unable to match parameters to a JSON web service ",
						"action with path ", path, " for ", contextName));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Matched parameters to a JSON web service action with ",
						"path ", path, " for ", contextName));
			}
		}

		return matchedJSONWebServiceActionConfig;
	}

	private int _getParameterPathIndex(String path) {
		int index = path.indexOf(CharPool.SLASH, 1);

		if (index != -1) {
			index = path.indexOf(CharPool.SLASH, index + 1);
		}

		return index;
	}

	private boolean _removeJSONWebServiceActionConfig(
		JSONWebServiceActionConfig jsonWebServiceActionConfig) {

		if (!_signatureIndexedJSONWebServiceActionConfigs.remove(
				jsonWebServiceActionConfig.getSignature(),
				jsonWebServiceActionConfig)) {

			return false;
		}

		String contextName = jsonWebServiceActionConfig.getContextName();

		List<JSONWebServiceActionConfig> jsonWebServiceActionConfigs =
			_contextNameIndexedJSONWebServiceActionConfigs.get(contextName);

		jsonWebServiceActionConfigs.remove(jsonWebServiceActionConfig);

		if (jsonWebServiceActionConfigs.isEmpty()) {
			_contextNameIndexedJSONWebServiceActionConfigs.remove(contextName);
		}

		jsonWebServiceActionConfigs =
			_pathIndexedJSONWebServiceActionConfigs.get(
				jsonWebServiceActionConfig.getPath());

		jsonWebServiceActionConfigs.remove(jsonWebServiceActionConfig);

		if (jsonWebServiceActionConfigs.isEmpty()) {
			_pathIndexedJSONWebServiceActionConfigs.remove(
				jsonWebServiceActionConfig.getPath());
		}

		return true;
	}

	private String[] _resolvePaths(
		HttpServletRequest httpServletRequest, String path) {

		String contextName = null;

		int index = path.indexOf(CharPool.FORWARD_SLASH, 1);

		if (index != -1) {
			index = path.lastIndexOf(CharPool.PERIOD, index);

			if (index != -1) {
				contextName = path.substring(1, index);
			}
		}

		if (contextName == null) {
			ServletContext servletContext =
				httpServletRequest.getServletContext();

			contextName = servletContext.getServletContextName();

			if (Validator.isNotNull(contextName)) {
				StringBundler sb = new StringBundler(4);

				sb.append(StringPool.SLASH);
				sb.append(contextName);
				sb.append(StringPool.PERIOD);
				sb.append(path.substring(1));

				path = sb.toString();
			}
		}

		return new String[] {contextName, path};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONWebServiceActionsManagerImpl.class);

	private final Map<String, List<JSONWebServiceActionConfig>>
		_contextNameIndexedJSONWebServiceActionConfigs =
			new ConcurrentHashMap<>();
	private final JSONWebServiceNaming _jsonWebServiceNaming =
		new JSONWebServiceNaming();
	private final Map<String, List<JSONWebServiceActionConfig>>
		_pathIndexedJSONWebServiceActionConfigs = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, JSONWebServiceActionConfig>
		_signatureIndexedJSONWebServiceActionConfigs =
			new ConcurrentHashMap<>();

}