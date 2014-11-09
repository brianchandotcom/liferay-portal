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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class VariableNamesExtractorTest {

	@Test
	public void testExpressionWithMultipleVariables() {
		String expressionString = "((a + b) * variable_3) / var4  > 5";

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		Assert.assertEquals(variableNames.size(), 4);
		Assert.assertEquals("a", variableNames.get(0));
		Assert.assertEquals("b", variableNames.get(1));
		Assert.assertEquals("variable_3", variableNames.get(2));
		Assert.assertEquals("var4", variableNames.get(3));
	}

	@Test
	public void testExpressionWithNoVariables() {
		String expressionString = "(1 + 2) * 3";

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		Assert.assertEquals(variableNames.size(), 0);
	}

	@Test
	public void testExpressionWithOneVariable() {
		String expressionString = "((1 + 2) * variable) > 5";

		List<String> variableNames = _variableNamesExtractor.extract(
			expressionString);

		Assert.assertEquals(variableNames.size(), 1);

		Assert.assertEquals("variable", variableNames.get(0));
	}

	private final VariableNamesExtractor _variableNamesExtractor =
		new VariableNamesExtractor();

}