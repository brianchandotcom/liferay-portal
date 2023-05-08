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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramCPTypeConstants;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.LinkedProduct;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = DTOConverter.class
)
public class LinkedProductDTOConverter
	implements DTOConverter<CPDefinitionGroupedEntry, LinkedProduct> {

	@Override
	public String getContentType() {
		return LinkedProduct.class.getSimpleName();
	}

	@Override
	public LinkedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		LinkedProductDTOConverterContext linkedProductDTOConverterContext =
			(LinkedProductDTOConverterContext)dtoConverterContext;

		CPDefinition cpDefinition = _getCPDefinition(
			(Long)linkedProductDTOConverterContext.getId(),
			linkedProductDTOConverterContext.getProductTypeName());

		CProduct cProduct = cpDefinition.getCProduct();

		return new LinkedProduct() {
			{
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
				type = cpDefinition.getProductTypeName();
			}
		};
	}

	private CPDefinition _getCPDefinition(long id, String productTypeName)
		throws Exception {

		if (CSDiagramCPTypeConstants.NAME.equals(productTypeName)) {
			CSDiagramEntry csDiagramEntry =
				_csDiagramEntryService.getCSDiagramEntry(id);

			return _cpDefinitionService.getCPDefinition(
				csDiagramEntry.getCPDefinitionId());
		}
		else if (GroupedCPTypeConstants.NAME.equals(productTypeName)) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
					id);

			return _cpDefinitionService.getCPDefinition(
				cpDefinitionGroupedEntry.getCPDefinitionId());
		}

		throw new CPDefinitionProductTypeNameException();
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

}