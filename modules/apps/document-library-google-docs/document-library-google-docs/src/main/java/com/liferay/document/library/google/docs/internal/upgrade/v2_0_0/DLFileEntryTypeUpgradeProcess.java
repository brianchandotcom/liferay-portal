/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class DLFileEntryTypeUpgradeProcess extends UpgradeProcess {

	public DLFileEntryTypeUpgradeProcess(
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
			actionableDynamicQuery.setPerformActionMethod(
				(DLFileEntryType dlFileEntryType) -> {
					dlFileEntryType.setScope(
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_SYSTEM);

					_dlFileEntryTypeLocalService.updateDLFileEntryType(
						dlFileEntryType);

					DDMStructure ddmStructure =
						_ddmStructureLocalService.getStructure(
							dlFileEntryType.getGroupId(),
							_classNameLocalService.getClassNameId(
								DLFileEntryMetadata.class),
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