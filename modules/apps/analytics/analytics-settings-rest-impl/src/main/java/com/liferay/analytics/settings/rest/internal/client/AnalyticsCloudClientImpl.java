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

package com.liferay.analytics.settings.rest.internal.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.internal.client.exception.DataSourceConnectionException;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsChannel;
import com.liferay.analytics.settings.rest.internal.client.model.AnalyticsDataSource;
import com.liferay.analytics.settings.rest.internal.client.pagination.Page;
import com.liferay.analytics.settings.rest.internal.client.pagination.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsCloudClient.class)
public class AnalyticsCloudClientImpl implements AnalyticsCloudClient {

	@Override
	public AnalyticsChannel addAnalyticsChannel(long companyId, String name)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);

		Http.Options options = _getOptions(analyticsConfiguration);

		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setBody(
			JSONUtil.put(
				"name", name
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation(
			analyticsConfiguration.liferayAnalyticsFaroBackendURL() +
				"/api/1.0/channels");
		options.setPost(true);

		String content = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
			TypeFactory typeFactory = TypeFactory.defaultInstance();

			ObjectReader objectReader = _objectMapper.readerFor(
				typeFactory.constructCollectionType(
					ArrayList.class, AnalyticsChannel.class));

			List<AnalyticsChannel> analyticsChannels = objectReader.readValue(
				content);

			return analyticsChannels.get(0);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Received response code " + response.getResponseCode());
		}

		throw new PortalException("Unable to add Channel");
	}

	public Map<String, Object> connectAnalyticsDataSource(
			long companyId, String connectionToken)
		throws Exception {

		JSONObject connectionTokenJSONObject = _decodeToken(connectionToken);

		Company company = _companyLocalService.getCompany(companyId);

		Http.Options options = new Http.Options();

		String url = HttpComponentsUtil.addParameter(
			connectionTokenJSONObject.getString("url"), "name",
			company.getName());

		url = HttpComponentsUtil.addParameter(
			url, "portalURL", company.getPortalURL(0));
		url = HttpComponentsUtil.addParameter(
			url, "token", connectionTokenJSONObject.getString("token"));

		options.setLocation(url);

		options.setPost(true);

		String content = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new DataSourceConnectionException(
				"Unable to connect data source");
		}

		JSONObject contentJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		return contentJSONObject.toMap();
	}

	public AnalyticsDataSource disconnectAnalyticsDataSource(long companyId)
		throws Exception {

		AnalyticsConfiguration analyticsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);

		try {
			Http.Options options = _getOptions(analyticsConfiguration);

			options.addHeader(HttpHeaders.CONTENT_LENGTH, "0");
			options.setLocation(
				String.format(
					"%s/api/1.0/data-sources/%s/disconnect",
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					analyticsConfiguration.liferayAnalyticsDataSourceId()));
			options.setPost(true);

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return _objectMapper.readValue(
					content, AnalyticsDataSource.class);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException("Unable to disconnect data source");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to disconnect data source", exception);
		}
	}

	public Page<AnalyticsChannel> fetchAnalyticsChannelsPage(
			long companyId, String keywords, int page, int size)
		throws Exception {

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, companyId);

			Http.Options options = _getOptions(analyticsConfiguration);

			String url = HttpComponentsUtil.addParameter(
				analyticsConfiguration.liferayAnalyticsFaroBackendURL() +
					"/api/1.0/channels",
				"page", page);

			url = HttpComponentsUtil.addParameter(url, "size", size);

			if (Validator.isNotNull(keywords)) {
				url = HttpComponentsUtil.addParameter(url, "filter", keywords);
			}

			options.setLocation(url);

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				List<AnalyticsChannel> analyticsChannels =
					Collections.emptyList();

				JsonNode jsonNode = _objectMapper.readTree(content);

				JsonNode embeddedJsonNode = jsonNode.get("_embedded");

				if (embeddedJsonNode != null) {
					TypeFactory typeFactory = TypeFactory.defaultInstance();

					ObjectReader objectReader = _objectMapper.readerFor(
						typeFactory.constructCollectionType(
							ArrayList.class, AnalyticsChannel.class));

					analyticsChannels = objectReader.readValue(
						embeddedJsonNode.get("channels"));
				}

				JsonNode pageJsonNode = jsonNode.get("page");

				JsonNode totalElementsJsonNode = pageJsonNode.get(
					"totalElements");

				return Page.of(
					analyticsChannels, Pagination.of(page, size),
					totalElementsJsonNode.asLong());
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Received response code %s",
						response.getResponseCode()));
			}

			throw new PortalException("Unable to fetch Channels page");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to fetch Channels page", exception);
		}
	}

	private JSONObject _decodeToken(String connectionToken) throws Exception {
		try {
			if (Validator.isBlank(connectionToken)) {
				throw new IllegalArgumentException();
			}

			return JSONFactoryUtil.createJSONObject(
				new String(Base64.decode(connectionToken)));
		}
		catch (Exception exception) {
			_log.error("Unable to decode token", exception);

			throw new PortalException("Unable to decode token", exception);
		}
	}

	private Http.Options _getOptions(
			AnalyticsConfiguration analyticsConfiguration)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());
		options.addHeader(
			"OSB-Asah-Project-ID",
			analyticsConfiguration.liferayAnalyticsProjectId());

		return options;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsCloudClientImpl.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		}
	};

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private SettingsFactory _settingsFactory;

}