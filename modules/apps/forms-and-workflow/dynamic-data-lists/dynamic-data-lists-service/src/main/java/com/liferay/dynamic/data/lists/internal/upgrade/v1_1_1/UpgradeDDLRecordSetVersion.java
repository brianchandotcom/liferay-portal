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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_1;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordSetVersion extends UpgradeProcess {

	public UpgradeDDLRecordSetVersion(
		CounterLocalService counterLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		UserLocalService userLocalService) {

		_counterLocalService = counterLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sql1 = new StringBundler();

		sql1.append("select recordSetId, groupId, companyId, userId, ");
		sql1.append("userName, createDate, modifiedDate, DDMStructureId, ");
		sql1.append("name, description, settings_ from DDLRecordSet");

		StringBundler sql2 = new StringBundler();

		sql2.append("insert into DDLRecordSetVersion (recordSetVersionId, ");
		sql2.append("groupId, companyId, userId, userName, createDate, ");
		sql2.append("recordSetId, DDMStructureVersionId, name, description, ");
		sql2.append("settings_, version,  status, statusByUserId, ");
		sql2.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ?, ");
		sql2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sql1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sql2.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordSetId = rs.getLong("recordSetId");

					long userId = rs.getLong("userId");

					User user = _userLocalService.getUser(userId);

					ps2.setLong(1, _counterLocalService.increment());
					ps2.setLong(2, rs.getLong("groupId"));
					ps2.setLong(3, rs.getLong("companyId"));
					ps2.setLong(4, userId);
					ps2.setString(5, rs.getString("userName"));
					ps2.setTimestamp(6, rs.getTimestamp("createDate"));
					ps2.setLong(7, recordSetId);
					ps2.setLong(
						8,
						getDDMStructureVersionId(rs.getLong("DDMStructureId")));
					ps2.setString(9, rs.getString("name"));
					ps2.setString(10, rs.getString("description"));
					ps2.setString(11, rs.getString("settings_"));
					ps2.setString(12, DDLRecordSetConstants.VERSION_DEFAULT);
					ps2.setInt(13, WorkflowConstants.STATUS_APPROVED);
					ps2.setLong(14, user.getUserId());
					ps2.setString(15, user.getFullName());
					ps2.setTimestamp(16, rs.getTimestamp("modifiedDate"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected long getDDMStructureVersionId(long ddmStructureId)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getLatestStructureVersion();

		return ddmStructureVersion.getStructureVersionId();
	}

	private final CounterLocalService _counterLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final UserLocalService _userLocalService;

}