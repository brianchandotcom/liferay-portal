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
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * @author Andrea Di Giorgi
 */
public class MongoDBProjectServiceImpl
	extends BaseMongoDBServiceImpl implements ProjectService {

	public MongoDBProjectServiceImpl(Vertx vertx, String connectionString) {
		super(vertx, connectionString);
	}

	@Override
	public void addProject(
		Project project, Handler<AsyncResult<Project>> handler) {

		// TODO Auto-generated method stub

	}

	@Override
	public void getProject(
		String projectId, Handler<AsyncResult<Project>> handler) {

		JsonObject queryJsonObject = new JsonObject();

		queryJsonObject.put("projectId", projectId);

		mongoClient.findOne(
			_COLLECTION, queryJsonObject, null,
			asyncResult -> VertxUtil.handleServiceJsonObject(
				asyncResult, handler, Project::new));
	}

	private static final String _COLLECTION = "projects";

}