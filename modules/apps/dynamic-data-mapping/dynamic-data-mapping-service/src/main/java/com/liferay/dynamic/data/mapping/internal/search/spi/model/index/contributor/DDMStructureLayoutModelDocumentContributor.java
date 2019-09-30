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

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcelo Mello
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMStructureLayout",
	service = ModelDocumentContributor.class
)
public class DDMStructureLayoutModelDocumentContributor
	implements ModelDocumentContributor<DDMStructureLayout> {

	@Override
	public void contribute(
		Document document, DDMStructureLayout ddmStructureLayout) {

		document.addKeyword(
			Field.CLASS_NAME_ID,
			classNameLocalService.getClassNameId(DDMStructureLayout.class));
		document.addLocalizedText(
			Field.DESCRIPTION,
			LocalizationUtil.populateLocalizationMap(
				ddmStructureLayout.getDescriptionMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()));
		document.addLocalizedText(
			Field.NAME,
			LocalizationUtil.populateLocalizationMap(
				ddmStructureLayout.getNameMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()));
		document.addLocalizedKeyword(
			"localized_name",
			LocalizationUtil.populateLocalizationMap(
				ddmStructureLayout.getNameMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()),
			true, true);
		document.addKeyword(
			"structureVersionId", ddmStructureLayout.getStructureVersionId());
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

}