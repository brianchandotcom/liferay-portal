/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Shinn Lok
 */
public class VerifyWorkflow extends VerifyProcess {

	protected void deleteOrphanedWorkflowDefinitionLinks(
			String columnName, String tableName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("delete from WorkflowDefinitionLink where classPk not ");
			sb.append("in(select ");
			sb.append(columnName);
			sb.append(" from ");
			sb.append(tableName);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			ps = con.prepareStatement(sb.toString());

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps, null);
		}
	}

	@Override
	protected void doVerify() throws Exception {
		verifyWorkflowDefinitionLinks();
	}

	protected void verifyWorkflowDefinitionLinks() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select distinct classNameId from WorkflowDefinitionLink");

			rs = ps.executeQuery();

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");

				ClassName className = ClassNameLocalServiceUtil.getClassName(
					classNameId);

				String classNameValue = className.getValue();

				if (classNameValue.equals(_kaleoProcessModelName)) {
					deleteOrphanedWorkflowDefinitionLinks(
						"kaleoProcessId", "KaleoProcess");
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static String _kaleoProcessModelName =
		"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess";

}