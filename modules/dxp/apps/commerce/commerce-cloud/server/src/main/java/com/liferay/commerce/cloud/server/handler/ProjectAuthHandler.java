/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
public class ProjectAuthHandler implements Handler<RoutingContext> {

	public ProjectAuthHandler(ProjectService projectService) {
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

		routingContext.put(WebKeys.PROJECT, asyncResult.result());

		routingContext.next();
	}

	private final ProjectService _projectService;

}