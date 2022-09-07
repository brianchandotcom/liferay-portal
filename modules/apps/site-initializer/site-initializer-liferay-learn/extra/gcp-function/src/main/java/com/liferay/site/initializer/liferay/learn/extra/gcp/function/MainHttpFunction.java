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

package com.liferay.site.initializer.liferay.learn.extra.gcp.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

/**
 * @author Allen Ziegenfus
 */
public class MainHttpFunction implements HttpFunction {

	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse)
		throws Exception {

		Main main = new Main(
			_getSystemEnv("LIFERAY_CLIENT_ID"),
			_getSystemEnv("LIFERAY_CLIENT_SECRET"),
			_getSystemEnv("LIFERAY_URL"));

		main.uploadToLiferay();
	}

	private String _getSystemEnv(String name) {
		return System.getenv(
			"SITE_INITIALIZER_LIFERAY_LEARN_EXTRA_GCP_FUNCTION_" + name);
	}

}