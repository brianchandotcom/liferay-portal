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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.RelatedProduct;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false, property = "dto.class.name=CPDefinitionLink",
	service = {DTOConverter.class, RelatedProductDTOConverter.class}
)
public class RelatedProductDTOConverter
	implements DTOConverter<CPDefinitionLink, RelatedProduct> {

	@Override
	public String getContentType() {
		return RelatedProduct.class.getSimpleName();
	}

	@Override
	public RelatedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionLink cpDefinitionLink =
			_cpDefinitionLinkLocalService.getCPDefinitionLink(
				(Long)dtoConverterContext.getId());

		CProduct cProduct = cpDefinitionLink.getCProduct();

		return new RelatedProduct() {
			{
				id = cpDefinitionLink.getCPDefinitionLinkId();
				priority = cpDefinitionLink.getPriority();
				productId = cProduct.getCProductId();
				type = cpDefinitionLink.getType();
			}
		};
	}

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

}