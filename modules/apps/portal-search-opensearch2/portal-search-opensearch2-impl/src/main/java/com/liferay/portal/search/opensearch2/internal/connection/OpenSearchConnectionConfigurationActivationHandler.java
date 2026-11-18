/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.opensearch2.configuration.OpenSearchConnectionConfiguration;
import com.liferay.portal.security.key.secret.SecretResolver;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Selena Aungst
 */
@Component(
	configurationPid = "com.liferay.portal.search.opensearch2.configuration.OpenSearchConnectionConfiguration",
	service = {}
)
public class OpenSearchConnectionConfigurationActivationHandler {

	@Activate
	protected void activate(Map<String, Object> properties) {
		OpenSearchConnectionConfiguration openSearchConnectionConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenSearchConnectionConfiguration.class, properties);

		_connectionId = openSearchConnectionConfiguration.connectionId();

		OpenSearchConnection.Builder openSearchConnectionBuilder =
			new OpenSearchConnection.Builder();

		openSearchConnectionManager.addOpenSearchConnection(
			openSearchConnectionBuilder.active(
				openSearchConnectionConfiguration.active()
			).authenticationEnabled(
				openSearchConnectionConfiguration.authenticationEnabled()
			).compressionEnabled(
				openSearchConnectionConfiguration.compressionEnabled()
			).connectionId(
				_connectionId
			).httpSSLEnabled(
				openSearchConnectionConfiguration.httpSSLEnabled()
			).maxConnections(
				openSearchConnectionConfiguration.maxConnections()
			).maxConnectionsPerRoute(
				openSearchConnectionConfiguration.maxConnectionsPerRoute()
			).networkHostAddresses(
				openSearchConnectionConfiguration.networkHostAddresses()
			).password(
				_secretResolver.resolve(
					CompanyConstants.SYSTEM,
					openSearchConnectionConfiguration.password())
			).proxyConfig(
				_createProxyConfig(openSearchConnectionConfiguration)
			).truststorePassword(
				_secretResolver.resolve(
					CompanyConstants.SYSTEM,
					openSearchConnectionConfiguration.truststorePassword())
			).truststorePath(
				openSearchConnectionConfiguration.truststorePath()
			).truststoreType(
				openSearchConnectionConfiguration.truststoreType()
			).userName(
				openSearchConnectionConfiguration.username()
			).build());
	}

	@Deactivate
	protected void deactivate() {
		openSearchConnectionManager.removeOpenSearchConnection(_connectionId);
	}

	@Reference
	protected Http http;

	@Reference
	protected OpenSearchConnectionManager openSearchConnectionManager;

	private ProxyConfig _createProxyConfig(
		OpenSearchConnectionConfiguration openSearchConnectionConfiguration) {

		ProxyConfig.Builder proxyConfigBuilder = ProxyConfig.builder(http);

		return proxyConfigBuilder.networkAddresses(
			openSearchConnectionConfiguration.networkHostAddresses()
		).host(
			openSearchConnectionConfiguration.proxyHost()
		).password(
			_secretResolver.resolve(
				CompanyConstants.SYSTEM,
				openSearchConnectionConfiguration.proxyPassword())
		).port(
			openSearchConnectionConfiguration.proxyPort()
		).userName(
			openSearchConnectionConfiguration.proxyUserName()
		).build();
	}

	private String _connectionId;

	@Reference
	private SecretResolver _secretResolver;

}