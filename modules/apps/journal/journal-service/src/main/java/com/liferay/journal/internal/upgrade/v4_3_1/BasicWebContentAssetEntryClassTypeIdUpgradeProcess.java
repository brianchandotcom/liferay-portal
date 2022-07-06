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
import java.sql.SQLException;

/**
 * @author Georgel Pop
 */
public class BasicWebContentAssetEntryClassTypeIdUpgradeProcess
	extends UpgradeProcess {

	public BasicWebContentAssetEntryClassTypeIdUpgradeProcess(
		CompanyLocalService companyLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		GroupLocalService groupLocalService) {

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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update AssetEntry set classTypeId = ? where classNameId = ? " +
					"and companyId = ? and classTypeId = ?")) {

			preparedStatement.setLong(1, ddmStructure.getStructureId());
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setLong(4, 0);

			preparedStatement.executeUpdate();
		}
		catch (SQLException sqlException) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqlException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicWebContentAssetEntryClassTypeIdUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final GroupLocalService _groupLocalService;

}