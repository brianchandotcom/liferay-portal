/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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