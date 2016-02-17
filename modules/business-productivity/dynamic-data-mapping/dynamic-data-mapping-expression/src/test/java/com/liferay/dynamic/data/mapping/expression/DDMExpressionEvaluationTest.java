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

package com.liferay.dynamic.data.mapping.expression;

import com.liferay.dynamic.data.mapping.expression.internal.DDMExpressionFactoryImpl;
import com.liferay.portal.kernel.util.MathUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class DDMExpressionEvaluationTest {

	@Test
	public void testEvaluateBasicBooleanEqualsExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("var1 == TRUE");

		ddmExpression.setBooleanVariableValue("var1", true);

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateBasicBooleanNotEqualsExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("var1 != TRUE");

		ddmExpression.setBooleanVariableValue("var1", true);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateBetweenExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression1 =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"between(22, 20, 25)");

		Assert.assertTrue(ddmExpression1.evaluate());

		DDMExpression<Boolean> ddmExpression2 =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"between(19, 20, 25)");

		Assert.assertFalse(ddmExpression2.evaluate());
	}

	@Test(expected = DDMExpressionEvaluationException.class)
	public void testEvaluateBlankExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("");

		ddmExpression.setIntegerVariableValue("var1", 5);
		ddmExpression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateConcatExpression1() throws Exception {
		DDMExpression<String> ddmExpression =
			_ddmExpressionFactory.createStringDDMExpression(
				"concat(var1, var2)");

		ddmExpression.setStringVariableValue("var1", "Life");
		ddmExpression.setStringVariableValue("var2", "ray");

		Assert.assertEquals("Liferay", ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateConcatExpression2() throws Exception {
		DDMExpression<String> ddmExpression =
			_ddmExpressionFactory.createStringDDMExpression(
				"concat(\"Hello \", \"World!\")");

		Assert.assertEquals("Hello World!", ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateContainsExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"contains(var1, var2)");

		ddmExpression.setStringVariableValue("var1", "Liferay");
		ddmExpression.setStringVariableValue("var2", "ray");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateDoubleExpression() throws Exception {
		DDMExpression<Double> ddmExpression =
			_ddmExpressionFactory.createDoubleDDMExpression(
				"var1 + var2 + var3");

		double var1 = 5.5;

		ddmExpression.setDoubleVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		double var2 = var1 + 3;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)(var1 + var2 + var3), ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression1() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("var1 == var2");

		ddmExpression.setBooleanVariableValue("var1", true);
		ddmExpression.setBooleanVariableValue("var2", false);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression2() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"equals(var1, var2)");

		ddmExpression.setStringVariableValue("var1", "Liferay");
		ddmExpression.setStringVariableValue("var2", "Liferay");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateEqualsExpression3() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"equals(var1, \"Liferay\")");

		ddmExpression.setStringVariableValue("var1", "Liferay");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateFalseConstantExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("FALSE");

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateFloatExpression() throws Exception {
		DDMExpression<Float> ddmExpression =
			_ddmExpressionFactory.createFloatDDMExpression(
				"var1 + var2 + var3");

		float var1 = 5.5f;

		ddmExpression.setFloatVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		float var2 = var1 + 3;

		float var3 = var1 + var2;

		Assert.assertEquals(
			(Float)(var1 + var2 + var3), ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateGreaterThanExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("var1 > var2");

		ddmExpression.setIntegerVariableValue("var1", 5);
		ddmExpression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateIntegerExpression() throws Exception {
		DDMExpression<Integer> ddmExpression =
			_ddmExpressionFactory.createIntegerDDMExpression(
				"var1 + var2 + var3");

		int var1 = 5;

		ddmExpression.setIntegerVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		int var2 = var1 + 3;

		int var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (int)ddmExpression.evaluate());
	}

	@Test(expected = DDMExpressionEvaluationException.class)
	public void testEvaluateInvalidExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("var1 >=+P var2");

		ddmExpression.setIntegerVariableValue("var1", 5);
		ddmExpression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateIsEmailAddressExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				"isEmailAddress(var1)");

		ddmExpression.setStringVariableValue("var1", "invalid_email");

		Assert.assertFalse(ddmExpression.evaluate());

		ddmExpression.setStringVariableValue("var1", "test@liferay.com");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateIsURLExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("isURL(var1)");

		ddmExpression.setStringVariableValue("var1", "invalid_url");

		Assert.assertFalse(ddmExpression.evaluate());

		ddmExpression.setStringVariableValue("var1", "http://www.liferay.com");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateLongExpression() throws Exception {
		DDMExpression<Long> ddmExpression =
			_ddmExpressionFactory.createLongDDMExpression("var1 + var2 + var3");

		long var1 = 5l;

		ddmExpression.setLongVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(var1 + var2 + var3, (long)ddmExpression.evaluate());
	}

	@Test(expected = DDMExpressionEvaluationException.class)
	public void testEvaluateNullExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(null);

		ddmExpression.setIntegerVariableValue("var1", 5);
		ddmExpression.setIntegerVariableValue("var2", 6);

		Assert.assertFalse(ddmExpression.evaluate());
	}

	@Test
	public void testEvaluateTrueConstantExpression() throws Exception {
		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression("TRUE");

		Assert.assertTrue(ddmExpression.evaluate());
	}

	@Test
	public void testSumWithDoubleValues() throws Exception {
		DDMExpression<Double> ddmExpression =
			_ddmExpressionFactory.createDoubleDDMExpression(
				"sum(var1, var2, var3)");

		double var1 = 5.5;

		ddmExpression.setDoubleVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3.5");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		double var2 = var1 + 3.5;

		double var3 = var1 + var2;

		Assert.assertEquals(
			(Double)MathUtil.sum(var1, var2, var3), ddmExpression.evaluate());
	}

	@Test
	public void testSumWithLongValues() throws Exception {
		DDMExpression<Long> ddmExpression =
			_ddmExpressionFactory.createLongDDMExpression(
				"sum(var1, var2, var3)");

		long var1 = 5;

		ddmExpression.setLongVariableValue("var1", var1);

		ddmExpression.setExpressionStringVariableValue("var2", "var1 + 3");
		ddmExpression.setExpressionStringVariableValue("var3", "var2 + var1");

		long var2 = var1 + 3;

		long var3 = var1 + var2;

		Assert.assertEquals(
			MathUtil.sum(var1, var2, var3), (long)ddmExpression.evaluate());
	}

	private final DDMExpressionFactory _ddmExpressionFactory =
		new DDMExpressionFactoryImpl();

}