/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.missing.reference;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.report.missing.reference.MissingReferenceManager;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = MissingReferenceManager.class)
public class MissingReferenceManagerImpl implements MissingReferenceManager {

	@Override
	public void reportMissingReference(
		String className, String externalReferenceCode, long groupId) {

		if (!ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		long exportImportConfigurationId = GetterUtil.getLong(
			ExportImportThreadLocal.getExportImportConfigurationId());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				fetchExportImportConfiguration(exportImportConfigurationId);

		if (exportImportConfiguration == null) {
			return;
		}

		_exportImportReportEntryLocalService.
			getOrAddMissingReferenceExportImportReportEntry(
				groupId, exportImportConfiguration.getCompanyId(),
				externalReferenceCode, _getClassNameId(className),
				exportImportConfigurationId, className);
	}

	private long _getClassNameId(String className) {
		ClassName className_ = _classNameLocalService.fetchClassName(className);

		if (className_ != null) {
			return className_.getClassNameId();
		}

		return 0;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}