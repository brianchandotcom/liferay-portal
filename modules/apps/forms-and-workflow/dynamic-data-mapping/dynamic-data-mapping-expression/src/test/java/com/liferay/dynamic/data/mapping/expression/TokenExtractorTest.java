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

import com.liferay.dynamic.data.mapping.expression.internal.TokenExtractor;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class TokenExtractorTest {

	@Test
	public void testExpressionWithConstantsOnly() throws Exception {
		String expressionString = "(1 + 2) * 3";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(3, variableMap.size());

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("1"));
		Assert.assertTrue(values.contains("2"));
		Assert.assertTrue(values.contains("3"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExpressionWithEmptyExpression() throws Exception {
		new TokenExtractor(StringPool.BLANK);
	}

	@Test
	public void testExpressionWithFunctionCall() throws Exception {
		String expressionString =
			"equals(name, \"Joe\") && isEmailAddress(email)";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(3, variableMap.size());

		Assert.assertEquals("name", variableMap.get("name"));
		Assert.assertEquals("email", variableMap.get("email"));

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("Joe"));
	}

	@Test
	public void testExpressionWithFunctionCallVariablesAndConstants()
		throws Exception {

		String expressionString = "(a + b) * 3456.12 > 10000 && isURL(field)";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(5, variableMap.size());

		Assert.assertEquals("a", variableMap.get("a"));
		Assert.assertEquals("b", variableMap.get("b"));
		Assert.assertEquals("field", variableMap.get("field"));

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("3456.12"));
		Assert.assertTrue(values.contains("10000"));
	}

	@Test
	public void testExpressionWithNoConstants() throws Exception {
		String expressionString = "(a / b) >= c";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(3, variableMap.size());

		Assert.assertEquals("a", variableMap.get("a"));
		Assert.assertEquals("b", variableMap.get("b"));
		Assert.assertEquals("c", variableMap.get("c"));
	}

	@Test(expected = DDMExpressionException.FunctionNotAllowed.class)
	public void testExpressionWithNotSupportedFunction() throws Exception {
		String expressionString = "round(a / b) && sqrt(99999)";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		tokenExtractor.getVariableMap();
	}

	@Test
	public void testExpressionWithNoVariablesAndConstants() throws Exception {
		String expressionString = "+ > >= && !=";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(0, variableMap.size());
	}

	@Test
	public void testExpressionWithRepeatedCharSequence() throws Exception {
		String expressionString = "11 + 111";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(2, variableMap.size());

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("11"));
		Assert.assertTrue(values.contains("111"));

		String variableName11 = getVariableName(variableMap, "11");
		String variableName111 = getVariableName(variableMap, "111");

		Assert.assertEquals(
			variableName11 + " + " + variableName111,
			tokenExtractor.getExpression());
	}

	@Test
	public void testExpressionWithStringConstants() throws Exception {
		String expressionString = "name != \"Joe\"";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(2, variableMap.size());

		Assert.assertEquals("name", variableMap.get("name"));

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("Joe"));
	}

	@Test
	public void testExpressionWithVariablesAndConstants() throws Exception {
		String expressionString = "((a + b) * variable_3) / var4  > 5";

		TokenExtractor tokenExtractor = new TokenExtractor(expressionString);

		Map<String, String> variableMap = tokenExtractor.getVariableMap();

		Assert.assertEquals(5, variableMap.size());
		Assert.assertEquals("a", variableMap.get("a"));
		Assert.assertEquals("b", variableMap.get("b"));
		Assert.assertEquals("variable_3", variableMap.get("variable_3"));
		Assert.assertEquals("var4", variableMap.get("var4"));

		Collection<String> values = variableMap.values();

		Assert.assertTrue(values.contains("5"));
	}

	protected String getVariableName(
		Map<String, String> variableMap, String value) {

		for (Map.Entry<String, String> entry : variableMap.entrySet()) {
			if (Objects.equals(entry.getValue(), value)) {
				return entry.getKey();
			}
		}

		return null;
	}

}