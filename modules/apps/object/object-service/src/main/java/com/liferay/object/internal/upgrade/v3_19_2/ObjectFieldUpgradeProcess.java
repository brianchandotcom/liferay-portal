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

package com.liferay.object.internal.upgrade.v3_19_2;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.sql.PreparedStatement;

import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, String> dbTableNamePrimaryKeyMap = HashMapBuilder.put(
			"AccountEntry", "accountEntryId"
		).put(
			"Address", "addressId"
		).put(
			"CommerceOrder", "commerceOrderId"
		).put(
			"CommercePricingClass", "commercePricingClassId"
		).put(
			"CPDefinition", "CPDefinitionId"
		).put(
			"User_", "userId"
		).build();

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update ObjectField set dbColumnName = ? where name = " +
						"'id' and dbTableName = ?")) {

			for (Map.Entry<String, String> entry :
					dbTableNamePrimaryKeyMap.entrySet()) {

				preparedStatement.setString(1, entry.getValue());
				preparedStatement.setString(2, entry.getKey());

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
		catch (Exception exception) {
			_log.error(exception);

			throw exception;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldUpgradeProcess.class);

}