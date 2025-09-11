/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.exportimport.report.helper;

import java.util.List;

/**
 * @author Stefano Motta
 */
public interface ExportImportReportEntryHelper {

	public Object addErrorExportImportReportEntry(
		long groupId, long companyId, String classExternalReferenceCode,
		long classNameId, long classPK, long exportImportConfigurationId,
		String error, String errorStacktrace, String modelName, int origin,
		String scope, String scopeKey);

	public List<Object> getExportImportReportEntries(
		long companyId, long exportImportConfigurationId);

}