/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.configuration.admin.definition;

import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnection;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionsHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
@Component(
	property = {
		"configuration.field.name=remoteClusterConnectionId",
		"configuration.pid=com.liferay.portal.search.opensearch.configuration.OpenSearchConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class ConnectionIdConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		List<Option> options = new ArrayList<>();

		for (OpenSearchConnection openSearchConnection :
				_openSearchConnectionConfigurationWrapper.
					getOpenSearchConnections()) {

			String connectionId = openSearchConnection.getConnectionId();

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
	private OpenSearchConnectionsHolder
		_openSearchConnectionConfigurationWrapper;

}