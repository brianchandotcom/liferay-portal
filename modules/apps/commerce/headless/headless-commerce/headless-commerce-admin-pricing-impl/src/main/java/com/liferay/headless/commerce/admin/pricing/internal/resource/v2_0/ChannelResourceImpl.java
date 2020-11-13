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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.service.CommercePriceListChannelRelService;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Channel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.ChannelDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ChannelResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {ChannelResource.class, NestedFieldSupport.class}
)
public class ChannelResourceImpl
	extends BaseChannelResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = DiscountChannel.class, value = "channel")
	@Override
	public Channel getDiscountChannelChannel(@NotNull Long id)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelService.getCommerceChannelRel(id);

		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceChannelRel.getCommerceChannelId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceListChannel.class, value = "channel")
	@Override
	public Channel getPriceListChannelChannel(@NotNull Long id)
		throws Exception {

		CommercePriceListChannelRel commercePriceListChannelRel =
			_commercePriceListChannelRelService.getCommercePriceListChannelRel(
				id);

		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListChannelRel.getCommerceChannelId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommercePriceListChannelRelService
		_commercePriceListChannelRelService;

}