/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class GetValueFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_getValueFunction = new GetValueFunction();
	}

	@Test
	public void testArray() {
		String[] expected = {"test1", "test2"};

		DefaultDDMExpressionFieldAccessor ddmExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(expected);

		ddmExpressionFieldAccessor.setGetFieldPropertyResponseFunction(
			getFieldPropertyRequest -> builder.build());

		_getValueFunction.setDDMExpressionFieldAccessor(
			ddmExpressionFieldAccessor);

		Object result = _getValueFunction.apply("field");

		Assert.assertEquals(expected, result);
	}

	@Test
	public void testNullObserver() {
		Object result = _getValueFunction.apply("field");

		Assert.assertEquals(StringPool.BLANK, result);
	}

	@Test
	public void testSingleValue() {
		DefaultDDMExpressionFieldAccessor ddmExpressionFieldAccessor =
			new DefaultDDMExpressionFieldAccessor();

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(new BigDecimal(10));

		ddmExpressionFieldAccessor.setGetFieldPropertyResponseFunction(
			getFieldPropertyRequest -> builder.build());

		_getValueFunction.setDDMExpressionFieldAccessor(
			ddmExpressionFieldAccessor);

		Object result = _getValueFunction.apply("field");

		Assert.assertEquals(new BigDecimal(10), result);
	}

	private GetValueFunction _getValueFunction;

}