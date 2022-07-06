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

package com.liferay.journal.internal.upgrade.v4_3_1;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Georgel Pop
 */
public class BasicWebContentAssetEntryClassTypeIdUpgradeProcess
	extends UpgradeProcess {

	public BasicWebContentAssetEntryClassTypeIdUpgradeProcess(
		AssetEntryLocalService assetEntryLocalService,
		CompanyLocalService companyLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
		_companyLocalService = companyLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateBasicWebContentAssetEntries();
	}

	private void _updateBasicWebContentAssetEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());

			_companyLocalService.forEachCompanyId(
				companyId -> _updateBasicWebContentAssetEntries(
					companyId, classNameId));
		}
	}

	private void _updateBasicWebContentAssetEntries(
			long companyId, long classNameId)
		throws Exception {

		Group companyGroup = _groupLocalService.getCompanyGroup(companyId);
		String structureKey = "BASIC-WEB-CONTENT";

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			companyGroup.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class.getName()),
			structureKey);

		if (ddmStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"No DDMStructure with structure key ", structureKey,
						" found in Global site", companyGroup.getGroupId(),
						" for companyId ", companyId));
			}

			return;
		}

		long basicWebContentStructureId = ddmStructure.getStructureId();

		if (hasColumnType(
				AssetEntryTable.INSTANCE.getName(), "classTypeId",
				"LONG null") &&
			hasColumnType(
				AssetEntryTable.INSTANCE.getName(), "companyId", "LONG null") &&
			hasColumnType(
				AssetEntryTable.INSTANCE.getName(), "classNameId",
				"LONG null")) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update AssetEntry set classTypeId = ? where ",
							"classTypeId = ? and companyId = ? and ",
							"classNameId = ?"))) {

				preparedStatement.setLong(1, basicWebContentStructureId);
				preparedStatement.setLong(2, 0);
				preparedStatement.setLong(3, companyId);
				preparedStatement.setLong(4, classNameId);

				preparedStatement.executeUpdate();
			}
			catch (SQLException sqlException) {
				if (_log.isWarnEnabled()) {
					_log.warn(sqlException);
				}
			}
		}
		else {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select resourcePrimKey, indexable from ",
							"JournalArticle where companyId = ", companyId,
							" and ddmtemplatekey = 'BASIC-WEB-CONTENT' "));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long resourcePrimKey = resultSet.getLong("resourcePrimKey");

					AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
						JournalArticle.class.getName(), resourcePrimKey);

					if (assetEntry == null) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								StringBundler.concat(
									"Journal article with resource ",
									"primary key ", resourcePrimKey,
									" does not have associated asset ",
									"entry"));
						}

						continue;
					}

					long classTypeId = assetEntry.getClassTypeId();

					if (classTypeId != basicWebContentStructureId) {
						assetEntry.setClassTypeId(basicWebContentStructureId);

						_assetEntryLocalService.updateAssetEntry(assetEntry);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicWebContentAssetEntryClassTypeIdUpgradeProcess.class);

	private final AssetEntryLocalService _assetEntryLocalService;
	private final CompanyLocalService _companyLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;

}