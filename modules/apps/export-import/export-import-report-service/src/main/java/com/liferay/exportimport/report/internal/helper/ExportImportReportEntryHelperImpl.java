/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.helper;

import com.liferay.exportimport.report.service.ExportImportReportEntryLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.exportimport.report.helper.ExportImportReportEntryHelper;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(service = ExportImportReportEntryHelper.class)
public class ExportImportReportEntryHelperImpl
	implements ExportImportReportEntryHelper {

	public Object addErrorExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId, long classPK, long exportImportConfigurationId,
		String error, String errorStacktrace, String modelName, int origin,
		String scope, String scopeKey) {

		return ExportImportReportEntryLocalServiceUtil.
			addErrorExportImportReportEntry(
				groupId, companyId, classExternalReferenceCode, classNameId,
				classPK, exportImportConfigurationId, error, errorStacktrace,
				modelName, origin, scope, scopeKey);
	}

	public List<Object> getExportImportReportEntries(
		long companyId, long exportImportConfigurationId) {

		return ListUtil.fromCollection(
			ExportImportReportEntryLocalServiceUtil.
				getExportImportReportEntries(
					companyId, exportImportConfigurationId));
	}

}