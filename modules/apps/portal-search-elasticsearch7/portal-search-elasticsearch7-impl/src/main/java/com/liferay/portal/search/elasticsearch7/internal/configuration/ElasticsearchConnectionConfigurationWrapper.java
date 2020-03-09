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

package com.liferay.portal.search.elasticsearch7.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration",
	immediate = true,
	service = ElasticsearchConnectionConfigurationWrapper.class
)
public class ElasticsearchConnectionConfigurationWrapper {

	public String getConnectionId() {
		return _elasticsearchConnectionConfiguration.connectionId();
	}

	public String[] getNetworkHostAddresses() {
		return _elasticsearchConnectionConfiguration.networkHostAddresses();
	}

	public String getPassword() {
		return _elasticsearchConnectionConfiguration.password();
	}

	public String getTruststorePassword() {
		return _elasticsearchConnectionConfiguration.truststorePassword();
	}

	public String getTruststorePath() {
		return _elasticsearchConnectionConfiguration.truststorePath();
	}

	public String getTruststoreType() {
		return _elasticsearchConnectionConfiguration.truststoreType();
	}

	public String getUsername() {
		return _elasticsearchConnectionConfiguration.username();
	}

	public boolean isActive() {
		return _elasticsearchConnectionConfiguration.active();
	}

	public boolean isAuthenticationEnabled() {
		return _elasticsearchConnectionConfiguration.authenticationEnabled();
	}

	public boolean isHttpSSLEnabled() {
		return _elasticsearchConnectionConfiguration.httpSSLEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConnectionConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchConnectionConfiguration.class, properties);
	}

	private volatile ElasticsearchConnectionConfiguration
		_elasticsearchConnectionConfiguration;

}