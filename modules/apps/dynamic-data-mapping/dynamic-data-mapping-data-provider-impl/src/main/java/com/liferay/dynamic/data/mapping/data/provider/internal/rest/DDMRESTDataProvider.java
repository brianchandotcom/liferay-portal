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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration;
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClientFactory;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.data.provider.configuration.DDMDataProviderConfiguration",
	immediate = true, property = "ddm.data.provider.type=rest",
	service = DDMDataProvider.class
)
public class DDMRESTDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		try {
			Optional<DDMDataProviderInstance> ddmDataProviderInstanceOptional =
				_getDDMDataProviderInstanceOptional(
					ddmDataProviderRequest.getDDMDataProviderId());

			if (!ddmDataProviderInstanceOptional.isPresent()) {
				DDMDataProviderResponse.Builder builder =
					DDMDataProviderResponse.Builder.newBuilder();

				return builder.withStatus(
					DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
				).build();
			}

			DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
				_ddmDataProviderInstanceSettings.getSettings(
					ddmDataProviderInstanceOptional.get(),
					DDMRESTDataProviderSettings.class);

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
	}

	private String _buildURL(
		Map<String, String> pathInputParametersMap, String url) {

		for (Map.Entry<String, String> urlInputParameter :
				pathInputParametersMap.entrySet()) {

			url = StringUtil.replaceFirst(
				url, String.format("{%s}", urlInputParameter.getKey()),
				HtmlUtil.escapeURL(urlInputParameter.getValue()));
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
					Optional<String> paginationEndOptional =
						ddmDataProviderRequest.getParameterOptional(
							"paginationEnd", String.class);

					int end = GetterUtil.getInteger(
						paginationEndOptional.orElse("10"));

					Optional<String> paginationStartOptional =
						ddmDataProviderRequest.getParameterOptional(
							"paginationStart", String.class);

					int start = GetterUtil.getInteger(
						paginationStartOptional.orElse("1"));

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

	private List<KeyValuePair> _getAdditionalParameters(
		Map<String, String> pathInputParametersMap,
		Map<String, Object> requestInputParametersMap) {

		Set<Map.Entry<String, Object>> set =
			requestInputParametersMap.entrySet();

		Stream<Map.Entry<String, Object>> stream = set.stream();

		return stream.collect(
			ArrayList::new,
			(keyValuePairs, entry) -> {
				String key = entry.getKey();

				if (!pathInputParametersMap.containsKey(key)) {
					keyValuePairs.add(
						new KeyValuePair(
							key, String.valueOf(entry.getValue())));
				}
			},
			ArrayList::addAll);
	}

	private String _getCacheKey(
		String ddmDataProviderId, List<KeyValuePair> keyValuePairs,
		String url) {

		Stream<KeyValuePair> stream = keyValuePairs.stream();

		return StringBundler.concat(
			ddmDataProviderId, StringPool.AT, url, StringPool.QUESTION,
			stream.sorted(
			).map(
				keyValuePair -> StringBundler.concat(
					keyValuePair.getKey(), StringPool.EQUAL,
					keyValuePair.getValue())
			).collect(
				Collectors.joining(StringPool.AMPERSAND)
			));
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

		List<KeyValuePair> allParameters = ListUtil.toList(
			_getAdditionalParameters(
				pathInputParametersMap, requestInputParametersMap));

		allParameters.addAll(
			_getFilterAndPaginationParameters(
				ddmDataProviderRequest, ddmRESTDataProviderSettings));

		String url = _buildURL(
			pathInputParametersMap, ddmRESTDataProviderSettings.url());

		URI uri = new URI(url);

		allParameters.addAll(_getQueryParameters(uri.getQuery()));

		String absoluteURL = _getAbsoluteURL(uri.getQuery(), url);

		String cacheKey = _getCacheKey(
			ddmDataProviderRequest.getDDMDataProviderId(), allParameters,
			absoluteURL);

		DDMDataProviderResponse ddmDataProviderResponse = _portalCache.get(
			cacheKey);

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

		String response = jsonWebServiceClient.doGet(
			absoluteURL, _getParametersArray(allParameters));

		jsonWebServiceClient.destroy();

		String sanitizedResponse = IOUtils.toString(
			new BOMInputStream(new ByteArrayInputStream(response.getBytes())),
			StandardCharsets.UTF_8);

		ddmDataProviderResponse = _createDDMDataProviderResponse(
			JsonPath.parse(sanitizedResponse), ddmDataProviderRequest,
			DDMDataProviderResponseStatus.OK, ddmRESTDataProviderSettings);

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(cacheKey, ddmDataProviderResponse);
		}

		return ddmDataProviderResponse;
	}

	private Optional<DDMDataProviderInstance>
			_getDDMDataProviderInstanceOptional(
				String ddmDataProviderInstanceId)
		throws Exception {

		DDMDataProviderInstance ddmDataProviderInstance =
			_ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				_ddmDataProviderInstanceService.fetchDataProviderInstance(
					Long.valueOf(ddmDataProviderInstanceId));
		}

		return Optional.ofNullable(ddmDataProviderInstance);
	}

	private List<KeyValuePair> _getFilterAndPaginationParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		if (ddmRESTDataProviderSettings.filterable()) {
			Optional<String> filterParameterValueOptional =
				ddmDataProviderRequest.getParameterOptional(
					"filterParameterValue", String.class);

			filterParameterValueOptional.ifPresent(
				filterParameterValueString -> keyValuePairs.add(
					new KeyValuePair(
						ddmRESTDataProviderSettings.filterParameterName(),
						filterParameterValueString)));
		}

		if (ddmRESTDataProviderSettings.pagination()) {
			Optional<String> paginationEndOptional =
				ddmDataProviderRequest.getParameterOptional(
					"paginationEnd", String.class);

			paginationEndOptional.ifPresent(
				paginationEndString -> keyValuePairs.add(
					new KeyValuePair(
						ddmRESTDataProviderSettings.
							paginationEndParameterName(),
						paginationEndString)));

			Optional<String> paginationStartOptional =
				ddmDataProviderRequest.getParameterOptional(
					"paginationStart", String.class);

			paginationStartOptional.ifPresent(
				paginationStartString -> keyValuePairs.add(
					new KeyValuePair(
						ddmRESTDataProviderSettings.
							paginationStartParameterName(),
						paginationStartString)));
		}

		return keyValuePairs;
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

	private String[] _getParametersArray(List<KeyValuePair> keyValuePairs) {
		Stream<KeyValuePair> stream = keyValuePairs.stream();

		return stream.collect(
			ArrayList<String>::new,
			(parameters, keyValuePair) -> {
				parameters.add(keyValuePair.getKey());
				parameters.add(keyValuePair.getValue());
			},
			ArrayList::addAll
		).toArray(
			new String[0]
		);
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

	private List<KeyValuePair> _getQueryParameters(String query) {
		return Stream.of(
			StringUtil.split(query, StringPool.AMPERSAND)
		).collect(
			ArrayList::new,
			(keyValuePairs, queryParameter) -> {
				String[] queryParameterPartsMap = StringUtil.split(
					queryParameter, StringPool.EQUAL);

				if (queryParameterPartsMap.length > 1) {
					keyValuePairs.add(
						new KeyValuePair(
							queryParameterPartsMap[0],
							queryParameterPartsMap[1]));
				}
				else {
					keyValuePairs.add(
						new KeyValuePair(
							queryParameterPartsMap[0], StringPool.BLANK));
				}
			},
			ArrayList::addAll
		);
	}

	private Map<String, Object> _getRequestInputParametersMap(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		return Stream.of(
			ddmRESTDataProviderSettings.inputParameters()
		).filter(
			inputParameter -> parameters.containsKey(
				inputParameter.inputParameterName())
		).collect(
			HashMap::new,
			(parametersMap, inputParameter) -> parametersMap.put(
				inputParameter.inputParameterName(),
				parameters.get(inputParameter.inputParameterName())),
			HashMap::putAll
		);
	}

	private String _normalizePath(String path) {
		if (StringUtil.startsWith(path, StringPool.DOLLAR) ||
			StringUtil.startsWith(path, StringPool.PERIOD) ||
			StringUtil.startsWith(path, StringPool.STAR)) {

			return path;
		}

		return StringPool.PERIOD.concat(path);
	}

	@Reference(unbind = "-")
	private void _setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMDataProviderResponse>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
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
	private JSONFactory _jsonFactory;

	@Reference
	private JSONWebServiceClientFactory _jsonWebServiceClientFactory;

	private PortalCache<String, DDMDataProviderResponse> _portalCache;

}