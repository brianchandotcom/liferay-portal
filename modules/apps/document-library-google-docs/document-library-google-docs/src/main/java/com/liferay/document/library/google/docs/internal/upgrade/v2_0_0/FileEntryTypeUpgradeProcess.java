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

package com.liferay.document.library.google.docs.internal.upgrade.v2_0_0;

import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alejandro Tardín
 */
public class FileEntryTypeUpgradeProcess extends UpgradeProcess {

	public FileEntryTypeUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_classNameLocalService = classNameLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_dlFileEntryTypeLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"fileEntryTypeKey",
						GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY)));

			long dlFileEntryMetadataClassNameId =
				_classNameLocalService.getClassNameId(
					DLFileEntryMetadata.class);

			actionableDynamicQuery.setPerformActionMethod(
				(DLFileEntryType dlFileEntryType) -> {
					dlFileEntryType.setScope(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_SYSTEM);

					_dlFileEntryTypeLocalService.updateDLFileEntryType(
						dlFileEntryType);

					DDMStructure ddmStructure =
						_ddmStructureLocalService.getStructure(
							dlFileEntryType.getGroupId(),
							dlFileEntryMetadataClassNameId,
							GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);

					ddmStructure.setType(DDMStructureConstants.TYPE_AUTO);

					_ddmStructureLocalService.updateDDMStructure(ddmStructure);
				});

			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new UpgradeException(portalException);
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}