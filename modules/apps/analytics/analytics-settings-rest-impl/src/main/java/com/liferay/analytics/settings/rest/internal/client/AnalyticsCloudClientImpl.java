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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
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
			_log.debug("Response code " + response.getResponseCode());
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
				"Unable to connect analytics data source");
		}

		JSONObject contentJSONObject = _jsonFactory.createJSONObject(content);

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

			throw new PortalException(
				"Unable to disconnect analytics data source");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to disconnect analytics data source", exception);
		}
	}

	public Page<AnalyticsChannel> getAnalyticsChannelsPage(
			long companyId, String keywords, int page, int size, Sort[] sorts)
		throws Exception {

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, companyId);

			Http.Options options = _getOptions(analyticsConfiguration);

			String url =
				analyticsConfiguration.liferayAnalyticsFaroBackendURL() +
					"/api/1.0/channels";

			if (Validator.isNotNull(keywords)) {
				url = HttpComponentsUtil.addParameter(url, "filter", keywords);
			}

			url = HttpComponentsUtil.addParameter(url, "page", page);
			url = HttpComponentsUtil.addParameter(url, "size", size);

			if (!ArrayUtil.isEmpty(sorts)) {
				StringBundler sb = new StringBundler(sorts.length * 3);

				for (Sort sort : sorts) {
					sb.append(sort.getFieldName());
					sb.append(StringPool.COMMA);

					if (sort.isReverse()) {
						sb.append("desc");
					}
					else {
						sb.append("asc");
					}
				}

				url = HttpComponentsUtil.addParameter(
					url, "sort", sb.toString());
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
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException("Unable to get analytics channels page");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to get analytics channels page", exception);
		}
	}

	@Override
	public AnalyticsChannel updateAnalyticsChannel(
			String channelId, Long[] commerceChannelIds, long companyId,
			String dataSourceId, Locale locale, Long[] siteGroupIds)
		throws Exception {

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, companyId);

			if (!dataSourceId.equals(
					analyticsConfiguration.liferayAnalyticsDataSourceId())) {

				throw new IllegalArgumentException("Unknown data source ID");
			}

			Http.Options options = _getOptions(analyticsConfiguration);

			options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
			options.setBody(
				JSONUtil.put(
					"commerceChannels",
					_getGroupsJSONArray(
						commerceChannelId -> _groupLocalService.fetchGroup(
							companyId, _commerceChannelClassNameId,
							commerceChannelId),
						commerceChannelIds, locale)
				).put(
					"dataSourceId", dataSourceId
				).put(
					"groups",
					_getGroupsJSONArray(
						groupId -> _groupLocalService.fetchGroup(groupId),
						siteGroupIds, locale)
				).toString(),
				ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			options.setLocation(
				String.format(
					"%s/api/1.0/channels/%s",
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					channelId));
			options.setPatch(true);

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				JsonNode jsonNode = _objectMapper.readTree(content);

				ObjectReader objectReader = _objectMapper.readerFor(
					AnalyticsChannel.class);

				return objectReader.readValue(jsonNode.get("channel"));
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException("Unable to update analytics channel");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to update analytics channels", exception);
		}
	}

	public AnalyticsDataSource updateAnalyticsDataSourceDetails(
			Boolean accountsSelected, long companyId,
			Boolean commerceChannelsSelected, Boolean contactsSelected,
			Boolean sitesSelected)
		throws Exception {

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AnalyticsConfiguration.class, companyId);

			Http.Options options = _getOptions(analyticsConfiguration);

			options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
			options.setBody(
				JSONUtil.put(
					"accountsSelected", accountsSelected
				).put(
					"commerceChannelsSelected", commerceChannelsSelected
				).put(
					"contactsSelected", contactsSelected
				).put(
					"sitesSelected", sitesSelected
				).toString(),
				ContentTypes.APPLICATION_JSON, StringPool.UTF8);
			options.setLocation(
				String.format(
					"%s/api/1.0/data-sources/%s/details",
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					analyticsConfiguration.liferayAnalyticsDataSourceId()));
			options.setPut(true);

			String content = _http.URLtoString(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return _objectMapper.readValue(
					content, AnalyticsDataSource.class);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Response code " + response.getResponseCode());
			}

			throw new PortalException(
				"Unable to update analytics data source details");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new PortalException(
				"Unable to update analytics data source details", exception);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceChannelClassNameId = _portal.getClassNameId(
			"com.liferay.commerce.product.model.CommerceChannel");
	}

	private JSONObject _decodeToken(String connectionToken) throws Exception {
		try {
			if (Validator.isBlank(connectionToken)) {
				throw new IllegalArgumentException();
			}

			return _jsonFactory.createJSONObject(
				new String(Base64.decode(connectionToken)));
		}
		catch (Exception exception) {
			_log.error("Unable to decode token", exception);

			throw new PortalException("Unable to decode token", exception);
		}
	}

	private JSONArray _getGroupsJSONArray(
			Function<Long, Group> fetchGroupFunction, Long[] groupIds,
			Locale locale)
		throws Exception {

		return JSONUtil.toJSONArray(
			groupIds,
			groupId -> {
				Group group = fetchGroupFunction.apply(groupId);

				if (group == null) {
					return null;
				}

				return JSONUtil.put(
					"id", String.valueOf(group.getClassPK())
				).put(
					"name",
					() -> {
						try {
							return group.getDescriptiveName(locale);
						}
						catch (PortalException portalException) {
							_log.error(portalException);

							return _language.get(locale, "unknown");
						}
					}
				);
			});
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

	private long _commerceChannelClassNameId;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}