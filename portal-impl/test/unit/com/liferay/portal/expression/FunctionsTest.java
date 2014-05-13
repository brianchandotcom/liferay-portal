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

package com.liferay.portal.expression;

import org.junit.Test;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public class FunctionsTest extends BaseExpresssionTest {

	@Test
	public void testSum() throws Exception {

		long expectedResult01 = (long) (5 + (5 + 3) + ((5 + 3) + 5));
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData("sum(var1,var2,var3)");
		expressionTestData.addLongVariable("var1", null, 5L);
		expressionTestData.addLongVariable("var2", "var1 + 3", null);
		expressionTestData.addLongVariable("var3", "var2 + var1", null);
		expressionTestData.setReturnType(Long.class);
		expressionTestData.setExpectedResult(expectedResult01);

		verifyExpression(expressionTestData);

		double expectedResult02 = 5.5 + (5.5 + 3.5) + ((5.5 + 3.5) + 5.5);
		expressionTestData = ExpressionTestData.newExpressionTestData(
			"sum(var1,var2,var3)");
		expressionTestData.addDoubleVariable("var1", null, 5.5);
		expressionTestData.addDoubleVariable("var2", "var1 + 3.5", null);
		expressionTestData.addDoubleVariable("var3", "var2 + var1", null);
		expressionTestData.setReturnType(Double.class);
		expressionTestData.setExpectedResult(expectedResult02);

		verifyExpression(expressionTestData);
	}

}