/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.engine.contributor;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceInventoryEngineContributor {

	public void consumeQuantityContribute(
			long userId, long commerceInventoryBookedQuantityId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey, Map<String, String> context)
		throws PortalException;

	public void decreaseStockQuantityContribute(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException;

	public void increaseStockQuantityContribute(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException;

}