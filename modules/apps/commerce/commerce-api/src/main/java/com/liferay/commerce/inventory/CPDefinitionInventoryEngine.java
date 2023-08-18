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

	public String[] getAllowedOrderQuantities(CPInstance cpInstance)
		throws PortalException;

	public String getAvailabilityEstimate(CPInstance cpInstance, Locale locale)
		throws PortalException;

	public String getKey();

	public String getLabel(Locale locale);

	public BigDecimal getMaxOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMinStockQuantity(CPInstance cpInstance)
		throws PortalException;

	public BigDecimal getMultipleOrderQuantity(CPInstance cpInstance)
		throws PortalException;

	public boolean isBackOrderAllowed(CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayAvailability(CPInstance cpInstance)
		throws PortalException;

	public boolean isDisplayStockQuantity(CPInstance cpInstance)
		throws PortalException;

}