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

package com.liferay.commerce.internal.inventory;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"cp.definition.inventory.engine.key=" + SalesforceInventoryEngineImpl.KEY,
		"cp.definition.inventory.engine.priority:Integer=1"
	},
	service = CPDefinitionInventoryEngine.class
)
public class SalesforceInventoryEngineImpl
	implements CPDefinitionInventoryEngine {

	public static final String KEY = "salesforce";

	@Override
	public String[] getAllowedOrderQuantities(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return new String[0];
		}

		return StringUtil.split(
			cpDefinitionInventory.getAllowedOrderQuantities());
	}

	@Override
	public String getAvailabilityRange(CPInstance cpInstance, Locale locale)
		throws PortalException {

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			_cpDefinitionAvailabilityRangeLocalService.
				fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionAvailabilityRange == null) {
			return StringPool.BLANK;
		}

		CommerceAvailabilityRange commerceAvailabilityRange =
			cpDefinitionAvailabilityRange.getCommerceAvailabilityRange();

		return commerceAvailabilityRange.getTitle(locale);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return "Salesforce";
	}

	@Override
	public int getMaxOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMaxOrderQuantity();
	}

	@Override
	public int getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMinOrderQuantity();
	}

	@Override
	public int getMinStockQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return 0;
		}

		return cpDefinitionInventory.getMinStockQuantity();
	}

	@Override
	public int getMultipleOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMultipleOrderQuantity();
	}

	@Override
	public int getStockQuantity(CPInstance cpInstance) {
		int warehouseCPInstanceQuantity =
			_commerceWarehouseItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId());

		int orderCPInstanceQuantity =
			_commerceOrderItemLocalService.getCPInstanceQuantity(
				cpInstance.getCPInstanceId(),
				CommerceOrderConstants.ORDER_STATUS_COMPLETED);

		return warehouseCPInstanceQuantity - orderCPInstanceQuantity;
	}

	@Override
	public boolean isBackOrderAllowed(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getBackOrders();
	}

	@Override
	public boolean isDisplayAvailability(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getDisplayAvailability();
	}

	@Override
	public boolean isDisplayStockQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return false;
		}

		return cpDefinitionInventory.getDisplayStockQuantity();
	}

	@Override
	public int updateStockQuantity(
		CommerceWarehouseItem commerceWarehouseItem, int quantity) {

		quantity = commerceWarehouseItem.getQuantity() - quantity;

		commerceWarehouseItem.setQuantity(quantity);

		_commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
			commerceWarehouseItem);

		return quantity;
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}