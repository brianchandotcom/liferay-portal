/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v2_1_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class ObjectFieldBusinessTypeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from ObjectField");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectField set businessType = ? where " +
						"objectFieldId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String dbType = resultSet.getString("dbType");

				if (StringUtil.equals(dbType, "String") &&
					Validator.isNotNull(
						resultSet.getLong("listTypeDefinitionId"))) {

					preparedStatement2.setString(1, "Picklist");
				}
				else if (StringUtil.equals(dbType, "Long") &&
						 Validator.isNotNull(
							 resultSet.getString("relationshipType"))) {

					preparedStatement2.setString(1, "Relationship");
				}
				else {
					preparedStatement2.setString(
						1, _objectFieldBusinessTypes.get(dbType));
				}

				preparedStatement2.setLong(
					2, resultSet.getLong("objectFieldId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Map<String, String> _objectFieldBusinessTypes =
		HashMapBuilder.put(
			"BigDecimal", "PrecisionDecimal"
		).put(
			"Boolean", "Boolean"
		).put(
			"Clob", "LongText"
		).put(
			"Date", "Date"
		).put(
			"Double", "Decimal"
		).put(
			"Integer", "Integer"
		).put(
			"Long", "LongInteger"
		).put(
			"String", "Text"
		).build();

}