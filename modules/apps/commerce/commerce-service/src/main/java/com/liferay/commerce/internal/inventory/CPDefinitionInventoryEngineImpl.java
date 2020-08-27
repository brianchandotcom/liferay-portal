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
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDAvailabilityEstimateLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"cp.definition.inventory.engine.key=" + CPDefinitionInventoryEngineImpl.KEY,
		"cp.definition.inventory.engine.priority:Integer=1"
	},
	service = CPDefinitionInventoryEngine.class
)
public class CPDefinitionInventoryEngineImpl
	implements CPDefinitionInventoryEngine {

	public static final String KEY = "default";

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

		return ArrayUtil.toStringArray(
			cpDefinitionInventory.getAllowedOrderQuantitiesArray());
	}

	@Override
	public String getAvailabilityEstimate(CPInstance cpInstance, Locale locale)
		throws PortalException {

		CPDAvailabilityEstimate cpDefinitionAvailabilityEstimate =
			_cpdAvailabilityEstimateLocalService.
				fetchCPDAvailabilityEstimateByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionAvailabilityEstimate == null) {
			return StringPool.BLANK;
		}

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			cpDefinitionAvailabilityEstimate.getCommerceAvailabilityEstimate();

		if (commerceAvailabilityEstimate == null) {
			return StringPool.BLANK;
		}

		return commerceAvailabilityEstimate.getTitle(locale);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, KEY);
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
			return CPDefinitionInventoryConstants.
				DEFAULT_MULTIPLE_ORDER_QUANTITY;
		}

		return cpDefinitionInventory.getMultipleOrderQuantity();
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

		return cpDefinitionInventory.isBackOrders();
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

		return cpDefinitionInventory.isDisplayAvailability();
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

		return cpDefinitionInventory.isDisplayStockQuantity();
	}

	@Reference
	private CPDAvailabilityEstimateLocalService
		_cpdAvailabilityEstimateLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}