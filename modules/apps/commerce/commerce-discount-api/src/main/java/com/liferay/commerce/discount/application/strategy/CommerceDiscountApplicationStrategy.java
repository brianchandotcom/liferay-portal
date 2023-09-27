/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.application.strategy;

import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Riccardo Alberti
 */
public interface CommerceDiscountApplicationStrategy {

	public BigDecimal applyCommerceDiscounts(
			BigDecimal commercePrice, BigDecimal[] commerceDiscountLevels)
		throws PortalException;

	public String getCommerceDiscountApplicationStrategyKey();

}