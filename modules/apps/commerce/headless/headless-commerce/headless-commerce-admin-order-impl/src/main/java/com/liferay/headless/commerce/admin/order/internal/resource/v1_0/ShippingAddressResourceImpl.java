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
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.ShippingAddressDTOConverter;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.ShippingAddressUtil;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/shipping-address.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ShippingAddressResource.class}
)
public class ShippingAddressResourceImpl
	extends BaseShippingAddressResourceImpl implements NestedFieldSupport {

	@Override
	public ShippingAddress getOrderByExternalReferenceCodeShippingAddress(
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

		CommerceAddress commerceAddress =
			_commerceAddressService.getCommerceAddress(
				commerceOrder.getShippingAddressId());

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Order.class, value = "shippingAddress")
	@Override
	public ShippingAddress getOrderIdShippingAddress(Long id) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			id);

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchCommerceAddress(
				commerceOrder.getShippingAddressId());

		if (commerceAddress == null) {
			return new ShippingAddress();
		}

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = OrderItem.class, value = "shippingAddress")
	@Override
	public ShippingAddress getOrderItemShippingAddress(Long id)
		throws Exception {

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(id);

		if (commerceOrderItem.getShippingAddressId() == 0) {
			return new ShippingAddress();
		}

		return _shippingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceOrderItem.getShippingAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Response patchOrderByExternalReferenceCodeShippingAddress(
			String externalReferenceCode, ShippingAddress shippingAddress)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		ShippingAddressUtil.addOrUpdateShippingAddress(
			_commerceAddressService, _commerceOrderService, commerceOrder,
			shippingAddress, _serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response patchOrderIdShippingAddress(
			Long id, ShippingAddress shippingAddress)
		throws Exception {

		ShippingAddressUtil.addOrUpdateShippingAddress(
			_commerceAddressService, _commerceOrderService,
			_commerceOrderService.getCommerceOrder(id), shippingAddress,
			_serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

	@Reference
	private ShippingAddressDTOConverter _shippingAddressDTOConverter;

}