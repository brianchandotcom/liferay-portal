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

import com.liferay.commerce.cloud.server.constants.ContentTypes;
import com.liferay.commerce.cloud.server.eleflow.handler.EleflowForecastCallbackHandler;
import com.liferay.commerce.cloud.server.handler.ActiveProjectAuthHandler;
import com.liferay.commerce.cloud.server.handler.GetForecastConfigurationHandler;
import com.liferay.commerce.cloud.server.handler.GetForecastsHandler;
import com.liferay.commerce.cloud.server.handler.GetProjectHandler;
import com.liferay.commerce.cloud.server.handler.PostForecastConfigurationHandler;
import com.liferay.commerce.cloud.server.handler.PostProjectHandler;
import com.liferay.commerce.cloud.server.handler.ProjectAuthHandler;
import com.liferay.commerce.cloud.server.handler.PutForecastOrdersHandler;
import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;
import com.liferay.commerce.cloud.server.service.ForecastService;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.util.CommerceCloudUtil;

import io.vertx.config.ConfigRetriever;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author Andrea Di Giorgi
 */
public class HttpServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		ConfigRetriever configRetriever = ConfigRetriever.create(vertx);

		ConfigRetriever.getConfigAsFuture(
			configRetriever
		).compose(
			this::_startHttpServer
		).compose(
			httpServer -> {
				if (_logger.isInfoEnabled()) {
					_logger.info(
						"HTTP server started listening on port {0}",
						httpServer.actualPort());
				}

				startFuture.complete();
			},
			startFuture
		);
	}

	private void _addRouteEleflowForecastCallback(
		Router router, ProjectAuthHandler projectAuthHandler,
		ForecastProcessorService forecastProcessorService,
		ForecastService forecastService) {

		Route route = router.post(EleflowForecastCallbackHandler.PATH);

		route.handler(BodyHandler.create());

		route.handler(projectAuthHandler);

		route.handler(
			new EleflowForecastCallbackHandler(
				forecastProcessorService, forecastService));
	}

	private void _addRouteGetForecastConfiguration(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastConfigurationService forecastConfigurationService) {

		Route route = router.get(GetForecastConfigurationHandler.PATH);

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(
			new GetForecastConfigurationHandler(forecastConfigurationService));

		route.produces(ContentTypes.APPLICATION_JSON);
	}

	private void _addRouteGetForecasts(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastService forecastService) {

		Route route = router.get(GetForecastsHandler.PATH);

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(new GetForecastsHandler(forecastService));

		route.produces(ContentTypes.APPLICATION_JSON);
	}

	private void _addRouteGetProject(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler) {

		Route route = router.get(GetProjectHandler.PATH);

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(new GetProjectHandler());

		route.produces(ContentTypes.APPLICATION_JSON);
	}

	private void _addRoutePostForecastConfiguration(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastConfigurationService forecastConfigurationService) {

		Route route = router.post(PostForecastConfigurationHandler.PATH);

		route.handler(BodyHandler.create());

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(
			new PostForecastConfigurationHandler(forecastConfigurationService));

		route.consumes(ContentTypes.APPLICATION_JSON);
	}

	private void _addRoutePostProject(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler,
		ProjectService projectService) {

		Route route = router.post(PostProjectHandler.PATH);

		route.handler(BodyHandler.create());

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(new PostProjectHandler(projectService));
	}

	private void _addRoutePutOrders(
		Router router, ProjectAuthHandler projectAuthHandler,
		ActiveProjectAuthHandler activeProjectAuthHandler,
		ForecastOrderService forecastOrderService) {

		Route route = router.put(PutForecastOrdersHandler.PATH);

		route.handler(BodyHandler.create());

		route.handler(projectAuthHandler);

		route.handler(activeProjectAuthHandler);

		route.handler(new PutForecastOrdersHandler(forecastOrderService));

		route.consumes(ContentTypes.APPLICATION_JSON);
	}

	private Future<HttpServer> _startHttpServer(JsonObject configJsonObject) {
		Future<HttpServer> future = Future.future();

		HttpServer httpServer = vertx.createHttpServer();

		Router router = Router.router(vertx);

		ForecastConfigurationService forecastConfigurationService =
			ForecastConfigurationService.createProxy(vertx);
		ForecastOrderService forecastOrderService =
			ForecastOrderService.createProxy(vertx);
		ForecastProcessorService forecastProcessorService =
			ForecastProcessorService.createProxy(vertx);
		ForecastService forecastService = ForecastService.createProxy(vertx);
		ProjectService projectService = ProjectService.createProxy(vertx);

		ProjectAuthHandler projectAuthHandler = new ProjectAuthHandler(
			projectService);

		_addRouteEleflowForecastCallback(
			router, projectAuthHandler, forecastProcessorService,
			forecastService);

		ActiveProjectAuthHandler activeProjectAuthHandler =
			new ActiveProjectAuthHandler();

		_addRouteGetForecastConfiguration(
			router, projectAuthHandler, activeProjectAuthHandler,
			forecastConfigurationService);
		_addRouteGetForecasts(
			router, projectAuthHandler, activeProjectAuthHandler,
			forecastService);
		_addRouteGetProject(
			router, projectAuthHandler, activeProjectAuthHandler);
		_addRoutePostForecastConfiguration(
			router, projectAuthHandler, activeProjectAuthHandler,
			forecastConfigurationService);
		_addRoutePostProject(
			router, projectAuthHandler, activeProjectAuthHandler,
			projectService);
		_addRoutePutOrders(
			router, projectAuthHandler, activeProjectAuthHandler,
			forecastOrderService);

		httpServer.requestHandler(router::accept);

		int port = CommerceCloudUtil.getPort(configJsonObject);

		httpServer.listen(port, future.completer());

		return future;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		HttpServerVerticle.class);

}