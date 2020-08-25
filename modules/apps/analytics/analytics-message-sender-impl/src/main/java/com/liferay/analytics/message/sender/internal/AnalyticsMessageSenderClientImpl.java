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

package com.liferay.analytics.message.sender.internal;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.Validator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = AnalyticsMessageSenderClient.class)
public class AnalyticsMessageSenderClientImpl
	extends BaseAnalyticsClientImpl implements AnalyticsMessageSenderClient {

	@Override
	public Object send(String body, long companyId) throws Exception {
		if (!isEnabled(companyId)) {
			return null;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			body, analyticsConfiguration.liferayAnalyticsDataSourceId(),
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.POST,
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/dxp-entities");

		return _execute(companyId, httpUriRequest);
	}

	@Override
	public void validateConnection(long companyId) throws Exception {
		if (!isEnabled(companyId)) {
			return;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			null, analyticsConfiguration.liferayAnalyticsDataSourceId(),
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.GET,
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/api/1.0/data-sources/" +
					analyticsConfiguration.liferayAnalyticsDataSourceId());

		_execute(companyId, httpUriRequest);
	}

	private HttpUriRequest _buildHttpUriRequest(
			String body, String dataSourceId,
			String faroBackendSecuritySignature, String method, String url)
		throws Exception {

		HttpUriRequest httpUriRequest = null;

		if (method.equals(HttpMethods.GET)) {
			httpUriRequest = new HttpGet(url);
		}
		else if (method.equals(HttpMethods.POST)) {
			HttpPost httpPost = new HttpPost(url);

			if (Validator.isNotNull(body)) {
				httpPost.setEntity(
					new StringEntity(body, StandardCharsets.UTF_8));
			}

			httpUriRequest = httpPost;
		}

		if (httpUriRequest != null) {
			httpUriRequest.setHeader("Content-Type", "application/json");
			httpUriRequest.setHeader("OSB-Asah-Data-Source-ID", dataSourceId);
			httpUriRequest.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				faroBackendSecuritySignature);
		}

		return httpUriRequest;
	}

	private CloseableHttpResponse _execute(
			long companyId, HttpUriRequest httpUriRequest)
		throws Exception {

		try (CloseableHttpClient closeableHttpClient =
				getCloseableHttpClient()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpUriRequest);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_FORBIDDEN) {
				return closeableHttpResponse;
			}

			JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
				EntityUtils.toString(
					closeableHttpResponse.getEntity(),
					Charset.defaultCharset()));

			processInvalidTokenMessage(
				companyId, responseJSONObject.getString("message"));

			return closeableHttpResponse;
		}
	}

}