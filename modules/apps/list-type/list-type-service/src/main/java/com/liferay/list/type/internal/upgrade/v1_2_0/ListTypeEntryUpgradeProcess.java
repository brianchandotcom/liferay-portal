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

package com.liferay.list.type.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Selton Guedes
 */
public class ListTypeEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn("ListTypeEntry", "externalReferenceCode")) {
			return;
		}

		alterTableAddColumn(
			"ListTypeEntry", "externalReferenceCode", "VARCHAR(75)");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select key_, listTypeEntryId from ListTypeEntry");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ListTypeEntry set externalReferenceCode = ? " +
						"where listTypeEntryId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(1, resultSet.getString("key_"));

				preparedStatement2.setLong(
					2, resultSet.getLong("listTypeEntryId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}