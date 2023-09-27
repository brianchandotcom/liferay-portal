/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v2_2_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectCPDefinitionOptionRelSQL =
			"select groupId, CPDefinitionOptionRelId from " +
				"CPDefinitionOptionRel";
		String updateCPDefinitionOptionValueRelSQL =
			"update CPDefinitionOptionValueRel set groupId = ? WHERE " +
				"CPDefinitionOptionRelId = ?";

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDefinitionOptionValueRelSQL);
			Statement s1 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet1 = s1.executeQuery(
				selectCPDefinitionOptionRelSQL)) {

			while (resultSet1.next()) {
				preparedStatement.setLong(1, resultSet1.getLong("groupId"));
				preparedStatement.setLong(
					2, resultSet1.getLong("CPDefinitionOptionRelId"));

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

}