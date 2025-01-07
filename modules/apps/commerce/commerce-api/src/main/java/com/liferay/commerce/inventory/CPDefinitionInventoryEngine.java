/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public interface CPDefinitionInventoryEngine {

	public String[] getAllowedOrderQuantities(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public String getAvailabilityEstimate(
			long cpConfigurationListId, CPInstance cpInstance, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

	public BigDecimal getMaxOrderQuantity(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMinOrderQuantity(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMinStockQuantity(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMultipleOrderQuantity(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public boolean isBackOrderAllowed(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayAvailability(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayStockQuantity(
			long cpConfigurationListId, CPInstance cpInstance)
		throws PortalException;

}