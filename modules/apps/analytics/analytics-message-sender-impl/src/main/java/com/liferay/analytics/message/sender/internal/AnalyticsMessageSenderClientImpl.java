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
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyService;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = AnalyticsMessageSenderClient.class)
public class AnalyticsMessageSenderClientImpl
	implements AnalyticsMessageSenderClient {

	@Override
	public Object send(String body, long companyId) throws Exception {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			AnalyticsConfiguration analyticsConfiguration =
				_analyticsConfigurationTracker.getAnalyticsConfiguration(
					companyId);

			HttpPost httpPost = new HttpPost(
				analyticsConfiguration.liferayAnalyticsEndpointURL() +
					"/dxp-entities");

			httpPost.setEntity(new StringEntity(body));
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				analyticsConfiguration.
					liferayAnalyticsFaroBackendSecuritySignature());

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_FORBIDDEN) {
				return closeableHttpResponse;
			}

			HttpEntity httpEntity = closeableHttpResponse.getEntity();

			JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
				EntityUtils.toString(httpEntity, Charset.defaultCharset()));

			String message = responseJSONObject.getString("message");

			if (message.equals("INVALID_TOKEN")) {
				_disconnectDataSource(companyId);
			}

			return closeableHttpResponse;
		}
	}

	private void _disconnectDataSource(long companyId) throws PortalException {
		_companyService.removePreferences(
			companyId,
			new String[] {
				"liferayAnalyticsDataSourceId", "liferayAnalyticsEndpointURL",
				"liferayAnalyticsFaroBackendSecuritySignature",
				"liferayAnalyticsFaroBackendURL", "liferayAnalyticsGroupIds",
				"liferayAnalyticsURL"
			});

		_configurationProvider.deleteCompanyConfiguration(
			AnalyticsConfiguration.class, companyId);
	}

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private CompanyService _companyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}