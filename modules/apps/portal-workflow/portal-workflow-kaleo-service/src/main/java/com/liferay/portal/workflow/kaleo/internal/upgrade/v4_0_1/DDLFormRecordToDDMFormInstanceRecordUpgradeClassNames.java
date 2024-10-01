/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v4_0_1;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.BaseUpgradeClassNames;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class DDLFormRecordToDDMFormInstanceRecordUpgradeClassNames
	extends BaseUpgradeClassNames {

	@Override
	protected String getWhereClause() {
		return " where workflowContext like " +
			"'%com.liferay.dynamic.data.lists.model.DDLFormRecord%'";
	}

	@Override
	protected void updateClassName(String tableName, String columnName) {
		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			Table table = new Table(tableName);

			table.updateColumnValue(
				columnName,
				"com.liferay.dynamic.data.lists.model.DDLFormRecord",
				"com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord");
		}
	}

	@Override
	protected Map<String, Serializable> updateWorkflowContext(
		String workflowContext) {

		return HashMapBuilder.<String, Serializable>putAll(
			WorkflowContextUtil.convert(workflowContext)
		).put(
			"entryClassName",
			"com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord"
		).build();
	}

}