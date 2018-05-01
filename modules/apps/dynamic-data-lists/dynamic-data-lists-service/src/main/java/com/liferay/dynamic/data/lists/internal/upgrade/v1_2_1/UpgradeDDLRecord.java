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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_2_1;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecord extends UpgradeProcess {

	public UpgradeDDLRecord(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	protected void deleteDDLRecord(long recordId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecord where recordId = ?")) {

			ps.setLong(1, recordId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select DDLRecord.* from DDLRecord inner join ");
		sb.append("DDMFormInstance on DDLRecord.recordSetId = ");
		sb.append("DDMFormInstance.formInstanceId");

		try (PreparedStatement ps1 =
				connection.prepareStatement(sb.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordId = rs.getLong("recordId");

					DDMFormInstanceRecord ddmFormInstanceRecord =
						_ddmFormInstanceRecordLocalService.
							createDDMFormInstanceRecord(recordId);

					ddmFormInstanceRecord.setCompanyId(rs.getLong("companyId"));
					ddmFormInstanceRecord.setCreateDate(
						rs.getTimestamp("createDate"));
					ddmFormInstanceRecord.setFormInstanceId(
						rs.getLong("recordSetId"));
					ddmFormInstanceRecord.setFormInstanceVersion(
						rs.getString("recordSetVersion"));
					ddmFormInstanceRecord.setGroupId(rs.getLong("groupId"));
					ddmFormInstanceRecord.setLastPublishDate(
						rs.getTimestamp("lastPublishDate"));
					ddmFormInstanceRecord.setModifiedDate(
						rs.getTimestamp("modifiedDate"));
					ddmFormInstanceRecord.setStorageId(
						rs.getLong("DDMStorageId"));
					ddmFormInstanceRecord.setUserId(rs.getLong("userId"));
					ddmFormInstanceRecord.setUserName(rs.getString("userName"));
					ddmFormInstanceRecord.setVersion(rs.getString("version"));
					ddmFormInstanceRecord.setVersionUserId(
						rs.getLong("versionUserId"));
					ddmFormInstanceRecord.setVersionUserName(
						rs.getString("versionUserName"));

					deleteDDLRecord(recordId);

					_ddmFormInstanceRecordLocalService.addDDMFormInstanceRecord(
						ddmFormInstanceRecord);
				}
			}
		}
	}

	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}