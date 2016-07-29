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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpressionString() throws Exception {
		new DDMExpressionImpl<>(null, Number.class);
	}

	@Test
	public void testGetFunctionNames() throws Exception {
		DDMExpressionImpl ddmExpressionImpl = new DDMExpressionImpl<>(
			"pow(pow(log(y))) + sum(3, 4)", Number.class);

		Set<String> expectedFunctionNames = new HashSet<>(
			Arrays.asList("pow", "log", "sum"));

		Assert.assertEquals(
			expectedFunctionNames,
			ddmExpressionImpl.getExpressionFunctionNames());
	}

	@Test
	public void testGetVariableNames1() throws Exception {
		DDMExpressionImpl ddmExpressionImpl = new DDMExpressionImpl<>(
			"(var1 + var2_) * __var3", Number.class);

		Set<String> expectedVariableNames = new HashSet<>(
			Arrays.asList("var1", "var2_", "__var3"));

		Assert.assertEquals(
			expectedVariableNames,
			ddmExpressionImpl.getExpressionVariableNames());
	}

	@Test
	public void testGetVariableNames2() throws Exception {
		DDMExpressionImpl ddmExpressionImpl = new DDMExpressionImpl<>(
			"(((1+2)*(1-2/x))+log(1*6-y))", Number.class);

		Set<String> expectedVariableNames = new HashSet<>(
			Arrays.asList("x", "y"));

		Assert.assertEquals(
			expectedVariableNames,
			ddmExpressionImpl.getExpressionVariableNames());
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidExpressionSyntax1() throws Exception {
		new DDMExpressionImpl<>("((", Number.class);
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidExpressionSyntax2() throws Exception {
		new DDMExpressionImpl<>("(())", Number.class);
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidExpressionSyntax3() throws Exception {
		new DDMExpressionImpl<>(")", Number.class);
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidExpressionSyntax4() throws Exception {
		new DDMExpressionImpl<>("+-/i", Number.class);
	}

	@Test(expected = DDMExpressionException.InvalidSyntax.class)
	public void testInvalidExpressionSyntax5() throws Exception {
		new DDMExpressionImpl<>("-----(", Number.class);
	}

	@Test
	public void testReturnTypeBooleanWithBoolean() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Boolean.class);

		Object result = ddmExpression.toRetunType(true);

		Assert.assertTrue(result instanceof Boolean);
		Assert.assertEquals(true, result);
	}

	@Test(expected = DDMExpressionException.IncompatipleReturnType.class)
	public void testReturnTypeBooleanWithNumber() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Boolean.class);

		ddmExpression.toRetunType(RandomTestUtil.randomDouble());
	}

	@Test(expected = DDMExpressionException.IncompatipleReturnType.class)
	public void testReturnTypeBooleanWithString() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Boolean.class);

		ddmExpression.toRetunType(StringUtil.randomString());
	}

	@Test
	public void testReturnTypeDoubleWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Double.class);

		double result = (double)ddmExpression.toRetunType(1.5d);

		Assert.assertEquals(1.5d, result, 0.1);
	}

	@Test
	public void testReturnTypeFloatWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Float.class);

		float result = (float)ddmExpression.toRetunType(1.5d);

		Assert.assertEquals(1.5f, result, 0.1);
	}

	@Test
	public void testReturnTypeIntegerWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Integer.class);

		int result = (int)ddmExpression.toRetunType(1.2);

		Assert.assertEquals(1, result);
	}

	@Test
	public void testReturnTypeLongWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Long.class);

		long result = (long)ddmExpression.toRetunType(1.2);

		Assert.assertEquals(1L, result);
	}

	@Test(expected = DDMExpressionException.IncompatipleReturnType.class)
	public void testReturnTypeNumberWithBoolean() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Number.class);

		ddmExpression.toRetunType(false);
	}

	@Test
	public void testReturnTypeNumberWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Number.class);

		Object result = ddmExpression.toRetunType(1.2);

		Assert.assertTrue(result instanceof Number);
	}

	@Test(expected = DDMExpressionException.IncompatipleReturnType.class)
	public void testReturnTypeNumberWithString() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Number.class);

		ddmExpression.toRetunType(StringUtil.randomString());
	}

	@Test
	public void testReturnTypeObjectWithBoolean() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Object.class);

		Object result = ddmExpression.toRetunType(true);

		Assert.assertEquals(true, result);
	}

	@Test
	public void testReturnTypeObjectWithDouble() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Object.class);

		Object result = ddmExpression.toRetunType(1.0);

		Assert.assertEquals(1.0, result);
	}

	@Test
	public void testReturnTypeObjectWithString() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", Object.class);

		Object result = ddmExpression.toRetunType("Joe");

		Assert.assertEquals("Joe", result);
	}

	@Test
	public void testReturnTypeStringWithBoolean() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", String.class);

		Object result = ddmExpression.toRetunType(false);

		Assert.assertTrue(result instanceof String);
		Assert.assertEquals("false", result);
	}

	@Test
	public void testReturnTypeStringWithNumber() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", String.class);

		Object result = ddmExpression.toRetunType(42.0);

		Assert.assertTrue(result instanceof String);
		Assert.assertEquals("42.0", result);
	}

	@Test
	public void testReturnTypeStringWithString() throws Exception {
		DDMExpressionImpl ddmExpression = new DDMExpressionImpl<>(
			"true", String.class);

		Object result = ddmExpression.toRetunType("Joe");

		Assert.assertTrue(result instanceof String);
		Assert.assertEquals("Joe", result);
	}

}