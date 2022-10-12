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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class EqualsFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApply() {
		EqualsFunction equalsFunction = new EqualsFunction();

		Assert.assertFalse(equalsFunction.apply("FORMS", "forms"));
		Assert.assertFalse(equalsFunction.apply("forms&#39;", "forms'"));
		Assert.assertFalse(equalsFunction.apply(2.0D, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(2, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(2L, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(null, "forms"));
		Assert.assertFalse(equalsFunction.apply(null, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply("1", new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply("forms", "forms"));
		Assert.assertTrue(equalsFunction.apply(1.0D, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply(1, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply(1L, new BigDecimal(1)));
	}

}