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

package com.liferay.commerce.cloud.server.util;

import com.liferay.commerce.cloud.server.model.JsonSerializable;

import io.netty.handler.codec.http.HttpResponseStatus;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.serviceproxy.ServiceException;

import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class VertxUtil {

	public static <T> void handleHttpResponse(
		AsyncResult<T> asyncResult, RoutingContext routingContext) {

		if (asyncResult.failed()) {
			routingContext.fail(asyncResult.cause());

			return;
		}

		HttpServerResponse httpServerResponse = routingContext.response();

		T result = asyncResult.result();

		if (result instanceof JsonSerializable) {
			JsonSerializable jsonable = (JsonSerializable)result;

			JsonObject jsonObject = jsonable.toJson();

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

		if (statusCode != HttpResponseStatus.OK.code()) {
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

	public static <T> void handleServiceJsonObject(
		AsyncResult<JsonObject> asyncResult, Handler<AsyncResult<T>> handler,
		Function<JsonObject, T> function) {

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

		JsonObject jsonObject = asyncResult.result();

		if (jsonObject == null) {
			handler.handle(
				ServiceException.fail(
					HttpResponseStatus.NOT_FOUND.code(),
					"Unable to get object"));

			return;
		}

		T object = function.apply(jsonObject);

		handler.handle(Future.succeededFuture(object));
	}

}