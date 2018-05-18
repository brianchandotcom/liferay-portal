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