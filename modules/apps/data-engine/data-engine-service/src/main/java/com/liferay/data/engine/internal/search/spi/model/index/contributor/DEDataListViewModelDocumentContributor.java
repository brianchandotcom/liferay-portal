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

package com.liferay.data.engine.internal.search.spi.model.index.contributor;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.data.engine.model.DEDataListView",
	service = ModelDocumentContributor.class
)
public class DEDataListViewModelDocumentContributor
	implements ModelDocumentContributor<DEDataListView> {

	@Override
	public void contribute(Document document, DEDataListView deDataListView) {
		document.addKeyword(
			"ddmStructureId", deDataListView.getDdmStructureId());

		String[] languageIds = getLanguageIds(
			deDataListView.getDefaultLanguageId(), deDataListView.getName());

		for (String languageId : languageIds) {
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				deDataListView.getName(languageId));
		}

		document.addLocalizedKeyword(
			"localized_name",
			LocalizationUtil.populateLocalizationMap(
				deDataListView.getNameMap(),
				deDataListView.getDefaultLanguageId(),
				deDataListView.getGroupId()),
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

}