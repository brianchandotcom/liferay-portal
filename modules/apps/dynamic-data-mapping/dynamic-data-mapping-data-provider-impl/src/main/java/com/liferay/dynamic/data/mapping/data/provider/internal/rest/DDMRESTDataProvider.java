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

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration;
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.json.web.service.client.JSONWebServiceClientFactory;
import com.liferay.portal.json.web.service.client.JSONWebServiceException;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;

import java.net.URI;

import java.nio.charset.StandardCharsets;

import java.security.KeyStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration",
	property = "ddm.data.provider.type=rest", service = DDMDataProvider.class
)
public class DDMRESTDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		try {
			DDMDataProviderInstance ddmDataProviderInstance =
				_getDDMDataProviderInstance(
					ddmDataProviderRequest.getDDMDataProviderId());

			if (ddmDataProviderInstance == null) {
				DDMDataProviderResponse.Builder builder =
					DDMDataProviderResponse.Builder.newBuilder();

				return builder.withStatus(
					DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
				).build();
			}

			DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
				_ddmDataProviderInstanceSettings.getSettings(
					ddmDataProviderInstance, DDMRESTDataProviderSettings.class);

			try {
				return _getData(
					ddmDataProviderRequest, ddmRESTDataProviderSettings);
			}
			catch (JSONWebServiceException jsonWebServiceException) {
				if (_log.isDebugEnabled()) {
					_log.debug(jsonWebServiceException);
				}
				else if (_log.isWarnEnabled()) {
					_log.warn(
						"The data provider was not able to connect to the " +
							"web service. " + jsonWebServiceException);
				}
			}

