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
import com.liferay.portal.search.solr8.configuration.SolrConfiguration;
import com.liferay.portal.search.solr8.internal.http.HttpClientFactory;

import java.io.IOException;

import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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
	protected void activate(Map<String, Object> properties) throws Exception {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		String clientType = _solrConfiguration.clientType();

		SolrClientFactory solrClientFactory = null;

		if (clientType.equals("CLOUD")) {
			solrClientFactory = _cloudSolrClientFactory;
		}

		if (clientType.equals("REPLICATED")) {
			solrClientFactory = _replicatedSolrClientFactory;
		}

		if (solrClientFactory == null) {
			throw new IllegalStateException(
				"Solr client factory not initialized: " + clientType);
		}

		String authMode = _solrConfiguration.authenticationMode();

		HttpClientFactory httpClientFactory = null;

		if (authMode.equals("BASIC")) {
			httpClientFactory = _basicAuthPoolingHttpClientFactory;
		}

		if (authMode.equals("CERT")) {
			httpClientFactory = _certAuthPoolingHttpClientFactory;
		}

		if (httpClientFactory == null) {
			throw new IllegalStateException(
				"No HTTP client factory for " + authMode);
		}

		_solrClient = solrClientFactory.getSolrClient(
			_solrConfiguration, httpClientFactory);
	}

	@Deactivate
	protected void deactivate() {
		try {
			_solrClient.close();
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException);
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

	@Reference(target = "(type=REPLICATED)")
	private SolrClientFactory _replicatedSolrClientFactory;

	private SolrClient _solrClient;
	private SolrConfiguration _solrConfiguration;

}