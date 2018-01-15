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
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceCartItemLocalService;
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

		if (!cpInstance.isApproved() || !cpInstance.getPublished()) {
			return new CommerceCartValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceCartValidatorResult(true);
		}

		int availableQuantity = cpDefinitionInventoryEngine.getStockQuantity(
			cpInstance);

		int cartQuantity = _commerceCartItemLocalService.getCPInstanceQuantity(
			commerceCartItem.getCPInstanceId());

		if (cartQuantity > availableQuantity) {
			return new CommerceCartValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"quantity-unavailable");
		}

		return new CommerceCartValidatorResult(true);
	}

	@Override
	public CommerceCartValidatorResult validate(
			CPInstance cpInstance, int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceCartValidatorResult(
				false, "please-select-a-valid-product");
		}

		if (!cpInstance.isApproved() || !cpInstance.getPublished()) {
			return new CommerceCartValidatorResult(
				false, "product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceCartValidatorResult(true);
		}

		int availableQuantity = cpDefinitionInventoryEngine.getStockQuantity(
			cpInstance);

		int cartQuantity = _commerceCartItemLocalService.getCPInstanceQuantity(
			cpInstance.getCPInstanceId());

		cartQuantity += quantity;

		if (cartQuantity > availableQuantity) {
			return new CommerceCartValidatorResult(
				false, "quantity-unavailable");
		}

		return new CommerceCartValidatorResult(true);
	}

	@Reference
	private CommerceCartItemLocalService _commerceCartItemLocalService;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}