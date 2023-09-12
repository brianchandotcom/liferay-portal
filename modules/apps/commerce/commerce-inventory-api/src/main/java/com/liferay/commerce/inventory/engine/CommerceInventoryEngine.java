/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.engine;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Map;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 * @author Ivica Cardic
 */
public interface CommerceInventoryEngine {

	public void consumeQuantity(
			long userId, long commerceInventoryBookedQuantityId,
			long commerceCatalogGroupId, long commerceInventoryWarehouseId,
			BigDecimal quantity, String sku, String unitOfMeasureKey,
			Map<String, String> context)
		throws PortalException;

	public void decreaseStockQuantity(
			long userId, long commerceCatalogGroupId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey)
		throws PortalException;

	public String getAvailabilityStatus(
		long companyId, long commerceCatalogGroupId,
		long commerceChannelGroupId, BigDecimal minStockQuantity, String sku,
		String unitOfMeasureKey);

	public BigDecimal getStockQuantity(
			long companyId, long commerceCatalogGroupId,
			long commerceChannelGroupId, String sku, String unitOfMeasureKey)
		throws PortalException;

	public BigDecimal getStockQuantity(
			long companyId, long commerceCatalogGroupId, String sku,
			String unitOfMeasureKey)
		throws PortalException;

	public boolean hasStockQuantity(
		long companyId, long commerceCatalogGroupId, BigDecimal quantity,
		String sku, String unitOfMeasureKey);

	public void increaseStockQuantity(
			long userId, long commerceCatalogGroupId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey)
		throws PortalException;

}