/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.internal.search.spi.model.index.contributor;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	property = "indexer.class.name=com.liferay.data.engine.model.DEDataListView",
	service = ModelDocumentContributor.class
)
public class DEDataListViewModelDocumentContributor
	implements ModelDocumentContributor<DEDataListView> {

	@Override
	public void contribute(Document document, DEDataListView deDataListView) {
		document.addKeyword(
			"ddmStructureId", deDataListView.getDdmStructureId());

		String[] languageIds = _getLanguageIds(
			deDataListView.getDefaultLanguageId(), deDataListView.getName());

		for (String languageId : languageIds) {
			document.addText(
				_localization.getLocalizedName(Field.NAME, languageId),
				deDataListView.getName(languageId));
		}

		document.addLocalizedKeyword(
			"localized_name",
			_localization.populateLocalizationMap(
				deDataListView.getNameMap(),
				deDataListView.getDefaultLanguageId(),
				deDataListView.getGroupId()),
			true, true);
	}

	private String[] _getLanguageIds(String defaultLanguageId, String content) {
		String[] languageIds = _localization.getAvailableLanguageIds(content);

		if (languageIds.length == 0) {
			languageIds = new String[] {defaultLanguageId};
		}

		return languageIds;
	}

	@Reference
	private Localization _localization;

}