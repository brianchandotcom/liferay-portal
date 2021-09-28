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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceListChannelRel",
	service = {DTOConverter.class, PriceListChannelDTOConverter.class}
)
public class PriceListChannelDTOConverter
	implements DTOConverter<CommercePriceListChannelRel, PriceListChannel> {

	@Override
	public String getContentType() {
		return PriceListChannel.class.getSimpleName();
	}

	@Override
	public PriceListChannel toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceListChannelRel commercePriceListChannelRel =
			_commercePriceListChannelRelService.getCommercePriceListChannelRel(
				(Long)dtoConverterContext.getId());

		CommerceChannel commerceChannel =
			commercePriceListChannelRel.getCommerceChannel();
		CommercePriceList commercePriceList =
			commercePriceListChannelRel.getCommercePriceList();

		return new PriceListChannel() {
			{
				actions = dtoConverterContext.getActions();
				channelExternalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				channelId = commerceChannel.getCommerceChannelId();
				order = commercePriceListChannelRel.getOrder();
				priceListChannelId =
					commercePriceListChannelRel.
						getCommercePriceListChannelRelId();
				priceListExternalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				priceListId = commercePriceList.getCommercePriceListId();
			}
		};
	}

	@Reference
	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

}