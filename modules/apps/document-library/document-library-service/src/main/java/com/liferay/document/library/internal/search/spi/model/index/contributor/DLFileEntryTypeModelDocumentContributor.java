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