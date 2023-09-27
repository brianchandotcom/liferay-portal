/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration",
	service = {}
)
public class ElasticsearchConnectionConfigurationActivationHandler {

	@Activate
	protected void activate(Map<String, Object> properties) {
		ElasticsearchConnectionConfiguration
			elasticsearchConnectionConfiguration =
				ConfigurableUtil.createConfigurable(
					ElasticsearchConnectionConfiguration.class, properties);

		ElasticsearchConnectionBuilder elasticsearchConnectionBuilder =
			new ElasticsearchConnectionBuilder();

		elasticsearchConnectionBuilder.active(
			elasticsearchConnectionConfiguration.active()
		).authenticationEnabled(
			elasticsearchConnectionConfiguration.authenticationEnabled()
		).connectionId(
			elasticsearchConnectionConfiguration.connectionId()
		).httpSSLEnabled(
			elasticsearchConnectionConfiguration.httpSSLEnabled()
		).maxConnections(
			elasticsearchConnectionConfiguration.maxConnections()
		).maxConnectionsPerRoute(
			elasticsearchConnectionConfiguration.maxConnectionsPerRoute()
		).networkHostAddresses(
			elasticsearchConnectionConfiguration.networkHostAddresses()
		).password(
			elasticsearchConnectionConfiguration.password()
		).proxyConfig(
			createProxyConfig(elasticsearchConnectionConfiguration)
		).truststorePassword(
			elasticsearchConnectionConfiguration.truststorePassword()
		).truststorePath(
			elasticsearchConnectionConfiguration.truststorePath()
		).truststoreType(
			elasticsearchConnectionConfiguration.truststoreType()
		).userName(
			elasticsearchConnectionConfiguration.username()
		);

		elasticsearchConnectionManager.addElasticsearchConnection(
			elasticsearchConnectionBuilder.build());
	}

	protected ProxyConfig createProxyConfig(
		ElasticsearchConnectionConfiguration
			elasticsearchConnectionConfiguration) {

		ProxyConfig.Builder proxyConfigBuilder = ProxyConfig.builder(http);

		return proxyConfigBuilder.networkAddresses(
			elasticsearchConnectionConfiguration.networkHostAddresses()
		).host(
			elasticsearchConnectionConfiguration.proxyHost()
		).password(
			elasticsearchConnectionConfiguration.proxyPassword()
		).port(
			elasticsearchConnectionConfiguration.proxyPort()
		).userName(
			elasticsearchConnectionConfiguration.proxyHost()
		).build();
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected Http http;

}