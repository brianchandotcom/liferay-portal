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

package com.liferay.commerce.inventory.internal.engine;

import com.liferay.commerce.inventory.configuration.CommerceInventoryGroupConfiguration;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.method.CommerceInventoryMethod;
import com.liferay.commerce.inventory.method.CommerceInventoryMethodRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 * @author Ivica Cardic
 */
@Component(service = CommerceInventoryEngine.class)
public class CommerceInventoryEngineImpl implements CommerceInventoryEngine {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void consumeQuantity(
			long userId, long commerceCatalogGroupId,
			long commerceInventoryWarehouseId, String sku, int quantity,
			long bookedQuantityId, Map<String, String> context)
		throws PortalException {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		if (commerceInventoryMethod == null) {
			return;
		}

		commerceInventoryMethod.consumeQuantity(
			userId, commerceInventoryWarehouseId, sku, quantity,
			bookedQuantityId, context);
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void decreaseStockQuantity(
			long userId, long commerceCatalogGroupId,
			long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		commerceInventoryMethod.decreaseStockQuantity(
			userId, commerceInventoryWarehouseId, sku, quantity);
	}

	@Override
	public String getAvailabilityStatus(
		long companyId, long commerceCatalogGroupId,
		long commerceChannelGroupId, int minStockQuantity, String sku) {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		if (commerceInventoryMethod == null) {
			return null;
		}

		return commerceInventoryMethod.getAvailabilityStatus(
			companyId, commerceChannelGroupId, minStockQuantity, sku);
	}

	@Override
	public int getStockQuantity(
			long companyId, long commerceCatalogGroupId,
			long commerceChannelGroupId, String sku)
		throws PortalException {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		if (commerceInventoryMethod == null) {
			return 0;
		}

		return commerceInventoryMethod.getStockQuantity(
			companyId, commerceChannelGroupId, sku);
	}

	@Override
	public int getStockQuantity(
			long companyId, long commerceCatalogGroupId, String sku)
		throws PortalException {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		if (commerceInventoryMethod == null) {
			return 0;
		}

		return commerceInventoryMethod.getStockQuantity(companyId, sku);
	}

	@Override
	public boolean hasStockQuantity(
		long companyId, long commerceCatalogGroupId, String sku, int quantity) {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		if (commerceInventoryMethod == null) {
			return false;
		}

		return commerceInventoryMethod.hasStockQuantity(
			companyId, sku, quantity);
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void increaseStockQuantity(
			long userId, long commerceCatalogGroupId,
			long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		CommerceInventoryMethod commerceInventoryMethod =
			_getCommerceInventoryMethod(commerceCatalogGroupId);

		commerceInventoryMethod.increaseStockQuantity(
			userId, commerceInventoryWarehouseId, sku, quantity);
	}

	private CommerceInventoryMethod _getCommerceInventoryMethod(
		long commerceCatalogGroupId) {

		try {
			CommerceInventoryGroupConfiguration
				commerceInventoryGroupConfiguration =
					_configurationProvider.getGroupConfiguration(
						CommerceInventoryGroupConfiguration.class,
						commerceCatalogGroupId);

			CommerceInventoryMethod commerceInventoryMethod =
				_commerceInventoryMethodRegistry.getCommerceInventoryMethod(
					commerceInventoryGroupConfiguration.inventoryMethodKey());

			if (commerceInventoryMethod == null) {
				return _commerceInventoryMethodRegistry.
					getCommerceInventoryMethod(
						CommerceInventoryConstants.DEFAULT_METHOD_KEY);
			}

			return commerceInventoryMethod;
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return _commerceInventoryMethodRegistry.getCommerceInventoryMethod(
			CommerceInventoryConstants.DEFAULT_METHOD_KEY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryEngineImpl.class);

	@Reference
	private CommerceInventoryMethodRegistry _commerceInventoryMethodRegistry;

	@Reference
	private ConfigurationProvider _configurationProvider;

}