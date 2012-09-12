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
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Shinn Lok
 */
public class VerifyWorkflow extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		ClassName kaleoProcessClassName =
			ClassNameLocalServiceUtil.fetchClassName(
				"com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess");

		if (kaleoProcessClassName.getClassNameId() == 0) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("delete from WorkflowDefinitionLink where classNameId ");
			sb.append("= ");
			sb.append(kaleoProcessClassName.getClassNameId());
			sb.append(" and classPk not in (select kaleoProcessId from ");
			sb.append("KaleoProcess)");

			ps = con.prepareStatement(sb.toString());

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(con, ps, null);
		}
	}

}