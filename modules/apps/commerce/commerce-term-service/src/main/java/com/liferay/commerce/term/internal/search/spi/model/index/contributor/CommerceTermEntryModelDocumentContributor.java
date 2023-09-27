/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.search.spi.model.index.contributor;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = ModelDocumentContributor.class
)
public class CommerceTermEntryModelDocumentContributor
	implements ModelDocumentContributor<CommerceTermEntry> {

	@Override
	public void contribute(
		Document document, CommerceTermEntry commerceTermEntry) {

		document.addKeyword(Field.NAME, commerceTermEntry.getName());
		document.addNumberSortable(
			Field.PRIORITY, commerceTermEntry.getPriority());
		document.addText(Field.TYPE, commerceTermEntry.getType());

		List<String> languageIds =
			_commerceTermEntryLocalService.getCTermEntryLocalizationLanguageIds(
				commerceTermEntry.getCommerceTermEntryId());

		for (String languageId : languageIds) {
			document.addKeywordSortable(
				_localization.getLocalizedName("label", languageId),
				commerceTermEntry.getLabel(languageId));
		}
	}

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Reference
	private Localization _localization;

}