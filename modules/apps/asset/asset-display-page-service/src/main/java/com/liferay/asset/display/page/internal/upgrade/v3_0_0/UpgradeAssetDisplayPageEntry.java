/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

		StringBundler sb1 = new StringBundler(7);

		sb1.append("select groupId, companyId, userId, userName, fileEntryId ");
		sb1.append("from DLFileEntry where fileEntryId not in (select ");
		sb1.append("classPK from AssetDisplayPageEntry where classNameId in (");
		sb1.append(dlFileEntryClassNameId);
		sb1.append(", ");
		sb1.append(fileEntryClassNameId);
		sb1.append("))");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("insert into AssetDisplayPageEntry (uuid_, ");
		sb2.append("assetDisplayPageEntryId, groupId, companyId, userId, ");
		sb2.append("userName, createDate, modifiedDate, classNameId, ");
		sb2.append("classPK, layoutPageTemplateEntryId, type_, plid) values( ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					Timestamp now = new Timestamp(System.currentTimeMillis());

					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, increment());
					ps2.setLong(3, rs.getLong("groupId"));
					ps2.setLong(4, rs.getLong("companyId"));
					ps2.setLong(5, rs.getLong("userId"));
					ps2.setString(6, rs.getString("userName"));
					ps2.setTimestamp(7, now);
					ps2.setTimestamp(8, now);
					ps2.setLong(9, fileEntryClassNameId);
					ps2.setLong(10, rs.getLong("fileEntryId"));
					ps2.setLong(11, 0);
					ps2.setLong(12, AssetDisplayPageConstants.TYPE_NONE);
					ps2.setLong(13, 0);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from AssetDisplayPageEntry where (classNameId = ? or " +
					"classNameId = ?) and type_ = ?")) {

			ps.setLong(1, dlFileEntryClassNameId);
			ps.setLong(2, fileEntryClassNameId);
			ps.setLong(3, AssetDisplayPageConstants.TYPE_DEFAULT);

			ps.executeUpdate();
		}
	}

}