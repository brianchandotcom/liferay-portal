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

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Di Giorgi
 */
public class GetProjectHandler implements Handler<RoutingContext> {

	public static final String PATH = "/projects/:projectId";

	@Override
	public void handle(RoutingContext routingContext) {
		Project project = routingContext.get(WebKeys.PROJECT);

		JsonObject jsonObject = project.toJson();

		Iterator<Map.Entry<String, Object>> iterator = jsonObject.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();

			if (!_clientPropertyKeys.contains(entry.getKey())) {
				iterator.remove();
			}
		}

		HttpServerResponse httpServerResponse = routingContext.response();

		httpServerResponse.end(jsonObject.encodePrettily());
	}

	private static final Set<String> _clientPropertyKeys =
		Collections.singleton("callbackHost");

}