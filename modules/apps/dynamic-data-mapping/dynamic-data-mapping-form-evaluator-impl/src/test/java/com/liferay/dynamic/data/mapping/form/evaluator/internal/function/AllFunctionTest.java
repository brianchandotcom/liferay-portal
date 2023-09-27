/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;
import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class AllFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_allFunction = new AllFunction(_ddmExpressionFactoryImpl);

		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFactoryImpl, "ddmExpressionFunctionRegistry",
			Mockito.mock(DDMExpressionFunctionRegistry.class));
	}

	@Test
	public void testArrayFalse() {
		Boolean result = _allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(0), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertFalse(result);
	}

	@Test
	public void testArrayTrue() {
		Boolean result = _allFunction.apply(
			"#value# >= 1",
			new BigDecimal[] {
				new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)
			});

		Assert.assertTrue(result);
	}

	@Test
	public void testEmptyArray() {
		Boolean result = _allFunction.apply("#value# >= 1", new BigDecimal[0]);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression1() {
		Boolean result = _allFunction.apply("#invalid# > 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testInvalidExpression2() {
		Boolean result = _allFunction.apply("#value# >>> 10", 11);

		Assert.assertFalse(result);
	}

	@Test
	public void testSingleValue() {
		Boolean result = _allFunction.apply("#value# >= 1", 2);

		Assert.assertTrue(result);
	}

	private static AllFunction _allFunction;
	private static final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();

}