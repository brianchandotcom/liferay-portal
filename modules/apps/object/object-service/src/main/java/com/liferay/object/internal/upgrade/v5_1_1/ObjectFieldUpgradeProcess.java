/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v5_1_1;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Leite
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectSQL = SQLTransformer.transform(
			StringBundler.concat(
				"select ObjectField.objectFieldId,ObjectField.companyId,",
				"ObjectField.dbTableName from ObjectField inner join ",
				"ObjectDefinition on ObjectDefinition.objectDefinitionId = ",
				"ObjectField.objectDefinitionId where ",
				"ObjectField.businessType = '",
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP,
				"' and ObjectField.relationshipType = '",
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY,
				"' and ObjectDefinition.system_ = [$TRUE$]"));

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				selectSQL);
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectField set dbTableName = ? where " +
						"objectFieldId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String suffixDBTableName =
					"_x_" + resultSet.getString("companyId");
				String dbTableName = resultSet.getString("dbTableName");

				if (dbTableName.contains(suffixDBTableName)) {
					preparedStatement2.setString(1, dbTableName);
				}
				else {
					preparedStatement2.setString(
						1, dbTableName + suffixDBTableName);
				}

				preparedStatement2.setLong(
					2, resultSet.getLong("objectFieldId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}