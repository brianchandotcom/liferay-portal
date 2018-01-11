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

package com.liferay.commerce.inventory;

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public interface CPDefinitionInventoryEngine {

	public boolean allowBackOrder(CPInstance cpInstance) throws PortalException;

	public String[] getAllowedCartQuantities(CPInstance cpInstance)
		throws PortalException;

	public String getAvailabilityRange(CPInstance cpInstance, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

	public int getMaxCartQuantity(CPInstance cpInstance) throws PortalException;

	public int getMinCartQuantity(CPInstance cpInstance) throws PortalException;

	public int getMinStockQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getMultipleCartQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getStockQuantity(CPInstance cpInstance);

	public boolean isDisplayAvailability(CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayStockQuantity(CPInstance cpInstance)
		throws PortalException;

	public int updateStockQuantity(
		CommerceWarehouseItem commerceWarehouseItem, int quantity);

}