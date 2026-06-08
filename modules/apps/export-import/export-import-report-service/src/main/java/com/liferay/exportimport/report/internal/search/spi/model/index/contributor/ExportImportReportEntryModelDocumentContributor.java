/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.internal.search.spi.model.index.contributor;

import com.liferay.exportimport.report.constants.ExportImportReportEntryConstants;
import com.liferay.exportimport.report.model.ExportImportReportEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

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

		document.setSortableTextFields(
			ArrayUtil.append(
				PropsUtil.getArray(PropsKeys.INDEX_SORTABLE_TEXT_FIELDS),
				new String[] {"classExternalReferenceCode"}));

		document.addKeyword(
			Field.COMPANY_ID, exportImportReportEntry.getCompanyId());
		document.addDate(
			Field.CREATE_DATE, exportImportReportEntry.getCreateDate());
		document.addDate(
			Field.MODIFIED_DATE, exportImportReportEntry.getModifiedDate());
		document.addKeyword(
			"classExternalReferenceCode",
			exportImportReportEntry.getClassExternalReferenceCode(), true);
		document.addText(
			"errorMessage", exportImportReportEntry.getErrorMessage());
		document.addText(
			"errorStacktrace", exportImportReportEntry.getErrorStacktrace());
		document.addNumber(
			"exportImportConfigurationId_long",
			exportImportReportEntry.getExportImportConfigurationId());
		document.addLocalizedText(
			"modelName",
			_localization.getLocalizationMap(
				_language.getAvailableLocales(), LocaleUtil.getDefault(),
				exportImportReportEntry.getModelNameLanguageKey()),
			true);
		document.addNumber(
			"origin_integer", exportImportReportEntry.getOrigin());
		document.addNumber("status", exportImportReportEntry.getStatus());
		document.addNumber("type_integer", exportImportReportEntry.getType());
		document.addLocalizedText(
			"type_label",
			_localization.getLocalizationMap(
				_language.getAvailableLocales(), LocaleUtil.getDefault(),
				ExportImportReportEntryConstants.getTypeLabel(
					exportImportReportEntry.getType())),
			true);
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}