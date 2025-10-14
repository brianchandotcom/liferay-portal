/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v5_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Georgel Pop
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String columnName = "externalReferenceCode";

		if (!hasColumn("DLFileEntry", columnName)) {
			columnName = "uuid_";
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat(
				"select DLFileEntry.fileEntryId, DLFileEntry.", columnName,
				", Layout.ctCollectionId, Layout.plid, Layout.groupId,  ",
				"DLFileEntry.groupId, Group_.externalReferenceCode from ",
				"Layout left join DLFileEntry on (DLFileEntry.ctCollectionId ",
				"= Layout.ctCollectionId or DLFileEntry.ctCollectionId = 0) ",
				"and DLFileEntry.fileEntryId = Layout.faviconFileEntryId left ",
				"join Group_ on (Group_.ctCollectionId = ",
				"Layout.ctCollectionId or Group_.ctCollectionId = 0) and ",
				"Group_.groupId = DLFileEntry.groupId where ",
				"Layout.faviconFileEntryId > 0"));

			 ResultSet resultSet = preparedStatement1.executeQuery();
			 PreparedStatement preparedStatement2 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 "update Layout set faviconFileEntryERC = ?, " +
					 "faviconFileEntryScopeERC = ? where ctCollectionId = ? " +
					 "and plid = ?")) {

			while (resultSet.next()) {
				long fileEntryId = resultSet.getLong(1);

				if (fileEntryId == 0) {
					continue;
				}

				String faviconFileEntryERC = resultSet.getString(2);
				long ctCollectionId = resultSet.getLong(3);
				long plid = resultSet.getLong(4);

				long groupId1 = resultSet.getLong(5);
				long groupId2 = resultSet.getLong(6);
				String faviconFileEntryScopeERC = resultSet.getString(7);

				if (groupId1 == groupId2) {
					faviconFileEntryScopeERC = null;
				}

				preparedStatement2.setString(1, faviconFileEntryERC);
				preparedStatement2.setString(2, faviconFileEntryScopeERC);
				preparedStatement2.setLong(3, ctCollectionId);
				preparedStatement2.setLong(4, plid);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("Layout", "faviconFileEntryId")
		};
	}

}