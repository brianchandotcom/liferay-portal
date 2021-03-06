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
		builder.quantity(1977);

		CommerceOptionValue commerceOptionValue = builder.build();

		Assert.assertEquals(20000, commerceOptionValue.getCPInstanceId());
		Assert.assertEquals(
			"test-option-key", commerceOptionValue.getOptionKey());
		Assert.assertEquals(
			"test-option-value-key", commerceOptionValue.getOptionValueKey());
		Assert.assertEquals(price, commerceOptionValue.getPrice());
		Assert.assertEquals(
			"test-price-type", commerceOptionValue.getPriceType());
		Assert.assertEquals(1977, commerceOptionValue.getQuantity());
	}

	private void _assertEmptyCommerceOptionValue(
		CommerceOptionValue commerceOptionValue) {

		Assert.assertEquals(0, commerceOptionValue.getCPInstanceId());
		Assert.assertNull(commerceOptionValue.getOptionKey());
		Assert.assertNull(commerceOptionValue.getOptionValueKey());
		Assert.assertNull(commerceOptionValue.getPrice());
		Assert.assertNull(commerceOptionValue.getPriceType());
		Assert.assertEquals(0, commerceOptionValue.getQuantity());
	}

}