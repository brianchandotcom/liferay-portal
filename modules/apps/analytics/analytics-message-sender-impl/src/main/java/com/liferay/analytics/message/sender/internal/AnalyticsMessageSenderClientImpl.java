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

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsDescriptor;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.net.UnknownHostException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Set;

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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = AnalyticsMessageSenderClient.class)
public class AnalyticsMessageSenderClientImpl
	extends BaseAnalyticsClientImpl implements AnalyticsMessageSenderClient {

	@Override
	public Object send(String body, long companyId) throws Exception {
		if (!isEnabled(companyId)) {
			return null;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(companyId);

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			body, analyticsConfiguration.liferayAnalyticsDataSourceId(),
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.POST,
			analyticsConfiguration.liferayAnalyticsProjectId(),
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/dxp-entities");

		return _execute(analyticsConfiguration, companyId, httpUriRequest);
	}

	@Override
	public void validateConnection(long companyId) throws Exception {
		if (!isEnabled(companyId)) {
			return;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationRegistry.getAnalyticsConfiguration(companyId);

		_checkEndpoints(analyticsConfiguration, companyId);

		HttpUriRequest httpUriRequest = _buildHttpUriRequest(
			null, analyticsConfiguration.liferayAnalyticsDataSourceId(),
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature(),
			HttpMethods.GET, analyticsConfiguration.liferayAnalyticsProjectId(),
			analyticsConfiguration.liferayAnalyticsFaroBackendURL() +
				"/api/1.0/data-sources/" +
					analyticsConfiguration.liferayAnalyticsDataSourceId());

		_execute(analyticsConfiguration, companyId, httpUriRequest);
	}

	private HttpUriRequest _buildHttpUriRequest(
			String body, String dataSourceId,
			String faroBackendSecuritySignature, String method,
			String projectId, String url)
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
			httpUriRequest.setHeader("OSB-Asah-Project-ID", projectId);
		}

		return httpUriRequest;
	}

	private void _checkEndpoints(
			AnalyticsConfiguration analyticsConfiguration, long companyId)
		throws Exception {

		HttpGet httpGet = new HttpGet(
			analyticsConfiguration.liferayAnalyticsURL() + "/endpoints/" +
				analyticsConfiguration.liferayAnalyticsProjectId());

		try (CloseableHttpClient closeableHttpClient =
				getCloseableHttpClient()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet);

			JSONObject responseJSONObject = null;

			try {
				responseJSONObject = _jsonFactory.createJSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));
			}
			catch (Exception exception) {
				_log.error(
					"Unable to check Analytics Cloud endpoints", exception);

				return;
			}

			String liferayAnalyticsEndpointURL = responseJSONObject.getString(
				"liferayAnalyticsEndpointURL");
			String liferayAnalyticsFaroBackendURL =
				responseJSONObject.getString("liferayAnalyticsFaroBackendURL");

			if (liferayAnalyticsEndpointURL.equals(
					PrefsPropsUtil.getString(
						companyId, "liferayAnalyticsEndpointURL")) &&
				liferayAnalyticsFaroBackendURL.equals(
					PrefsPropsUtil.getString(
						companyId, "liferayAnalyticsFaroBackendURL"))) {

				return;
			}

			companyLocalService.updatePreferences(
				companyId,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"liferayAnalyticsEndpointURL", liferayAnalyticsEndpointURL
				).put(
					"liferayAnalyticsFaroBackendURL",
					liferayAnalyticsFaroBackendURL
				).build());

			Dictionary<String, Object> configurationProperties =
				_getConfigurationProperties(companyId);

			configurationProperties.put(
				"liferayAnalyticsEndpointURL", liferayAnalyticsEndpointURL);

			configurationProvider.saveCompanyConfiguration(
				AnalyticsConfiguration.class, companyId,
				configurationProperties);
		}
	}

	private CloseableHttpResponse _execute(
			AnalyticsConfiguration analyticsConfiguration, long companyId,
			HttpUriRequest httpUriRequest)
		throws Exception {

		try (CloseableHttpClient closeableHttpClient =
				getCloseableHttpClient()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpUriRequest);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			boolean disconnected = false;
			JSONObject responseJSONObject = _jsonFactory.createJSONObject();

			String response = EntityUtils.toString(
				closeableHttpResponse.getEntity(), Charset.defaultCharset());

			if ((response != null) && response.startsWith("{")) {
				responseJSONObject = _jsonFactory.createJSONObject(response);

				disconnected = StringUtil.equals(
					GetterUtil.getString(responseJSONObject.getString("state")),
					"DISCONNECTED");
			}

			if ((statusLine.getStatusCode() != HttpStatus.SC_FORBIDDEN) &&
				!disconnected) {

				return closeableHttpResponse;
			}

			processInvalidTokenMessage(
				companyId, disconnected,
				responseJSONObject.getString("message"));

			return closeableHttpResponse;
		}
		catch (UnknownHostException unknownHostException) {
			_checkEndpoints(analyticsConfiguration, companyId);

			throw unknownHostException;
		}
	}

	private Dictionary<String, Object> _getConfigurationProperties(
			long companyId)
		throws Exception {

		Dictionary<String, Object> configurationProperties = new Hashtable<>();

		Class<?> clazz = AnalyticsConfiguration.class;

		Meta.OCD ocd = clazz.getAnnotation(Meta.OCD.class);

		Settings settings = _settingsFactory.getSettings(
			new CompanyServiceSettingsLocator(companyId, ocd.id()));

		SettingsDescriptor settingsDescriptor =
			_settingsFactory.getSettingsDescriptor(ocd.id());

		if (settingsDescriptor == null) {
			return configurationProperties;
		}

		Set<String> multiValuedKeys = settingsDescriptor.getMultiValuedKeys();

		for (String multiValuedKey : multiValuedKeys) {
			configurationProperties.put(
				multiValuedKey,
				settings.getValues(multiValuedKey, new String[0]));
		}

		Set<String> keys = settingsDescriptor.getAllKeys();

		keys.removeAll(multiValuedKeys);

		for (String key : keys) {
			configurationProperties.put(
				key, settings.getValue(key, StringPool.BLANK));
		}

		return configurationProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsMessageSenderClientImpl.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.analytics.settings.web)(release.schema.version>=1.0.1))"
	)
	private Release _release;

	@Reference
	private SettingsFactory _settingsFactory;

}