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
					"select groupId, companyId, userId, userName, fileEntryId ",
					"from DLFileEntry where fileEntryId not in (select ",
					"classPK from AssetDisplayPageEntry where classNameId in (",
					dlFileEntryClassNameId, ", ", fileEntryClassNameId, "))"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into AssetDisplayPageEntry (uuid_, ",
						"assetDisplayPageEntryId, groupId, companyId, userId, ",
						"userName, createDate, modifiedDate, classNameId, ",
						"classPK, layoutPageTemplateEntryId, type_, plid) ",
						"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					Timestamp now = new Timestamp(System.currentTimeMillis());

					preparedStatement2.setString(1, PortalUUIDUtil.generate());
					preparedStatement2.setLong(2, increment());
					preparedStatement2.setLong(3, resultSet.getLong("groupId"));
					preparedStatement2.setLong(
						4, resultSet.getLong("companyId"));
					preparedStatement2.setLong(5, resultSet.getLong("userId"));
					preparedStatement2.setString(
						6, resultSet.getString("userName"));
					preparedStatement2.setTimestamp(7, now);
					preparedStatement2.setTimestamp(8, now);
					preparedStatement2.setLong(9, fileEntryClassNameId);
					preparedStatement2.setLong(
						10, resultSet.getLong("fileEntryId"));
					preparedStatement2.setLong(11, 0);
					preparedStatement2.setLong(
						12, AssetDisplayPageConstants.TYPE_NONE);
					preparedStatement2.setLong(13, 0);

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