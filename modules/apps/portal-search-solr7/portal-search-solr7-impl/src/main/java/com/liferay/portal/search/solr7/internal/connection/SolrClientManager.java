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

package com.liferay.portal.search.solr7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.http.HttpClientFactory;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.solr.client.solrj.SolrClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, service = SolrClientManager.class
)
public class SolrClientManager {

	public SolrClient getSolrClient() {
		return _solrClient;
	}

	@Activate
	@Modified
	protected synchronized void activate(Map<String, Object> properties)
		throws Exception {

		if (_solrClient != null) {
			try {
				_solrClient.close();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn("");
				}
			}
		}

		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		String clientType = _solrConfiguration.clientType();

		SolrClientFactory solrClientFactory = _solrClientFactories.get(
			clientType);

		if (solrClientFactory == null) {
			throw new IllegalStateException(
				"Solr client factory not initialized: " + clientType);
		}

		String authMode = _solrConfiguration.authenticationMode();

		HttpClientFactory httpClientFactory = _httpClientFactories.get(
			authMode);

		if (httpClientFactory == null) {
			throw new IllegalStateException(
				"No HTTP client factory for " + authMode);
		}

		_solrClient = solrClientFactory.getSolrClient(
			_solrConfiguration, httpClientFactory);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(type=BASIC)"
	)
	protected void setBasicHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		setHttpClientFactory(httpClientFactory, properties);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(type=CERT)"
	)
	protected void setCertHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		setHttpClientFactory(httpClientFactory, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(type=CLOUD)"
	)
	protected void setCloudSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		setSolrClientFactory(solrClientFactory, properties);
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
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=REPLICATED)"
	)
	protected void setReplicatedSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		setSolrClientFactory(solrClientFactory, properties);
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

	protected void unsetBasicHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		unsetHttpClientFactory(httpClientFactory, properties);
	}

	protected void unsetCertHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		unsetHttpClientFactory(httpClientFactory, properties);
	}

	protected void unsetCloudSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		unsetSolrClientFactory(solrClientFactory, properties);
	}

	protected void unsetHttpClientFactory(
		HttpClientFactory httpClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		if (Validator.isNull(type)) {
			return;
		}

		_httpClientFactories.remove(type);
	}

	protected void unsetReplicatedSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		unsetSolrClientFactory(solrClientFactory, properties);
	}

	protected void unsetSolrClientFactory(
		SolrClientFactory solrClientFactory, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		_solrClientFactories.remove(type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrClientManager.class);

	private final Map<String, HttpClientFactory> _httpClientFactories =
		new HashMap<>();
	private volatile SolrClient _solrClient;
	private final Map<String, SolrClientFactory> _solrClientFactories =
		new ConcurrentHashMap<>();
	private volatile SolrConfiguration _solrConfiguration;

}