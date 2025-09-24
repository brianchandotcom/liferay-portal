/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_2_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Dictionary;

import org.apache.felix.cm.file.ConfigurationHandler;

/**
 * @author Christian Moura
 */
public class OpenIdConnectProviderConfigurationUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("Configuration_")) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from Configuration_ where configurationId LIKE ?");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"update Configuration_ set dictionary = ? where " +
					"configurationId = ?")) {

			preparedStatement1.setString(1, _CONFIGURATION_ID);

			ResultSet resultSet = preparedStatement1.executeQuery();

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				if (Validator.isNull(dictionaryString)) {
					return;
				}

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				String discoveryEndpointValue = GetterUtil.getString(
					dictionary.get("discoveryEndPoint"));

				dictionary.remove("discoveryEndPoint");
				dictionary.put("discoveryEndpoint", discoveryEndpointValue);

				long discoveryEndpointCacheInMillisValue = GetterUtil.getLong(
					dictionary.get("discoveryEndPointCacheInMillis"));

				dictionary.remove("discoveryEndPointCacheInMillis");
				dictionary.put(
					"discoveryEndpointCacheInMillis",
					discoveryEndpointCacheInMillisValue);

				String authorizationEndpointValue = GetterUtil.getString(
					dictionary.get("authorizationEndPoint"));

				dictionary.remove("authorizationEndPoint");
				dictionary.put(
					"authorizationEndpoint", authorizationEndpointValue);

				String tokenEndpointValue = GetterUtil.getString(
					dictionary.get("tokenEndPoint"));

				dictionary.remove("tokenEndPoint");
				dictionary.put("tokenEndpoint", tokenEndpointValue);

				String userInfoEndpointValue = GetterUtil.getString(
					dictionary.get("userInfoEndPoint"));

				dictionary.remove("userInfoEndPoint");
				dictionary.put("userInfoEndpoint", userInfoEndpointValue);

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				ConfigurationHandler.write(
					unsyncByteArrayOutputStream, dictionary);

				preparedStatement2.setString(
					1, unsyncByteArrayOutputStream.toString());

				preparedStatement2.setString(
					2, resultSet.getString("configurationId"));

				preparedStatement2.executeUpdate();
			}
		}
	}

	private static final String _CONFIGURATION_ID =
		"%com.liferay.portal.security.sso.openid.connect.internal." +
			"configuration.OpenIdConnectProviderConfiguration%";

}