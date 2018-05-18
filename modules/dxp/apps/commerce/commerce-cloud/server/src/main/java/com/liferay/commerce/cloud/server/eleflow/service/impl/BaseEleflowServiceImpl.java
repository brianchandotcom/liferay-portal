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

import com.liferay.commerce.cloud.server.model.Project;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseEleflowServiceImpl {

	public BaseEleflowServiceImpl(Vertx vertx, String host, String path) {
		this.path = Objects.requireNonNull(path);

		WebClientOptions webClientOptions = new WebClientOptions();

		webClientOptions.setDefaultHost(Objects.requireNonNull(host));
		webClientOptions.setDefaultPort(443);
		webClientOptions.setSsl(true);

		webClient = WebClient.create(vertx, webClientOptions);
	}

	protected void addAuthorization(
		HttpRequest<?> httpRequest, Project project) {

		httpRequest.putHeader("x-api-key", project.getApiKey());
	}

	protected final String path;
	protected final WebClient webClient;

}