/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.upgrade.v3_0_0;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.upgrade.BaseUpgradeAssetDisplayPageEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Jürgen Kappler
 */
public class UpgradeAssetDisplayPageEntry
	extends BaseUpgradeAssetDisplayPageEntry {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeAssetDisplayPageTypes(
			"BlogsEntry", "entryId", "com.liferay.blogs.model.BlogsEntry");

		upgradeAssetDisplayPageTypes(
			"JournalArticle", "resourcePrimKey",
			"com.liferay.journal.model.JournalArticle");

		_upgradeDLAssetDisplayPageTypes();
	}

	private void _upgradeDLAssetDisplayPageTypes() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntryConstants.getClassName());

		long fileEntryClassNameId = PortalUtil.getClassNameId(
			FileEntry.class.getName());

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select distinct DLFileEntry.ctCollectionId, ",
					"DLFileEntry.groupId, DLFileEntry.companyId, ",
					"DLFileEntry.userId, DLFileEntry.userName, ",
					"DLFileEntry.fileEntryId from DLFileEntry where ",
					"DLFileEntry.fileEntryId not in (select classPK from ",
					"AssetDisplayPageEntry where classNameId in (",
					dlFileEntryClassNameId, ", ", fileEntryClassNameId,
					")) and ctCollectionId = DLFileEntry.ctCollectionId"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into AssetDisplayPageEntry (ctCollectionId, ",
						"uuid_, assetDisplayPageEntryId, groupId, companyId, ",
						"userId, userName, createDate, modifiedDate, ",
						"classNameId, classPK, layoutPageTemplateEntryId, ",
						"type_, plid) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?)"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					Timestamp now = new Timestamp(System.currentTimeMillis());

					preparedStatement2.setLong(
						1, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setString(2, PortalUUIDUtil.generate());
					preparedStatement2.setLong(3, increment());
					preparedStatement2.setLong(4, resultSet.getLong("groupId"));
					preparedStatement2.setLong(
						5, resultSet.getLong("companyId"));
					preparedStatement2.setLong(6, resultSet.getLong("userId"));
					preparedStatement2.setString(
						7, resultSet.getString("userName"));
					preparedStatement2.setTimestamp(8, now);
					preparedStatement2.setTimestamp(9, now);
					preparedStatement2.setLong(10, fileEntryClassNameId);
					preparedStatement2.setLong(
						11, resultSet.getLong("fileEntryId"));
					preparedStatement2.setLong(12, 0);
					preparedStatement2.setLong(
						13, AssetDisplayPageConstants.TYPE_NONE);
					preparedStatement2.setLong(14, 0);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from AssetDisplayPageEntry where (classNameId = ? or " +
					"classNameId = ?) and type_ = ?")) {

			preparedStatement.setLong(1, dlFileEntryClassNameId);
			preparedStatement.setLong(2, fileEntryClassNameId);
			preparedStatement.setLong(
				3, AssetDisplayPageConstants.TYPE_DEFAULT);

			preparedStatement.executeUpdate();
		}
	}

}