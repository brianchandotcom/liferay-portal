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

import com.liferay.commerce.inventory.constants.CommerceInventoryAvailabilityConstants;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 * @author Ivica Cardic
 */
@Component(
	enabled = false, immediate = true, service = CommerceInventoryEngine.class
)
public class CommerceInventoryEngineImpl implements CommerceInventoryEngine {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void consumeQuantity(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity, long bookedQuantityId, Map<String, String> context)
		throws PortalException {

		if (bookedQuantityId > 0) {
			_commerceBookedQuantityLocalService.consumeCommerceBookedQuantity(
				bookedQuantityId, quantity);
		}

		decreaseStockQuantity(
			userId, commerceInventoryWarehouseId, sku, quantity);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_CONSUME_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, sku, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context), quantity);
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void decreaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku);

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId,
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouseItem.getQuantity() - quantity,
				commerceInventoryWarehouseItem.getMvccVersion());
	}

	@Override
	public String getAvailabilityStatus(
		long companyId, long commerceChannelGroupId, int minStockQuantity,
		String sku) {

		return _getAvailabilityStatus(
			minStockQuantity,
			getStockQuantity(companyId, commerceChannelGroupId, sku));
	}

	@Override
	public Map<String, Integer> getStockQuantities(
		long companyId, long commerceChannelGroupId, List<String> skus) {

		Map<String, Integer> results = new HashMap<>();

		for (String sku : skus) {
			int stockQuantity = getStockQuantity(
				companyId, commerceChannelGroupId, sku);

			results.put(sku, stockQuantity);
		}

		return results;
	}

	@Override
	public int getStockQuantity(
		long companyId, long commerceChannelGroupId, String sku) {

		int stockQuantity =
			_commerceInventoryWarehouseItemLocalService.getStockQuantity(
				companyId, commerceChannelGroupId, sku);

		int commerceBookedQuantity =
			_commerceBookedQuantityLocalService.getCommerceBookedQuantity(
				companyId, sku);

		return stockQuantity - commerceBookedQuantity;
	}

	@Override
	public int getStockQuantity(long companyId, String sku) {
		int stockQuantity =
			_commerceInventoryWarehouseItemLocalService.getStockQuantity(
				companyId, sku);

		int commerceBookedQuantity =
			_commerceBookedQuantityLocalService.getCommerceBookedQuantity(
				companyId, sku);

		return stockQuantity - commerceBookedQuantity;
	}

	@Override
	public boolean hasStockQuantity(long companyId, String sku, int quantity) {
		if (quantity <= getStockQuantity(companyId, sku)) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void increaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku);

		try {
			_commerceInventoryWarehouseItemLocalService.
				updateCommerceInventoryWarehouseItem(
					userId,
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					commerceInventoryWarehouseItem.getQuantity() + quantity,
					commerceInventoryWarehouseItem.getMvccVersion());
		}
		catch (MVCCException mvccException) {
			_log.error(mvccException.getMessage(), mvccException);

			throw mvccException;
		}

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, sku, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(null), quantity);
	}

	private String _getAvailabilityStatus(
		int minStockQuantity, int stockQuantity) {

		String availabilityStatus =
			CommerceInventoryAvailabilityConstants.UNAVAILABLE;

		boolean available = false;

		if (stockQuantity > minStockQuantity) {
			available = true;
		}

		if (available) {
			availabilityStatus =
				CommerceInventoryAvailabilityConstants.AVAILABLE;
		}

		return availabilityStatus;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryEngineImpl.class);

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceBookedQuantityLocalService;

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Reference
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

}