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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Jonathan McCann
 */
public class UpgradeWorkflow extends UpgradeProcess {

	protected void deleteOrphaned() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] orphanedAttachedModel : getOrphanedAttachedModels()) {
				String tableName = orphanedAttachedModel[0];
				String columnName = orphanedAttachedModel[1];
				String columnValue = orphanedAttachedModel[2];

				if (!hasTable(tableName) || !hasColumn(tableName, columnName)) {
					continue;
				}

				String orphanedTableName = orphanedAttachedModel[3];
				String orphanedColumnName = orphanedAttachedModel[4];

				if (!hasTable(orphanedTableName) ||
					!hasColumn(orphanedTableName, orphanedColumnName)) {

					continue;
				}

				deleteOrphaned(
					tableName, columnName, columnValue, orphanedTableName,
					orphanedColumnName);
			}
		}
	}

	protected void deleteOrphaned(
			String tableName, String columnName, String columnValue,
			String orphanedTableName, String orphanedColumnName)
		throws Exception {

		StringBundler sb = new StringBundler(11);

		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(columnName);
		sb.append(" = ");
		sb.append(columnValue);
		sb.append(" and classPK not in (select ");
		sb.append(orphanedColumnName);
		sb.append(" from ");
		sb.append(orphanedTableName);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		runSQL(sb.toString());
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphaned();
	}

	protected String[][] getOrphanedAttachedModels() {
		return _ORPHANED_ATTACHED_MODELS;
	}

	private static final String _CLASS_NAME_ID = String.valueOf(
		PortalUtil.getClassNameId(
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess"));

	private static final String[][] _ORPHANED_ATTACHED_MODELS = {
		{
			"KaleoInstance", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'",
			"DDLRecord", "recordId"
		},
		{
			"KaleoInstanceToken", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'",
			"DDLRecord", "recordId"
		},
		{
			"WorkflowDefinitionLink", "classNameId", _CLASS_NAME_ID,
			"KaleoProcess", "kaleoProcessId"
		},
		{
			"WorkflowInstanceLink", "classNameId", _CLASS_NAME_ID, "DDLRecord",
			"recordId"
		}
	};

}