/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.rest.internal.client;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;

/**
 * @author Ivica Cardic
 */
public class AnalyticsCloudClient {

	public AnalyticsCloudClient(Http http) {
		_http = http;
	}

	public Response executeGraphQl(
			AnalyticsConfiguration analyticsConfiguration, String body)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"OSB-Asah-Data-Source-ID",
			analyticsConfiguration.liferayAnalyticsDataSourceId());
		options.addHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());
		options.addHeader(
			"OSB-Asah-Project-ID",
			analyticsConfiguration.liferayAnalyticsProjectId());
		options.setBody(body, ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			analyticsConfiguration.liferayAnalyticsFaroBackendURL() +
				"/api/1.0/graphql");
		options.setPost(true);

		String responseBody = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		return new Response(responseCode, responseBody);
	}

	public static class Response {

		public Response(int statusCode, String body) {
			_statusCode = statusCode;
			_body = body;
		}

		public String getBody() {
			return _body;
		}

		public int getStatusCode() {
			return _statusCode;
		}

		private final String _body;
		private final int _statusCode;

	}

	private final Http _http;

}