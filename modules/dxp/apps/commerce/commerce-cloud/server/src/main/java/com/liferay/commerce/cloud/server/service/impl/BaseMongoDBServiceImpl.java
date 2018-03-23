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

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseMongoDBServiceImpl {

	public BaseMongoDBServiceImpl(Vertx vertx, String connectionString) {
		JsonObject jsonObject = new JsonObject();

		jsonObject.put(
			"connection_string", Objects.requireNonNull(connectionString));

		mongoClient = MongoClient.createShared(vertx, jsonObject);
	}

	protected final MongoClient mongoClient;

}