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

package com.liferay.dynamic.data.lists.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet",
	service = ModelDocumentContributor.class
)
public class DDLRecordSetModelDocumentContributor
	implements ModelDocumentContributor<DDLRecordSet> {

	@Override
	public void contribute(Document document, DDLRecordSet ddlRecordSet) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		String[] descriptionLanguageIds = _getLanguageIds(
			defaultLanguageId, ddlRecordSet.getDescription());

		for (String descriptionLanguageId : descriptionLanguageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(
					Field.DESCRIPTION, descriptionLanguageId),
				ddlRecordSet.getDescription(descriptionLanguageId));
		}

		String[] nameLanguageIds = _getLanguageIds(
			defaultLanguageId, ddlRecordSet.getName());

		for (String nameLanguageId : nameLanguageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, nameLanguageId),
				ddlRecordSet.getName(nameLanguageId));
		}

		document.addKeyword(Field.VERSION, ddlRecordSet.getVersion());
		document.addKeyword("DDMStructureId", ddlRecordSet.getDDMStructureId());
		document.addKeyword("scope", ddlRecordSet.getScope());
	}

	@Reference
	protected ClassNameLocalService classNameLocalService;

	private String[] _getLanguageIds(String defaultLanguageId, String content) {
		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

}