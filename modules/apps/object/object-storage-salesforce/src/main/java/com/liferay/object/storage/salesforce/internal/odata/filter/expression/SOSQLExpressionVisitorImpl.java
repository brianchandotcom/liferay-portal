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

package com.liferay.object.storage.salesforce.internal.odata.filter.expression;

import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;

import java.util.List;
import java.util.Objects;

/**
 * @author Paulo Albuquerque
 */
public class SOSQLExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public SOSQLExpressionVisitorImpl(
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService) {

		_objectDefinitionId = objectDefinitionId;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public String visitBinaryExpressionOperation(
			BinaryExpression.Operation operation, Object left, Object right)
		throws ExpressionVisitException {

		StringBuilder sb = new StringBuilder();

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			_buildBinaryOperation(left, right, " AND ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			_buildBinaryOperation(left, right, " OR ", sb);
		}
		else {
			left = _getObjectFieldExternalReferenceCode(left);
		}

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			_buildBinaryOperation(left, right, " = ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			_buildBinaryOperation(left, right, " >= ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			_buildBinaryOperation(left, right, " > ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			_buildBinaryOperation(left, right, " <= ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			_buildBinaryOperation(left, right, " < ", sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			_buildBinaryOperation(left, right, " != ", sb);
		}

		if (Validator.isNull(sb.toString())) {
			throw new UnsupportedOperationException();
		}

		return sb.toString();
	}

	@Override
	public Object visitListExpressionOperation(
			ListExpression.Operation operation, Object left, List<Object> right)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression)
		throws ExpressionVisitException {

		if (!Objects.equals(
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			throw new UnsupportedOperationException();
		}

		return StringUtil.replace(
			literalExpression.getText(), StringPool.DOUBLE_APOSTROPHE,
			StringPool.APOSTROPHE);
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException {

		Expression expression = memberExpression.getExpression();

		return expression.accept(this);
	}

	@Override
	public Object visitMethodExpression(
			List<Object> expressions, MethodExpression.Type type)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Object visitPrimitivePropertyExpression(
			PrimitivePropertyExpression primitivePropertyExpression)
		throws ExpressionVisitException {

		return primitivePropertyExpression.getName();
	}

	private void _buildBinaryOperation(
		Object left, Object right, String operator, StringBuilder sb) {

		sb.append(left);
		sb.append(operator);
		sb.append(right);
	}

	private Object _getObjectFieldExternalReferenceCode(Object fieldName) {
		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_objectDefinitionId, (String)fieldName);

		if (objectField != null) {
			return objectField.getExternalReferenceCode();
		}

		throw new UnsupportedOperationException();
	}

	private final long _objectDefinitionId;
	private final ObjectFieldLocalService _objectFieldLocalService;

}