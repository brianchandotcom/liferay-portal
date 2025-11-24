/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.search.spi.model.index.contributor;

import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "indexer.class.name=com.liferay.exportimport.report.model.ExportImportReportEntry",
	service = ModelDocumentContributor.class
)
public class ExportImportReportEntryModelDocumentContributor
	implements ModelDocumentContributor<ExportImportReportEntry> {

	@Override
	public void contribute(
		Document document, ExportImportReportEntry exportImportReportEntry) {

		document.addKeyword(
			Field.COMPANY_ID, exportImportReportEntry.getCompanyId());
		document.addDate(
			Field.CREATE_DATE, exportImportReportEntry.getCreateDate());
		document.addDate(
			Field.MODIFIED_DATE, exportImportReportEntry.getModifiedDate());
		document.addText(
			"errorMessage", exportImportReportEntry.getErrorMessage());
		document.addText(
			"errorStacktrace", exportImportReportEntry.getErrorStacktrace());
		document.addNumber(
			"exportImportConfigurationId_long",
			exportImportReportEntry.getExportImportConfigurationId());
		document.addLocalizedText(
			"modelName", _getModelNameMap(exportImportReportEntry), true);
		document.addNumber(
			"origin_integer", exportImportReportEntry.getOrigin());
		document.addNumber("status", exportImportReportEntry.getStatus());
		document.addNumber("type_integer", exportImportReportEntry.getType());
	}

	private Map<Locale, String> _getModelNameMap(
		ExportImportReportEntry exportImportReportEntry) {

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : _language.getAvailableLocales()) {
			map.put(
				locale,
				_language.get(
					locale, exportImportReportEntry.getModelNameLanguageKey()));
		}

		return map;
	}

	@Reference
	private Language _language;

}