/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.search.spi.model.index.contributor;

import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.exportimport.report.service.ExportImportReportEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Petteri Karttunen
 */
public class ExportImportReportEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ExportImportReportEntry> {

	public ExportImportReportEntryModelIndexerWriterContributor(
		ExportImportReportEntryLocalService
			exportImportReportEntryLocalService) {

		_exportImportReportEntryLocalService =
			exportImportReportEntryLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _exportImportReportEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final ExportImportReportEntryLocalService
		_exportImportReportEntryLocalService;

}