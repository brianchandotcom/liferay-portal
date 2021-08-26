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

package com.liferay.document.library.internal.upgrade.v3_2_2;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DLFileEntryUpgradeProcess extends UpgradeProcess {

	public DLFileEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_dlFileEntryClassNameId = classNameLocalService.getClassNameId(
			DLFileEntry.class);

		_dlFolderClassNameId = classNameLocalService.getClassNameId(
			DLFolder.class);
	}

	@Override
	protected void doUpgrade() throws Exception {
		_deleteDLFileShortcuts();

		_deleteTableByFileEntryId("AssetEntry");
		_deleteTableByFileEntryId("RatingsEntry");
		_deleteTableByFileEntryId("RatingsStats");

		_deleteTableByFolderId("AssetEntry");
	}

	private void _deleteDLFileShortcuts() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("delete from DLFileShortcut where not exists (select * ");
		sb.append("from DLFileEntry where fileEntryId = ");
		sb.append("DLFileShortcut.toFileEntryId)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.execute();
		}
	}

	private void _deleteTableByClassName(
			long classNameId, String tableName, String joinTableName,
			String joinPrimaryKey)
		throws Exception {

		StringBundler sb = new StringBundler(13);

		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(tableName);
		sb.append(".classNameId = ");
		sb.append(classNameId);
		sb.append(" and not exists (select * from ");
		sb.append(joinTableName);
		sb.append(" where ");
		sb.append(joinPrimaryKey);
		sb.append(" = ");
		sb.append(tableName);
		sb.append(".classPK)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.execute();
		}
	}

	private void _deleteTableByFileEntryId(String tableName) throws Exception {
		_deleteTableByClassName(
			_dlFileEntryClassNameId, tableName, "DLFileEntry", "fileEntryId");
	}

	private void _deleteTableByFolderId(String tableName) throws Exception {
		_deleteTableByClassName(
			_dlFolderClassNameId, tableName, "DLFolder", "folderId");
	}

	private final long _dlFileEntryClassNameId;
	private final long _dlFolderClassNameId;

}