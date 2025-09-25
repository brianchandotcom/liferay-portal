/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v2_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

		try (Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(
				 StringBundler.concat(
					 "select * from Configuration_ where configurationId LIKE ",
					 "%com.liferay.portal.security.sso.openid.connect.internal.",
					 "configuration.OpenIdConnectProviderConfiguration%"));
			 PreparedStatement preparedStatement = connection.prepareStatement(
				 "update Configuration_ set dictionary = ? where " +
				 "configurationId = ?")) {

			if (resultSet.next()) {
				String dictionaryString = resultSet.getString("dictionary");

				if (Validator.isNull(dictionaryString)) {
					return;
				}

				Dictionary<String, Object> dictionary =
					ConfigurationHandler.read(
						new UnsyncByteArrayInputStream(
							dictionaryString.getBytes(StringPool.UTF8)));

				dictionary.put(
					"discoveryEndpoint",
					GetterUtil.getString(
						dictionary.remove("discoveryEndPoint")));
				dictionary.put(
					"discoveryEndpointCacheInMillis",
					GetterUtil.getLong(
						dictionary.remove("discoveryEndPointCacheInMillis")));
				dictionary.put(
					"authorizationEndpoint",
					GetterUtil.getString(
						dictionary.remove("authorizationEndPoint")));
				dictionary.put(
					"tokenEndpoint",
					GetterUtil.getString(dictionary.remove("tokenEndPoint")));
				dictionary.put(
					"userInfoEndpoint",
					GetterUtil.getString(dictionary.get("userInfoEndPoint")));

				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				ConfigurationHandler.write(
					unsyncByteArrayOutputStream, dictionary);

				preparedStatement.setString(
					1, unsyncByteArrayOutputStream.toString());

				preparedStatement.setString(
					2, resultSet.getString("configurationId"));

				preparedStatement.executeUpdate();
			}
		}
	}

}