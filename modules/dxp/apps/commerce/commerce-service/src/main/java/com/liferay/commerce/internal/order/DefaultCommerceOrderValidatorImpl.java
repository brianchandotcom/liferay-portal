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

package com.liferay.commerce.internal.order;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.order.validator.key=" + DefaultCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=10"
	},
	service = CommerceOrderValidator.class
)
public class DefaultCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		CPInstance cpInstance = commerceCartItem.fetchCPInstance();

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceOrderValidatorResult(true);
		}

		int minCartQuantity = cpDefinitionInventoryEngine.getMinCartQuantity(
			cpInstance);
		int maxCartQuantity = cpDefinitionInventoryEngine.getMaxCartQuantity(
			cpInstance);
		String[] allowedCartQuantities =
			cpDefinitionInventoryEngine.getAllowedCartQuantities(cpInstance);

		if ((minCartQuantity > 0) &&
			(commerceCartItem.getQuantity() < minCartQuantity)) {

			return new CommerceOrderValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"minimum-quantity-is-x", String.valueOf(minCartQuantity));
		}

		if ((maxCartQuantity > 0) &&
			(commerceCartItem.getQuantity() > maxCartQuantity)) {

			return new CommerceOrderValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"maximum-quantity-is-x", String.valueOf(maxCartQuantity));
		}

		if ((allowedCartQuantities.length > 0) &&
			!ArrayUtil.contains(
				allowedCartQuantities,
				String.valueOf(commerceCartItem.getQuantity()))) {

			return new CommerceOrderValidatorResult(
				commerceCartItem.getCommerceCartItemId(), false,
				"quantity-is-not-allowed");
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			CPInstance cpInstance, int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceOrderValidatorResult(true);
		}

		int minCartQuantity = cpDefinitionInventoryEngine.getMinCartQuantity(
			cpInstance);
		int maxCartQuantity = cpDefinitionInventoryEngine.getMaxCartQuantity(
			cpInstance);
		String[] allowedCartQuantities =
			cpDefinitionInventoryEngine.getAllowedCartQuantities(cpInstance);

		if ((minCartQuantity > 0) && (quantity < minCartQuantity)) {
			return new CommerceOrderValidatorResult(
				false, "minimum-quantity-is-x",
				String.valueOf(minCartQuantity));
		}

		if ((maxCartQuantity > 0) && (quantity > maxCartQuantity)) {
			return new CommerceOrderValidatorResult(
				false, "maximum-quantity-is-x",
				String.valueOf(maxCartQuantity));
		}

		if ((allowedCartQuantities.length > 0) &&
			!ArrayUtil.contains(
				allowedCartQuantities, String.valueOf(quantity))) {

			return new CommerceOrderValidatorResult(
				false, "quantity-is-not-allowed");
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}