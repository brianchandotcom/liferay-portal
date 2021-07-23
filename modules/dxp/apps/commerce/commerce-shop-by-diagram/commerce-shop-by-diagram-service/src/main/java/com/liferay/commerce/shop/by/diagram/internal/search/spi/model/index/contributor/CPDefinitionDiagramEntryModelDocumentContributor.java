/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.index.contributor;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "indexer.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry",
	service = ModelDocumentContributor.class
)
public class CPDefinitionDiagramEntryModelDocumentContributor
	implements ModelDocumentContributor<CPDefinitionDiagramEntry> {

	public static final String FIELD_NUMBER = "number";

	public static final String FIELD_QUANTITY = "quantity";

	@Override
	public void contribute(
		Document document, CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		document.addNumber(FIELD_NUMBER, cpDefinitionDiagramEntry.getNumber());
		document.addText(CPField.SKU, cpDefinitionDiagramEntry.getSku());
		document.addNumber(
			FIELD_QUANTITY, cpDefinitionDiagramEntry.getQuantity());
	}

}