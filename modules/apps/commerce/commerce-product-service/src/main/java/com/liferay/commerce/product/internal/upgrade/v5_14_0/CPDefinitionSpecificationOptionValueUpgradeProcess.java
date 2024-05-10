/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v5_14_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionSpecificationOptionValueUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select * from CPDSpecificationOptionValue");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update CPDSpecificationOptionValue set key_ = ? where " +
						"ctCollectionId = ? and " +
							"CPDSpecificationOptionValueId = ?")) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long cpdSpecificationOptionValueId = resultSet.getLong(
						"CPDSpecificationOptionValueId");

					updatePreparedStatement.setString(
						1, String.valueOf(cpdSpecificationOptionValueId));

					updatePreparedStatement.setLong(
						2, resultSet.getLong("ctCollectionId"));

					updatePreparedStatement.setLong(
						3, cpdSpecificationOptionValueId);

					updatePreparedStatement.addBatch();
				}
			}

			updatePreparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CPDSpecificationOptionValue", "key_ VARCHAR(75) null")
		};
	}

}