/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Set;

/**
 * @author Luis Ortiz
 */
public class CompanyDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_dropOrphanObjectTables();

		upgrade(
			new DefaultAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				"companyId", "Company"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null,
				"[$SOURCE_TABLE_ALIAS$].ownerType = " +
					PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				"ownerId", "PortalPreferences", "companyId", "Company"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null,
				"[$SOURCE_TABLE_ALIAS$].ownerType = " +
					PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				"ownerId", "PortletPreferences", "companyId", "Company"));
	}

	private void _dropOrphanObjectTables() throws Exception {
		Set<Long> companyIds = SetUtil.fromArray(
			PortalInstancePool.getCompanyIds());

		DBInspector dbInspector = new DBInspector(connection);

		List<String> tableNames = dbInspector.getTableNames(null);

		for (String tableName : tableNames) {
			long companyId = _getCompanyIdFromTableName(tableName);

			if ((companyId > 0) && !companyIds.contains(companyId)) {
				dropTable(tableName);

				DataCleanupLoggingUtil.logDrop(
					_log, tableName,
					"it belonged to a nonexistent company: " + companyId);
			}
		}
	}

	private long _getCompanyIdFromTableName(String tableName) {
		String[] tableNameParts = StringUtil.split(
			tableName, StringPool.UNDERLINE);

		if ((tableNameParts.length > 1) &&
			(StringUtil.equalsIgnoreCase(tableNameParts[0], "l") ||
			 StringUtil.equalsIgnoreCase(tableNameParts[0], "o"))) {

			return GetterUtil.getLong(tableNameParts[1]);
		}

		if ((tableNameParts.length > 2) &&
			StringUtil.equalsIgnoreCase(
				tableNameParts[tableNameParts.length - 2], "x")) {

			return GetterUtil.getLong(
				tableNameParts[tableNameParts.length - 1]);
		}

		return -1;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyDataCleanupPreupgradeProcess.class);

}