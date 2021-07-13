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

package com.liferay.commerce.inventory.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Luca Pellizzon
 * @generated
 */
@ProviderType
public interface CommerceInventoryWarehouseItemFinder {

	public int countItemsByCompanyId(long companyId, String sku);

	public int countStockQuantityByC_S(long companyId, String sku);

	public int countStockQuantityByC_G_S(
		long companyId, long commerceChannelGroupId, String sku);

	public int countUpdatedItemsByC_M(
		long companyId, java.util.Date startDate, java.util.Date endDate);

	public java.util.List<Object[]> findItemsByCompanyId(
		long companyId, String sku, int start, int end);

	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem>
			findUpdatedItemsByC_M(
				long companyId, java.util.Date startDate,
				java.util.Date endDate, int start, int end);

}