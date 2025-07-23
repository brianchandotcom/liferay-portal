/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v13_0_6;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Ivica Cardic
 */
public class CPDefinitionInventoryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String updateAllowedOrderQuantitiesSQL =
			"update CPDefinitionInventory set allowedOrderQuantities = ? " +
				"where CPDefinitionInventoryId = ?";

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateAllowedOrderQuantitiesSQL);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = s.executeQuery(
				"select distinct CPDefinitionInventoryId, " +
					"allowedOrderQuantities from CPDefinitionInventory")) {

			while (resultSet.next()) {
				String allowedOrderQuantities = resultSet.getString(
					"allowedOrderQuantities");

				if (Validator.isNotNull(allowedOrderQuantities)) {
					allowedOrderQuantities = StringUtil.replace(
						allowedOrderQuantities, CharPool.COMMA, CharPool.SPACE);

					allowedOrderQuantities = StringUtil.replace(
						allowedOrderQuantities, CharPool.PERIOD,
						CharPool.SPACE);

					String[] allowedOrderQuantitiesItems =
						allowedOrderQuantities.split(StringPool.SPACE);

					StringBundler sb = new StringBundler(
						allowedOrderQuantitiesItems.length * 2);

					for (String item : allowedOrderQuantitiesItems) {
						sb.append(
							String.format("%,.2f", GetterUtil.getDouble(item)));
						sb.append(StringPool.SPACE);
					}

					allowedOrderQuantities = sb.toString();

					preparedStatement.setString(
						1, allowedOrderQuantities.trim());

					long cpDefinitionInventoryId = resultSet.getLong(
						"CPDefinitionInventoryId");

					preparedStatement.setLong(2, cpDefinitionInventoryId);

					preparedStatement.execute();
				}
			}

			preparedStatement.executeBatch();
		}
	}

}