/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.internal.upgrade.v3_0_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Andrea Sbarra
 */
public class CPDefinitionVirtualSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String updateCPDefinitionVirtualSettingGroupId =
			"update CPDefinitionVirtualSetting set groupId = ? where classPK " +
				"= ?";

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, cpDefinitionId from CPDefinition");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDefinitionVirtualSettingGroupId);
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long classPK = resultSet.getLong("cpDefinitionId");

				preparedStatement2.setLong(1, groupId);
				preparedStatement2.setLong(2, classPK);

				preparedStatement2.execute();
			}
		}

		try (PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select groupId, cpInstanceId from CPInstance");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDefinitionVirtualSettingGroupId);
			ResultSet resultSet = preparedStatement3.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long classPK = resultSet.getLong("cpInstanceId");

				preparedStatement4.setLong(1, groupId);
				preparedStatement4.setLong(2, classPK);

				preparedStatement4.execute();
			}
		}
	}

}