			return _createDDMDataProviderResponse(
				JsonPath.parse("{}"), ddmDataProviderRequest,
				DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE,
				ddmRESTDataProviderSettings);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new DDMDataProviderException(exception);
		}
	}

	@Override
	public Class<?> getSettings() {
		return _ddmDataProviderSettingsProvider.getSettings();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ddmDataProviderConfiguration = ConfigurableUtil.createConfigurable(
			DDMDataProviderConfiguration.class, properties);
		_portalCache =
			(PortalCache<String, DDMDataProviderResponse>)
				_multiVMPool.getPortalCache(
					DDMRESTDataProvider.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(DDMRESTDataProvider.class.getName());
	}

	private String _buildURL(
		Map<String, String> pathInputParametersMap, String url) {

		for (Map.Entry<String, String> urlInputParameter :
				pathInputParametersMap.entrySet()) {

			url = StringUtil.replaceFirst(
				url, String.format("{%s}", urlInputParameter.getKey()),
				_html.escapeURL(urlInputParameter.getValue()));
		}

		return url;
	}

	private DDMDataProviderResponse _createDDMDataProviderResponse(
		DocumentContext documentContext,
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMDataProviderResponseStatus ddmDataProviderResponseStatus,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		DDMDataProviderOutputParametersSettings[] outputParameters =
			ddmRESTDataProviderSettings.outputParameters();

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		builder.withStatus(ddmDataProviderResponseStatus);

		if (ArrayUtil.isEmpty(outputParameters)) {
			return builder.build();
		}

		for (DDMDataProviderOutputParametersSettings outputParameter :
				outputParameters) {

			String id = outputParameter.outputParameterId();
			String type = outputParameter.outputParameterType();
			String path = outputParameter.outputParameterPath();

			if (Objects.equals(type, "text")) {
				builder = builder.withOutput(
					id,
					documentContext.read(_normalizePath(path), String.class));
			}
			else if (Objects.equals(type, "number")) {
				builder = builder.withOutput(
					id,
					documentContext.read(_normalizePath(path), Number.class));
			}
			else if (Objects.equals(type, "list")) {
				String[] paths = StringUtil.split(path, CharPool.SEMICOLON);

				String normalizedValuePath = _normalizePath(paths[0]);

				List<?> values = documentContext.read(
					normalizedValuePath, List.class);

				if (values == null) {
					continue;
				}

				List<?> keys = values;

				if (paths.length >= 2) {
					keys = documentContext.read(_normalizePath(paths[1]));
				}

				List<KeyValuePair> keyValuePairs = new ArrayList<>();

				for (int i = 0; i < values.size(); i++) {
					keyValuePairs.add(
						new KeyValuePair(
							String.valueOf(keys.get(i)),
							String.valueOf(values.get(i))));
				}

				if (ddmRESTDataProviderSettings.pagination()) {
					String paginationEnd = ddmDataProviderRequest.getParameter(
						"paginationEnd", String.class);

					if (paginationEnd == null) {
						paginationEnd = "10";
					}

					int end = GetterUtil.getInteger(paginationEnd);

					String paginationStart =
						ddmDataProviderRequest.getParameter(
							"paginationStart", String.class);

					if (paginationStart == null) {
						paginationStart = "1";
					}

					int start = GetterUtil.getInteger(paginationStart);

					if (keyValuePairs.size() > (end - start)) {
						builder = builder.withOutput(
							id, ListUtil.subList(keyValuePairs, start, end));
					}
				}
				else {
					builder = builder.withOutput(id, keyValuePairs);
				}
			}
		}

		return builder.build();
	}

	private String _getAbsoluteURL(String query, String url) {
		if (query != null) {
			return StringUtil.replaceLast(
				url, StringPool.QUESTION + query, StringPool.BLANK);
		}

		return url;
	}

	private Map<String, String> _getAllParametersMap(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings,
		Map<String, String> pathInputParametersMap, String query,
		Map<String, Object> requestInputParametersMap) {

		Map<String, String> allParametersMap = new TreeMap<>();

		for (Map.Entry<String, Object> entry :
				requestInputParametersMap.entrySet()) {

			String key = entry.getKey();

			if (pathInputParametersMap.containsKey(key)) {
				continue;
			}

			allParametersMap.put(key, String.valueOf(entry.getValue()));
		}

		if (ddmRESTDataProviderSettings.filterable()) {
			String filterParameterValue = ddmDataProviderRequest.getParameter(
				"filterParameterValue", String.class);

			if (filterParameterValue != null) {
				allParametersMap.put(
					ddmRESTDataProviderSettings.filterParameterName(),
					filterParameterValue);
			}
		}

		if (ddmRESTDataProviderSettings.pagination()) {
			String paginationEnd = ddmDataProviderRequest.getParameter(
				"paginationEnd", String.class);

			if (paginationEnd != null) {
				allParametersMap.put(
					ddmRESTDataProviderSettings.paginationEndParameterName(),
					paginationEnd);
			}

			String paginationStart = ddmDataProviderRequest.getParameter(
				"paginationStart", String.class);

			if (paginationStart != null) {
				allParametersMap.put(
					ddmRESTDataProviderSettings.paginationStartParameterName(),
					paginationStart);
			}
		}

		for (String queryParameter :
				StringUtil.split(query, StringPool.AMPERSAND)) {

			String[] queryParameterPartsMap = StringUtil.split(
				queryParameter, StringPool.EQUAL);

			if (queryParameterPartsMap.length > 1) {
				allParametersMap.put(
					queryParameterPartsMap[0], queryParameterPartsMap[1]);
			}
			else {
				allParametersMap.put(
					queryParameterPartsMap[0], StringPool.BLANK);
			}
		}

		return allParametersMap;
	}

	private DDMDataProviderResponse _getData(
			DDMDataProviderRequest ddmDataProviderRequest,
			DDMRESTDataProviderSettings ddmRESTDataProviderSettings)
		throws Exception {

		Map<String, Object> requestInputParametersMap =
			_getRequestInputParametersMap(
				ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Map<String, String> pathInputParametersMap = _getPathInputParametersMap(
			requestInputParametersMap, ddmRESTDataProviderSettings.url());

		String url = _buildURL(
			pathInputParametersMap, ddmRESTDataProviderSettings.url());

		URI uri = new URI(url);

		Map<String, String> allParametersMap = _getAllParametersMap(
			ddmDataProviderRequest, ddmRESTDataProviderSettings,
			pathInputParametersMap, uri.getQuery(), requestInputParametersMap);

		String absoluteURL = _getAbsoluteURL(uri.getQuery(), url);

		String portalCacheKey = _getPortalCacheKey(
			ddmDataProviderRequest.getDDMDataProviderId(), allParametersMap,
			absoluteURL);

		DDMDataProviderResponse ddmDataProviderResponse = _portalCache.get(
			portalCacheKey);

		if ((ddmDataProviderResponse != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmDataProviderResponse;
		}

		JSONWebServiceClient jsonWebServiceClient =
			_jsonWebServiceClientFactory.getInstance(
				HashMapBuilder.<String, Object>put(
					"hostName", _getHostName(uri.getHost())
				).put(
					"hostPort", _getHostPort(uri.getPort(), uri.getScheme())
				).put(
					"keyStore", _getKeyStore()
				).put(
					"login", ddmRESTDataProviderSettings.username()
				).put(
					"password", ddmRESTDataProviderSettings.password()
				).put(
					"protocol", uri.getScheme()
				).put(
					"trustSelfSignedCertificates",
					_ddmDataProviderConfiguration.trustSelfSignedCertificates()
				).putAll(
					_getProxySettingsMap()
				).build(),
				false);

		String response = null;

		try {
			response = jsonWebServiceClient.doGet(
				absoluteURL, _getParametersArray(allParametersMap));
		}
		finally {
			jsonWebServiceClient.destroy();
		}

		String sanitizedResponse = IOUtils.toString(
			new BOMInputStream(new ByteArrayInputStream(response.getBytes())),
			StandardCharsets.UTF_8);

		ddmDataProviderResponse = _createDDMDataProviderResponse(
			JsonPath.parse(sanitizedResponse), ddmDataProviderRequest,
			DDMDataProviderResponseStatus.OK, ddmRESTDataProviderSettings);

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(portalCacheKey, ddmDataProviderResponse);
		}

		return ddmDataProviderResponse;
	}

	private DDMDataProviderInstance _getDDMDataProviderInstance(
			String ddmDataProviderInstanceId)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			_ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				_ddmDataProviderInstanceService.fetchDataProviderInstance(
					GetterUtil.getLong(ddmDataProviderInstanceId));
		}

		return ddmDataProviderInstance;
	}

	private String _getHostName(String host) {
		if (StringUtil.startsWith(host, "www.")) {
			return host.substring(4);
		}

		return host;
	}

	private int _getHostPort(int port, String scheme) {
		if (port == -1) {
			if (StringUtil.equals(scheme, Http.HTTPS)) {
				return Http.HTTPS_PORT;
			}

			return Http.HTTP_PORT;
		}

		return port;
	}

	private KeyStore _getKeyStore() throws Exception {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

		keyStore.load(null);

		return keyStore;
	}

	private String[] _getParametersArray(Map<String, String> allParametersMap) {
		List<String> parameters = new ArrayList<>();

		for (Map.Entry<String, String> entry : allParametersMap.entrySet()) {
			parameters.add(entry.getKey());
			parameters.add(entry.getValue());
		}

		return ArrayUtil.toStringArray(parameters);
	}

	private Map<String, String> _getPathInputParametersMap(
		Map<String, Object> requestInputParametersMap, String url) {

		Map<String, String> pathInputParametersMap = new HashMap<>();

		Matcher matcher = _pathParameterPattern.matcher(url);

		while (matcher.find()) {
			String pathParameterName = matcher.group(1);

			if (requestInputParametersMap.containsKey(pathParameterName)) {
				pathInputParametersMap.put(
					pathParameterName,
					GetterUtil.getString(
						requestInputParametersMap.get(pathParameterName)));
			}
		}

		return pathInputParametersMap;
	}

	private String _getPortalCacheKey(
		String ddmDataProviderId, Map<String, String> allParametersMap,
		String url) {

		StringBundler sb = new StringBundler((4 * allParametersMap.size()) + 4);

		sb.append(ddmDataProviderId);
		sb.append(StringPool.AT);
		sb.append(url);
		sb.append(StringPool.QUESTION);

		for (Map.Entry<String, String> entry : allParametersMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
			sb.append(StringPool.AMPERSAND);
		}

		if (!allParametersMap.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private Map<String, Object> _getProxySettingsMap() {
		Map<String, Object> proxySettingsMap = new HashMap<>();

		try {
			String proxyHost = SystemProperties.get("http.proxyHost");
			String proxyPort = SystemProperties.get("http.proxyPort");

			if (Validator.isNotNull(proxyHost) &&
				Validator.isNotNull(proxyPort)) {

				proxySettingsMap.put("proxyHostName", proxyHost);
				proxySettingsMap.put(
					"proxyHostPort", GetterUtil.getInteger(proxyPort));
			}
		}
		catch (Exception exception) {
			proxySettingsMap.clear();

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get proxy settings from system properties",
					exception);
			}
		}

		return proxySettingsMap;
	}

	private Map<String, Object> _getRequestInputParametersMap(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, Object> requestInputParametersMap = new HashMap<>();

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		for (DDMDataProviderInputParametersSettings
				ddmDataProviderInputParametersSettings :
					ddmRESTDataProviderSettings.inputParameters()) {

			String inputParameterName =
				ddmDataProviderInputParametersSettings.inputParameterName();

			Object value = parameters.get(inputParameterName);

			if (value != null) {
				requestInputParametersMap.put(inputParameterName, value);
			}
		}

		return requestInputParametersMap;
	}

	private String _normalizePath(String path) {
		if (StringUtil.startsWith(path, StringPool.DOLLAR) ||
			StringUtil.startsWith(path, StringPool.PERIOD) ||
			StringUtil.startsWith(path, StringPool.STAR)) {

			return path;
		}

		return StringPool.PERIOD.concat(path);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMRESTDataProvider.class);

	private static final Pattern _pathParameterPattern = Pattern.compile(
		"\\{(.+?)\\}");

	private volatile DDMDataProviderConfiguration _ddmDataProviderConfiguration;

	@Reference
	private DDMDataProviderInstanceService _ddmDataProviderInstanceService;

	@Reference
	private DDMDataProviderInstanceSettings _ddmDataProviderInstanceSettings;

	@Reference(target = "(ddm.data.provider.type=rest)")
	private DDMDataProviderSettingsProvider _ddmDataProviderSettingsProvider;

	@Reference
	private Html _html;

	@Reference
	private JSONWebServiceClientFactory _jsonWebServiceClientFactory;

	@Reference
	private MultiVMPool _multiVMPool;

	private volatile PortalCache<String, DDMDataProviderResponse> _portalCache;

}