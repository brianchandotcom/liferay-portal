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

package com.liferay.commerce.cloud.server.eleflow.service.impl;

import com.liferay.commerce.cloud.server.eleflow.model.EleflowOrder;
import com.liferay.commerce.cloud.server.eleflow.util.EleflowOrderEncoder;
import com.liferay.commerce.cloud.server.model.ForecastOrder;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.ForecastOrderService;
import com.liferay.commerce.cloud.server.service.ForecastProcessorService;
import com.liferay.commerce.cloud.server.service.ProjectService;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.codec.BodyCodec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class EleflowForecastProcessorServiceImpl
	extends BaseEleflowServiceImpl implements ForecastProcessorService {

	public EleflowForecastProcessorServiceImpl(
		Vertx vertx, String host, String path) {

		super(vertx, host, path);

		_forecastOrderService = ForecastOrderService.createProxy(vertx);
		_projectService = ProjectService.createProxy(vertx);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void processForecastOrders(Handler<AsyncResult<Integer>> handler) {
		Future<List<Project>> projectsFuture = Future.future();

		_projectService.getProjects(true, projectsFuture);

		projectsFuture.compose(
			projects -> {
				List<Future> futures = new ArrayList<>(projects.size());

				for (Project project : projects) {
					Future<Integer> future = _processForecastOrders(project);

					futures.add(future);
				}

				return CompositeFuture.all(futures);
			}
		).map(
			compositeFuture -> {
				int forecastOrdersCount = 0;

				for (int i = 0; i < compositeFuture.size(); i++) {
					int projectForecastOrdersCount = compositeFuture.resultAt(
						i);

					forecastOrdersCount += projectForecastOrdersCount;
				}

				return forecastOrdersCount;
			}
		).setHandler(
			handler
		);
	}

	@SuppressWarnings("rawtypes")
	private Future<Integer> _processForecastOrders(Project project) {
		Future<List<ForecastOrder>> forecastOrdersFuture = Future.future();

		_forecastOrderService.getForecastOrders(
			project.getId(), false, forecastOrdersFuture);

		return forecastOrdersFuture.compose(
			forecastOrders -> _sendForecastOrders(project, forecastOrders)
		).compose(
			forecastOrders -> {
				List<Future> futures = new ArrayList<>(forecastOrders.size());

				for (ForecastOrder forecastOrder : forecastOrders) {
					Future<Void> future = _updateForecastOrderProcessed(
						project, forecastOrder, true);

					futures.add(future);
				}

				CompositeFuture compositeFuture = CompositeFuture.all(futures);

				return compositeFuture.map(forecastOrders.size());
			}
		);
	}

	private Future<List<ForecastOrder>> _sendForecastOrders(
		Project project, List<ForecastOrder> forecastOrders) {

		if (forecastOrders.isEmpty()) {
			return Future.succeededFuture(forecastOrders);
		}

		Future<Void> future = Future.future();

		HttpRequest<Void> httpRequest = webClient.post(
			path + "/orders"
		).as(
			BodyCodec.none()
		);

		addAuthorization(httpRequest, project);

		List<EleflowOrder> eleflowOrders = new ArrayList<>(
			forecastOrders.size());

		Iterator<ForecastOrder> iterator = forecastOrders.iterator();

		while (iterator.hasNext()) {
			ForecastOrder forecastOrder = iterator.next();

			EleflowOrder eleflowOrder = null;

			try {
				eleflowOrder = _eleflowOrderEncoder.apply(forecastOrder);
			}
			catch (Exception e) {
				_logger.error(
					"Unable to encode forecast order {0} of project {1}",
					forecastOrder.getOrderId(), project.getId());
			}

			if (eleflowOrder != null) {
				eleflowOrders.add(eleflowOrder);
			}
			else {
				iterator.remove();
			}
		}

		if (forecastOrders.isEmpty()) {
			return Future.succeededFuture(forecastOrders);
		}

		httpRequest.sendJson(
			eleflowOrders,
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, future));

		return future.map(forecastOrders);
	}

	private Future<Void> _updateForecastOrderProcessed(
		Project project, ForecastOrder forecastOrder, boolean processed) {

		Future<Void> future = Future.future();

		_forecastOrderService.updateProcessed(
			project.getId(), forecastOrder.getOrderId(), processed, future);

		return future;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		EleflowForecastProcessorServiceImpl.class);

	private static final Function<ForecastOrder, EleflowOrder>
		_eleflowOrderEncoder = new EleflowOrderEncoder();

	private final ForecastOrderService _forecastOrderService;
	private final ProjectService _projectService;

}