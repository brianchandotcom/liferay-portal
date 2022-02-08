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

package com.liferay.commerce.term.internal.search.spi.model.index.contributor;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
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
				LocalizationUtil.getLocalizedName("label", languageId),
				commerceTermEntry.getLabel(languageId));
		}
	}

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

}