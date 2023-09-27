/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v5_3_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.SetUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Objects;
import java.util.Set;

/**
 * @author Paulo Albuquerque
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select objectFieldId, businessType, name from ObjectField");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectField set readOnly = ? where objectFieldId " +
						"= ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String readOnly = ObjectFieldConstants.READ_ONLY_FALSE;

				if (Objects.equals(
						resultSet.getString("businessType"),
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
					Objects.equals(
						resultSet.getString("businessType"),
						ObjectFieldConstants.BUSINESS_TYPE_FORMULA) ||
					_readOnlyObjectFieldNames.contains(
						resultSet.getString("name"))) {

					readOnly = ObjectFieldConstants.READ_ONLY_TRUE;
				}

				preparedStatement2.setString(1, readOnly);
				preparedStatement2.setLong(
					2, resultSet.getLong("objectFieldId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ObjectField", "readOnly VARCHAR(75)"),
			UpgradeProcessFactory.addColumns(
				"ObjectField", "readOnlyConditionExpression TEXT")
		};
	}

	private final Set<String> _readOnlyObjectFieldNames = SetUtil.fromArray(
		"createDate", "creator", "id", "modifiedDate", "status");

}