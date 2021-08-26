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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DLAppDLFileEntryCleanUpgradeProcess extends UpgradeProcess {

	public DLAppDLFileEntryCleanUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_cleanDLFileShortcuts();

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntry.class);

		_cleanTableByClassName(classNameId, "AssetEntry");
		_cleanTableByClassName(classNameId, "RatingsEntry");
		_cleanTableByClassName(classNameId, "RatingsStats");
	}

	private void _cleanDLFileShortcuts() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("delete from DLFileShortcut where not exists (select * ");
		sb.append("from DLFileEntry where fileEntryId = ");
		sb.append("DLFileShortcut.toFileEntryId)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.execute();
		}
	}

	private void _cleanTableByClassName(long classNameId, String tableName)
		throws Exception {

		StringBundler sb = new StringBundler(10);

		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(tableName);
		sb.append(".classNameId = ");
		sb.append(classNameId);
		sb.append(" and not exists (select * from DLFileEntry where ");
		sb.append("fileEntryId = ");
		sb.append(tableName);
		sb.append(".classPK)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.execute();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}