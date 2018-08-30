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

import com.liferay.structured.content.apio.architect.filter.expression.Binary.Operation;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;

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
public class ODataExpressionToExpressionVisitor
	implements ExpressionVisitor<Expression> {

	@Override
	public Expression visitAlias(String s) {
		throw new UnsupportedOperationException("Unsupported visitAlias");
	}

	@Override
	public Expression visitBinaryOperator(
		BinaryOperatorKind binaryOperatorKind, Expression left,
		Expression right) {

		if (binaryOperatorKind == BinaryOperatorKind.EQ) {
			return new BinaryImpl(left, Operation.EQ, right);
		}

		throw new UnsupportedOperationException(
			"Unsupported visitBinaryOperator with operator " +
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
	public Expression visitLambdaReference(String s) {
		throw new UnsupportedOperationException(
			"Unsupported visitLambdaReference");
	}

	@Override
	public Expression visitLiteral(Literal literal) {
		EdmType type = literal.getType();

		if (type instanceof EdmString) {
			return new LiteralImpl(
				literal.getText(),
				com.liferay.structured.content.apio.architect.filter.expression.
					Literal.Type.STRING);
		}

		throw new UnsupportedOperationException(
			"Unsupported visitLiteral with type " + type);
	}

	@Override
	public Expression visitMember(Member member) {
		UriInfoResource uriInfoResource = member.getResourcePath();

		List<UriResource> uriResourceParts =
			uriInfoResource.getUriResourceParts();

		Stream<UriResource> stream = uriResourceParts.stream();

		List<String> list = stream.map(
			UriResource::getSegmentValue
		).collect(
			Collectors.toList()
		);

		return new MemberImpl(list);
	}

	@Override
	public Expression visitMethodCall(
		MethodKind methodKind, List<Expression> list) {

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