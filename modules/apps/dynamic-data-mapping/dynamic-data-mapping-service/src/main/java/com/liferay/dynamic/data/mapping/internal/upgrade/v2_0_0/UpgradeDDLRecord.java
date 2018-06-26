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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecord extends UpgradeProcess {

	public UpgradeDDLRecord(AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void addAssetEntry(
			String uuid, long formInstanceRecordId, long groupId, long userId,
			Timestamp createDate, Timestamp modifiedDate, long formInstanceId)
		throws Exception {

		String defaultLanguageId = null;
		Map<Locale, String> localizationMap = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select name from DDMFormInstance where formInstanceId = ?")) {

			ps.setLong(1, formInstanceId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");

				defaultLanguageId = LocalizationUtil.getDefaultLanguageId(name);
				localizationMap = LocalizationUtil.getLocalizationMap(name);
			}
		}

		if (Validator.isNotNull(defaultLanguageId) &&
			localizationMap.containsKey(defaultLanguageId)) {

			String title = LanguageUtil.format(
				getResourceBundle(LanguageUtil.getLocale(defaultLanguageId)),
				"new-entry-for-form-x", localizationMap.get(defaultLanguageId),
				false);

			_assetEntryLocalService.updateEntry(
				userId, groupId, createDate, modifiedDate,
				DDMFormInstanceRecord.class.getName(), formInstanceRecordId,
				uuid, 0, new long[0], new String[0], true, true, null, null,
				null, null, ContentTypes.TEXT_HTML, title, null,
				StringPool.BLANK, null, null, 0, 0, 0.0);
		}
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
		StringBundler sb1 = new StringBundler(4);

		sb1.append("select DDLRecord.*, DDMFormInstance.version as ");
		sb1.append("formInstanceVersion from DDLRecord inner join ");
		sb1.append("DDMFormInstance on DDLRecord.recordSetId = ");
		sb1.append("DDMFormInstance.formInstanceId");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into DDMFormInstanceRecord(uuid_, ");
		sb2.append("formInstanceRecordId, groupId, companyId, userId, ");
		sb2.append("userName, versionUserId, versionUserName, createDate, ");
		sb2.append("modifiedDate, formInstanceId, formInstanceVersion, ");
		sb2.append("storageId, version, lastPublishDate) values(?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			while (rs.next()) {
				String uuid = PortalUUIDUtil.generate();
				long recordId = rs.getLong("recordId");
				long groupId = rs.getLong("groupId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long recordSetId = rs.getLong("recordSetId");

				ps2.setString(1, uuid);
				ps2.setLong(2, recordId);
				ps2.setLong(3, groupId);
				ps2.setLong(4, rs.getLong("companyId"));
				ps2.setLong(5, userId);
				ps2.setString(6, rs.getString("userName"));
				ps2.setLong(7, rs.getLong("versionUserId"));
				ps2.setString(8, rs.getString("versionUserName"));
				ps2.setTimestamp(9, createDate);
				ps2.setTimestamp(10, modifiedDate);

				ps2.setLong(11, recordSetId);
				ps2.setString(12, rs.getString("formInstanceVersion"));
				ps2.setLong(13, rs.getLong("DDMStorageId"));
				ps2.setString(14, rs.getString("version"));
				ps2.setTimestamp(15, rs.getTimestamp("lastPublishDate"));

				deleteDDLRecord(recordId);

				addAssetEntry(
					uuid, recordId, groupId, userId, createDate, modifiedDate,
					recordSetId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected ResourceBundle getResourceBundle(Locale defaultLocale) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		return portalResourceBundleLoader.loadResourceBundle(defaultLocale);
	}

	private final AssetEntryLocalService _assetEntryLocalService;

}