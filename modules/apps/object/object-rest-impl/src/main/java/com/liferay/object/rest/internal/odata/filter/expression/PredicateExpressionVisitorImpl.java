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

package com.liferay.object.rest.internal.odata.filter.expression;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.expression.DefaultPredicate;
import com.liferay.petra.sql.dsl.spi.expression.Operand;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LambdaFunctionExpression;
import com.liferay.portal.odata.filter.expression.LambdaVariableExpression;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Marco Leo
 */
public class PredicateExpressionVisitorImpl
	implements ExpressionVisitor<Object> {

	public PredicateExpressionVisitorImpl(
		EntityModel entityModel, long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService) {

		this(
			entityModel, new HashMap<>(), objectDefinitionId,
			objectFieldLocalService);
	}

	@Override
	public Predicate visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		Optional<Predicate> predicateOptional = _getPredicateOptional(
			operation, left, right);

		return predicateOptional.orElseThrow(
			() -> new UnsupportedOperationException(
				"Unsupported method visitBinaryExpressionOperation with " +
					"operation " + operation));
	}

	@Override
	public Predicate visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		return (Predicate)lambdaFunctionExpression.accept(
			new PredicateExpressionVisitorImpl(
				_entityModel,
				Collections.singletonMap(
					lambdaFunctionExpression.getVariableName(),
					collectionPropertyExpression.getName()),
				_objectDefinitionId, _objectFieldLocalService));
	}

	@Override
	public Object visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variableName,
			Expression expression)
		throws ExpressionVisitException {

		return expression.accept(this);
	}

	@Override
	public Object visitLambdaVariableExpression(
		LambdaVariableExpression lambdaVariableExpression) {

		return _lambdaVariableExpressionFieldNames.get(
			lambdaVariableExpression.getVariableName());
	}

	@Override
	public Predicate visitListExpressionOperation(
			ListExpression.Operation operation, Object left, List<Object> right)
		throws ExpressionVisitException {

		if (Objects.equals(ListExpression.Operation.IN, operation)) {
			Column<?, Object> column =
				(Column<?, Object>)_objectFieldLocalService.getColumn(
					_objectDefinitionId, GetterUtil.getString(left));

			return column.in(right.toArray());
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitListExpressionOperation with operation " +
				operation);
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		if (Objects.equals(
				LiteralExpression.Type.BOOLEAN, literalExpression.getType())) {

			return GetterUtil.getBoolean(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.DATE, literalExpression.getType())) {

			return GetterUtil.getDate(
				literalExpression.getText(),
				DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd"));
		}
		else if (Objects.equals(
					LiteralExpression.Type.DOUBLE,
					literalExpression.getType())) {

			return GetterUtil.getDouble(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.INTEGER,
					literalExpression.getType())) {

			return GetterUtil.getInteger(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.NULL, literalExpression.getType())) {

			return null;
		}
		else if (Objects.equals(
					LiteralExpression.Type.STRING,
					literalExpression.getType())) {

			return StringUtil.unquote(literalExpression.getText());
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
		List<Object> expressions, MethodExpression.Type type) {

		if (type == MethodExpression.Type.CONTAINS) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method ",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _contains(expressions.get(0), expressions.get(1));
		}

		if (type == MethodExpression.Type.STARTS_WITH) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _startsWith(expressions.get(0), expressions.get(1));
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitMethodExpression with method type " +
				type);
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		return primitivePropertyExpression.getName();
	}

	@Override
	public Predicate visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (!Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			throw new UnsupportedOperationException(
				"Unsupported method visitUnaryExpressionOperation with " +
					"operation " + operation);
		}

		DefaultPredicate defaultPredicate = (DefaultPredicate)operand;

		if (Objects.equals(Operand.IN, defaultPredicate.getOperand())) {
			return new DefaultPredicate(
				defaultPredicate.getLeftExpression(), Operand.NOT_IN,
				defaultPredicate.getRightExpression());
		}

		return Predicate.not(defaultPredicate);
	}

	private PredicateExpressionVisitorImpl(
		EntityModel entityModel,
		Map<String, String> lambdaVariableExpressionFieldNames,
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService) {

		_entityModel = entityModel;
		_lambdaVariableExpressionFieldNames =
			lambdaVariableExpressionFieldNames;
		_objectDefinitionId = objectDefinitionId;
		_objectFieldLocalService = objectFieldLocalService;
	}

	private Predicate _contains(Object fieldName, Object fieldValue) {
		Column<?, ?> column = _objectFieldLocalService.getColumn(
			_objectDefinitionId, GetterUtil.getString(fieldName));

		return column.like(
			StringPool.PERCENT + fieldValue + StringPool.PERCENT);
	}

	private Optional<Predicate> _getPredicateOptional(
		BinaryExpression.Operation operation, Object left, Object right) {

		Predicate predicate = null;

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			predicate = Predicate.and((Predicate)left, (Predicate)right);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			predicate = Predicate.or((Predicate)left, (Predicate)right);
		}
		else {
			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				_objectDefinitionId, String.valueOf(left));

			if ((objectField != null) &&
				StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				predicate = _contains(left, right);
			}
		}

		if (predicate != null) {
			return Optional.of(predicate);
		}

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		EntityField entityField = entityFieldsMap.get(
			GetterUtil.getString(left));

		Column<?, Object> column =
			(Column<?, Object>)_objectFieldLocalService.getColumn(
				_objectDefinitionId, entityField.getFilterableName(null));

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			predicate = column.eq(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			predicate = column.gte(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			predicate = column.gt(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			predicate = column.lte(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			predicate = column.lt(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			predicate = column.neq(right);
		}
		else {
			return Optional.empty();
		}

		return Optional.of(predicate);
	}

	private Predicate _startsWith(Object fieldName, Object fieldValue) {
		Column<?, ?> column = _objectFieldLocalService.getColumn(
			_objectDefinitionId, GetterUtil.getString(fieldName));

		return column.like(fieldValue + StringPool.PERCENT);
	}

	private final EntityModel _entityModel;
	private Map<String, String> _lambdaVariableExpressionFieldNames;
	private final long _objectDefinitionId;
	private final ObjectFieldLocalService _objectFieldLocalService;

}