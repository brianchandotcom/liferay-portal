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

package com.liferay.portal.search.elasticsearch7.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.constants.ConnectionConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	property = {
		"configuration.field.name=remoteClusterConnectionId",
		"configuration.pid=com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class ConnectionIdConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		List<Option> options = new ArrayList<>();

		for (ElasticsearchConnection elasticsearchConnection :
				elasticsearchConnectionManager.getElasticsearchConnections()) {

			String connectionId = elasticsearchConnection.getConnectionId();

			if (connectionId.equals(ConnectionConstants.REMOTE_CONNECTION_ID) ||
				connectionId.equals(
					ConnectionConstants.SIDECAR_CONNECTION_ID) ||
				!elasticsearchConnection.isActive()) {

				continue;
			}

			Option option = new Option() {

				@Override
				public String getLabel(Locale locale) {
					return connectionId;
				}

				@Override
				public String getValue() {
					return connectionId;
				}

			};

			options.add(option);
		}

		return options;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}