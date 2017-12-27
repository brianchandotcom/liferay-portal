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

import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceWarehouseItemLocalService;
import com.liferay.commerce.util.CommerceShippingOriginLocatorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
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
	public boolean allowBackOrder(CPInstance cpInstance)
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
	public String[] getAllowedCartQuantities(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return new String[0];
		}

		return StringUtil.split(
			cpDefinitionInventory.getAllowedCartQuantities());
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
		return LanguageUtil.get(locale, KEY);
	}

	@Override
	public int getMaxCartQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MAX_CART_QUANTITY;
		}

		return cpDefinitionInventory.getMaxCartQuantity();
	}

	@Override
	public int getMinCartQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return CPDefinitionInventoryConstants.DEFAULT_MIN_CART_QUANTITY;
		}

		return cpDefinitionInventory.getMinCartQuantity();
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
	public int getMultipleCartQuantity(CPInstance cpInstance)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			return
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_CART_QUANTITY;
		}

		return cpDefinitionInventory.getMultipleCartQuantity();
	}

	@Override
	public int getStockQuantity(CPInstance cpInstance) {
		return _commerceWarehouseItemLocalService.getCPInstanceQuantity(
			cpInstance.getCPInstanceId());
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
	public int updateStockQuantity(CommerceOrderItem commerceOrderItem)
		throws PortalException {

		int quantity = 0;

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		CommerceShippingOriginLocator commerceShippingOriginLocator =
			_getCommerceShippingOriginLocator(commerceOrderItem.getGroupId());

		CommerceWarehouse commerceWarehouse =
			commerceShippingOriginLocator.getClosestCommerceWarehouse(
				commerceOrder.getShippingAddress(),
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getQuantity());

		List<CommerceWarehouseItem> commerceWarehouseItems =
			commerceWarehouse.getCommerceWarehouseItems();

		for (CommerceWarehouseItem commerceWarehouseItem :
				commerceWarehouseItems) {

			if (commerceWarehouseItem.getCPInstanceId() ==
					commerceOrderItem.getCPInstanceId()) {

				quantity =
					commerceWarehouseItem.getQuantity() -
						commerceOrderItem.getQuantity();

				commerceWarehouseItem.setQuantity(quantity);

				_commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
					commerceWarehouseItem);

				break;
			}
		}

		return quantity;
	}

	private CommerceShippingOriginLocator _getCommerceShippingOriginLocator(
			long groupId)
		throws PortalException {

		CommerceShippingGroupServiceConfiguration
			commerceShippingGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceShippingGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						groupId, CommerceConstants.SHIPPING_SERVICE_NAME));

		String commerceShippingOriginLocatorKey =
			commerceShippingGroupServiceConfiguration.
				commerceShippingOriginLocatorKey();

		CommerceShippingOriginLocator commerceShippingOriginLocator =
			_commerceShippingOriginLocatorRegistry.
				getCommerceShippingOriginLocator(
					commerceShippingOriginLocatorKey);

		if (commerceShippingOriginLocator == null) {
			throw new
				CommerceShippingEngineException.MustSetShippingOriginLocator(
					commerceShippingOriginLocatorKey);
		}

		return commerceShippingOriginLocator;
	}

	@Reference
	private CommerceShippingOriginLocatorRegistry
		_commerceShippingOriginLocatorRegistry;

	@Reference
	private CommerceWarehouseItemLocalService
		_commerceWarehouseItemLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}