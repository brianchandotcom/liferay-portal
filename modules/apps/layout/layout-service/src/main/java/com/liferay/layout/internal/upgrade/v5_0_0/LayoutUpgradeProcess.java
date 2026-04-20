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
					"select DLFileEntry.externalReferenceCode as ",
					"externalReferenceCode1, DLFileEntry.groupId as groupId1, ",
					"Group_.externalReferenceCode as externalReferenceCod2, ",
					"Layout.ctCollectionId, Layout.plid, Layout.groupId as ",
					"groupId2 from Layout inner join DLFileEntry on (",
					"DLFileEntry.ctCollectionId = Layout.ctCollectionId or ",
					"DLFileEntry.ctCollectionId = 0) and DLFileEntry.",
					"fileEntryId = Layout.faviconFileEntryId inner join ",
					"Group_ on (DLFileEntry.ctCollectionId = Group_.",
					"ctCollectionId or Group_.ctCollectionId = 0) and ",
					"DLFileEntry.groupId = Group_.groupId where Layout.",
					"faviconFileEntryId > 0"));

			ResultSet resultSet = preparedStatement1.executeQuery();

			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update Layout set faviconFileEntryERC = ?, " +
						"faviconFileEntryScopeERC = ? where ctCollectionId = " +
							"? and plid = ?")) {

			while (resultSet.next()) {
				long dlFileEntryGroupId = resultSet.getLong("groupId1");
				String dlFileEntryScopeERC = resultSet.getString(
					"externalReferenceCod2");
				long layoutGroupId = resultSet.getLong("groupId2");

				if (dlFileEntryGroupId == layoutGroupId) {
					dlFileEntryScopeERC = null;
				}

				preparedStatement2.setString(
					1, resultSet.getString("externalReferenceCode1"));
				preparedStatement2.setString(2, dlFileEntryScopeERC);
				preparedStatement2.setLong(
					3, resultSet.getLong("ctCollectionId"));
				preparedStatement2.setLong(4, resultSet.getLong("plid"));

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