/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.internal.upgrade.v1_9_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionRelUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addColumn("CPDefinitionOptionRel", "key_", "VARCHAR(75)");

		String selectCPOptionSQL =
			"select distinct CPOptionId, key_  from CPOption";
		String updateCPDefinitionOptionRelSQL =
			"update CPDefinitionOptionRel set key_ = ? WHERE CPOptionId = ?";

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDefinitionOptionRelSQL);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = s.executeQuery(selectCPOptionSQL)) {

			while (resultSet.next()) {
				preparedStatement.setString(1, resultSet.getString("key_"));
				preparedStatement.setLong(2, resultSet.getLong("CPOptionId"));

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

}