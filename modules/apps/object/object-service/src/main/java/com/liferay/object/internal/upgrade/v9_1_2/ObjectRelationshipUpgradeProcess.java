/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_1_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Thalles Montenegro
 */
public class ObjectRelationshipUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectDefinition.objectDefinitionId from ",
						"ObjectDefinition left join User_ on ",
						"ObjectDefinition.userId = User_.userId where ",
						"User_.userId is null")));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				SQLTransformer.transform(
					"select userId from User_ where screenName = " +
						"'default-service-account'"));
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				SQLTransformer.transform(
					"update ObjectDefinition set userId = ? where " +
						"objectDefinitionId = ?"));
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			ResultSet resultSet2 = preparedStatement2.executeQuery()) {

			resultSet2.next();

			while (resultSet1.next()) {
				preparedStatement3.setLong(1, resultSet2.getLong("userId"));

				preparedStatement3.setLong(
					2, resultSet1.getLong("objectDefinitionId"));

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

}