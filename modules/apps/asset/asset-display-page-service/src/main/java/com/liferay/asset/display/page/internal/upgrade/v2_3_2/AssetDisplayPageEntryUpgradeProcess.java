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

package com.liferay.asset.display.page.internal.upgrade.v2_3_2;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class AssetDisplayPageEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteDuplicateDLAssetDisplayPages();
		_upgradeDLAssetDisplayPageTypes();
	}

	private void _deleteDuplicateDLAssetDisplayPages() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntryConstants.getClassName());
		long fileEntryClassNameId = PortalUtil.getClassNameId(
			FileEntry.class.getName());

		StringBundler sb1 = new StringBundler(11);

		sb1.append("select assetDisplayPageEntry1.assetDisplayPageEntryId ");
		sb1.append("from AssetDisplayPageEntry assetDisplayPageEntry1 inner ");
		sb1.append("join AssetDisplayPageEntry assetDisplayPageEntry2 on ");
		sb1.append("assetDisplayPageEntry1.groupId = ");
		sb1.append("assetDisplayPageEntry2.groupId and ");
		sb1.append("assetDisplayPageEntry2.classNameId = ");
		sb1.append(fileEntryClassNameId);
		sb1.append(" and assetDisplayPageEntry1.classPK = ");
		sb1.append("assetDisplayPageEntry2.classPK where ");
		sb1.append("assetDisplayPageEntry1.classNameId = ");
		sb1.append(dlFileEntryClassNameId);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from AssetDisplayPageEntry where " +
						"assetDisplayPageEntryId = ?")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setLong(
						1, resultSet.getLong("assetDisplayPageEntryId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeDLAssetDisplayPageTypes() throws Exception {
		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntryConstants.getClassName());
		long fileEntryClassNameId = PortalUtil.getClassNameId(
			FileEntry.class.getName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update AssetDisplayPageEntry set classNameId = ? where " +
					"classNameId = ?")) {

			preparedStatement.setLong(1, fileEntryClassNameId);
			preparedStatement.setLong(2, dlFileEntryClassNameId);

			preparedStatement.executeUpdate();
		}
	}

}