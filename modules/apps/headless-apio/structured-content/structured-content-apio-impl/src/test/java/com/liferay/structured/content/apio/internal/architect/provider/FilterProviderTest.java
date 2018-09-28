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

package com.liferay.structured.content.apio.internal.architect.provider;

import com.liferay.structured.content.apio.architect.entity.EntityField;
import com.liferay.structured.content.apio.architect.entity.EntityModel;
import com.liferay.structured.content.apio.architect.filter.InvalidFilterException;
import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class FilterProviderTest {

	@Test
	public void testParseNonexistingField() {
		String filterString = "(nonExistingField eq 'value')";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse(filterString)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from filter '%s': 'Unknown property.'",
				filterString));
	}

	@Test
	public void testParseWithEmptyFilter() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse("")
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Filter is null");
	}

	@Test
	public void testParseWithEqBinaryExpressionWithDate() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse("dateExternal ge 2012-05-29")
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageContaining("Incompatible types");
	}

	@Test
	public void testParseWithEqBinaryExpressionWithDateTimeOffset()
		throws ExpressionVisitException {

		Expression expression = _filterParserImpl.parse(
			"dateExternal ge 2012-05-29T09:13:28Z");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.GE, binaryExpression.getOperation());
		Assert.assertEquals(
			"[dateExternal]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"2012-05-29T09:13:28Z",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithEqBinaryExpressionWithNoSingleQuotes() {
		String filterString = "(fieldExternal eq value)";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse(filterString)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessageStartingWith(
			String.format(
				"Invalid query computed from filter '%s': 'Unknown property.'",
				filterString));
	}

	@Test
	public void testParseWithEqBinaryExpressionWithSingleQuotes()
		throws ExpressionVisitException {

		Expression expression = _filterParserImpl.parse(
			"fieldExternal eq 'value'");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.EQ, binaryExpression.getOperation());
		Assert.assertEquals(
			"[fieldExternal]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'value'",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithEqBinaryExpressionWithSingleQuotesAndParentheses()
		throws ExpressionVisitException {

		Expression expression = _filterParserImpl.parse(
			"(fieldExternal eq 'value')");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.EQ, binaryExpression.getOperation());
		Assert.assertEquals(
			"[fieldExternal]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'value'",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithGeBinaryExpression()
		throws ExpressionVisitException {

		Expression expression = _filterParserImpl.parse(
			"fieldExternal ge 'value'");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.GE, binaryExpression.getOperation());
		Assert.assertEquals(
			"[fieldExternal]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'value'",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithLeBinaryExpression()
		throws ExpressionVisitException {

		Expression expression = _filterParserImpl.parse(
			"fieldExternal le 'value'");

		Assert.assertNotNull(expression);

		BinaryExpression binaryExpression = (BinaryExpression)expression;

		Assert.assertEquals(
			BinaryExpression.Operation.LE, binaryExpression.getOperation());
		Assert.assertEquals(
			"[fieldExternal]",
			binaryExpression.getLeftOperationExpression().toString());
		Assert.assertEquals(
			"'value'",
			binaryExpression.getRightOperationExpression().toString());
	}

	@Test
	public void testParseWithNullExpression() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse(null)
		).isInstanceOf(
			InvalidFilterException.class
		);

		exception.hasMessage("Filter is null");
	}

	private static final FilterProvider _filterParserImpl = new FilterProvider(
		new EntityModel() {

			@Override
			public Map<String, EntityField> getEntityFieldsMap() {
				return Stream.of(
					new EntityField(
						"fieldExternal", EntityField.Type.STRING,
						locale -> "fieldInternal"),
					new EntityField(
						"dateExternal", EntityField.Type.DATE,
						locale -> "dateInternal")
				).collect(
					Collectors.toMap(EntityField::getName, Function.identity())
				);
			}

			@Override
			public String getName() {
				return "SomeEntityName";
			}

		});

}