/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.method;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public interface CommerceInventoryMethod {

	public void consumeQuantity(
			long userId, long commerceInventoryBookedQuantityId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey, Map<String, String> context)
		throws PortalException;

	public void decreaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException;

	public String getAvailabilityStatus(
		long companyId, long commerceChannelGroupId,
		BigDecimal minStockQuantity, String sku, String unitOfMeasureKey);

	public String getKey();

	public String getLabel(Locale locale);

	public BigDecimal getStockQuantity(
			long companyId, long commerceChannelGroupId, String sku,
			String unitOfMeasureKey)
		throws PortalException;

	public BigDecimal getStockQuantity(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException;

	public boolean hasStockQuantity(
		long companyId, BigDecimal quantity, String sku,
		String unitOfMeasureKey);

	public void increaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException;

}