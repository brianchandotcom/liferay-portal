/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Istvan Sajtos
 */
public class OAuthClientEntryUpgradeProcess extends UpgradeProcess {

	public OAuthClientEntryUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeOAuthClientEntriesWithOIDCProviderConfiguration();

		_upgradeOAuthClientEntriesWithoutOIDCProviderConfiguration();
	}

	private void _upgradeOAuthClientEntriesWithOIDCProviderConfiguration()
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=com.liferay.portal.security.sso.openid." +
				"connect.internal.configuration." +
					"OpenIdConnectProviderConfiguration)");

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				if (properties != null) {
					String openIdConnectClientId = GetterUtil.getString(
						properties.get("openIdConnectClientId"));

					long discoveryEndPointCacheInMillis = GetterUtil.getLong(
						properties.get("discoveryEndPointCacheInMillis"));

					try (PreparedStatement preparedStatement =
							connection.prepareStatement(
								"update OAuthClientEntry set " +
									"metadataCacheInMillis = ? where " +
										"clientId = ?")) {

						preparedStatement.setLong(
							1, discoveryEndPointCacheInMillis);
						preparedStatement.setString(2, openIdConnectClientId);

						preparedStatement.execute();
					}
				}
			}
		}
	}

	private void _upgradeOAuthClientEntriesWithoutOIDCProviderConfiguration()
		throws Exception {

		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select oAuthClientEntryId from OAuthClientEntry where " +
					"metadataCacheInMillis is null");
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update OAuthClientEntry set metadataCacheInMillis = 360000 " +
					"where oAuthClientEntryId = ?")) {

			while (resultSet.next()) {
				preparedStatement.setLong(
					1, resultSet.getLong("oAuthClientEntryId"));

				preparedStatement.execute();
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;

}