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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscountRel-Sku",
	service = {DiscountSkuDTOConverter.class, DTOConverter.class}
)
public class DiscountSkuDTOConverter
	implements DTOConverter<CommerceDiscountRel, DiscountSku> {

	@Override
	public String getContentType() {
		return DiscountSku.class.getSimpleName();
	}

	@Override
	public DiscountSku toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			_commerceDiscountRelService.getCommerceDiscountRel(
				(Long)dtoConverterContext.getId());

		CommerceDiscount commerceDiscount =
			commerceDiscountRel.getCommerceDiscount();

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			commerceDiscountRel.getClassPK());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		return new DiscountSku() {
			{
				actions = dtoConverterContext.getActions();
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
				discountSkuId = commerceDiscountRel.getCommerceDiscountRelId();
				productId = cpDefinition.getCPDefinitionId();
				productName = LanguageUtils.getLanguageIdMap(
					cpDefinition.getNameMap());
				skuExternalReferenceCode =
					cpInstance.getExternalReferenceCode();
				skuId = cpInstance.getCPInstanceId();
			}
		};
	}

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CPInstanceService _cpInstanceService;

}