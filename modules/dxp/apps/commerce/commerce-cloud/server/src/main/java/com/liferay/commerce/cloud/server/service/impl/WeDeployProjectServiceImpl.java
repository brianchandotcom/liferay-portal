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

package com.liferay.commerce.cloud.server.service.impl;

import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.util.JsonUtil;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.codec.impl.BodyCodecImpl;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeployProjectServiceImpl
	extends BaseWeDeployServiceImpl implements ProjectService {

	public WeDeployProjectServiceImpl(Vertx vertx, String host, String token) {
		super(vertx, host, token);
	}

	@Override
	public void addProject(
		Project project, Handler<AsyncResult<Project>> handler) {

		HttpRequest<Project> httpRequest = webClient.post(
			"/projects"
		).as(
			_projectBodyCodec
		);

		addAuthorization(httpRequest);

		httpRequest.sendJsonObject(
			project.toJson(),
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	@Override
	public void getProject(
		String projectId, Handler<AsyncResult<Project>> handler) {

		HttpRequest<Project> httpRequest = webClient.get(
			"/projects/" + projectId
		).as(
			_projectBodyCodec
		);

		addAuthorization(httpRequest);

		httpRequest.send(
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	@Override
	public void getProjects(
		boolean active, Handler<AsyncResult<List<Project>>> handler) {

		HttpRequest<List<Project>> httpRequest = webClient.get(
			"/projects"
		).as(
			_projectsBodyCodec
		);

		addAuthorization(httpRequest);

		JsonArray filterJsonArray = new JsonArray();

		filterJsonArray.add(JsonUtil.getFilterJsonObject("active", active));

		httpRequest.setQueryParam("filter", filterJsonArray.encode());

		httpRequest.send(
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	@Override
	public void updateCallbackHost(
		String projectId, String callbackHost,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = webClient.patch(
			"/projects/" + projectId
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest);

		JsonObject jsonObject = new JsonObject();

		if (callbackHost == null) {
			callbackHost = "";
		}

		jsonObject.put("callbackHost", callbackHost);

		httpRequest.sendJsonObject(
			jsonObject,
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	private static final BodyCodec<Project> _projectBodyCodec =
		BodyCodec.create(
			BodyCodecImpl.JSON_OBJECT_DECODER.andThen(Project::new));
	private static final BodyCodec<List<Project>> _projectsBodyCodec =
		BodyCodec.create(
			BodyCodecImpl.JSON_ARRAY_DECODER.andThen(
				jsonArray -> JsonUtil.fromJsonArray(jsonArray, Project::new)));

}