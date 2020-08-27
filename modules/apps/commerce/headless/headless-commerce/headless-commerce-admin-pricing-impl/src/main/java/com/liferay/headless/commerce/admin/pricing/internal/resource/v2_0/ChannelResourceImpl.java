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

import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Channel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountChannel;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.ChannelDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ChannelResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/channel.properties",
	scope = ServiceScope.PROTOTYPE, service = ChannelResource.class
)
public class ChannelResourceImpl extends BaseChannelResourceImpl {

	@NestedField(parentClass = DiscountChannel.class, value = "channel")
	@Override
	public Channel getDiscountIdChannel(
			@NestedFieldId(value = "channelId") @NotNull Long id)
		throws Exception {

		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceListChannel.class, value = "channel")
	@Override
	public Channel getPriceListIdChannel(
			@NestedFieldId(value = "channelId") @NotNull Long id)
		throws Exception {

		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

}