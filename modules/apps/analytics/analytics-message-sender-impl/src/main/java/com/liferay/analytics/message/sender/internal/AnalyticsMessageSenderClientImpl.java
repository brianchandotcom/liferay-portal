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
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionCheckerUtil;

import java.nio.charset.Charset;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
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
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.liferayAnalyticsEndpointURL() == null) {
			return null;
		}

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			body,
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.POST,
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/dxp-entities");

		return _execute(companyId, httpUriRequest);
	}

	@Override
	public void validateConnection(long companyId) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.liferayAnalyticsEndpointURL() == null) {
			return;
		}

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			null,
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.GET,
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/api/1.0/data-sources/" +
					analyticsConfiguration.liferayAnalyticsDataSourceId());

		_execute(companyId, httpUriRequest);
	}

	private HttpUriRequest _buildHttpUriRequest(
			String body, String liferayAnalyticsFaroBackendSecuritySignature,
			String method, String url)
		throws Exception {

		HttpUriRequest httpUriRequest = null;

		if (method.equals(HttpMethods.GET)) {
			httpUriRequest = new HttpGet(url);
		}
		else if (method.equals(HttpMethods.POST)) {
			HttpPost httpPost = new HttpPost(url);

			if (Validator.isNotNull(body)) {
				httpPost.setEntity(new StringEntity(body));
			}

			httpUriRequest = httpPost;
		}

		httpUriRequest.setHeader("Content-Type", "application/json");
		httpUriRequest.setHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			liferayAnalyticsFaroBackendSecuritySignature);

		return httpUriRequest;
	}

	private void _disconnectDataSource(long companyId) {
		PermissionCheckerUtil.setThreadValues(
			_userLocalService.fetchUserByScreenName(
				companyId,
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN));

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.setProperty("liferayAnalyticsConnectionType", "");
		unicodeProperties.setProperty("liferayAnalyticsDataSourceId", "");
		unicodeProperties.setProperty("liferayAnalyticsEndpointURL", "");
		unicodeProperties.setProperty(
			"liferayAnalyticsFaroBackendSecuritySignature", "");
		unicodeProperties.setProperty("liferayAnalyticsFaroBackendURL", "");
		unicodeProperties.setProperty("liferayAnalyticsGroupIds", "");
		unicodeProperties.setProperty("liferayAnalyticsURL", "");

		try {
			_companyService.updatePreferences(companyId, unicodeProperties);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics preferences for company " +
						companyId);
			}
		}

		try {
			_configurationProvider.deleteCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics configuration for company " +
						companyId);
			}
		}
	}

	private CloseableHttpResponse _execute(
			long companyId, HttpUriRequest httpUriRequest)
		throws Exception {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

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

			String message = responseJSONObject.getString("message");

			if (message.equals("INVALID_TOKEN")) {
				_disconnectDataSource(companyId);

				_analyticsMessageLocalService.deleteAnalyticsMessages(
					companyId);
			}

			return closeableHttpResponse;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessageSenderClientImpl.class);

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserLocalService _userLocalService;

}