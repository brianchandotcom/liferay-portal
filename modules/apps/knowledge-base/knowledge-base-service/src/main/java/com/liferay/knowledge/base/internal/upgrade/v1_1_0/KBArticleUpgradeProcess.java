/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBArticleAttachmentsHelper;
import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBArticleLatestUpgradeColumnImpl;
import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBArticleMainUpgradeColumnImpl;
import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBArticleRootResourcePrimKeyUpgradeColumnImpl;
import com.liferay.knowledge.base.internal.upgrade.v1_1_0.util.KBArticleTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Peter Shin
 */
public class KBArticleUpgradeProcess extends UpgradeProcess {

	public KBArticleUpgradeProcess(Store store) {
		_store = store;
	}

	@Override
	protected void doUpgrade() throws Exception {
		renameAndUpdateTable(
			StringUtil.replaceFirst(KBArticleTable.TABLE_NAME, "KB", "KB_"),
			KBArticleTable.TABLE_NAME, KBArticleTable.TABLE_COLUMNS,
			KBArticleTable.TABLE_SQL_CREATE, KBArticleTable.TABLE_SQL_DROP);
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

		updateTable(newTableName, tableColumns, tableSqlCreate);

		KBArticleAttachmentsHelper kbArticleAttachmentsHelper =
			new KBArticleAttachmentsHelper(_store);

		kbArticleAttachmentsHelper.deleteAttachmentsDirectory(
			PortalUtil.getDefaultCompanyId());
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

		updateColumn(oldTableName, "kbArticleId", "LONG", "articleId");
		updateColumn(oldTableName, "rootResourcePrimKey", "LONG", "0");
		updateColumn(oldTableName, "kbTemplateId", "LONG", "0");
		updateColumn(oldTableName, "sections", "STRING", "'_general_'");
		updateColumn(oldTableName, "viewCount", "INTEGER", "0");
		updateColumn(oldTableName, "latest", "BOOLEAN", "FALSE");
		updateColumn(oldTableName, "main", "BOOLEAN", "FALSE");
		updateColumn(oldTableName, "status", "INTEGER", "0");
		updateColumn(oldTableName, "statusByUserId", "LONG", "userId");
		updateColumn(oldTableName, "statusByUserName", "STRING", "userName");
		updateColumn(oldTableName, "statusDate", "DATE", "modifiedDate");

		alterTableDropColumn(oldTableName, "articleId");
	}

	protected void updateTable(
			String newTableName, Object[][] tableColumns, String tableSqlCreate)
		throws Exception {

		UpgradeColumn kbArticleIdColumn = new TempUpgradeColumnImpl(
			"kbArticleId");

		UpgradeColumn resourcePrimKeyColumn = new TempUpgradeColumnImpl(
			"resourcePrimKey");

		KBArticleRootResourcePrimKeyUpgradeColumnImpl
			kbArticleRootResourcePrimKeyUpgradeColumnImpl =
				new KBArticleRootResourcePrimKeyUpgradeColumnImpl(
					resourcePrimKeyColumn);

		KBArticleAttachmentsHelper kbArticleAttachmentsHelper =
			new KBArticleAttachmentsHelper(_store);

		KBArticleLatestUpgradeColumnImpl kbArticleLatestUpgradeColumnImpl =
			new KBArticleLatestUpgradeColumnImpl(
				kbArticleAttachmentsHelper, kbArticleIdColumn,
				resourcePrimKeyColumn);
		KBArticleMainUpgradeColumnImpl kbArticleMainUpgradeColumnImpl =
			new KBArticleMainUpgradeColumnImpl(
				kbArticleAttachmentsHelper, kbArticleIdColumn,
				resourcePrimKeyColumn);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			newTableName, tableColumns, kbArticleIdColumn,
			resourcePrimKeyColumn,
			kbArticleRootResourcePrimKeyUpgradeColumnImpl,
			kbArticleLatestUpgradeColumnImpl, kbArticleMainUpgradeColumnImpl);

		upgradeTable.setCreateSQL(tableSqlCreate);

		upgradeTable.updateTable();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleUpgradeProcess.class);

	private final Store _store;

}