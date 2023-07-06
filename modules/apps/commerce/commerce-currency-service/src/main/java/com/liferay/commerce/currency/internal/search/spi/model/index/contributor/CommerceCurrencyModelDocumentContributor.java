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

package com.liferay.commerce.currency.internal.search.spi.model.index.contributor;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.currency.model.CommerceCurrency",
	service = ModelDocumentContributor.class
)
public class CommerceCurrencyModelDocumentContributor
	implements ModelDocumentContributor<CommerceCurrency> {

	@Override
	public void contribute(
		Document document, CommerceCurrency commerceCurrency) {

		document.addText(
			CPField.ACTIVE, String.valueOf(commerceCurrency.isActive()));

		document.addText(CPField.CODE, commerceCurrency.getCode());

		document.addText(Field.NAME, commerceCurrency.getName());

		document.addNumberSortable(
			Field.PRIORITY, commerceCurrency.getPriority());

		String commerceCurrencyDefaultLanguageId =
			_localization.getDefaultLanguageId(commerceCurrency.getName());

		String[] languageIds = _localization.getAvailableLanguageIds(
			commerceCurrency.getName());

		for (String languageId : languageIds) {
			String name = commerceCurrency.getName(languageId);

			document.addText(Field.CONTENT, name);

			document.addText(
				_localization.getLocalizedName(Field.NAME, languageId), name);

			if (languageId.equals(commerceCurrencyDefaultLanguageId)) {
				document.addText("defaultLanguageId", languageId);
			}
		}
	}

	@Reference
	private Localization _localization;

}