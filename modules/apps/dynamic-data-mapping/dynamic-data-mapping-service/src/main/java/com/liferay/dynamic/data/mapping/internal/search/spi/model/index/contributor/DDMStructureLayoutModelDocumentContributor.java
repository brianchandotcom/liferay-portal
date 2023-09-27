/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcelo Mello
 */
@Component(
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
			_localization.populateLocalizationMap(
				ddmStructureLayout.getDescriptionMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()));
		document.addLocalizedText(
			Field.NAME,
			_localization.populateLocalizationMap(
				ddmStructureLayout.getNameMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()));
		document.addLocalizedKeyword(
			"localized_name",
			_localization.populateLocalizationMap(
				ddmStructureLayout.getNameMap(),
				ddmStructureLayout.getDefaultLanguageId(),
				ddmStructureLayout.getGroupId()),
			true, true);
		document.addKeyword(
			"structureVersionId", ddmStructureLayout.getStructureVersionId());
	}

	protected String[] getLanguageIds(
		String defaultLanguageId, String content) {

		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	@Reference
	private Localization _localization;

}