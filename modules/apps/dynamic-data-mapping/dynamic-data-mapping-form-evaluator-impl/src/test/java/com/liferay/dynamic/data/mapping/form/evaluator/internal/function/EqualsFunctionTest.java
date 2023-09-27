/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Assert.assertFalse(equalsFunction.apply(2, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(2.0D, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(2L, new BigDecimal(1)));
		Assert.assertFalse(equalsFunction.apply(null, "forms"));
		Assert.assertFalse(equalsFunction.apply(null, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply("1", new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply("forms", "forms"));
		Assert.assertTrue(equalsFunction.apply(1, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply(1.0D, new BigDecimal(1)));
		Assert.assertTrue(equalsFunction.apply(1L, new BigDecimal(1)));
	}

}