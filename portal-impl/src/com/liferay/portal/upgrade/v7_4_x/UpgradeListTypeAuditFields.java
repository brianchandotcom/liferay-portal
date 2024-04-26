/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Matyas Wollner
 */
public class UpgradeListTypeAuditFields extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select listTypeId from ListType where uuid_ is null");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update ListType set uuid_ = ?, createDate = ?, " +
						"modifiedDate = ?, userId = ? where listTypeId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setString(1, PortalUUIDUtil.generate());
				preparedStatement2.setTimestamp(
					2, new Timestamp(System.currentTimeMillis()));
				preparedStatement2.setTimestamp(
					3, new Timestamp(System.currentTimeMillis()));
				preparedStatement2.setLong(4, 0);
				preparedStatement2.setLong(5, resultSet.getLong(1));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"ListType", "uuid_ VARCHAR(75) null", "userId LONG",
				"userName VARCHAR(75) null", "createDate DATE null",
				"modifiedDate DATE null")
		};
	}

}