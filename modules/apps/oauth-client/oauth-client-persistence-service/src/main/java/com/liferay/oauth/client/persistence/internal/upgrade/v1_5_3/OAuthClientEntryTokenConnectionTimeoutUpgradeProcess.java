/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_5_3;

import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Christian Moura
 */
public class OAuthClientEntryTokenConnectionTimeoutUpgradeProcess
	extends UpgradeProcess {

	public OAuthClientEntryTokenConnectionTimeoutUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update OAuthClientEntry set tokenConnectionTimeout = ? " +
						"where oAuthClientEntryId = ?");

			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(
				"select authServerWellKnownURI, clientId, companyId, " +
					"oAuthClientEntryId from OAuthClientEntry")) {

			while (resultSet.next()) {
				preparedStatement.setInt(
					1,
					_getTokenConnectionTimeout(
						resultSet.getString("authServerWellKnownURI"),
						resultSet.getString("clientId"),
						resultSet.getLong("companyId")));
				preparedStatement.setLong(
					2, resultSet.getLong("oAuthClientEntryId"));

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"OAuthClientEntry", "tokenConnectionTimeout INTEGER")
		};
	}

	private int _getTokenConnectionTimeout(
			String authServerWellKnownURI, String clientId, long companyId)
		throws Exception {

		String filterString = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select issuer from OAuthClientASLocalMetadata where " +
					"companyId = ? and localWellKnownURI = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, authServerWellKnownURI);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					filterString = StringBundler.concat(
						"(&(companyId=", companyId, ")(issuerURL=",
						resultSet.getString("issuer"),
						")(openIdConnectClientId=", clientId, "))");
				}
			}
		}

		if (filterString == null) {
			filterString = StringBundler.concat(
				"(&(companyId=", companyId, ")(discoveryEndpoint=",
				authServerWellKnownURI, ")(openIdConnectClientId=", clientId,
				"))");
		}

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (ArrayUtil.isEmpty(configurations)) {
			return OAuthClientEntryConstants.TOKEN_CONNECTION_TIMEOUT_DEFAULT;
		}

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		return GetterUtil.getInteger(
			properties.get("tokenConnectionTimeout"),
			OAuthClientEntryConstants.TOKEN_CONNECTION_TIMEOUT_DEFAULT);
	}

	private final ConfigurationAdmin _configurationAdmin;

}