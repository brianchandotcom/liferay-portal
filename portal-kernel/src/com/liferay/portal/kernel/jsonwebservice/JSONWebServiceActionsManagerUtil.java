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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerUtil {

	public static Set<String> getContextNames() {
		return _jsonWebServiceActionsManager.getContextNames();
	}

	public static JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest httpServletRequest)
		throws NoSuchJSONWebServiceException {

		return _jsonWebServiceActionsManager.getJSONWebServiceAction(
			httpServletRequest);
	}

	public static JSONWebServiceAction getJSONWebServiceAction(
			HttpServletRequest httpServletRequest, String path, String method,
			Map<String, Object> parameterMap)
		throws NoSuchJSONWebServiceException {

		return _jsonWebServiceActionsManager.getJSONWebServiceAction(
			httpServletRequest, path, method, parameterMap);
	}

	public static JSONWebServiceActionMapping getJSONWebServiceActionMapping(
		String signature) {

		return _jsonWebServiceActionsManager.getJSONWebServiceActionMapping(
			signature);
	}

	public static List<JSONWebServiceActionMapping>
		getJSONWebServiceActionMappings(String contextName) {

		return _jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
			contextName);
	}

	public static JSONWebServiceActionsManager
		getJSONWebServiceActionsManager() {

		return _jsonWebServiceActionsManager;
	}

	public static void registerJSONWebServiceAction(
		String contextName, String contextPath, Object actionObject,
		Class<?> actionClass, Method actionMethod, String path, String method) {

		_jsonWebServiceActionsManager.registerJSONWebServiceAction(
			contextName, contextPath, actionObject, actionClass, actionMethod,
			path, method);
	}

	public static int registerServletContext(ServletContext servletContext) {
		return _jsonWebServiceActionsManager.registerServletContext(
			servletContext);
	}

	public static int unregisterJSONWebServiceActions(Object actionObject) {
		return _jsonWebServiceActionsManager.unregisterJSONWebServiceActions(
			actionObject);
	}

	public static int unregisterServletContext(ServletContext servletContext) {
		return _jsonWebServiceActionsManager.unregisterServletContext(
			servletContext);
	}

	private static volatile JSONWebServiceActionsManager
		_jsonWebServiceActionsManager =
			ServiceProxyFactory.newServiceTrackedInstance(
				JSONWebServiceActionsManager.class,
				JSONWebServiceActionsManagerUtil.class,
				"_jsonWebServiceActionsManager", false);

}