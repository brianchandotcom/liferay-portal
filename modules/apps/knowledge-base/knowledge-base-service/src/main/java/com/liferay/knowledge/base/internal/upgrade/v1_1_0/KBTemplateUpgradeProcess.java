/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBTemplateTable;
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
public class KBTemplateUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		renameAndUpdateTable(
			StringUtil.replaceFirst(KBTemplateTable.TABLE_NAME, "KB", "KB_"),
			KBTemplateTable.TABLE_NAME, KBTemplateTable.TABLE_COLUMNS,
			KBTemplateTable.TABLE_SQL_CREATE, KBTemplateTable.TABLE_SQL_DROP);
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

		updateColumn(oldTableName, "kbTemplateId", "LONG", "templateId");
		updateColumn(oldTableName, "engineType", "INTEGER", "0");
		updateColumn(oldTableName, "cacheable", "BOOLEAN", "TRUE");

		alterTableDropColumn(oldTableName, "templateId");
		alterTableDropColumn(oldTableName, "description");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBTemplateUpgradeProcess.class);

}