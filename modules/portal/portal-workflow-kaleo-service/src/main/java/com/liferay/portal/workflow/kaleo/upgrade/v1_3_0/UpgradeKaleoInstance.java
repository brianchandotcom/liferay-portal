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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Christopher Kian
 */
public class UpgradeKaleoInstance extends UpgradeProcess {

	public UpgradeKaleoInstance(DDLRecordLocalService ddlRecordLocalService) {
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	protected void deleteOrphanedWorkflowInstanceLinks(
			String tableName, String columnName, Object columnValue)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(6);

			sb.append("select * from ");
			sb.append(tableName);
			sb.append(" where ");
			sb.append(columnName);
			sb.append(" = ");
			sb.append(columnValue);

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			if (rs.next()) {
				long classPK = rs.getLong("classPK");

				DDLRecord ddlRecord = _ddlRecordLocalService.fetchDDLRecord(
					classPK);

				if (ddlRecord == null) {
					sb = new StringBundler(8);

					sb.append("delete from ");
					sb.append(tableName);
					sb.append(" where ");
					sb.append(columnName);
					sb.append(" = ");
					sb.append(columnValue);
					sb.append(" and classPK = ");
					sb.append(classPK);

					runSQL(sb.toString());
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanedWorkflowInstanceLinks(
			"KaleoInstance", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'");
		deleteOrphanedWorkflowInstanceLinks(
			"KaleoInstanceToken", "className",
			"'com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess'");
	}

	private final DDLRecordLocalService _ddlRecordLocalService;

}