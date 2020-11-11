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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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
		).networkHostAddresses(
			elasticsearchConnectionConfiguration.networkHostAddresses()
		).password(
			elasticsearchConnectionConfiguration.password()
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

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}