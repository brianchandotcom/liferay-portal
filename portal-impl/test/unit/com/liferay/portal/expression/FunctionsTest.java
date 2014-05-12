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
		ExpressionTestData expressionTestData =
			ExpressionTestData.newExpressionTestData(
				"sum(variable1,variable2,variable3)")
				.usingLongVariable("variable1", null, 5L)
				.usingLongVariable("variable2", "variable1 + 3", null)
				.usingLongVariable("variable3", "variable2 + variable1",null)
				.withExpectedResult((long)(5 + (5 + 3) + ((5 + 3) + 5)));

		testExpression(expressionTestData);

		ExpressionTestData doubleExpressionTestData =
			ExpressionTestData.newExpressionTestData(
				"sum(variable1,variable2,variable3)")
				.usingDoubleVariable("variable1", null, 5.5)
				.usingDoubleVariable("variable2", "variable1 + 3.5", null)
				.usingDoubleVariable("variable3", "variable2 + variable1",null)
				.withExpectedResult(5.5 + (5.5 + 3.5) + ((5.5 + 3.5) + 5.5));

		testExpression(doubleExpressionTestData);
	}

}