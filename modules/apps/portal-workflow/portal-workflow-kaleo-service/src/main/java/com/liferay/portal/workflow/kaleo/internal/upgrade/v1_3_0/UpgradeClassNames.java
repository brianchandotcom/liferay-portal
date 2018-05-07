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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0;

import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.util.Table;
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