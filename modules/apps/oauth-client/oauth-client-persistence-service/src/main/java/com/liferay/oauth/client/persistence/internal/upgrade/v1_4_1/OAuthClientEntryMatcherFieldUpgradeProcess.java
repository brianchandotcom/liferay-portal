/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_4_1;

import com.liferay.petra.string.StringBundler;
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
 * @author Manuele Castro
 */
public class OAuthClientEntryMatcherFieldUpgradeProcess extends UpgradeProcess {

	public OAuthClientEntryMatcherFieldUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update OAuthClientEntry set matcherField = ? WHERE " +
					"oAuthClientEntryId = ?");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select authServerWellKnownURI, clientId, companyId, " +
					"oAuthClientEntryId from OAuthClientEntry")) {

			while (resultSet.next()) {
				preparedStatement.setString(
					1,
					_getMatcherField(
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
				"OAuthClientEntry", "matcherField VARCHAR(75) null")
		};
	}

	private String _getFilter(
			String authServerWellKnownURI, String clientId, long companyId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select issuer from OAuthClientASLocalMetadata where " +
					"companyId = ? and localWellKnownURI = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, authServerWellKnownURI);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return StringBundler.concat(
					"(&(companyId=", companyId, ")(issuerURL=",
					resultSet.getString("issuer"), ")(openIdConnectClientId=",
					clientId, "))");
			}
		}

		return StringBundler.concat(
			"(&(companyId=", companyId, ")(discoveryEndpoint=",
			authServerWellKnownURI, ")(openIdConnectClientId=", clientId, "))");
	}

	private String _getMatcherField(
			String authServerWellKnownURI, String clientId, long companyId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getFilter(authServerWellKnownURI, clientId, companyId));

		if (ArrayUtil.isEmpty(configurations)) {
			return "email";
		}

		Dictionary<String, Object> properties =
			configurations[0].getProperties();

		return GetterUtil.getString(properties.get("matcherField"));
	}

	private final ConfigurationAdmin _configurationAdmin;

}