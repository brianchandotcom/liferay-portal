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

package com.liferay.commerce.cloud.server.service.impl;

import com.liferay.commerce.cloud.server.constants.ContentTypes;
import com.liferay.commerce.cloud.server.model.Forecast;
import com.liferay.commerce.cloud.server.model.Project;
import com.liferay.commerce.cloud.server.service.PushSenderService;
import com.liferay.commerce.cloud.server.util.JsonUtil;
import com.liferay.commerce.cloud.server.util.VertxUtil;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.codec.BodyCodec;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class PushSenderServiceImpl implements PushSenderService {

	public PushSenderServiceImpl(Vertx vertx) {
		WebClientOptions webClientOptions = new WebClientOptions();

		webClientOptions.setDefaultPort(443);
		webClientOptions.setSsl(true);

		_webClient = WebClient.create(vertx, webClientOptions);
	}

	@Override
	public void pushForecasts(
		Project project, List<Forecast> forecasts,
		Handler<AsyncResult<Void>> handler) {

		HttpRequest<Void> httpRequest = _webClient.post(
			project.getCallbackHost(),
			"/o/commerce-cloud-client-rest/" + project.getId() + "/forecasts"
		).as(
			BodyCodec.none()
		);

		JsonArray jsonArray = JsonUtil.toJsonArray(forecasts);

		httpRequest.putHeader(
			HttpHeaders.CONTENT_TYPE.toString(), ContentTypes.APPLICATION_JSON);

		httpRequest.sendBuffer(
			Buffer.buffer(jsonArray.encode()),
			asyncResult -> VertxUtil.handleServiceHttpResponse(
				asyncResult, handler));
	}

	private final WebClient _webClient;

}