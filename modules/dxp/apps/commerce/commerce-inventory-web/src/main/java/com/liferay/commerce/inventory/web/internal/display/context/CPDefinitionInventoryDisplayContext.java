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

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.web.internal.portlet.action.CPDefinitionInventoryActionHelper;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.impl.CPDefinitionInventoryImpl;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeService;
import com.liferay.commerce.service.CommerceAvailabilityRangeService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivity;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.commerce.util.comparator.CommerceAvailabilityRangePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionInventoryDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionInventoryDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CommerceAvailabilityRangeService commerceAvailabilityRangeService,
			CommerceLowStockActivityRegistry commerceLowStockActivityRegistry,
			CPDefinitionAvailabilityRangeService
				cpDefinitionAvailabilityRangeService,
			CPDefinitionInventoryActionHelper cpDefinitionInventoryActionHelper,
			CPDefinitionInventoryEngineRegistry
				cpDefinitionInventoryEngineRegistry)
		throws PortalException {

		super(actionHelper, httpServletRequest);

		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
		_commerceLowStockActivityRegistry = commerceLowStockActivityRegistry;
		_cpDefinitionAvailabilityRangeService =
			cpDefinitionAvailabilityRangeService;
		_cpDefinitionInventoryActionHelper = cpDefinitionInventoryActionHelper;
		_cpDefinitionInventoryEngineRegistry =
			cpDefinitionInventoryEngineRegistry;
	}

	public List<CommerceAvailabilityRange> getCommerceAvailabilityRanges() {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRanges(
			getScopeGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CommerceAvailabilityRangePriorityComparator(true));
	}

	public List<CommerceLowStockActivity> getCommerceLowStockActivities() {
		return
			_commerceLowStockActivityRegistry.getCommerceLowStockActivities();
	}

	public CPDefinitionAvailabilityRange getCPDefinitionAvailabilityRange()
		throws PortalException {

		return _cpDefinitionAvailabilityRangeService.
			fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
				getCPDefinitionId());
	}

	public CPDefinitionInventory getCPDefinitionInventory()
		throws PortalException {

		if (_cpDefinitionInventory != null) {
			return _cpDefinitionInventory;
		}

		_cpDefinitionInventory =
			_cpDefinitionInventoryActionHelper.getCPDefinitionInventory(
				cpRequestHelper.getRenderRequest());

		if (_cpDefinitionInventory == null) {
			_cpDefinitionInventory = new CPDefinitionInventoryImpl();

			_cpDefinitionInventory.setMinCartQuantity(
				CPDefinitionInventoryConstants.DEFAULT_MIN_CART_QUANTITY);
			_cpDefinitionInventory.setMaxCartQuantity(
				CPDefinitionInventoryConstants.DEFAULT_MAX_CART_QUANTITY);
			_cpDefinitionInventory.setMultipleCartQuantity(
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_CART_QUANTITY);
		}

		return _cpDefinitionInventory;
	}

	public List<CPDefinitionInventoryEngine> getCPDefinitionInventoryEngines() {
		return _cpDefinitionInventoryEngineRegistry.
			getCPDefinitionInventoryEngines();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION;
	}

	private final CommerceAvailabilityRangeService
		_commerceAvailabilityRangeService;
	private final CommerceLowStockActivityRegistry
		_commerceLowStockActivityRegistry;
	private final CPDefinitionAvailabilityRangeService
		_cpDefinitionAvailabilityRangeService;
	private CPDefinitionInventory _cpDefinitionInventory;
	private final CPDefinitionInventoryActionHelper
		_cpDefinitionInventoryActionHelper;
	private final CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

}