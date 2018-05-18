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

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseWeDeployServiceImpl {

	public BaseWeDeployServiceImpl(Vertx vertx, String host, String token) {
		_token = Objects.requireNonNull(token);

		WebClientOptions webClientOptions = new WebClientOptions();

		webClientOptions.setDefaultHost(Objects.requireNonNull(host));
		webClientOptions.setDefaultPort(443);
		webClientOptions.setSsl(true);

		webClient = WebClient.create(vertx, webClientOptions);
	}

	protected void addAuthorization(HttpRequest<?> httpRequest) {
		httpRequest.putHeader(
			HttpHeaders.AUTHORIZATION.toString(), "Bearer " + _token);
	}

	protected final WebClient webClient;

	private final String _token;

}