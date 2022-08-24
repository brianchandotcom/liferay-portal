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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Hai Yu
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class AddressModelListener extends BaseModelListener<Address> {

	@Override
	public void onAfterRemove(Address address) throws ModelListenerException {
		try {
			List<CommerceOrder> commerceOrders =
				_commerceOrderLocalService.getCommerceOrdersByBillingAddress(
					address.getAddressId());

			_removeCommerceOrderAddresses(
				commerceOrders, address.getAddressId());

			commerceOrders =
				_commerceOrderLocalService.getCommerceOrdersByShippingAddress(
					address.getAddressId());

			_removeCommerceOrderAddresses(
				commerceOrders, address.getAddressId());
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Override
	public void onAfterUpdate(Address originalAddress, Address address)
		throws ModelListenerException {

		try {
			List<CommerceOrder> commerceOrders =
				_commerceOrderLocalService.getCommerceOrdersByShippingAddress(
					address.getAddressId());

			for (CommerceOrder commerceOrder : commerceOrders) {
				_commerceOrderLocalService.resetCommerceOrderShipping(
					commerceOrder.getCommerceOrderId());
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _removeCommerceOrderAddresses(
			List<CommerceOrder> commerceOrders, long commerceAddressId)
		throws PortalException {

		for (CommerceOrder commerceOrder : commerceOrders) {
			long billingAddressId = commerceOrder.getBillingAddressId();
			long shippingAddressId = commerceOrder.getShippingAddressId();

			long commerceShippingMethodId =
				commerceOrder.getCommerceShippingMethodId();
			String shippingOptionName = commerceOrder.getShippingOptionName();
			BigDecimal shippingPrice = commerceOrder.getShippingAmount();

			if (billingAddressId == commerceAddressId) {
				billingAddressId = 0;
			}

			if (shippingAddressId == commerceAddressId) {
				shippingAddressId = 0;

				commerceShippingMethodId = 0;
				shippingOptionName = null;
				shippingPrice = BigDecimal.ZERO;
			}

			_commerceOrderLocalService.updateCommerceOrder(
				null, commerceOrder.getCommerceOrderId(), billingAddressId,
				commerceShippingMethodId, shippingAddressId,
				commerceOrder.getAdvanceStatus(),
				commerceOrder.getCommercePaymentMethodKey(),
				commerceOrder.getPurchaseOrderNumber(), shippingPrice,
				shippingOptionName, commerceOrder.getSubtotal(),
				commerceOrder.getTotal(), null);
		}
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}