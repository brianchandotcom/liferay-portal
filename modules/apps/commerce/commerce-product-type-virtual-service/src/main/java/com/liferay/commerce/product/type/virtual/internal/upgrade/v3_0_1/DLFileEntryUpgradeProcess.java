/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.internal.upgrade.v3_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Andrea Sbarra
 */
public class DLFileEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String updateDLFileEntryClassNameClassPK =
			"update DLFileEntry set classNameId = ?, classPK = ? where " +
				"fileEntryId = ? ";

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select Group_.classNameId, Group_.classPK, ",
					"CPDVirtualSettingFileEntry.fileEntryId from Group_ inner ",
					"join CPDVirtualSettingFileEntry on Group_.groupId = ",
					"CPDVirtualSettingFileEntry.groupId"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateDLFileEntryClassNameClassPK);
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long className = resultSet.getLong("classNameId");
				long classPK = resultSet.getLong("classPK");
				long fileEntryId = resultSet.getLong("fileEntryId");

				preparedStatement2.setLong(1, className);
				preparedStatement2.setLong(2, classPK);
				preparedStatement2.setLong(3, fileEntryId);

				preparedStatement2.execute();
			}
		}
	}

}