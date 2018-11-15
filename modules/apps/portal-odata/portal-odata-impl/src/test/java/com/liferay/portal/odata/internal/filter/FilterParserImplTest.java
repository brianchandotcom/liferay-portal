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

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DoubleEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;

import java.util.List;
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
public class FilterParserImplTest {

	@Test
	public void testParseNonexistingField() {
		String filterString = "(nonExistingField eq 'value')";

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse(filterString)
		).isInstanceOf(
			ExpressionVisitException.class
		);

		exception.hasMessage("Unknown property.");
	}

	@Test
	public void testParseWithContainsMethod() throws ExpressionVisitException {
		Expression expression = _filterParserImpl.parse(
			"contains(fieldExternal, 'value')");

		Assert.assertNotNull(expression);

		MethodExpression methodExpression = (MethodExpression)expression;

		Assert.assertEquals(
			MethodExpression.Type.CONTAINS, methodExpression.getType());

		List<Expression> expressions = methodExpression.getExpressions();

		MemberExpression memberExpression1 = (MemberExpression)expressions.get(
			0);

		List<String> resourcePath = memberExpression1.getResourcePath();

		Assert.assertEquals("fieldExternal", resourcePath.get(0));

		LiteralExpression literalExpression =
			(LiteralExpression)expressions.get(1);

		Assert.assertEquals("'value'", literalExpression.getText());
	}

	@Test
	public void testParseWithContainsMethodAndDateType() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse(
				"contains(dateExternal, 2012-05-29T09:13:28Z)")
		).isInstanceOf(
			ExpressionVisitException.class
		);

		exception.hasMessage("Incompatible types.");
	}

	@Test
	public void testParseWithContainsMethodAndDoubleType() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse("contains(doubleExternal, 7)")
		).isInstanceOf(
			ExpressionVisitException.class
		);

		exception.hasMessage("Incompatible types.");
	}

	@Test
	public void testParseWithEmptyFilter() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse("")
		).isInstanceOf(
			ExpressionVisitException.class
		);

		exception.hasMessage("Filter is null");
	}

	@Test
	public void testParseWithEqBinaryExpressionWithDate() {
		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> _filterParserImpl.parse("dateExternal ge 2012-05-29")
		).isInstanceOf(
			ExpressionVisitException.class
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
			ExpressionVisitException.class
		);

		exception.hasMessage("Unknown property.");
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
			ExpressionVisitException.class
		);

		exception.hasMessage("Filter is null");
	}

	private static final FilterParserImpl _filterParserImpl =
		new FilterParserImpl(
			new EntityModel() {

				@Override
				public Map<String, EntityField> getEntityFieldsMap() {
					return Stream.of(
						new DateEntityField(
							"dateExternal", locale -> "dateInternal",
							locale -> "dateInternal"),
						new DoubleEntityField(
							"doubleExternal", locale -> "doubleInternal"),
						new StringEntityField(
							"fieldExternal", locale -> "fieldInternal")
					).collect(
						Collectors.toMap(
							EntityField::getName, Function.identity())
					);
				}

				@Override
				public String getName() {
					return "SomeEntityName";
				}

			});

}