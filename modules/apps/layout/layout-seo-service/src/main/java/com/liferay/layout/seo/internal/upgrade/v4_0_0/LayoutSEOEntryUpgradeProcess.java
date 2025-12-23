/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.v4_0_0;

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
public class LayoutSEOEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String sql = StringBundler.concat(
			"select DLFileEntry.externalReferenceCode, DLFileEntry.groupId, ",
			"Group_.externalReferenceCode, LayoutSEOEntry.ctCollectionId, ",
			"LayoutSEOEntry.layoutSEOEntryId, LayoutSEOEntry.groupId from ",
			"LayoutSEOEntry inner join DLFileEntry on ",
			"(DLFileEntry.ctCollectionId = LayoutSEOEntry.ctCollectionId or ",
			"DLFileEntry.ctCollectionId = 0) and ",
			"LayoutSEOEntry.openGraphImageFileEntryId = ",
			"DLFileEntry.fileEntryId inner join Group_ on ",
			"(Group_.ctCollectionId = LayoutSEOEntry.ctCollectionId or ",
			"Group_.ctCollectionId = 0) and DLFileEntry.groupId = ",
			"Group_.groupId where LayoutSEOEntry.openGraphImageFileEntryId > ",
			"0");

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(sql);
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update LayoutSEOEntry set openGraphImageFileEntryERC = " +
						"?, openGraphImageFileEntrySERC = ? where " +
							"layoutSEOEntryId = ? and ctCollectionId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String fileEntryERC = resultSet.getString(1);

				long fileEntryGroupId = resultSet.getLong(2);

				String fileEntryScopeERC = resultSet.getString(3);

				long layoutGroupId = resultSet.getLong(6);

				if (layoutGroupId == fileEntryGroupId) {
					fileEntryScopeERC = null;
				}

				updatePreparedStatement.setString(1, fileEntryERC);
				updatePreparedStatement.setString(2, fileEntryScopeERC);

				updatePreparedStatement.setLong(
					3, resultSet.getLong("layoutSEOEntryId"));
				updatePreparedStatement.setLong(
					4, resultSet.getLong("ctCollectionId"));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns(
				"LayoutSEOEntry", "openGraphImageFileEntryId")
		};
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"LayoutSEOEntry", "openGraphImageFileEntryERC VARCHAR(75) null",
				"openGraphImageFileEntrySERC VARCHAR(75) null")
		};
	}

}