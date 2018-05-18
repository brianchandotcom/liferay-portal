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

package com.liferay.commerce.cloud.server.util;

import com.liferay.commerce.cloud.server.model.JsonSerializable;

import io.netty.handler.codec.http.HttpResponseStatus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.serviceproxy.ServiceException;

/**
 * @author Andrea Di Giorgi
 */
public class VertxUtil {

	@SuppressWarnings("unchecked")
	public static <T> void handleHttpResponse(
		AsyncResult<T> asyncResult, RoutingContext routingContext) {

		if (asyncResult.failed()) {
			routingContext.fail(asyncResult.cause());

			return;
		}

		HttpServerResponse httpServerResponse = routingContext.response();

		T result = asyncResult.result();

		if (result instanceof Iterable<?>) {
			JsonArray jsonArray = JsonUtil.toJsonArray(
				(Iterable<JsonSerializable>)result);

			httpServerResponse.end(jsonArray.encodePrettily());
		}
		else if (result instanceof JsonSerializable) {
			JsonSerializable jsonSerializable = (JsonSerializable)result;

			JsonObject jsonObject = jsonSerializable.toJson();

			httpServerResponse.end(jsonObject.encodePrettily());
		}
		else {
			httpServerResponse.end();
		}
	}

	public static <T> void handleServiceHttpResponse(
		AsyncResult<HttpResponse<T>> asyncResult,
		Handler<AsyncResult<T>> handler) {

		if (asyncResult.failed()) {
			Throwable t = asyncResult.cause();

			JsonObject jsonObject = new JsonObject();

			jsonObject.put("stackTrace", StackTraceUtil.getStackTrace(t));

			handler.handle(
				ServiceException.fail(
					HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
					t.getMessage(), jsonObject));

			return;
		}

		HttpResponse<T> httpResponse = asyncResult.result();

		int statusCode = httpResponse.statusCode();

		if ((statusCode != HttpResponseStatus.NO_CONTENT.code()) &&
			(statusCode != HttpResponseStatus.OK.code())) {

			JsonObject jsonObject = new JsonObject();

			jsonObject.put("response", httpResponse.bodyAsJsonObject());
			jsonObject.put("statusCode", statusCode);

			handler.handle(
				ServiceException.fail(
					statusCode, httpResponse.statusMessage(), jsonObject));

			return;
		}

		handler.handle(Future.succeededFuture(httpResponse.body()));
	}

}