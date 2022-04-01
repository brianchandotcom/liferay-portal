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

package com.liferay.site.initializer.testray.extra.gcp.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class MainHttpFunction implements HttpFunction {

	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse)
		throws Exception {

		Main main = new Main(
			_getSystemEnv("LIFERAY_LOGIN"), _getSystemEnv("LIFERAY_PASSWORD"),
			_getSystemEnv("LIFERAY_URL"), _getSystemEnv("S3_API_KEY_PATH"),
			_getSystemEnv("S3_BUCKET_NAME"),
			_getSystemEnv("S3_ERRORED_FOLDER_NAME"),
			_getSystemEnv("S3_INBOX_FOLDER_NAME"),
			_getSystemEnv("S3_PROCESSED_FOLDER_NAME"));

		main.uploadToTestray();
	}

	private String _getSystemEnv(String name) {
		return System.getenv(
			"SITE_INITIALIZER_TESTRAY_EXTRA_GCP_FUNCTION_" + name);
	}

}