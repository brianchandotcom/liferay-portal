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

package com.liferay.portal.search.solr8.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.http.HttpClientFactory;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.solr.client.solrj.SolrClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrConfiguration",
	service = SolrClientManager.class
)
public class SolrClientManager {

	public SolrClient getSolrClient() {
		return _solrClient;
	}

	@Activate
	@Modified
	protected synchronized void activate(Map<String, Object> properties)
		throws Exception {

		_close();

		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		String clientType = _solrConfiguration.clientType();

		SolrClientFactory solrClientFactory = _solrClientFactories.get(
			clientType);

		if (solrClientFactory == null) {
			if (clientType.equals("CLOUD")) {
				solrClientFactory = _cloudSolrClientFactory;
			}

			if (clientType.equals("REPLICATED")) {
				solrClientFactory = _replicatedSolrClientFactory;
			}
		}

		if (solrClientFactory == null) {
			throw new IllegalStateException(
				"Solr client factory not initialized: " + clientType);
		}

		String authMode = _solrConfiguration.authenticationMode();

		HttpClientFactory httpClientFactory = _httpClientFactories.get(
			authMode);

		if (httpClientFactory == null) {
			if (authMode.equals("BASIC")) {
				httpClientFactory = _basicAuthPoolingHttpClientFactory;
			}

			if (authMode.equals("CERT")) {
				httpClientFactory = _certAuthPoolingHttpClientFactory;
			}
		}

		if (httpClientFactory == null) {
			throw new IllegalStateException(
				"No HTTP client factory for " + authMode);
		}

		_solrClient = solrClientFactory.getSolrClient(
			_solrConfiguration, httpClientFactory);
	}

	@Deactivate
	protected synchronized void deactivate(Map<String, Object> properties) {
		_close();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(!(type=BASIC))(!(type=CERT)))"
	)
	protected void setHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		if (Validator.isNull(type)) {
			throw new IllegalArgumentException(
				"Invalid authentication type " + type);
		}

		_httpClientFactories.put(type, httpClientFactory);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(!(type=CLOUD))(!(type=REPLICATED)))"
	)
	protected void setSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		_solrClientFactories.put(type, solrClientFactory);
	}

	protected void unsetHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		if (Validator.isNull(type)) {
			return;
		}

		_httpClientFactories.remove(type);
	}

	protected void unsetSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		_solrClientFactories.remove(type);
	}

	private void _close() {
		if (_solrClient != null) {
			try {
				_solrClient.close();
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrClientManager.class);

	@Reference(target = "(type=BASIC)")
	private HttpClientFactory _basicAuthPoolingHttpClientFactory;

	@Reference(target = "(type=CERT)")
	private HttpClientFactory _certAuthPoolingHttpClientFactory;

	@Reference(target = "(type=CLOUD)")
	private SolrClientFactory _cloudSolrClientFactory;

	private final Map<String, HttpClientFactory> _httpClientFactories =
		new HashMap<>();

	@Reference(target = "(type=REPLICATED)")
	private SolrClientFactory _replicatedSolrClientFactory;

	private volatile SolrClient _solrClient;
	private final Map<String, SolrClientFactory> _solrClientFactories =
		new ConcurrentHashMap<>();
	private volatile SolrConfiguration _solrConfiguration;

}