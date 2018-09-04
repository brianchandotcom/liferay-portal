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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author David Arques
 */
public class ExpressionParserTest {

	@Before
	public void setUp() {
		_expressionParser = new ExpressionParserImpl();

		_expressionParser.activate();
	}

	@Test
	public void testParseNonexistingField() {
		String expressionString = "(nonExistingField eq 'value')";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _expressionParser.parse(expressionString)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from expression '%s': 'Unknown " +
					"property.'",
				expressionString));
	}

	@Test
	public void testParseWithEmptyFilter() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _expressionParser.parse("")
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Expression is empty");
	}

	@Test
	public void testParseWithNoSingleQuotes() {
		String expressionString = "(title eq title1)";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _expressionParser.parse(expressionString)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from expression '%s': 'Unknown " +
					"property.'",
				expressionString));
	}

	@Test
	public void testParseWithNullExpression() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _expressionParser.parse(null)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Expression is empty");
	}

	@Test
	public void testParseWithSingleQuotes() throws ExpressionVisitException {
		Expression expression = _expressionParser.parse("title eq 'title1'");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.EQ, binaryExpression.getOperation());
		Assert.assertEquals(
			"[title]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'title1'",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithSingleQuotesAndParentheses()
		throws ExpressionVisitException {

		Expression expression = _expressionParser.parse("(title eq 'title1')");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.EQ, binaryExpression.getOperation());
		Assert.assertEquals(
			"[title]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'title1'",
			binaryExpression.getRightOperationExpression().toString());
	}

	private ExpressionParserImpl _expressionParser;

}