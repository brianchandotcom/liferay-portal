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

package com.liferay.commerce.product.type.grouped.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = ModelDocumentContributor.class
)
public class CPDefinitionGroupedEntryModelDocumentContributor
	implements ModelDocumentContributor<CPDefinitionGroupedEntry> {

	@Override
	public void contribute(
		Document document, CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

		try {
			document.addNumberSortable(
				Field.ENTRY_CLASS_PK,
				cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId());

			CPDefinition cpDefinition =
				cpDefinitionGroupedEntry.getCPDefinition();

			document.addNumber(
				"cpDefinitionId", cpDefinition.getCPDefinitionId());

			CPDefinition entryCPDefinition =
				cpDefinitionGroupedEntry.getEntryCPDefinition();

			List<String> languageIds =
				_cpDefinitionLocalService.
					getCPDefinitionLocalizationLanguageIds(
						entryCPDefinition.getCPDefinitionId());

			for (String languageId : languageIds) {
				String name = entryCPDefinition.getName(languageId);

				document.addText(
					_localization.getLocalizedName(
						"entryCPDefinitionName", languageId),
					name);
			}

			String cpDefinitionDefaultLanguageId =
				_localization.getDefaultLanguageId(entryCPDefinition.getName());

			document.addText(
				"entryCPDefinitionName",
				entryCPDefinition.getName(cpDefinitionDefaultLanguageId));

			document.addNumberSortable(
				"priority", cpDefinitionGroupedEntry.getPriority());
			document.addNumberSortable(
				"quantity", cpDefinitionGroupedEntry.getQuantity());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				long cpDefinitionGroupedEntryId =
					cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId();

				_log.warn(
					"Unable to index commerce product definition grouped " +
						"entry " + cpDefinitionGroupedEntryId,
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionGroupedEntryModelDocumentContributor.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}