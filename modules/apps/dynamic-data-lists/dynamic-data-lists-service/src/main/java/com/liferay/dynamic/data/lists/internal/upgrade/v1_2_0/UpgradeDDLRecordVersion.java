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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_2_0;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordVersion extends UpgradeProcess {

	public UpgradeDDLRecordVersion(
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService) {

		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
	}

	protected void deleteDDLRecordVersion(long recordVersionId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecordVersion where recordVersionId ?")) {

			ps.setLong(1, recordVersionId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select DDLRecordVersion.* from DDLRecordVersion inner ");
		sb.append("join DDMFormInstance on DDLRecordVersion.recordSetId = ");
		sb.append("DDMFormInstance.formInstanceId");

		try (PreparedStatement ps1 =
				connection.prepareStatement(sb.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordVersionId = rs.getLong("recordVersionId");

					DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
						_ddmFormInstanceRecordVersionLocalService.
							createDDMFormInstanceRecordVersion(recordVersionId);

					ddmFormInstanceRecordVersion.setCompanyId(
						rs.getLong("companyId"));
					ddmFormInstanceRecordVersion.setCreateDate(
						rs.getTimestamp("createDate"));
					ddmFormInstanceRecordVersion.setFormInstanceId(
						rs.getLong("recordSetId"));
					ddmFormInstanceRecordVersion.setFormInstanceRecordId(
						rs.getLong("recordId"));
					ddmFormInstanceRecordVersion.setFormInstanceVersion(
						rs.getString("recordSetVersion"));
					ddmFormInstanceRecordVersion.setGroupId(
						rs.getLong("groupId"));
					ddmFormInstanceRecordVersion.setStorageId(
						rs.getInt("DDMStorageId"));
					ddmFormInstanceRecordVersion.setStatus(rs.getInt("status"));
					ddmFormInstanceRecordVersion.setStatusByUserId(
						rs.getLong("statusByUserId"));
					ddmFormInstanceRecordVersion.setStatusByUserName(
						rs.getString("statusByUserName"));
					ddmFormInstanceRecordVersion.setStatusDate(
						rs.getTimestamp("statusDate"));
					ddmFormInstanceRecordVersion.setUserId(
						rs.getLong("userId"));
					ddmFormInstanceRecordVersion.setUserName(
						rs.getString("userName"));
					ddmFormInstanceRecordVersion.setVersion(
						rs.getString("version"));

					deleteDDLRecordVersion(recordVersionId);

					_ddmFormInstanceRecordVersionLocalService.
						addDDMFormInstanceRecordVersion(
							ddmFormInstanceRecordVersion);
				}
			}
		}
	}

	private final DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

}