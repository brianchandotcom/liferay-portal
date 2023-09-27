/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.internal.application.strategy;

import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Alberti
 */
@Component(service = CommerceDiscountApplicationStrategy.class)
public class ChainCommerceDiscountApplicationStrategyImpl
	implements CommerceDiscountApplicationStrategy {

	@Override
	public BigDecimal applyCommerceDiscounts(
			BigDecimal commercePrice, BigDecimal[] commerceDiscountLevels)
		throws PortalException {

		BigDecimal discountedAmount = commercePrice;

		for (BigDecimal commerceDiscountLevel : commerceDiscountLevels) {
			if ((commerceDiscountLevel == null) ||
				BigDecimalUtil.isZero(commerceDiscountLevel)) {

				continue;
			}

			BigDecimal currentDiscountAmount = discountedAmount.multiply(
				commerceDiscountLevel);

			currentDiscountAmount = currentDiscountAmount.divide(_ONE_HUNDRED);

			discountedAmount = discountedAmount.subtract(currentDiscountAmount);
		}

		return discountedAmount;
	}

	@Override
	public String getCommerceDiscountApplicationStrategyKey() {
		return CommercePricingConstants.DISCOUNT_CHAIN_METHOD;
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

}