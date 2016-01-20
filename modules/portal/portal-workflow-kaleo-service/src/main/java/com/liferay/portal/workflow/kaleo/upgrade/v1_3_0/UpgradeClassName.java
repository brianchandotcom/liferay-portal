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

/**
 * @author Lino Alves
 */

public class UpgradeClassName extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateTableClassName("KaleoInstance");
		updateTableClassName("KaleoInstanceToken");
		updateTableClassName("KaleoTaskInstanceToken");

		updateWorkflowContextClassName("KaleoInstance");
		updateWorkflowContextClassName("KaleoLog");
		updateWorkflowContextClassName("KaleoTaskInstanceToken");
	}

	protected void updateTableClassName(String tableName) throws Exception {
		StringBundler sb = new StringBundler();
		sb.append("update "+ tableName +" set ");
		sb.append("className = \"com.liferay.journal.model.JournalArticle\"");
		sb.append("where className = ");
		sb.append("\"com.liferay.portlet.journal.model.JournalArticle\"");
		String sql = sb.toString();
		runSQL(sql);
	}

	protected void updateWorkflowContextClassName(String tableName)
		throws Exception {

		StringBundler sb = new StringBundler();
		sb.append("update "+ tableName +" set ");
		sb.append("workflowContext = ");
		sb.append("REPLACE( workflowContext,");
		sb.append("'com.liferay.portlet.journal.model.JournalArticle',");
		sb.append("'com.liferay.journal.model.JournalArticle' )");
		String sql = sb.toString();
		runSQL(sql);
	}

}