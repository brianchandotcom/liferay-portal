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

package com.liferay.commerce.inventory.web.internal.display.context;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.inventory.CommerceInventoryEngine;
import com.liferay.commerce.inventory.CommerceInventoryEngineRegistry;
import com.liferay.commerce.inventory.web.internal.portlet.action.CommerceInventoryActionHelper;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.model.impl.CommerceInventoryImpl;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeService;
import com.liferay.commerce.service.CommerceAvailabilityRangeService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.commerce.util.comparator.CommerceAvailabilityRangePriorityComparator;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CommerceInventoryDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceAvailabilityRangeService commerceAvailabilityRangeService,
			CommerceInventoryActionHelper commerceInventoryActionHelper,
			CommerceInventoryEngineRegistry commerceInventoryEngineRegistry,
			CommerceLowStockActivityRegistry commerceLowStockActivityRegistry,
			CPDefinitionAvailabilityRangeService
				cpDefinitionAvailabilityRangeService)
		throws PortalException {

		super(actionHelper, httpServletRequest);

		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
		_commerceInventoryActionHelper = commerceInventoryActionHelper;
		_commerceInventoryEngineRegistry = commerceInventoryEngineRegistry;
		_commerceLowStockActivityRegistry = commerceLowStockActivityRegistry;
		_cpDefinitionAvailabilityRangeService =
			cpDefinitionAvailabilityRangeService;
	}

	public List<CommerceAvailabilityRange> getCommerceAvailabilityRanges() {
		int total =
			_commerceAvailabilityRangeService.
				getCommerceAvailabilityRangesCount(getScopeGroupId());

		return _commerceAvailabilityRangeService.getCommerceAvailabilityRanges(
			getScopeGroupId(), 0, total,
			new CommerceAvailabilityRangePriorityComparator(true));
	}

	public CommerceInventory getCommerceInventory() throws PortalException {
		if (_commerceInventory != null) {
			return _commerceInventory;
		}

		_commerceInventory =
			_commerceInventoryActionHelper.getCommerceInventory(
				cpRequestHelper.getRenderRequest());

		if (_commerceInventory == null) {
			_commerceInventory = new CommerceInventoryImpl();

			_commerceInventory.setMinCartQuantity(
				CommerceConstants.COMMERCE_INVENTORY_DEFAULT_MIN_CART_QUANTITY);
			_commerceInventory.setMaxCartQuantity(
				CommerceConstants.COMMERCE_INVENTORY_DEFAULT_MAX_CART_QUANTITY);
			_commerceInventory.setMultipleCartQuantity(
				CommerceConstants.
					COMMERCE_INVENTORY_DEFAULT_MULTIPLE_CART_QUANTITY);
		}

		return _commerceInventory;
	}

	public Map<String, CommerceInventoryEngine> getCommerceInventoryEngines() {
		return _commerceInventoryEngineRegistry.getCommerceInventoryEngines();
	}

	public Map<String, CommerceLowStockActivity>
		getCommerceLowStockActivities() {

		return
			_commerceLowStockActivityRegistry.getCommerceLowStockActivities();
	}

	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRange()
		throws PortalException {

		return _cpDefinitionAvailabilityRangeService.
			fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
				getCPDefinitionId());
	}

	@Override
	public String getScreenNavigationCategoryKey() throws PortalException {
		return "inventory";
	}

	private final CommerceAvailabilityRangeService
		_commerceAvailabilityRangeService;
	private CommerceInventory _commerceInventory;
	private final CommerceInventoryActionHelper _commerceInventoryActionHelper;
	private final CommerceInventoryEngineRegistry
		_commerceInventoryEngineRegistry;
	private final CommerceLowStockActivityRegistry
		_commerceLowStockActivityRegistry;
	private final CPDefinitionAvailabilityRangeService
		_cpDefinitionAvailabilityRangeService;

}