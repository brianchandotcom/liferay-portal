/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_2;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DLFileEntryUpgradeProcess extends UpgradeProcess {

	public DLFileEntryUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"delete from DLFileShortcut where not exists (select * from ",
				"DLFileEntry where fileEntryId = ",
				"DLFileShortcut.toFileEntryId)"));

		_delete(DLFileEntry.class, "AssetEntry", "DLFileEntry", "fileEntryId");
		_delete(
			DLFileEntry.class, "RatingsEntry", "DLFileEntry", "fileEntryId");
		_delete(
			DLFileEntry.class, "RatingsStats", "DLFileEntry", "fileEntryId");
		_delete(DLFolder.class, "AssetEntry", "DLFolder", "folderId");
	}

	private void _delete(
			Class<?> clazz, String tableName, String joinTableName,
			String joinColumnName)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"delete from ", tableName, " where ", tableName,
				".classNameId = ", _classNameLocalService.getClassNameId(clazz),
				" and not exists (select * from ", joinTableName, " where ",
				joinColumnName, " = ", tableName, ".classPK)"));
	}

	private final ClassNameLocalService _classNameLocalService;

}