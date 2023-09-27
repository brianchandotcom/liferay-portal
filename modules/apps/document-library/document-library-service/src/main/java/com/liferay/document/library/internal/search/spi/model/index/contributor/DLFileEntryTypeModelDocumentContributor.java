/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search.spi.model.index.contributor;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntryType",
	service = ModelDocumentContributor.class
)
public class DLFileEntryTypeModelDocumentContributor
	implements ModelDocumentContributor<DLFileEntryType> {

	@Override
	public void contribute(Document document, DLFileEntryType dlFileEntryType) {
		document.addLocalizedText(
			Field.DESCRIPTION,
			_localization.populateLocalizationMap(
				dlFileEntryType.getDescriptionMap(),
				dlFileEntryType.getDefaultLanguageId(),
				dlFileEntryType.getGroupId()));
		document.addLocalizedText(
			Field.NAME,
			_localization.populateLocalizationMap(
				dlFileEntryType.getNameMap(),
				dlFileEntryType.getDefaultLanguageId(),
				dlFileEntryType.getGroupId()));
		document.addLocalizedKeyword(
			"localized_name",
			_localization.populateLocalizationMap(
				dlFileEntryType.getNameMap(),
				dlFileEntryType.getDefaultLanguageId(),
				dlFileEntryType.getGroupId()),
			true, true);
	}

	@Reference
	private Localization _localization;

}