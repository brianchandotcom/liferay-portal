/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.empty.model.listener;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Javier Moreno Lage
 */
public abstract class EmptyModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public void solveEmptyExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId) {

		ExportImportReportEntry exportImportReportEntry =
			exportImportReportEntryLocalService.
				fetchEmptyExportImportReportEntryByG_C_C_C(
					groupId, companyId, classExternalReferenceCode,
					classNameId);

		if (exportImportReportEntry == null) {
			return;
		}

		if (ExportImportThreadLocal.isImportInProcess() &&
			(ExportImportThreadLocal.getExportImportConfigurationId() ==
				exportImportReportEntry.getExportImportConfigurationId())) {

			exportImportReportEntryLocalService.deleteExportImportReportEntry(
				exportImportReportEntry);
		}
		else {
			exportImportReportEntry.setStatus(1);

			exportImportReportEntryLocalService.updateExportImportReportEntry(
				exportImportReportEntry);
		}
	}

	@Reference
	protected ExportImportReportEntryLocalService
		exportImportReportEntryLocalService;

}