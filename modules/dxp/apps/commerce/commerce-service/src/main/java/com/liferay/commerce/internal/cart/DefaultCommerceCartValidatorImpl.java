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
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceCartItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.cart.validator.key=" + DefaultCommerceCartValidatorImpl.KEY,
		"commerce.cart.validator.priority:Integer=10"
	},
	service = CommerceCartValidator.class
)
public class DefaultCommerceCartValidatorImpl implements CommerceCartValidator {

	public static final String KEY = "default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceCartValidatorResult validate(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		if (commerceCartItem == null) {
			return new CommerceCartValidatorResult(
				false, "product-is-no-longer-available");
		}

		CPInstance cpInstance = commerceCartItem.fetchCPInstance();

		if (cpInstance == null) {
			return new CommerceCartValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"please-select-a-valid-product");
		}

		if (!cpInstance.isApproved()) {
			return new CommerceCartValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory != null) {
			int minCartQuantity = cpDefinitionInventory.getMinCartQuantity();
			int maxCartQuantity = cpDefinitionInventory.getMaxCartQuantity();
			String[] allowedCartQuantities = StringUtil.split(
				cpDefinitionInventory.getAllowedCartQuantities());

			if ((minCartQuantity > 0) &&
				(commerceCartItem.getQuantity() < minCartQuantity)) {

				return new CommerceCartValidatorResult(
					commerceCartItem.getCommerceCartItemId(), false,
					"minimum-quantity-is-x", String.valueOf(minCartQuantity));
			}

			if ((maxCartQuantity > 0) &&
				(commerceCartItem.getQuantity() > maxCartQuantity)) {

				return new CommerceCartValidatorResult(
					commerceCartItem.getCommerceCartItemId(), false,
					"maximum-quantity-is-x", String.valueOf(maxCartQuantity));
			}

			if ((allowedCartQuantities.length > 0) &&
				!ArrayUtil.contains(
					allowedCartQuantities,
					String.valueOf(commerceCartItem.getQuantity()))) {

				return new CommerceCartValidatorResult(
					commerceCartItem.getCommerceCartItemId(), false,
					"quantity-is-not-allowed");
			}
		}

		return new CommerceCartValidatorResult(true);
	}

	@Override
	public CommerceCartValidatorResult validate(
			CPInstance cpInstance, CommerceCart commerceCart, int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceCartValidatorResult(
				false, "please-select-a-valid-product");
		}

		if (!cpInstance.isApproved()) {
			return new CommerceCartValidatorResult(
				false, "product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory != null) {
			int minCartQuantity = cpDefinitionInventory.getMinCartQuantity();
			int maxCartQuantity = cpDefinitionInventory.getMaxCartQuantity();
			String[] allowedCartQuantities = StringUtil.split(
				cpDefinitionInventory.getAllowedCartQuantities());

			if ((minCartQuantity > 0) && (quantity < minCartQuantity)) {
				return new CommerceCartValidatorResult(
					false, "minimum-quantity-is-x",
					String.valueOf(minCartQuantity));
			}

			if ((maxCartQuantity > 0) && (quantity > maxCartQuantity)) {
				return new CommerceCartValidatorResult(
					false, "maximum-quantity-is-x",
					String.valueOf(maxCartQuantity));
			}

			if ((allowedCartQuantities.length > 0) &&
				!ArrayUtil.contains(
					allowedCartQuantities, String.valueOf(quantity))) {

				return new CommerceCartValidatorResult(
					false, "quantity-is-not-allowed");
			}
		}

		return new CommerceCartValidatorResult(true);
	}

	@Reference
	private CommerceCartItemLocalService _commerceCartItemLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}