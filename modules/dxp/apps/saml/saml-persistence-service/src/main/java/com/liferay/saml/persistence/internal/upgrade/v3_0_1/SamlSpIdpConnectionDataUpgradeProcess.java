/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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