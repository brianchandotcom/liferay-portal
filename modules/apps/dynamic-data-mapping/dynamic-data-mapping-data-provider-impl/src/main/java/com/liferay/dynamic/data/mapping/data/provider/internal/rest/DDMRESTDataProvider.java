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
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderContext;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseOutput;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.ConnectException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	public List<KeyValuePair> getData(
			DDMDataProviderContext ddmDataProviderContext)
		throws DDMDataProviderException {

		try {
			DDMDataProviderRequest ddmDataProviderRequest =
				createDDMDataProviderRequest(ddmDataProviderContext);

			DDMDataProviderResponse ddmDataProviderResponse = doGetData(
				ddmDataProviderRequest);

			DDMDataProviderResponseOutput ddmDataProviderResponseOutput =
				ddmDataProviderResponse.get("Default-Output");

			return ddmDataProviderResponseOutput.getValue(List.class);
		}
		catch (Exception e) {
			throw new DDMDataProviderException(e);
		}
	}

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

				return DDMDataProviderResponse.error(
					DDMDataProviderResponse.Status.SERVICE_UNAVAILABLE);
			}

			throw new DDMDataProviderException(he);
		}
		catch (Exception e) {
			throw new DDMDataProviderException(e);
		}
	}

	@Override
	public Class<?> getSettings() {
		return DDMRESTDataProviderSettings.class;
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

	protected DDMDataProviderRequest createDDMDataProviderRequest(
		DDMDataProviderContext ddmDataProviderContext) {

		DDMDataProviderRequest ddmDataProviderRequest =
			new DDMDataProviderRequest(null, null);

		ddmDataProviderRequest.setDDMDataProviderContext(
			ddmDataProviderContext);

		// Backwards compatibility

		ddmDataProviderRequest.queryString(
			ddmDataProviderContext.getParameters());

		return ddmDataProviderRequest;
	}

	protected DDMDataProviderResponse createDDMDataProviderResponse(
		DocumentContext documentContext,
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		DDMDataProviderOutputParametersSettings[] outputParameterSettingsArray =
			ddmRESTDataProviderSettings.outputParameters();

		if ((outputParameterSettingsArray == null) ||
			(outputParameterSettingsArray.length == 0)) {

			return DDMDataProviderResponse.of();
		}

		List<DDMDataProviderResponseOutput> ddmDataProviderResponseOutputs =
			new ArrayList<>();

		for (DDMDataProviderOutputParametersSettings outputParameterSettings :
				outputParameterSettingsArray) {

			String id = outputParameterSettings.outputParameterId();
			String type = outputParameterSettings.outputParameterType();
			String path = outputParameterSettings.outputParameterPath();

			if (Objects.equals(type, "text")) {
				String value = documentContext.read(
					normalizePath(path), String.class);

				if (value != null) {
					ddmDataProviderResponseOutputs.add(
						DDMDataProviderResponseOutput.of(id, "text", value));
				}
			}
			else if (Objects.equals(type, "number")) {
				Number value = documentContext.read(
					normalizePath(path), Number.class);

				if (value != null) {
					ddmDataProviderResponseOutputs.add(
						DDMDataProviderResponseOutput.of(id, "number", value));
				}
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
					int start = Integer.valueOf(
						ddmDataProviderRequest.getParameter("paginationStart"));

					int end = Integer.valueOf(
						ddmDataProviderRequest.getParameter("paginationEnd"));

					if (keyValuePairs.size() > (end - start)) {
						keyValuePairs = ListUtil.subList(
							keyValuePairs, start, end);
					}
				}

				ddmDataProviderResponseOutputs.add(
					DDMDataProviderResponseOutput.of(
						id, "list", keyValuePairs));
			}
		}

		int size = ddmDataProviderResponseOutputs.size();

		return DDMDataProviderResponse.of(
			ddmDataProviderResponseOutputs.toArray(
				new DDMDataProviderResponseOutput[size]));
	}

	protected DDMDataProviderResponse doGetData(
		DDMDataProviderRequest ddmDataProviderRequest) {

		DDMDataProviderContext ddmDataProviderContext =
			ddmDataProviderRequest.getDDMDataProviderContext();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			ddmDataProviderContext.getSettingsInstance(
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

		DDMRESTDataProviderResult ddmRESTDataProviderResult = _portalCache.get(
			cacheKey);

		if ((ddmRESTDataProviderResult != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmRESTDataProviderResult.getDDMDataProviderResponse();
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

		String responseBodyText = _removeUTFBOM(httpResponse.bodyText());

		DocumentContext documentContext = JsonPath.parse(responseBodyText);

		DDMDataProviderResponse ddmDataProviderResponse =
			createDDMDataProviderResponse(
				documentContext, ddmDataProviderRequest,
				ddmRESTDataProviderSettings);

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(
				cacheKey,
				new DDMRESTDataProviderResult(ddmDataProviderResponse));
		}

		return ddmDataProviderResponse;
	}

	protected String getCacheKey(HttpRequest httpRequest) {
		return httpRequest.url();
	}

	protected Map<String, String> getParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, String> parameters = ddmDataProviderRequest.getParameters();

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

		Map<String, String> parameters = getParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Map<String, String> pathParameters = new HashMap<>();

		Matcher matcher = _pathParameterPattern.matcher(
			ddmRESTDataProviderSettings.url());

		while (matcher.find()) {
			String parameterName = matcher.group(1);

			if (parameters.containsKey(parameterName)) {
				pathParameters.put(
					parameterName, parameters.get(parameterName));
			}
		}

		return pathParameters;
	}

	protected Map<String, Object> getProxySettings() {
		Map<String, Object> proxySettings = new HashMap<>(2);

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

		return MapUtil.filter(
			getParameters(ddmDataProviderRequest, ddmRESTDataProviderSettings),
			new PredicateFilter<Map.Entry<String, String>>() {

				@Override
				public boolean filter(Map.Entry<String, String> parameter) {
					return !pathParameters.containsKey(parameter.getKey());
				}

			});
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
		if (StringUtil.startsWith(path, StringPool.PERIOD) ||
			StringUtil.startsWith(path, StringPool.DOLLAR)) {

			return path;
		}

		return StringPool.PERIOD.concat(path);
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMRESTDataProviderResult>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	protected void setRequestParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings,
		HttpRequest httpRequest) {

		if (ddmRESTDataProviderSettings.filterable()) {
			httpRequest.query(
				ddmRESTDataProviderSettings.filterParameterName(),
				ddmDataProviderRequest.getParameter("filterParameterValue"));
		}

		if (ddmRESTDataProviderSettings.pagination()) {
			httpRequest.query(
				ddmRESTDataProviderSettings.paginationEndParameterName(),
				ddmDataProviderRequest.getParameter("paginationStart"));
			httpRequest.query(
				ddmRESTDataProviderSettings.paginationEndParameterName(),
				ddmDataProviderRequest.getParameter("paginationEnd"));
		}

		httpRequest.query(
			getQueryParameters(
				ddmDataProviderRequest, ddmRESTDataProviderSettings));
	}

	@Reference
	protected Http http;

	private String _removeUTFBOM(String bodyText) {
		for (int i = 0; i < bodyText.length(); i++) {
			if ((bodyText.charAt(i) == '[') || (bodyText.charAt(i) == '{')) {
				return bodyText.substring(i);
			}
		}

		return "";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMRESTDataProvider.class);

	private static final Pattern _pathParameterPattern = Pattern.compile(
		"\\{(.*)\\}");

	private PortalCache<String, DDMRESTDataProviderResult> _portalCache;

	private static class DDMRESTDataProviderResult implements Serializable {

		public DDMRESTDataProviderResult(
			DDMDataProviderResponse ddmDataProviderResponse) {

			_ddmDataProviderResponse = ddmDataProviderResponse;
		}

		public DDMDataProviderResponse getDDMDataProviderResponse() {
			return _ddmDataProviderResponse;
		}

		private final DDMDataProviderResponse _ddmDataProviderResponse;

	}

}