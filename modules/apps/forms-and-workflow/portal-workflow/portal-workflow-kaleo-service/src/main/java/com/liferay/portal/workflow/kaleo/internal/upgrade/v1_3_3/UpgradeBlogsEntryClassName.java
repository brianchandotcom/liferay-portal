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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_3;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.BaseUpgradeClassNames;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class UpgradeBlogsEntryClassName extends BaseUpgradeClassNames {

	@Override
	protected void updateClassName(String tableName, String columnName) {
		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			Table table = new Table(tableName);

			table.updateColumnValue(
				columnName, "com.liferay.blogs.kernel.model.BlogsEntry",
				"com.liferay.blogs.model.BlogsEntry");
		}
	}

	@Override
	protected Map<String, Serializable> updateWorkflowContext(
		String workflowContextJSON) {

		Map<String, Serializable> workflowContext = WorkflowContextUtil.convert(
			workflowContextJSON);

		String entryClassName = GetterUtil.getString(
			workflowContext.get("entryClassName"));

		if (Objects.equals(
				"com.liferay.blogs.kernel.model.BlogsEntry", entryClassName)) {

			workflowContext.put(
				"entryClassName", "com.liferay.blogs.model.BlogsEntry");

			return workflowContext;
		}

		return null;
	}

}