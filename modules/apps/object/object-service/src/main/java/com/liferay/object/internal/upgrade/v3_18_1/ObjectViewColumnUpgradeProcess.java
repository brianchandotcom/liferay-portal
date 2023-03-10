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

package com.liferay.object.internal.upgrade.v3_18_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

/**
 * @author Julián Vela
 */
public class ObjectViewColumnUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select distinct(objectFieldName) as objectFieldName from " +
					"ObjectViewColumn where  objectFieldName = 'dateCreated' " +
						"or objectFieldName = 'dateModified'");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectViewColumn set objectFieldName = ? where " +
						"objectFieldName  = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String objectFieldName = resultSet.getString("objectFieldName");

				String newObjectFieldName = _labelKeys.get(
					resultSet.getString("objectFieldName"));

				preparedStatement2.setString(1, newObjectFieldName);

				preparedStatement2.setString(2, objectFieldName);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final Map<String, String> _labelKeys = HashMapBuilder.put(
		"dateCreated", "createDate"
	).put(
		"dateModified", "modifiedDate"
	).build();

}