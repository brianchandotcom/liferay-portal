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