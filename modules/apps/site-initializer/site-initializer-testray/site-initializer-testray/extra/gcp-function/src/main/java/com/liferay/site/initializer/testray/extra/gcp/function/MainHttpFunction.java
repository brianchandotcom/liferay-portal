/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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