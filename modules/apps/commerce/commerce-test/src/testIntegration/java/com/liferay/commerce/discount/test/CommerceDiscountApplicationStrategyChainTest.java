/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.discount.application.strategy.CommerceDiscountApplicationStrategy;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
@RunWith(Arquillian.class)
public class CommerceDiscountApplicationStrategyChainTest {

	@ClassRule
	@Rule
	public static AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testApplyDiscountsChainMethod() throws Exception {
		frutillaRule.scenario(
			"The discounts are applied to the price using the chain method"
		).given(
			"A price and four level discounts"
		).when(
			"The discounted price is calculated"
		).then(
			"The chain method is applied to the price"
		);

		BigDecimal price = BigDecimal.valueOf(100);

		BigDecimal[] commerceDiscountLevels = {
			BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN
		};

		BigDecimal discountedPrice =
			_commerceDiscountApplicationStrategy.applyCommerceDiscounts(
				price, commerceDiscountLevels);

		BigDecimal expectedPrice = price.multiply(
			_getChainableLevelDiscount(commerceDiscountLevels[0]));

		expectedPrice = expectedPrice.multiply(
			_getChainableLevelDiscount(commerceDiscountLevels[1]));

		expectedPrice = expectedPrice.multiply(
			_getChainableLevelDiscount(commerceDiscountLevels[2]));

		expectedPrice = expectedPrice.multiply(
			_getChainableLevelDiscount(commerceDiscountLevels[3]));

		Assert.assertEquals(
			expectedPrice.doubleValue(), discountedPrice.doubleValue(), 0);
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	private BigDecimal _getChainableLevelDiscount(BigDecimal value) {
		BigDecimal valuePercentage = value.divide(_ONE_HUNDRED);

		return _ONE.subtract(valuePercentage);
	}

	private static final BigDecimal _ONE = BigDecimal.valueOf(1);

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	@Inject(
		filter = "component.name=com.liferay.commerce.discount.internal.application.strategy.ChainCommerceDiscountApplicationStrategyImpl"
	)
	private CommerceDiscountApplicationStrategy
		_commerceDiscountApplicationStrategy;

}