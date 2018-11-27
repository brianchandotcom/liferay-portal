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

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.olingo.commons.api.edm.EdmEnumType;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.core.edm.primitivetype.EdmBoolean;
import org.apache.olingo.commons.core.edm.primitivetype.EdmByte;
import org.apache.olingo.commons.core.edm.primitivetype.EdmDate;
import org.apache.olingo.commons.core.edm.primitivetype.EdmDateTimeOffset;
import org.apache.olingo.commons.core.edm.primitivetype.EdmDecimal;
import org.apache.olingo.commons.core.edm.primitivetype.EdmDouble;
import org.apache.olingo.commons.core.edm.primitivetype.EdmInt16;
import org.apache.olingo.commons.core.edm.primitivetype.EdmInt32;
import org.apache.olingo.commons.core.edm.primitivetype.EdmInt64;
import org.apache.olingo.commons.core.edm.primitivetype.EdmSByte;
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
		throw new UnsupportedOperationException("Alias: " + alias);
	}

	@Override
	public Expression visitBinaryOperator(
		BinaryOperatorKind binaryOperatorKind,
		Expression leftBinaryOperationExpression,
		Expression rightBinaryOperationExpression) {

		Optional<BinaryExpression.Operation> binaryExpressionOperationOptional =
			_getOperationOptional(binaryOperatorKind);

		return binaryExpressionOperationOptional.map(
			binaryExpressionOperation -> new BinaryExpressionImpl(
				leftBinaryOperationExpression, binaryExpressionOperation,
				rightBinaryOperationExpression)
		).orElseThrow(
			() -> new UnsupportedOperationException(
				"Binary operator: " + binaryOperatorKind)
		);
	}

	@Override
	public Expression visitEnum(EdmEnumType edmEnumType, List<String> list) {
		throw new UnsupportedOperationException(
			"Enum: " + StringUtil.merge(edmEnumType.getMemberNames()));
	}

	@Override
	public Expression visitLambdaExpression(
		String lambdaFunction, String lambdaVariable,
		org.apache.olingo.server.api.uri.queryoption.expression.Expression
			expression) {

		throw new UnsupportedOperationException(
			"Lambda expression: " + lambdaFunction);
	}

	@Override
	public Expression visitLambdaReference(String lambdaReference) {
		throw new UnsupportedOperationException(
			"Lambda reference: " + lambdaReference);
	}

	@Override
	public Expression visitLiteral(Literal literal) {
		EdmType edmType = literal.getType();

		if (edmType instanceof EdmBoolean) {
			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.BOOLEAN);
		}
		else if (edmType instanceof EdmByte || edmType instanceof EdmInt16 ||
				 edmType instanceof EdmInt32 || edmType instanceof EdmInt64 ||
				 edmType instanceof EdmSByte) {

			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.INTEGER);
		}
		else if (edmType instanceof EdmDate ||
				 edmType instanceof EdmDateTimeOffset) {

			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.DATE);
		}
		else if (edmType instanceof EdmDecimal ||
				 edmType instanceof EdmDouble) {

			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.DOUBLE);
		}
		else if (edmType instanceof EdmString) {
			return new LiteralExpressionImpl(
				literal.getText(), LiteralExpression.Type.STRING);
		}

		throw new UnsupportedOperationException(
			"Literal: " + edmType.getFullQualifiedName());
	}

	@Override
	public Expression visitMember(Member member) {
		UriInfoResource uriInfoResource = member.getResourcePath();

		List<UriResource> uriResources = uriInfoResource.getUriResourceParts();

		Stream<UriResource> stream = uriResources.stream();

		List<String> resourcePath = stream.map(
			UriResource::getSegmentValue
		).collect(
			Collectors.toList()
		);

		return new MemberExpressionImpl(resourcePath);
	}

	@Override
	public Expression visitMethodCall(
		MethodKind methodKind, List<Expression> expressions) {

		if (methodKind == MethodKind.CONTAINS) {
			return new MethodExpressionImpl(
				expressions, MethodExpression.Type.CONTAINS);
		}

		throw new UnsupportedOperationException("Method call: " + methodKind);
	}

	@Override
	public Expression visitTypeLiteral(EdmType edmType) {
		throw new UnsupportedOperationException(
			"Type literal: " + edmType.getKind());
	}

	@Override
	public Expression visitUnaryOperator(
		UnaryOperatorKind unaryOperatorKind, Expression expression) {

		if (unaryOperatorKind == UnaryOperatorKind.NOT) {
			return new UnaryExpressionImpl(
				expression, UnaryExpression.Operation.NOT);
		}

		throw new UnsupportedOperationException(
			"Unary operator: " + unaryOperatorKind);
	}

	private Optional<BinaryExpression.Operation> _getOperationOptional(
		BinaryOperatorKind binaryOperatorKind) {

		if (binaryOperatorKind == BinaryOperatorKind.AND) {
			return Optional.of(BinaryExpression.Operation.AND);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.EQ) {
			return Optional.of(BinaryExpression.Operation.EQ);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.GE) {
			return Optional.of(BinaryExpression.Operation.GE);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.GT) {
			return Optional.of(BinaryExpression.Operation.GT);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.LE) {
			return Optional.of(BinaryExpression.Operation.LE);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.LT) {
			return Optional.of(BinaryExpression.Operation.LT);
		}
		else if (binaryOperatorKind == BinaryOperatorKind.OR) {
			return Optional.of(BinaryExpression.Operation.OR);
		}

		return Optional.empty();
	}

}