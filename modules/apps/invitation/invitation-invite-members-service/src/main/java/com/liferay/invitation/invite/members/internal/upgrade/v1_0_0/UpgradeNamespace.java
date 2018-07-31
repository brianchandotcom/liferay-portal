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

package com.liferay.invitation.invite.members.internal.upgrade.v1_0_0;

import com.liferay.invitation.invite.members.internal.upgrade.v1_0_0.util.MemberRequestTable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adolfo Pérez
 */
public class UpgradeNamespace extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		renameTable(
			_getOldTableName(), MemberRequestTable.TABLE_NAME,
			MemberRequestTable.TABLE_COLUMNS,
			MemberRequestTable.TABLE_SQL_CREATE,
			MemberRequestTable.TABLE_SQL_DROP);
	}

	protected void renameTable(
			String oldTableName, String newTableName, Object[][] tableColumns,
			String tableSqlCreate, String tableSqlDrop)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(newTableName)) {
			boolean hasNewTable = hasTable(newTableName);

			if (hasNewTable && hasRows(newTableName)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Not renaming ", oldTableName, " to ", newTableName,
							" because ", newTableName, " has data"));
				}

				return;
			}

			boolean hasOldTable = hasTable(oldTableName);

			if (hasOldTable && !hasRows(oldTableName)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Not renaming ", oldTableName, " to ", newTableName,
							" because ", oldTableName, " has no data"));
				}

				return;
			}

			if (!hasNewTable && !hasOldTable) {
				runSQL(tableSqlCreate);

				return;
			}

			if (hasNewTable) {
				runSQL(tableSqlDrop);
			}

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				oldTableName, tableColumns);

			upgradeTable.setCreateSQL(tableSqlCreate);

			upgradeTable.updateTable();
		}
	}

	private String _getOldTableName() {
		if (MemberRequestTable.TABLE_NAME.startsWith(_NEW_NAMESPACE)) {
			return StringUtil.replaceFirst(
				MemberRequestTable.TABLE_NAME, _NEW_NAMESPACE, _OLD_NAMESPACE);
		}

		return _OLD_NAMESPACE + MemberRequestTable.TABLE_NAME;
	}

	private static final String _NEW_NAMESPACE = "IM_";

	private static final String _OLD_NAMESPACE = "SO_";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeNamespace.class);

}