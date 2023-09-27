/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionRegistry;
import com.liferay.dynamic.data.mapping.expression.internal.functions.PowFunction;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMExpressionFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpDDMExpressionFunctionRegistry();
	}

	@Test
	public void testCreateDDMExpression() throws Exception {
		DDMExpression<BigDecimal> ddmExpression =
			_ddmExpressionFactoryImpl.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					"pow(2,3)"
				).build());

		BigDecimal bigDecimal = ddmExpression.evaluate();

		Assert.assertEquals(0, bigDecimal.compareTo(new BigDecimal(8)));
	}

	private void _setUpDDMExpressionFunctionRegistry() throws Exception {
		DDMExpressionFunctionRegistry ddmExpressionFunctionRegistry =
			Mockito.mock(DDMExpressionFunctionRegistry.class);

		Mockito.when(
			ddmExpressionFunctionRegistry.getDDMExpressionFunctionFactories(
				Mockito.any())
		).thenReturn(
			HashMapBuilder.<String, DDMExpressionFunctionFactory>put(
				"pow", () -> new PowFunction()
			).build()
		);

		ReflectionTestUtil.setFieldValue(
			_ddmExpressionFactoryImpl, "ddmExpressionFunctionRegistry",
			ddmExpressionFunctionRegistry);
	}

	private final DDMExpressionFactoryImpl _ddmExpressionFactoryImpl =
		new DDMExpressionFactoryImpl();

}