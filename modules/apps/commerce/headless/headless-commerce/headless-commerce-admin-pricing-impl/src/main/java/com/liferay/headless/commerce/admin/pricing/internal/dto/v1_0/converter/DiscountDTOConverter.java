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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter;

import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.Discount;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscount",
	service = {DiscountDTOConverter.class, DTOConverter.class}
)
public class DiscountDTOConverter
	implements DTOConverter<CommerceDiscount, Discount> {

	@Override
	public String getContentType() {
		return Discount.class.getSimpleName();
	}

	@Override
	public Discount toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.getCommerceDiscount(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = commerceDiscount.getExpandoBridge();

		return new Discount() {
			{
				active = commerceDiscount.isActive();
				couponCode = commerceDiscount.getCouponCode();
				customFields = expandoBridge.getAttributes();
				displayDate = commerceDiscount.getDisplayDate();
				expirationDate = commerceDiscount.getExpirationDate();
				externalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				id = commerceDiscount.getCommerceDiscountId();
				limitationTimes = commerceDiscount.getLimitationTimes();
				limitationType = commerceDiscount.getLimitationType();
				maximumDiscountAmount =
					commerceDiscount.getMaximumDiscountAmount();
				numberOfUse = commerceDiscount.getNumberOfUse();
				percentageLevel1 = commerceDiscount.getLevel1();
				percentageLevel2 = commerceDiscount.getLevel2();
				percentageLevel3 = commerceDiscount.getLevel3();
				percentageLevel4 = commerceDiscount.getLevel4();
				target = commerceDiscount.getTarget();
				title = commerceDiscount.getTitle();
				useCouponCode = commerceDiscount.isUseCouponCode();
				usePercentage = commerceDiscount.isUsePercentage();
			}
		};
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

}