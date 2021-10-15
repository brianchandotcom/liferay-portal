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

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
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
	}

}