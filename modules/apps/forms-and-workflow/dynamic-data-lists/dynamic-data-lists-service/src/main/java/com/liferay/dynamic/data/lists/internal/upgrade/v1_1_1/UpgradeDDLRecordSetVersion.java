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
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
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
		DDMStructureVersionLocalService ddmStructureVersionLocalService,
		UserLocalService userLocalService) {

		_counterLocalService = counterLocalService;
		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(4);

		sb1.append("select recordSetId, groupId, companyId, userId, ");
		sb1.append("userName, createDate, modifiedDate, DDMStructureId, ");
		sb1.append("name, description, settings_ from DDLRecordSet where ");
		sb1.append("scope = 2");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into DDLRecordSetVersion (recordSetVersionId, ");
		sb2.append("groupId, companyId, userId, userName, createDate, ");
		sb2.append("recordSetId, DDMStructureVersionId, name, description, ");
		sb2.append("settings_, version,  status, statusByUserId, ");
		sb2.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					User user = _userLocalService.getUser(rs.getLong("userId"));

					ps2.setLong(1, _counterLocalService.increment());
					ps2.setLong(2, rs.getLong("groupId"));
					ps2.setLong(3, rs.getLong("companyId"));
					ps2.setLong(4, user.getUserId());
					ps2.setString(5, rs.getString("userName"));
					ps2.setTimestamp(6, rs.getTimestamp("createDate"));
					ps2.setLong(7, rs.getLong("recordSetId"));
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

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getLatestStructureVersion(
				ddmStructureId);

		return ddmStructureVersion.getStructureVersionId();
	}

	private final CounterLocalService _counterLocalService;
	private final DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private final UserLocalService _userLocalService;

}