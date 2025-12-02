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
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
			StringBundler.concat(
				"select DLFileEntry.externalReferenceCode, ",
				"DLFileEntry.groupId, Group_.externalReferenceCode, ",
				"Layout.ctCollectionId, Layout.plid, Layout.groupId from ",
				"Layout inner join DLFileEntry on (DLFileEntry.ctCollectionId ",
				"= Layout.ctCollectionId or DLFileEntry.ctCollectionId = 0) ",
				"and DLFileEntry.fileEntryId = Layout.faviconFileEntryId left ",
				"join Group_ on (Group_.ctCollectionId = ",
				"Layout.ctCollectionId or Group_.ctCollectionId = 0) and ",
				"DLFileEntry.groupId = Group_.groupId where ",
				"Layout.faviconFileEntryId > 0"));

			 ResultSet resultSet = preparedStatement1.executeQuery();
			 PreparedStatement preparedStatement2 =
				 AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					 connection,
					 "update Layout set faviconFileEntryERC = ?, " +
					 "faviconFileEntryScopeERC = ? where ctCollectionId = ? " +
					 "and plid = ?")) {

			while (resultSet.next()) {
				long dlFileEntryGroupId = resultSet.getLong(2);
				String dlFileEntryScopeERC = resultSet.getString(3);
				long layoutGroupId = resultSet.getLong(6);

				if (dlFileEntryGroupId == layoutGroupId) {
					dlFileEntryScopeERC = null;
				}

				preparedStatement2.setString(1, resultSet.getString(1));
				preparedStatement2.setString(2, dlFileEntryScopeERC);
				preparedStatement2.setLong(3, resultSet.getLong(4));
				preparedStatement2.setLong(4, resultSet.getLong(5));

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