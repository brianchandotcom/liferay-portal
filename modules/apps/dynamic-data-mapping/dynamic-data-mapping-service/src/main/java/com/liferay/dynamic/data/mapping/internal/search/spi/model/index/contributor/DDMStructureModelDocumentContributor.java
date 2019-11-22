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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.mapping.model.DDMStructure",
	service = ModelDocumentContributor.class
)
public class DDMStructureModelDocumentContributor
	implements ModelDocumentContributor<DDMStructure> {

	@Override
	public void contribute(Document document, DDMStructure ddmStructure) {
		document.addKeyword(Field.CLASS_NAME_ID, ddmStructure.getClassNameId());
		document.addLocalizedText(
			Field.DESCRIPTION,
			LocalizationUtil.populateLocalizationMap(
				ddmStructure.getDescriptionMap(),
				ddmStructure.getDefaultLanguageId(),
				ddmStructure.getGroupId()));
		document.addLocalizedText(
			Field.NAME,
			LocalizationUtil.populateLocalizationMap(
				ddmStructure.getNameMap(), ddmStructure.getDefaultLanguageId(),
				ddmStructure.getGroupId()));

		try {
			DDMStructureVersion structureVersion =
				ddmStructureVersionLocalService.getStructureVersion(
					ddmStructure.getStructureId(), ddmStructure.getVersion());

			document.addKeyword(Field.STATUS, structureVersion.getStatus());
			document.addKeyword(Field.VERSION, structureVersion.getVersion());
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		document.addKeyword("structureKey", ddmStructure.getStructureKey());
		document.addKeyword("storageType", ddmStructure.getStorageType());
		document.addKeyword("type", ddmStructure.getType());
		document.addLocalizedKeyword(
			"localized_name",
			LocalizationUtil.populateLocalizationMap(
				ddmStructure.getNameMap(), ddmStructure.getDefaultLanguageId(),
				ddmStructure.getGroupId()),
			true, true);
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
	protected DDMStructureVersionLocalService ddmStructureVersionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureModelDocumentContributor.class);

}