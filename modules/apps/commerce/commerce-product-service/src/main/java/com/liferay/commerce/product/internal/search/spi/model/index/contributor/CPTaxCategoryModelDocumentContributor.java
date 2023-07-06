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

package com.liferay.commerce.product.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPTaxCategory;
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
	property = "indexer.class.name=com.liferay.commerce.product.model.CPTaxCategory",
	service = ModelDocumentContributor.class
)
public class CPTaxCategoryModelDocumentContributor
	implements ModelDocumentContributor<CPTaxCategory> {

	@Override
	public void contribute(Document document, CPTaxCategory cpTaxCategory) {
		document.addText(Field.DESCRIPTION, cpTaxCategory.getDescription());

		document.addText(Field.NAME, cpTaxCategory.getName());

		document.addDateSortable(
			Field.CREATE_DATE, cpTaxCategory.getCreateDate());

		String cpTaxCategoryDefaultLanguageId =
			_localization.getDefaultLanguageId(cpTaxCategory.getName());

		String[] languageIds = _localization.getAvailableLanguageIds(
			cpTaxCategory.getName());

		for (String languageId : languageIds) {
			String name = cpTaxCategory.getName(languageId);

			String description = cpTaxCategory.getDescription(languageId);

			document.addText(Field.CONTENT, description);

			document.addText(
				_localization.getLocalizedName(Field.NAME, languageId), name);

			document.addText(
				_localization.getLocalizedName(Field.DESCRIPTION, languageId),
				description);

			if (languageId.equals(cpTaxCategoryDefaultLanguageId)) {
				document.addText("defaultLanguageId", languageId);
			}
		}
	}

	@Reference
	private Localization _localization;

}