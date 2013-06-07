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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceActionsManagerUtil {

	public static Set<String> getContextPaths() {
		return _jsonWebServiceActionsManager. getContextPaths();
	}

	public static JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request) {

		return getJSONWebServiceActionsManager().getJSONWebServiceAction(
			request);
	}

	public static JSONWebServiceAction getJSONWebServiceAction(
		HttpServletRequest request, String path, String method,
		Map<String, Object> parameterMap) {

		return getJSONWebServiceActionsManager().getJSONWebServiceAction(
			request, path, method, parameterMap);
	}

	public static JSONWebServiceActionMapping getJSONWebServiceActionMapping(
		String signature) {

		return getJSONWebServiceActionsManager().getJSONWebServiceActionMapping(
			signature);
	}

	public static List<JSONWebServiceActionMapping>
		getJSONWebServiceActionMappings(String contextPath) {

		PortalRuntimePermission.checkGetBeanProperty(
			JSONWebServiceActionsManagerUtil.class);

		return _jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
			contextPath);
	}

	public static int getJSONWebServiceActionsCount(String contextPath) {
		return getJSONWebServiceActionsManager().getJSONWebServiceActionsCount(
			contextPath);
	}

	public static JSONWebServiceActionsManager
		getJSONWebServiceActionsManager() {

		return _jsonWebServiceActionsManager;
	}

	public static void registerJSONWebServiceAction(
		String contextPath, Class<?> actionClass, Method actionMethod,
		String path, String method) {

		getJSONWebServiceActionsManager().registerJSONWebServiceAction(
			contextPath, actionClass, actionMethod, path, method);
	}

	public static int unregisterJSONWebServiceActions(String contextPath) {
		return getJSONWebServiceActionsManager().
			unregisterJSONWebServiceActions(contextPath);
	}

	public void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	private static JSONWebServiceActionsManager _jsonWebServiceActionsManager;

}