/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.internal.upgrade.v3_0_1;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.OrderedProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.StringWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Properties;

/**
 * @author Olivér Kecskeméty
 */
public class SamlSpIdpConnectionDataUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select samlSpIdpConnectionId, userAttributeMappings from " +
					"SamlSpIdpConnection")) {

			while (resultSet.next()) {
				String userAttributeMappings = resultSet.getString(
					"userAttributeMappings");

				if (Validator.isNull(userAttributeMappings)) {
					continue;
				}

				Properties userAttributeMappingsProperties =
					new OrderedProperties();

				userAttributeMappingsProperties.load(
					new UnsyncStringReader(userAttributeMappings));

				userAttributeMappingsProperties.forEach(
					(key, value) -> {
						if (Validator.isNull(value)) {
							userAttributeMappingsProperties.replace(key, key);
						}
					});

				try (StringWriter stringWriter = new StringWriter()) {
					userAttributeMappingsProperties.store(stringWriter, null);

					try (PreparedStatement preparedStatement =
							connection.prepareStatement(
								"update SamlSpIdpConnection set " +
									"userAttributeMappings = ? where " +
										"samlSpIdpConnectionId =  ?")) {

						preparedStatement.setString(1, stringWriter.toString());
						preparedStatement.setLong(
							2, resultSet.getLong("samlSpIdpConnectionId"));

						preparedStatement.execute();
					}
				}
			}
		}
	}

}