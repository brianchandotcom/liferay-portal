/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.internal.odata.filter.expression;

import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
 * @author Maurice Sepe
 */
public class SugarCRMQueryExpressionVisitorImpl
	implements ExpressionVisitor<Object> {

	public SugarCRMQueryExpressionVisitorImpl(
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
			_buildBinaryOperationAndOr(left, "$and", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			_buildBinaryOperationAndOr(left, "$or", right, sb);
		}
		else {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				_objectDefinitionId, (String)left);

			if (objectField != null) {
				left = objectField.getExternalReferenceCode();
				right = StringUtil.unquote((String)right);
			}
		}

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			_buildBinaryOperation(left, "$equals", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			_buildBinaryOperation(left, "$gte", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			_buildBinaryOperation(left, "$gt", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			_buildBinaryOperation(left, "$lte", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			_buildBinaryOperation(left, "$lt", right, sb);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			_buildBinaryOperation(left, "$not_equals", right, sb);
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
				LiteralExpression.Type.BOOLEAN, literalExpression.getType()) &&
			!Objects.equals(
				LiteralExpression.Type.DATE, literalExpression.getType()) &&
			!Objects.equals(
				LiteralExpression.Type.DATE_TIME,
				literalExpression.getType()) &&
			!Objects.equals(
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			throw new UnsupportedOperationException();
		}

		if (Objects.equals(
				LiteralExpression.Type.BOOLEAN, literalExpression.getType())) {

			return GetterUtil.getBoolean(literalExpression.getText());
		}

		if (Objects.equals(
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			return StringUtil.replace(
				literalExpression.getText(), StringPool.DOUBLE_APOSTROPHE,
				StringPool.APOSTROPHE);
		}

		return literalExpression.getText();
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
		Object left, String operator, Object right, StringBuilder sb) {

		sb.append(
			JSONUtil.put(
				left.toString(), JSONUtil.put(operator, right.toString())));
	}

	private void _buildBinaryOperationAndOr(
		Object left, String operator, Object right, StringBuilder sb) {

		try {
			JSONObject leftJSONObject = JSONFactoryUtil.createJSONObject(
				(String)left);

			JSONArray jsonArray = leftJSONObject.getJSONArray(operator);

			if (jsonArray == null) {
				jsonArray = JSONUtil.put(leftJSONObject);
			}

			jsonArray.put(JSONFactoryUtil.createJSONObject((String)right));

			sb.append(JSONUtil.put(operator, jsonArray));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SugarCRMQueryExpressionVisitorImpl.class);

	private final long _objectDefinitionId;
	private final ObjectFieldLocalService _objectFieldLocalService;

}