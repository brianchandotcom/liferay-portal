/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.inventory;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CPDefinitionInventoryEngine {

	public String[] getAllowedOrderQuantities(CPInstance cpInstance)
		throws PortalException;

	public String getAvailabilityRange(CPInstance cpInstance, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

	public int getMaxOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getMinStockQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getMultipleOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public int getStockQuantity(CPInstance cpInstance);

	public boolean isBackOrderAllowed(CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayAvailability(CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayStockQuantity(CPInstance cpInstance)
		throws PortalException;

	public int updateStockQuantity(
		CommerceWarehouseItem commerceWarehouseItem, int quantity);

}