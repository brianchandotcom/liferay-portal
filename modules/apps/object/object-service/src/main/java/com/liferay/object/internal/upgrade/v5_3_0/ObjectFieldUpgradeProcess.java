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
				"select ObjectField.objectFieldId, ObjectField.businessType, " +
					"ObjectField.name from ObjectField");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update ObjectField set readOnly = ? where objectFieldId " +
						"= ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				String readOnly = ObjectFieldConstants.READ_ONLY_FALSE;

				if (_readOnlyObjectFieldNames.contains(
						resultSet.getString("name")) ||
					Objects.equals(
						resultSet.getString("businessType"),
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
					Objects.equals(
						resultSet.getString("businessType"),
						ObjectFieldConstants.BUSINESS_TYPE_FORMULA)) {

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