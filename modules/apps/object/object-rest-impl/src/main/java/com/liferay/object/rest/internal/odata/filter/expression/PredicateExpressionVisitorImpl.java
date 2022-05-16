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

import com.liferay.object.petra.sql.dsl.ObjectTableProvider;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
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
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;

import java.text.Format;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Marco Leo
 */
public class PredicateExpressionVisitorImpl
	implements ExpressionVisitor<Object> {

	public PredicateExpressionVisitorImpl(
		Format format, Locale locale, EntityModel entityModel,
		long objectDefinitionId, ObjectTableProvider objectTableProvider) {

		_format = format;
		_locale = locale;
		_entityModel = entityModel;
		_objectDefinitionId = objectDefinitionId;
		_objectTableProvider = objectTableProvider;
	}

	@Override
	public Predicate visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		Optional<Predicate> filterOptional = _getPredicateOptional(
			operation, left, right, _locale);

		return filterOptional.orElseThrow(
			() -> new UnsupportedOperationException(
				"Unsupported method visitBinaryExpressionOperation with " +
					"operation " + operation));
	}

	@Override
	public Object visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		return lambdaFunctionExpression.accept(
			new PredicateExpressionVisitorImpl(
				_format, _locale,
				_getLambdaEntityModel(
					lambdaFunctionExpression.getVariableName(),
					(CollectionEntityField)entityFieldsMap.get(
						collectionPropertyExpression.getName())),
				_objectDefinitionId, _objectTableProvider));
	}

	@Override
	public Object visitComplexPropertyExpression(
		ComplexPropertyExpression complexPropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ComplexEntityField complexEntityField =
			(ComplexEntityField)entityFieldsMap.get(
				complexPropertyExpression.getName());

		PropertyExpression propertyExpression =
			complexPropertyExpression.getPropertyExpression();

		Map<String, EntityField> complexEntityFieldEntityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		return complexEntityFieldEntityFieldsMap.get(
			propertyExpression.getName());
	}

	@Override
	public Object visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variable,
			Expression expression)
		throws ExpressionVisitException {

		if (type == LambdaFunctionExpression.Type.ANY) {
			return _any(expression);
		}

		throw new UnsupportedOperationException(
			"Unsupported type visitLambdaFunctionExpression with type " + type);
	}

	@Override
	public EntityField visitLambdaVariableExpression(
			LambdaVariableExpression lambdaVariableExpression)
		throws ExpressionVisitException {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		EntityField entityField = entityFieldsMap.get(
			lambdaVariableExpression.getVariableName());

		if (entityField == null) {
			throw new ExpressionVisitException(
				"Invoked visitLambdaVariableExpression when no entity field " +
					"is stored for lambda variable name " +
						lambdaVariableExpression.getVariableName());
		}

		return entityField;
	}

	@Override
	public Object visitListExpressionOperation(
			ListExpression.Operation operation, Object left, List<Object> right)
		throws ExpressionVisitException {

		if (operation == ListExpression.Operation.IN) {
			Object[] objects = new Object[right.size()];

			objects = right.toArray(objects);

			return ((Column)left).in(objects);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitListExpressionOperation with operation " +
				operation);
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {

		// TODO Implement all types conversions

		/*if (Objects.equals(
				LiteralExpression.Type.DATE, literalExpression.getType()) ||
			Objects.equals(
				LiteralExpression.Type.DATE_TIME,
				literalExpression.getType())) {

			return TO CONVERT TO DATE
		}

		else*/
		if (Objects.equals(
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			return _normalizeStringLiteral(literalExpression.getText());
		}
		else if (Objects.equals(
					LiteralExpression.Type.INTEGER,
					literalExpression.getType())) {

			return GetterUtil.getInteger(literalExpression.getText());
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

			return _contains(
				(EntityField)expressions.get(0), expressions.get(1), _locale);
		}

		if (type == MethodExpression.Type.STARTS_WITH) {
			if (expressions.size() != 2) {
				throw new UnsupportedOperationException(
					StringBundler.concat(
						"Unsupported method visitMethodExpression with method",
						"type ", type, " and ", expressions.size(), "params"));
			}

			return _startsWith(
				(EntityField)expressions.get(0), expressions.get(1), _locale);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitMethodExpression with method type " +
				type);
	}

	@Override
	public Object visitPrimitivePropertyExpression(
		PrimitivePropertyExpression primitivePropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		return entityFieldsMap.get(primitivePropertyExpression.getName());
	}

	@Override
	public Predicate visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			return Predicate.not((Predicate)operand);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitUnaryExpressionOperation with operation " +
				operation);
	}

	private Object _any(Expression expression) throws ExpressionVisitException {
		return expression.accept(this);
	}

	private Predicate _contains(
		EntityField entityField, Object fieldValue, Locale locale) {

		Column<?, ?> column = _getColumn(entityField.getFilterableName(locale));

		return column.like(
			"*" + entityField.getFilterableValue(fieldValue) + "*");
	}

	private Column<?, ?> _getColumn(String columnName) {
		Table<?> table = _getTable(columnName);

		return table.getColumn(columnName);
	}

	private EntityModel _getLambdaEntityModel(
		String variableName, CollectionEntityField collectionEntityField) {

		return new EntityModel() {

			@Override
			public Map<String, EntityField> getEntityFieldsMap() {
				return Collections.singletonMap(
					variableName, collectionEntityField.getEntityField());
			}

			@Override
			public String getName() {
				return collectionEntityField.getName();
			}

		};
	}

	private Optional<Predicate> _getPredicateOptional(
		BinaryExpression.Operation operation, Object left, Object right,
		Locale locale) {

		Predicate predicate = null;

		if (Objects.equals(BinaryExpression.Operation.AND, operation)) {
			predicate = Predicate.and((Predicate)left, (Predicate)right);
		}
		else if (Objects.equals(BinaryExpression.Operation.OR, operation)) {
			predicate = Predicate.or((Predicate)left, (Predicate)right);
		}

		if (predicate != null) {
			return Optional.of(predicate);
		}

		Column<?, Object> leftColumn = (Column<?, Object>)_getColumn(
			((EntityField)left).getFilterableName(locale));

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			predicate = leftColumn.eq(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			predicate = leftColumn.gte(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			predicate = leftColumn.gt(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			predicate = leftColumn.lte(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			predicate = leftColumn.lt(right);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			predicate = leftColumn.neq(right);
		}
		else {
			return Optional.empty();
		}

		return Optional.of(predicate);
	}

	private Table _getTable(String columnName) {
		try {
			return _objectTableProvider.getTable(
				_objectDefinitionId, columnName);
		}
		catch (PortalException portalException) {
			portalException.printStackTrace();
		}

		return null;
	}

	private Object _normalizeStringLiteral(String literal) {
		literal = StringUtil.toLowerCase(literal);

		literal = StringUtil.unquote(literal);

		return StringUtil.replace(
			literal, StringPool.DOUBLE_APOSTROPHE, StringPool.APOSTROPHE);
	}

	private Predicate _startsWith(
		EntityField entityField, Object fieldValue, Locale locale) {

		Column<?, ?> column = _getColumn(entityField.getFilterableName(locale));

		return column.like(entityField.getFilterableValue(fieldValue) + "*");
	}

	private final EntityModel _entityModel;
	private final Format _format;
	private final Locale _locale;
	private final long _objectDefinitionId;
	private final ObjectTableProvider _objectTableProvider;

}