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

package com.liferay.server.manager.internal.servlet;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.server.manager.internal.constants.JSONKeys;
import com.liferay.server.manager.internal.executor.Executor;
import com.liferay.server.manager.internal.executor.ExecutorPathResolver;
import com.liferay.server.manager.internal.executor.ExecutorServiceRegistry;

import java.io.IOException;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jonathan Potter
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.server.manager.internal.servlet.ServerManagerServlet",
		"osgi.http.whiteboard.servlet.pattern=/server-manager/*",
		"servlet.init.httpMethods=DELETE,GET,POST,PUT"
	},
	service = Servlet.class
)
public class ServerManagerServlet extends HttpServlet {

	protected void execute(
			HttpServletRequest request, JSONObject responseJSONObject,
			String pathInfo)
		throws Exception {

		String executorPath = getExecutorPath(pathInfo);

		Executor executor = _executorServiceRegistry.getExecutor(executorPath);

		Queue<String> arguments = getExecutorArguments(executorPath, pathInfo);

		String method = request.getMethod();

		if (StringUtil.equalsIgnoreCase(method, HttpMethods.DELETE)) {
			executor.executeDelete(request, responseJSONObject, arguments);
		}
		else if (StringUtil.equalsIgnoreCase(method, HttpMethods.GET)) {
			executor.executeRead(request, responseJSONObject, arguments);
		}
		else if (StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {
			executor.executeCreate(request, responseJSONObject, arguments);
		}
		else if (StringUtil.equalsIgnoreCase(method, HttpMethods.PUT)) {
			executor.executeUpdate(request, responseJSONObject, arguments);
		}
	}

	protected Queue<String> getExecutorArguments(
		String matchingExecutorPath, String pathInfo) {

		Queue<String> arguments = new LinkedList<>();

		String path = StringUtil.toLowerCase(pathInfo);

		path = StringUtil.replace(
			path, matchingExecutorPath + StringPool.SLASH, StringPool.BLANK);

		String[] pathParts = StringUtil.split(path, StringPool.SLASH);

		for (String pathPart : pathParts) {
			arguments.add(pathPart);
		}

		return arguments;
	}

	protected String getExecutorPath(String pathInfo) {
		ExecutorPathResolver executorPathResolver = new ExecutorPathResolver(
			_executorServiceRegistry.getAvailableExecutorPaths());

		return executorPathResolver.getExecutorPath(pathInfo);
	}

	protected boolean isValidUser(HttpServletRequest request) {
		try {
			User user = PortalUtil.getUser(request);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			if (permissionChecker.isOmniadmin()) {
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (!isValidUser(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return;
		}

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject();

		responseJSONObject.put(JSONKeys.ERROR, StringPool.BLANK);
		responseJSONObject.put(JSONKeys.OUTPUT, StringPool.BLANK);
		responseJSONObject.put(JSONKeys.STATUS, 0);

		try {
			execute(request, responseJSONObject, request.getPathInfo());
		}
		catch (Exception e) {
			responseJSONObject.put(
				JSONKeys.ERROR, StackTraceUtil.getStackTrace(e));
			responseJSONObject.put(JSONKeys.STATUS, 1);
		}

		String format = ParamUtil.getString(request, "format");

		if (format.equals("raw")) {
			response.setContentType(ContentTypes.TEXT_PLAIN);

			String outputStream = responseJSONObject.getString(JSONKeys.OUTPUT);

			ServletResponseUtil.write(response, outputStream);
		}
		else {
			response.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(response, responseJSONObject.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServerManagerServlet.class);

	@Reference
	private ExecutorServiceRegistry _executorServiceRegistry;

}