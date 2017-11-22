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

package com.liferay.commerce.internal.cart;

import com.liferay.commerce.cart.CommerceCartValidator;
import com.liferay.commerce.cart.CommerceCartValidatorResult;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceCartItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.cart.validator.key=" + AvailabilityCommerceCartValidatorImpl.KEY,
		"commerce.cart.validator.priority:Integer=20"
	},
	service = CommerceCartValidator.class
)
public class AvailabilityCommerceCartValidatorImpl
	implements CommerceCartValidator {

	public static final String KEY = "availability";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceCartValidatorResult validate(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		if (commerceCartItem != null) {
			int availableQuantity =
				_commerceWarehouseItemLocalService.getCPInstanceQuantity(
					commerceCartItem.getCPInstanceId());

			int cartQuantity =
				_commerceCartItemLocalService.getCPInstanceQuantity(
					commerceCartItem.getCPInstanceId());

			if (cartQuantity > availableQuantity) {
				return new CommerceCartValidatorResult(
					commerceCartItem.getCommerceCartItemId(), false,
					"quantity-unavailable");
			}

			return new CommerceCartValidatorResult(true);
		}

		return new CommerceCartValidatorResult(false);
	}

	@Override
	public CommerceCartValidatorResult validate(
			CPInstance cpInstance, CommerceCart commerceCart, int quantity)
		throws PortalException {

		if (cpInstance != null) {
			int availableQuantity =
				_commerceWarehouseItemLocalService.getCPInstanceQuantity(
					cpInstance.getCPInstanceId());

			int cartQuantity =
				_commerceCartItemLocalService.getCPInstanceQuantity(
					cpInstance.getCPInstanceId());

			cartQuantity += quantity;

			if (cartQuantity > availableQuantity) {
				return new CommerceCartValidatorResult(
					false, "quantity-unavailable");
			}

			return new CommerceCartValidatorResult(true);
		}

		return new CommerceCartValidatorResult(false);
	}

	@Reference
	private CommerceCartItemLocalService _commerceCartItemLocalService;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

}