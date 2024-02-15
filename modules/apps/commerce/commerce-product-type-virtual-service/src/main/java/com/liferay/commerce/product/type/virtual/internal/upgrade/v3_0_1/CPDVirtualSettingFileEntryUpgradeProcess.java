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
public class CPDVirtualSettingFileEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasTable("CPDVirtualSettingFileEntry")) {
			return;
		}

		String updateCPDVirtualFileEntriesSQL =
			"update CPDVirtualSettingFileEntry set groupId = ? where " +
				"CPDefinitionVirtualSettingId = ?";

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, CPDefinitionVirtualSettingId from " +
					"CPDefinitionVirtualSetting");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPDVirtualFileEntriesSQL);
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long cpDefinitionVirtualSettingId = resultSet.getLong(
					"CPDefinitionVirtualSettingId");

				preparedStatement2.setLong(1, groupId);
				preparedStatement2.setLong(2, cpDefinitionVirtualSettingId);

				preparedStatement2.execute();
			}
		}
	}

}