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

package com.liferay.jenkins.results.parser.kubernetes;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;

import java.io.IOException;

/**
 * @author Kenji Heigel
 */
public class LiferayKubernetesConnection {

	public static AppsV1Api apps;
	public static CoreV1Api core;

	public static LiferayKubernetesConnection getInstance() {
		if (_liferayKubernetesApi == null) {
			_liferayKubernetesApi = new LiferayKubernetesConnection();
		}

		return _liferayKubernetesApi;
	}

	private static final ApiClient _apiClient;
	private static LiferayKubernetesConnection _liferayKubernetesApi;

	static {
		try {
			_apiClient = Config.defaultClient();

			Configuration.setDefaultApiClient(_apiClient);

			_apiClient.setDebugging(true);

			apps = new AppsV1Api(_apiClient);
			core = new CoreV1Api(_apiClient);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}