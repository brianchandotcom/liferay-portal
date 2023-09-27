/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Map<String, String> map = HashMapBuilder.put(
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

			for (Map.Entry<String, String> entry : map.entrySet()) {
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