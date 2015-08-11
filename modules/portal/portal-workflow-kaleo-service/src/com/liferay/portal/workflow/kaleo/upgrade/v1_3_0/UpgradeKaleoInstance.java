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

package com.liferay.portal.workflow.kaleo.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Christopher Kian
 */
public class UpgradeKaleoInstance extends UpgradeProcess {

	protected void deleteOrphanedWorkflowInstanceLinks(
			String tableName, String columnName, Object columnValue)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("delete from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(columnName);
		sb.append(" = '");
		sb.append(columnValue);
		sb.append("' and classPK not in (select recordId from DDLRecord)");

		runSQL(sb.toString());
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanedWorkflowInstanceLinks(
			"KaleoInstance", "className",
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess");
		deleteOrphanedWorkflowInstanceLinks(
			"KaleoInstanceToken", "className",
			"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess");
		deleteOrphanedWorkflowInstanceLinks(
			"WorkflowInstanceLink", "classNameId",
			PortalUtil.getClassNameId(
				"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess"));
	}

}