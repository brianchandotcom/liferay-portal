/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
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
	property = "indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
	service = ModelDocumentContributor.class
)
public class CSDiagramEntryModelDocumentContributor
	implements ModelDocumentContributor<CSDiagramEntry> {

	@Override
	public void contribute(Document document, CSDiagramEntry csDiagramEntry) {
		document.addNumber(
			CPField.CP_DEFINITION_ID, csDiagramEntry.getCPDefinitionId());
		document.addText(CPField.SKU, csDiagramEntry.getSku());
		document.addNumber("quantity", csDiagramEntry.getQuantity());
		document.addText("sequence", csDiagramEntry.getSequence());

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(
				csDiagramEntry.getCProductId());

		if (cpDefinition == null) {
			return;
		}

		List<String> languageIds =
			_cpDefinitionLocalService.getCPDefinitionLocalizationLanguageIds(
				cpDefinition.getCPDefinitionId());

		for (String languageId : languageIds) {
			String shortDescription = cpDefinition.getShortDescription(
				languageId);

			String description = cpDefinition.getDescription(languageId);
			String name = cpDefinition.getName(languageId);

			document.addText(
				_localization.getLocalizedName(
					CPField.SHORT_DESCRIPTION, languageId),
				shortDescription);
			document.addText(
				_localization.getLocalizedName(Field.DESCRIPTION, languageId),
				description);
			document.addText(
				_localization.getLocalizedName(Field.NAME, languageId), name);
		}

		String cpDefinitionDefaultLanguageId =
			_localization.getDefaultLanguageId(cpDefinition.getName());

		document.addText(
			CPField.SHORT_DESCRIPTION,
			cpDefinition.getShortDescription(cpDefinitionDefaultLanguageId));
		document.addText(
			Field.DESCRIPTION,
			cpDefinition.getDescription(cpDefinitionDefaultLanguageId));
		document.addText(
			Field.NAME, cpDefinition.getName(cpDefinitionDefaultLanguageId));
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Localization _localization;

}