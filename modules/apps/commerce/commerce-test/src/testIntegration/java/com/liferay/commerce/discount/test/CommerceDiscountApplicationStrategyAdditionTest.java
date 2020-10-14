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

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Alberti
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class CommerceDiscountApplicationStrategyAdditionTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testApplyDiscountsAdditionMethod() throws Exception {
		frutillaRule.scenario(
			"The discounts are applied to the price using the additive method"
		).given(
			"A price and four level discounts"
		).when(
			"The discounted price is calculated"
		).then(
			"The additive method is applied to the price"
		);

		BigDecimal price = BigDecimal.valueOf(100);

		BigDecimal[] commerceDiscountLevels = {
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN
		};

		BigDecimal discountedPrice =
			_commerceDiscountApplicationStrategy.applyCommerceDiscounts(
				price, commerceDiscountLevels);

		BigDecimal totalDiscount = BigDecimal.ZERO;

		for (BigDecimal commerceDiscountLevel : commerceDiscountLevels) {
			totalDiscount = totalDiscount.add(commerceDiscountLevel);
		}

		totalDiscount = totalDiscount.divide(_ONE_HUNDRED);

		BigDecimal totalDiscountPercentage = _ONE.subtract(totalDiscount);

		BigDecimal expectedPrice = price.multiply(totalDiscountPercentage);

		Assert.assertEquals(
			expectedPrice.doubleValue(), discountedPrice.doubleValue(), 0);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private static final BigDecimal _ONE = BigDecimal.valueOf(1);

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	@Inject(
		filter = "commerce.discount.application.strategy.key=" + CommercePricingConstants.DISCOUNT_ADDITION_METHOD
	)
	private CommerceDiscountApplicationStrategy
		_commerceDiscountApplicationStrategy;

}