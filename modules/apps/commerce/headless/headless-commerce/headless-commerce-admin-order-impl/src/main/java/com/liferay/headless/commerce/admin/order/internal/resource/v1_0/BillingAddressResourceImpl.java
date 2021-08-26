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
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.BillingAddressDTOConverter;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.BillingAddressUtil;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/billing-address.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {BillingAddressResource.class, NestedFieldSupport.class}
)
public class BillingAddressResourceImpl
	extends BaseBillingAddressResourceImpl implements NestedFieldSupport {

	@Override
	public BillingAddress getOrderByExternalReferenceCodeBillingAddress(
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
				commerceOrder.getBillingAddressId());

		return _billingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Order.class, value = "billingAddress")
	@Override
	public BillingAddress getOrderIdBillingAddress(Long id) throws Exception {
		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			id);

		CommerceAddress commerceAddress =
			_commerceAddressService.fetchCommerceAddress(
				commerceOrder.getBillingAddressId());

		if (commerceAddress == null) {
			return new BillingAddress();
		}

		return _billingAddressDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceAddress.getCommerceAddressId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Response patchOrderByExternalReferenceCodeBillingAddress(
			String externalReferenceCode, BillingAddress billingAddress)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		BillingAddressUtil.addOrUpdateBillingAddress(
			_commerceAddressService, _commerceOrderService, commerceOrder,
			billingAddress, _serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response patchOrderIdBillingAddress(
			Long id, BillingAddress billingAddress)
		throws Exception {

		BillingAddressUtil.addOrUpdateBillingAddress(
			_commerceAddressService, _commerceOrderService,
			_commerceOrderService.getCommerceOrder(id), billingAddress,
			_serviceContextHelper.getServiceContext());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Reference
	private BillingAddressDTOConverter _billingAddressDTOConverter;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}