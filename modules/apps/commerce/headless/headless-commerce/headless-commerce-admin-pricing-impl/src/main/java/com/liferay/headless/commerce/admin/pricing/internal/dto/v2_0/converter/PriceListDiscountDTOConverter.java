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
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.commerce.price.list.service.CommercePriceListDiscountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListDiscount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceListDiscountRel",
	service = {DTOConverter.class, PriceListDiscountDTOConverter.class}
)
public class PriceListDiscountDTOConverter
	implements DTOConverter<CommercePriceListDiscountRel, PriceListDiscount> {

	@Override
	public String getContentType() {
		return PriceListDiscount.class.getSimpleName();
	}

	@Override
	public PriceListDiscount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceListDiscountRel commercePriceListDiscountRel =
			_commercePriceListDiscountRelService.
				getCommercePriceListDiscountRel(
					(Long)dtoConverterContext.getId());

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.getCommerceDiscount(
				commercePriceListDiscountRel.getCommerceDiscountId());
		CommercePriceList commercePriceList =
			commercePriceListDiscountRel.getCommercePriceList();

		return new PriceListDiscount() {
			{
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
				discountName = commerceDiscount.getTitle();
				order = commercePriceListDiscountRel.getOrder();
				priceListDiscountId =
					commercePriceListDiscountRel.
						getCommercePriceListDiscountRelId();
				priceListExternalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				priceListId = commercePriceList.getCommercePriceListId();
			}
		};
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommercePriceListDiscountRelService
		_commercePriceListDiscountRelService;

}