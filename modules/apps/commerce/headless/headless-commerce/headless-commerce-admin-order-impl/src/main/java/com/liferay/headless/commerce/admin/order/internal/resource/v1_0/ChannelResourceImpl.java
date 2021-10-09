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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeRelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderTypeChannel;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.ChannelDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ChannelResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {ChannelResource.class, NestedFieldSupport.class}
)
public class ChannelResourceImpl
	extends BaseChannelResourceImpl implements NestedFieldSupport {

	@Override
	public Channel getOrderByExternalReferenceCodeChannel(
			String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		return _toChannel(commerceChannel.getCommerceChannelId());
	}

	@NestedField(parentClass = Order.class, value = "channel")
	@Override
	public Channel getOrderIdChannel(Long id) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			id);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		return _toChannel(commerceChannel.getCommerceChannelId());
	}

	@NestedField(parentClass = OrderRuleChannel.class, value = "channel")
	@Override
	public Channel getOrderRuleChannelChannel(Long id) throws Exception {
		COREntryRel corEntryRel = _corEntryRelService.getCOREntryRel(id);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				corEntryRel.getClassPK());

		return _toChannel(commerceChannel.getCommerceChannelId());
	}

	@NestedField(parentClass = OrderTypeChannel.class, value = "channel")
	@Override
	public Channel getOrderTypeChannelChannel(Long id) throws Exception {
		CommerceOrderTypeRel commerceOrderTypeRel =
			_commerceOrderTypeRelService.getCommerceOrderTypeRel(id);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commerceOrderTypeRel.getClassPK());

		return _toChannel(commerceChannel.getCommerceChannelId());
	}

	private Channel _toChannel(long commerceChannelId) throws Exception {
		return _channelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceChannelId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private ChannelDTOConverter _channelDTOConverter;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderTypeRelService _commerceOrderTypeRelService;

	@Reference
	private COREntryRelService _corEntryRelService;

}