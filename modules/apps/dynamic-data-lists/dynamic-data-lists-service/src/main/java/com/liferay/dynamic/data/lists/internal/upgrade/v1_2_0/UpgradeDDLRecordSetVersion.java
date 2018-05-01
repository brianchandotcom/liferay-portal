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

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordSetVersion extends UpgradeProcess {

	public UpgradeDDLRecordSetVersion(
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService) {

		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
	}

	protected void deleteDDLRecordSetVersion(long recordSetVersionId)
		throws Exception {

		StringBundler sb = new StringBundler(2);

		sb.append("delete from DDLRecordSetVersion where recordSetVersionId ");
		sb.append("= ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, recordSetVersionId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select DDLRecordSetVersion.* from DDLRecordSetVersion ");
		sb.append("inner join DDMFormInstance on DDLRecordSetVersion.");
		sb.append("recordSetId = DDMFormInstance.formInstanceId");

		try (PreparedStatement ps1 =
				connection.prepareStatement(sb.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordSetVersionId = rs.getLong("recordSetVersionId");

					DDMFormInstanceVersion ddmFormInstanceVersion =
						_ddmFormInstanceVersionLocalService.
							createDDMFormInstanceVersion(recordSetVersionId);

					ddmFormInstanceVersion.setCompanyId(
						rs.getLong("companyId"));
					ddmFormInstanceVersion.setCreateDate(
						rs.getTimestamp("createDate"));
					ddmFormInstanceVersion.setDescriptionMap(
						LocalizationUtil.getLocalizationMap(
							rs.getString("description")));
					ddmFormInstanceVersion.setFormInstanceId(
						rs.getLong("recordSetId"));
					ddmFormInstanceVersion.setGroupId(rs.getLong("groupId"));
					ddmFormInstanceVersion.setNameMap(
						LocalizationUtil.getLocalizationMap(
							rs.getString("name")));
					ddmFormInstanceVersion.setSettings(
						rs.getString("settings_"));
					ddmFormInstanceVersion.setStatus(rs.getInt("status"));
					ddmFormInstanceVersion.setStatusByUserId(
						rs.getLong("statusByUserId"));
					ddmFormInstanceVersion.setStatusByUserName(
						rs.getString("statusByUserName"));
					ddmFormInstanceVersion.setStatusDate(
						rs.getTimestamp("statusDate"));
					ddmFormInstanceVersion.setStructureVersionId(
						rs.getLong("DDMStructureVersionId"));
					ddmFormInstanceVersion.setUserId(rs.getLong("userId"));
					ddmFormInstanceVersion.setUserName(
						rs.getString("userName"));
					ddmFormInstanceVersion.setVersion(rs.getString("version"));

					deleteDDLRecordSetVersion(recordSetVersionId);

					_ddmFormInstanceVersionLocalService.
						addDDMFormInstanceVersion(ddmFormInstanceVersion);
				}
			}
		}
	}

	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;

}