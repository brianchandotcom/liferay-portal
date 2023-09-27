/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.workflow.kaleo.internal.upgrade.helper.v1_3_0.WorkflowContextUpgradeHelper;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Lino Alves
 */
public class UpgradeClassNames extends BaseUpgradeClassNames {

	@Override
	protected void updateClassName(String tableName, String columnName) {
		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			Table table = new Table(tableName);

			for (Map.Entry<String, String> entry :
					_workflowContextUpgradeHelper.
						getRenamedClassNamesEntrySet()) {

				table.updateColumnValue(
					columnName, entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	protected Map<String, Serializable> updateWorkflowContext(
		String workflowContextJSON) {

		String updatedWorkflowContextJSON =
			_workflowContextUpgradeHelper.renamePortalClassNames(
				workflowContextJSON);

		Map<String, Serializable> workflowContext = WorkflowContextUtil.convert(
			updatedWorkflowContextJSON);

		if (workflowContextJSON.equals(updatedWorkflowContextJSON) &&
			!_workflowContextUpgradeHelper.isEntryClassNameRenamed(
				workflowContext)) {

			return null;
		}

		return _workflowContextUpgradeHelper.renameEntryClassName(
			workflowContext);
	}

	private final WorkflowContextUpgradeHelper _workflowContextUpgradeHelper =
		new WorkflowContextUpgradeHelper();

}