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
import com.liferay.dynamic.data.mapping.data.provider.settings.DDMDataProviderSettingsProvider;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.net.ConnectException;

import java.util.ArrayList;
import java.util.Arrays;
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

import jodd.http.HttpException;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.http.net.SocketHttpConnectionProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.data.provider.type=rest",
	service = DDMDataProvider.class
)
public class DDMRESTDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		try {
			return doGetData(ddmDataProviderRequest);
		}
		catch (HttpException he) {
			Throwable cause = he.getCause();

			if (cause instanceof ConnectException) {
				if (_log.isWarnEnabled()) {
					_log.warn(cause, cause);
				}

				DDMDataProviderResponse.Builder builder =
					DDMDataProviderResponse.Builder.newBuilder();

				return builder.withStatus(
					DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
				).build();
			}

			throw new DDMDataProviderException(he);
		}
		catch (Exception e) {
			throw new DDMDataProviderException(e);
		}
	}

	@Override
	public Class<?> getSettings() {
		return ddmDataProviderSettingsProvider.getSettings();
	}

	protected String buildURL(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, String> pathParameters = getPathParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		String url = ddmRESTDataProviderSettings.url();

		for (Map.Entry<String, String> pathParameter :
				pathParameters.entrySet()) {

			url = StringUtil.replaceFirst(
				url, String.format("{%s}", pathParameter.getKey()),
				HtmlUtil.escapeURL(pathParameter.getValue()));
		}

		return url;
	}

	protected DDMDataProviderResponse createDDMDataProviderResponse(
		DocumentContext documentContext,
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		DDMDataProviderOutputParametersSettings[] outputParameterSettingsArray =
			ddmRESTDataProviderSettings.outputParameters();

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		if ((outputParameterSettingsArray == null) ||
			(outputParameterSettingsArray.length == 0)) {

			return builder.build();
		}

		for (DDMDataProviderOutputParametersSettings outputParameterSettings :
				outputParameterSettingsArray) {

			String id = outputParameterSettings.outputParameterId();
			String type = outputParameterSettings.outputParameterType();
			String path = outputParameterSettings.outputParameterPath();

			if (Objects.equals(type, "text")) {
				String value = documentContext.read(
					normalizePath(path), String.class);

				builder = builder.withOutput(id, value);
			}
			else if (Objects.equals(type, "number")) {
				Number value = documentContext.read(
					normalizePath(path), Number.class);

				builder = builder.withOutput(id, value);
			}
			else if (Objects.equals(type, "list")) {
				String[] paths = StringUtil.split(path, CharPool.SEMICOLON);

				String normalizedValuePath = normalizePath(paths[0]);

				String normalizedKeyPath = normalizedValuePath;

				List<String> values = documentContext.read(
					normalizedValuePath, List.class);

				if (values == null) {
					continue;
				}

				List<String> keys = new ArrayList<>(values);

				if (paths.length >= 2) {
					normalizedKeyPath = normalizePath(paths[1]);

					keys = documentContext.read(normalizedKeyPath);
				}

				List<KeyValuePair> keyValuePairs = new ArrayList<>();

				for (int i = 0; i < values.size(); i++) {
					keyValuePairs.add(
						new KeyValuePair(keys.get(i), values.get(i)));
				}

				if (ddmRESTDataProviderSettings.pagination()) {
					Optional<String> paginationStartOptional =
						ddmDataProviderRequest.getParameterOptional(
							"paginationStart", String.class);

					int start = Integer.valueOf(
						paginationStartOptional.orElse("1"));

					Optional<String> paginationEndOptional =
						ddmDataProviderRequest.getParameterOptional(
							"paginationEnd", String.class);

					int end = Integer.valueOf(
						paginationEndOptional.orElse("10"));

					if (keyValuePairs.size() > (end - start)) {
						List<KeyValuePair> sublist = ListUtil.subList(
							keyValuePairs, start, end);

						builder = builder.withOutput(id, sublist);
					}
				}
				else {
					builder = builder.withOutput(id, keyValuePairs);
				}
			}
		}

		return builder.build();
	}

	protected DDMDataProviderResponse doGetData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws Exception {

		Optional<DDMDataProviderInstance> ddmDataProviderInstance =
			fetchDDMDataProviderInstance(
				ddmDataProviderRequest.getDDMDataProviderId());

		if (!ddmDataProviderInstance.isPresent()) {
			DDMDataProviderResponse.Builder builder =
				DDMDataProviderResponse.Builder.newBuilder();

			return builder.withStatus(
				DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
			).build();
		}

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			ddmDataProviderInstanceSettings.getSettings(
				ddmDataProviderInstance.get(),
				DDMRESTDataProviderSettings.class);

		HttpRequest httpRequest = HttpRequest.get(
			buildURL(ddmDataProviderRequest, ddmRESTDataProviderSettings));

		if (StringUtil.startsWith(
				ddmRESTDataProviderSettings.url(), Http.HTTPS)) {

			httpRequest.trustAllCerts(true);
		}

		if (Validator.isNotNull(ddmRESTDataProviderSettings.username())) {
			httpRequest.basicAuthentication(
				ddmRESTDataProviderSettings.username(),
				ddmRESTDataProviderSettings.password());
		}

		setRequestParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings, httpRequest);

		String cacheKey = getCacheKey(httpRequest);

		DDMDataProviderResponse ddmDataProviderResponse = _portalCache.get(
			cacheKey);

		if ((ddmDataProviderResponse != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmDataProviderResponse;
		}

		HttpResponse httpResponse = null;

		Map<String, Object> proxySettings = getProxySettings();

		if (isNonproxiedHttpRequest(httpRequest, proxySettings)) {
			httpResponse = httpRequest.send();
		}
		else {
			SocketHttpConnectionProvider socketHttpConnectionProvider =
				new SocketHttpConnectionProvider();

			String proxyAddress = GetterUtil.getString(
				proxySettings.get("proxyAddress"));
			int proxyPort = GetterUtil.getInteger(
				proxySettings.get("proxyPort"));

			socketHttpConnectionProvider.useProxy(
				ProxyInfo.httpProxy(proxyAddress, proxyPort, null, null));

			HttpRequest proxiedHttpRequest = httpRequest.withConnectionProvider(
				socketHttpConnectionProvider);

			httpResponse = proxiedHttpRequest.send();
		}

		DocumentContext documentContext = JsonPath.parse(
			httpResponse.bodyText());

		ddmDataProviderResponse = createDDMDataProviderResponse(
			documentContext, ddmDataProviderRequest,
			ddmRESTDataProviderSettings);

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(cacheKey, ddmDataProviderResponse);
		}

		return ddmDataProviderResponse;
	}

	protected Optional<DDMDataProviderInstance> fetchDDMDataProviderInstance(
			String ddmDataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				ddmDataProviderInstanceService.fetchDataProviderInstance(
					Long.valueOf(ddmDataProviderInstanceId));
		}

		return Optional.ofNullable(ddmDataProviderInstance);
	}

	protected String getCacheKey(HttpRequest httpRequest) {
		return httpRequest.url();
	}

	protected Map<String, Object> getParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		Stream<DDMDataProviderInputParametersSettings> stream = Arrays.stream(
			ddmRESTDataProviderSettings.inputParameters());

		return stream.filter(
			inputParameter -> parameters.containsKey(
				inputParameter.inputParameterName())
		).collect(
			Collectors.toMap(
				DDMDataProviderInputParametersSettings::inputParameterName,
				value -> parameters.get(value.inputParameterName()))
		);
	}

	protected Map<String, String> getPathParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, Object> parameters = getParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Map<String, String> pathParameters = new HashMap<>();

		Matcher matcher = _pathParameterPattern.matcher(
			ddmRESTDataProviderSettings.url());

		while (matcher.find()) {
			String parameterName = matcher.group(1);

			if (parameters.containsKey(parameterName)) {
				pathParameters.put(
					parameterName,
					GetterUtil.getString(parameters.get(parameterName)));
			}
		}

		return pathParameters;
	}

	protected Map<String, Object> getProxySettings() {
		Map<String, Object> proxySettings = new HashMap<>();

		try {
			String proxyAddress = SystemProperties.get("http.proxyHost");
			String proxyPort = SystemProperties.get("http.proxyPort");

			if (Validator.isNotNull(proxyAddress) &&
				Validator.isNotNull(proxyPort)) {

				proxySettings.put("proxyAddress", proxyAddress);
				proxySettings.put("proxyPort", Integer.valueOf(proxyPort));
			}
		}
		catch (Exception e) {
			proxySettings.clear();

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get proxy settings from system properties", e);
			}
		}

		return proxySettings;
	}

	protected Map<String, String> getQueryParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, String> pathParameters = getPathParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Map<String, Object> parametersMap = getParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Set<Map.Entry<String, Object>> entrySet = parametersMap.entrySet();

		Map<String, String> parameters = new HashMap<>();

		entrySet.forEach(
			entry -> {
				String key = entry.getKey();

				if (!pathParameters.containsKey(key)) {
					parameters.put(key, String.valueOf(entry.getValue()));
				}
			});

		return parameters;
	}

	protected boolean isNonproxiedHttpRequest(
		HttpRequest httpRequest, Map<String, Object> proxySettings) {

		if (proxySettings.isEmpty() ||
			(proxySettings.get("proxyAddress") == null) ||
			http.isNonProxyHost(httpRequest.host())) {

			return true;
		}

		return false;
	}

	protected String normalizePath(String path) {
		if (StringUtil.startsWith(path, StringPool.DOLLAR) ||
			StringUtil.startsWith(path, StringPool.PERIOD) ||
			StringUtil.startsWith(path, StringPool.STAR)) {

			return path;
		}

		return StringPool.PERIOD.concat(path);
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMDataProviderResponse>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	protected void setRequestParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings,
		HttpRequest httpRequest) {

		if (ddmRESTDataProviderSettings.filterable()) {
			Optional<String> filterParameterValue =
				ddmDataProviderRequest.getParameterOptional(
					"filterParameterValue", String.class);

			if (filterParameterValue.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.filterParameterName(),
					filterParameterValue.get());
			}
		}

		if (ddmRESTDataProviderSettings.pagination()) {
			Optional<String> paginationStart =
				ddmDataProviderRequest.getParameterOptional(
					"paginationStart", String.class);

			if (paginationStart.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.paginationStartParameterName(),
					paginationStart.get());
			}

			Optional<String> paginationEnd =
				ddmDataProviderRequest.getParameterOptional(
					"paginationEnd", String.class);

			if (paginationEnd.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.paginationEndParameterName(),
					paginationEnd.get());
			}
		}

		httpRequest.query(
			getQueryParameters(
				ddmDataProviderRequest, ddmRESTDataProviderSettings));
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings;

	@Reference(target = "(ddm.data.provider.type=rest)")
	protected DDMDataProviderSettingsProvider ddmDataProviderSettingsProvider;

	@Reference
	protected Http http;

	@Reference
	protected Portal portal;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMRESTDataProvider.class);

	private static final Pattern _pathParameterPattern = Pattern.compile(
		"\\{(.+?)\\}");

	private PortalCache<String, DDMDataProviderResponse> _portalCache;

}