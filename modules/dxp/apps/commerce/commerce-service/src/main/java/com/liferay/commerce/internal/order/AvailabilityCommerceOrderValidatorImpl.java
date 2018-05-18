/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.order.validator.key=" + AvailabilityCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=20"
	},
	service = CommerceOrderValidator.class
)
public class AvailabilityCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "availability";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CPInstance cpInstance = commerceOrderItem.getCPInstance();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (!cpDefinition.isApproved() || !cpInstance.isApproved() ||
			!cpInstance.getPublished()) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				"product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceOrderValidatorResult(true);
		}

		int availableQuantity = cpDefinitionInventoryEngine.getStockQuantity(
			cpInstance);

		int orderQuantity =
			_commerceOrderItemLocalService.getCPInstanceQuantity(
				commerceOrderItem.getCPInstanceId(),
				CommerceOrderConstants.ORDER_STATUS_OPEN);

		if (orderQuantity > availableQuantity) {
			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				"quantity-unavailable");
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			CPInstance cpInstance, int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(
				false, "please-select-a-valid-product");
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (!cpDefinition.isApproved() || !cpInstance.isApproved() ||
			!cpInstance.getPublished()) {

			return new CommerceOrderValidatorResult(
				false, "product-is-no-longer-available");
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		if (cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {
			return new CommerceOrderValidatorResult(true);
		}

		int availableQuantity = cpDefinitionInventoryEngine.getStockQuantity(
			cpInstance);

		int orderQuantity =
			_commerceOrderItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId(),
				CommerceOrderConstants.ORDER_STATUS_OPEN);

		orderQuantity += quantity;

		if (orderQuantity > availableQuantity) {
			return new CommerceOrderValidatorResult(
				false, "quantity-unavailable");
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}