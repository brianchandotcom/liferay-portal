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

import io.netty.handler.codec.http.HttpResponseStatus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceException;

/**
 * @author Andrea Di Giorgi
 */
public class ActiveProjectAuthHandler implements Handler<RoutingContext> {

	public ActiveProjectAuthHandler(ProjectService projectService) {
		_projectService = projectService;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		String projectId = routingContext.pathParam("projectId");

		if (projectId == null) {
			routingContext.fail(HttpResponseStatus.BAD_REQUEST.code());

			return;
		}

		_projectService.getProject(
			projectId,
			asyncResult -> _handleGetProject(asyncResult, routingContext));
	}

	private void _handleGetProject(
		AsyncResult<Project> asyncResult, RoutingContext routingContext) {

		if (asyncResult.failed()) {
			Throwable t = asyncResult.cause();

			if (t instanceof ServiceException) {
				ServiceException serviceException = (ServiceException)t;

				int statusCode = serviceException.failureCode();

				if (statusCode == HttpResponseStatus.NOT_FOUND.code()) {
					statusCode = HttpResponseStatus.UNAUTHORIZED.code();
				}
				else {
					statusCode =
						HttpResponseStatus.INTERNAL_SERVER_ERROR.code();
				}

				routingContext.fail(statusCode);
			}
			else {
				routingContext.fail(t);
			}

			return;
		}

		Project project = asyncResult.result();

		if (!project.isActive()) {
			routingContext.fail(HttpResponseStatus.UNAUTHORIZED.code());

			return;
		}

		routingContext.put(WebKeys.PROJECT, project);

		routingContext.next();
	}

	private final ProjectService _projectService;

}