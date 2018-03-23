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

package com.liferay.commerce.cloud.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.liferay.commerce.cloud.server.constants.ContentTypes;
import com.liferay.commerce.cloud.server.handler.ActiveProjectAuthHandler;
import com.liferay.commerce.cloud.server.handler.GetForecastConfigurationHandler;
import com.liferay.commerce.cloud.server.handler.PostOrdersHandler;
import com.liferay.commerce.cloud.server.handler.PutForecastConfigurationHandler;
import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.service.OrderService;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		CompositeFuture deployVerticleCompositeFuture = CompositeFuture.all(
			_deployServiceVerticle(vertx, ForecastConfigurationService.ADDRESS),
			_deployServiceVerticle(vertx, OrderService.ADDRESS),
			_deployServiceVerticle(vertx, ProjectService.ADDRESS));

		Future<JsonObject> configJsonObjectFuture = _getConfigJsonObject();

		Future<HttpServer> httpServerFuture = configJsonObjectFuture.compose(
			this::_startHttpServer);

		CompositeFuture compositeFuture = CompositeFuture.all(
			deployVerticleCompositeFuture, httpServerFuture);

		compositeFuture.setHandler(
			asyncResult -> {
				if (asyncResult.failed()) {
					startFuture.fail(asyncResult.cause());

					return;
				}

				if (_logger.isInfoEnabled()) {
					_logger.info("Commerce Cloud Server started succesfully");
				}

				startFuture.complete();
			});
	}

	private static Future<String> _deployServiceVerticle(
		Vertx vertx, String serviceName) {

		Future<String> future = Future.future();

		vertx.deployVerticle("service:" + serviceName, future);

		return future;
	}

	private void _addRouteGetForecastConfiguration(
		Router router, ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastConfigurationService forecastConfigurationService) {

		Route route = router.get("/projects/:projectId/configuration/");

		route.handler(activeProjectAuthHandler);

		route.handler(
			new GetForecastConfigurationHandler(forecastConfigurationService));

		route.produces(ContentTypes.APPLICATION_JSON);
	}

	private void _addRoutePostOrders(
		Router router, ActiveProjectAuthHandler activeProjectAuthHandler,
		OrderService orderService) {

		Route route = router.post("/projects/:projectId/orders/");

		route.handler(BodyHandler.create());

		route.handler(activeProjectAuthHandler);

		route.handler(new PostOrdersHandler(orderService));

		route.consumes(ContentTypes.APPLICATION_JSON);
	}

	private void _addRoutePutForecastConfiguration(
		Router router, ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastConfigurationService forecastConfigurationService) {

		Route route = router.put("/projects/:projectId/configuration/");

		route.handler(BodyHandler.create());

		route.handler(activeProjectAuthHandler);

		route.handler(
			new PutForecastConfigurationHandler(forecastConfigurationService));

		route.consumes(ContentTypes.APPLICATION_JSON);
	}

	private Future<JsonObject> _getConfigJsonObject() {
		ConfigRetriever configRetriever = ConfigRetriever.create(vertx);

		return ConfigRetriever.getConfigAsFuture(configRetriever);
	}

	private Future<HttpServer> _startHttpServer(JsonObject configJsonObject) {
		Future<HttpServer> future = Future.future();

		HttpServer httpServer = vertx.createHttpServer();

		Router router = Router.router(vertx);

		ForecastConfigurationService forecastConfigurationService =
			ForecastConfigurationService.createProxy(vertx);
		OrderService orderService = OrderService.createProxy(vertx);
		ProjectService projectService = ProjectService.createProxy(vertx);

		ActiveProjectAuthHandler activeProjectAuthHandler =
			new ActiveProjectAuthHandler(projectService);

		_addRouteGetForecastConfiguration(
			router, activeProjectAuthHandler, forecastConfigurationService);
		_addRoutePostOrders(router, activeProjectAuthHandler, orderService);
		_addRoutePutForecastConfiguration(
			router, activeProjectAuthHandler, forecastConfigurationService);

		httpServer.requestHandler(router::accept);

		int port = CommerceCloudUtil.getPort(configJsonObject);

		httpServer.listen(port, future.completer());

		return future;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		CommerceCloudServerVerticle.class);

	static {
		Json.mapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Json.mapper.registerModule(new JavaTimeModule());
	}

}