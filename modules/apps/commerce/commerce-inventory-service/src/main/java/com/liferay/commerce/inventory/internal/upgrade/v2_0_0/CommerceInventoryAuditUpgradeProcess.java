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

package com.liferay.commerce.inventory.internal.upgrade.v2_0_0;

import com.liferay.commerce.inventory.internal.upgrade.v2_0_0.util.CommerceInventoryAuditTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryAuditUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_addColumn(
			CommerceInventoryAuditTable.class,
			CommerceInventoryAuditTable.TABLE_NAME, "logType", "VARCHAR(75)");
		_addColumn(
			CommerceInventoryAuditTable.class,
			CommerceInventoryAuditTable.TABLE_NAME, "logTypeSettings", "TEXT");

		if (hasColumn(CommerceInventoryAuditTable.TABLE_NAME, "description")) {
			runSQL("delete from CIAudit");

			runSQL("alter table CIAudit drop column description");
		}
	}

	private void _addColumn(
			Class<?> entityClass, String tableName, String columnName,
			String columnType)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Adding column %s to table %s", columnName, tableName));
		}

		if (!hasColumn(tableName, columnName)) {
			alter(
				entityClass,
				new AlterTableAddColumn(
					columnName + StringPool.SPACE + columnType));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s", columnName,
						tableName));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryAuditUpgradeProcess.class);

}