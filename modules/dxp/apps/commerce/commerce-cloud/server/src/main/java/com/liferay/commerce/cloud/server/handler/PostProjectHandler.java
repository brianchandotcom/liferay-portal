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

package com.liferay.commerce.cloud.server.handler;

import com.liferay.commerce.cloud.server.constants.WebKeys;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Andrea Di Giorgi
 */
public class PostProjectHandler implements Handler<RoutingContext> {

	public static final String PATH = GetProjectHandler.PATH;

	public PostProjectHandler(ProjectService projectService) {
		_projectService = projectService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		HttpServerRequest httpServerRequest = routingContext.request();

		String callbackHost = httpServerRequest.getParam("callbackHost");

		_projectService.updateCallbackHost(
			project.getId(), callbackHost,
			asyncResult -> VertxUtil.handleHttpResponse(
				asyncResult, routingContext));
	}

	private final ProjectService _projectService;

}