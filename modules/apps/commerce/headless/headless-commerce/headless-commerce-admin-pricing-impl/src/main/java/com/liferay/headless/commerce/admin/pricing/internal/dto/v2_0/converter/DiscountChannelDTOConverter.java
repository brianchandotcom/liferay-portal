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
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceChannelRel",
	service = {DiscountChannelDTOConverter.class, DTOConverter.class}
)
public class DiscountChannelDTOConverter
	implements DTOConverter<CommerceChannelRel, DiscountChannel> {

	@Override
	public String getContentType() {
		return DiscountChannel.class.getSimpleName();
	}

	@Override
	public DiscountChannel toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceChannelRel commerceDiscountChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(
				(Long)dtoConverterContext.getId());

		CommerceChannel commerceChannel =
			commerceDiscountChannelRel.getCommerceChannel();

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.getCommerceDiscount(
				commerceDiscountChannelRel.getClassPK());

		return new DiscountChannel() {
			{
				actions = dtoConverterContext.getActions();
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				discountChannelId =
					commerceDiscountChannelRel.getCommerceChannelRelId();
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
			}
		};
	}

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

}