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

package com.liferay.structured.content.apio.internal.architect.filter.expression;

import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.olingo.commons.api.edm.EdmEnumType;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.core.edm.primitivetype.EdmString;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.queryoption.expression.BinaryOperatorKind;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitor;
import org.apache.olingo.server.api.uri.queryoption.expression.Literal;
import org.apache.olingo.server.api.uri.queryoption.expression.Member;
import org.apache.olingo.server.api.uri.queryoption.expression.MethodKind;
import org.apache.olingo.server.api.uri.queryoption.expression.UnaryOperatorKind;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImpl implements ExpressionVisitor<Expression> {

	@Override
	public Expression visitAlias(String alias) {
		throw new UnsupportedOperationException("Unsupported visitAlias");
	}

	@Override
	public Expression visitBinaryOperator(
		BinaryOperatorKind binaryOperatorKind,
		Expression leftBinaryOperationExpression,
		Expression rightBinaryOperationExpression) {

		if (binaryOperatorKind == BinaryOperatorKind.EQ) {
			return new BinaryExpressionImpl(
				leftBinaryOperationExpression, BinaryExpression.Operation.EQ,
				rightBinaryOperationExpression);
		}

		throw new UnsupportedOperationException(
			"Unsupported visitBinaryExpressionOperation with operator " +
				binaryOperatorKind);
	}

	@Override
	public Expression visitEnum(EdmEnumType edmEnumType, List<String> list) {
		throw new UnsupportedOperationException("Unsupported visitEnum");
	}

	@Override
	public Expression visitLambdaExpression(
		String lambdaFunction, String lambdaVariable,
		org.apache.olingo.server.api.uri.queryoption.expression.Expression
			expression) {

		throw new UnsupportedOperationException(
			"Unsupported visitLambdaExpression");
	}

	@Override
	public Expression visitLambdaReference(String lambdaReference) {
		throw new UnsupportedOperationException(
			"Unsupported visitLambdaReference");
	}

	@Override
	public Expression visitLiteral(Literal literal) {
		EdmType type = literal.getType();

		if (type instanceof EdmString) {
			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.STRING);
		}

		throw new UnsupportedOperationException(
			"Unsupported visitLiteral with type " + type);
	}

	@Override
	public Expression visitMember(Member member) {
		UriInfoResource uriInfoResource = member.getResourcePath();

		List<UriResource> uriResources = uriInfoResource.getUriResourceParts();

		Stream<UriResource> stream = uriResources.stream();

		List<String> list = stream.map(
			UriResource::getSegmentValue
		).collect(
			Collectors.toList()
		);

		return new MemberExpressionImpl(list);
	}

	@Override
	public Expression visitMethodCall(
		MethodKind methodKind, List<Expression> expressions) {

		throw new UnsupportedOperationException("Unsupported visitMethodCall");
	}

	@Override
	public Expression visitTypeLiteral(EdmType edmType) {
		throw new UnsupportedOperationException("Unsupported visitTypeLiteral");
	}

	@Override
	public Expression visitUnaryOperator(
		UnaryOperatorKind unaryOperatorKind, Expression expression) {

		throw new UnsupportedOperationException(
			"Unsupported visitUnaryOperator");
	}

}