/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.option;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Igor Belslic
 */
public class CommerceOptionValueTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuilder() {
		CommerceOptionValue.Builder builder = new CommerceOptionValue.Builder();

		_assertEmptyCommerceOptionValue(builder.build());

		builder.cpInstanceId(20000);
		builder.optionKey("test-option-key");
		builder.optionValueKey("test-option-value-key");

		BigDecimal price = new BigDecimal(1977);

		builder.price(price);

		builder.priceType("test-price-type");

		BigDecimal quantity = BigDecimal.valueOf(1977);

		builder.quantity(quantity);

		CommerceOptionValue commerceOptionValue = builder.build();

		Assert.assertEquals(20000, commerceOptionValue.getCPInstanceId());
		Assert.assertEquals(
			"test-option-key", commerceOptionValue.getOptionKey());
		Assert.assertEquals(
			"test-option-value-key", commerceOptionValue.getOptionValueKey());
		Assert.assertEquals(price, commerceOptionValue.getPrice());
		Assert.assertEquals(
			"test-price-type", commerceOptionValue.getPriceType());
		Assert.assertEquals(quantity, commerceOptionValue.getQuantity());
	}

	private void _assertEmptyCommerceOptionValue(
		CommerceOptionValue commerceOptionValue) {

		Assert.assertEquals(0, commerceOptionValue.getCPInstanceId());
		Assert.assertNull(commerceOptionValue.getOptionKey());
		Assert.assertNull(commerceOptionValue.getOptionValueKey());
		Assert.assertNull(commerceOptionValue.getPrice());
		Assert.assertNull(commerceOptionValue.getPriceType());
		Assert.assertEquals(BigDecimal.ZERO, commerceOptionValue.getQuantity());
	}

}