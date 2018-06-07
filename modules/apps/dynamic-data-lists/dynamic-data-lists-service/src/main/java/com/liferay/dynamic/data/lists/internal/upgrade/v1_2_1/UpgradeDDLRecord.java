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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecord extends UpgradeProcess {

	public UpgradeDDLRecord(
		AssetEntryLocalService assetEntryLocalService,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	protected void addAssetEntry(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		DDMFormInstance formInstance = ddmFormInstanceRecord.getFormInstance();

		String title = LanguageUtil.format(
			getResourceBundle(ddmFormValues), "new-entry-for-form-x",
			formInstance.getName(ddmFormValues.getDefaultLocale()), false);

		_assetEntryLocalService.updateEntry(
			ddmFormInstanceRecord.getUserId(),
			ddmFormInstanceRecord.getGroupId(),
			ddmFormInstanceRecord.getCreateDate(),
			ddmFormInstanceRecord.getModifiedDate(),
			DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecord.getFormInstanceRecordId(),
			ddmFormInstanceRecord.getUuid(), 0, new long[0], new String[0],
			true, true, null, null, null, null, ContentTypes.TEXT_HTML, title,
			null, StringPool.BLANK, null, null, 0, 0, 0.0);
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

					ddmFormInstanceRecord.setGroupId(rs.getLong("groupId"));
					ddmFormInstanceRecord.setCompanyId(rs.getLong("companyId"));
					ddmFormInstanceRecord.setUserId(rs.getLong("userId"));
					ddmFormInstanceRecord.setUserName(rs.getString("userName"));
					ddmFormInstanceRecord.setVersionUserId(
						rs.getLong("versionUserId"));
					ddmFormInstanceRecord.setVersionUserName(
						rs.getString("versionUserName"));
					ddmFormInstanceRecord.setCreateDate(
						rs.getTimestamp("createDate"));
					ddmFormInstanceRecord.setModifiedDate(
						rs.getTimestamp("modifiedDate"));
					ddmFormInstanceRecord.setFormInstanceId(
						rs.getLong("recordSetId"));
					ddmFormInstanceRecord.setFormInstanceVersion(
						rs.getString("recordSetVersion"));
					ddmFormInstanceRecord.setStorageId(
						rs.getLong("DDMStorageId"));
					ddmFormInstanceRecord.setVersion(rs.getString("version"));
					ddmFormInstanceRecord.setLastPublishDate(
						rs.getTimestamp("lastPublishDate"));

					deleteDDLRecord(recordId);

					ddmFormInstanceRecord =
						_ddmFormInstanceRecordLocalService.
							addDDMFormInstanceRecord(ddmFormInstanceRecord);

					addAssetEntry(ddmFormInstanceRecord);
				}
			}
		}
	}

	protected ResourceBundle getResourceBundle(DDMFormValues ddmFormValues) {
		Class<?> clazz = _ddmFormInstanceRecordLocalService.getClass();

		return ResourceBundleUtil.getBundle(
			"content.Language", ddmFormValues.getDefaultLocale(),
			clazz.getClassLoader());
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}