/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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