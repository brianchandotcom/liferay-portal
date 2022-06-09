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

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBCommentTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Peter Shin
 */
public class KBCommentUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		renameAndUpdateTable(
			StringUtil.replaceFirst(KBCommentTable.TABLE_NAME, "KB", "KB_"),
			KBCommentTable.TABLE_NAME, KBCommentTable.TABLE_COLUMNS,
			KBCommentTable.TABLE_SQL_CREATE, KBCommentTable.TABLE_SQL_DROP);
	}

	protected void renameAndUpdateTable(
			String oldTableName, String newTableName, Object[][] tableColumns,
			String tableSqlCreate, String tableSqlDrop)
		throws Exception {

		if (hasRows(newTableName)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Not renaming ", oldTableName, " to ", newTableName,
						" because ", newTableName, " has data"));
			}

			return;
		}

		if (!hasRows(oldTableName)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Not renaming ", oldTableName, " to ", newTableName,
						" because ", oldTableName, " has no data"));
			}

			return;
		}

		updateSchema(oldTableName, newTableName, tableSqlDrop);

		renameTable(oldTableName, tableColumns, tableSqlCreate);
	}

	protected void renameTable(
			String oldTableName, Object[][] tableColumns, String tableSqlCreate)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			oldTableName, tableColumns);

		upgradeTable.setCreateSQL(tableSqlCreate);

		upgradeTable.updateTable();
	}

	protected void updateColumn(
			String tableName, String columnName, String dataType, String data)
		throws Exception {

		if (hasColumn(tableName, columnName)) {
			return;
		}

		String dataTypeUpperCase = StringUtil.toUpperCase(dataType);

		if (dataTypeUpperCase.equals("DATE") || dataType.equals("STRING")) {
			dataTypeUpperCase = dataTypeUpperCase.concat(" null");
		}

		alterTableAddColumn(tableName, columnName, dataTypeUpperCase);

		runSQL(
			StringBundler.concat(
				"update ", tableName, " set ", columnName, " = ", data));
	}

	protected void updateSchema(
			String oldTableName, String newTableName, String tableSqlDrop)
		throws Exception {

		if (hasTable(newTableName)) {
			runSQL(tableSqlDrop);
		}

		updateColumn(oldTableName, "kbCommentId", "LONG", "commentId");

		alterTableDropColumn(oldTableName, "commentId");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBCommentUpgradeProcess.class);

}