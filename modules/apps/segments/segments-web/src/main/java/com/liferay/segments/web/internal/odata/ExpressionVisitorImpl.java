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

package com.liferay.segments.web.internal.odata;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public ExpressionVisitorImpl(int groupCount, EntityModel entityModel) {
		_groupCount = groupCount;
		_entityModel = entityModel;
	}

	@Override
	public Object visitBinaryExpressionOperation(
			BinaryExpression.Operation operation, Object left, Object right)
		throws ExpressionVisitException {

		if (Objects.equals(BinaryExpression.Operation.AND, operation) ||
			Objects.equals(BinaryExpression.Operation.OR, operation)) {

			return _getConjunctionJSONObject(
				operation, (JSONObject)left, (JSONObject)right);
		}
		else if (Objects.equals(BinaryExpression.Operation.EQ, operation) ||
				 Objects.equals(BinaryExpression.Operation.GE, operation) ||
				 Objects.equals(BinaryExpression.Operation.GT, operation) ||
				 Objects.equals(BinaryExpression.Operation.LE, operation) ||
				 Objects.equals(BinaryExpression.Operation.LT, operation) ||
				 Objects.equals(BinaryExpression.Operation.NE, operation)) {

			return _getOperationJSONObject(
				String.valueOf(operation), left, right);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitBinaryExpressionOperation with " +
				"operation " + operation);
	}

	@Override
	public Object visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		CollectionEntityField collectionEntityField =
			(CollectionEntityField)entityFieldsMap.get(
				collectionPropertyExpression.getName());

		return lambdaFunctionExpression.accept(
			new ExpressionVisitorImpl(
				0,
				new EntityModel() {

					@Override
					public Map<String, EntityField> getEntityFieldsMap() {
						return Collections.singletonMap(
							lambdaFunctionExpression.getVariableName(),
							collectionEntityField.getEntityField());
					}

					@Override
					public String getName() {
						return collectionEntityField.getName();
					}

				}));
	}

	public Object visitComplexPropertyExpression(
		ComplexPropertyExpression complexPropertyExpression) {

		Map<String, EntityField> entityFieldsMap =
			_entityModel.getEntityFieldsMap();

		ComplexEntityField complexEntityField =
			(ComplexEntityField)entityFieldsMap.get(
				complexPropertyExpression.getName());

		Map<String, EntityField> complexEntityFieldFieldsMap =
			complexEntityField.getEntityFieldsMap();

		PropertyExpression propertyExpression =
			complexPropertyExpression.getPropertyExpression();

		EntityField entityField = complexEntityFieldFieldsMap.get(
			propertyExpression.getName());

		return complexEntityField.getName() + "/" + entityField.getName();
	}

	@Override
	public Object visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variable,
			Expression expression)
		throws ExpressionVisitException {

		if (type == LambdaFunctionExpression.Type.ANY) {
			return expression.accept(this);
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
			return _getOperationJSONObject(
				String.valueOf(operation), left, right);
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitListExpressionOperation with operation " +
				operation);
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression)
		throws ExpressionVisitException {

		return StringUtil.unquote(literalExpression.getText());
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

			return _getOperationJSONObject(
				String.valueOf(type), expressions.get(0), expressions.get(1));
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
	public JSONObject visitUnaryExpressionOperation(
		UnaryExpression.Operation operation, Object operand) {

		if (Objects.equals(UnaryExpression.Operation.NOT, operation)) {
			JSONObject jsonObject = (JSONObject)operand;

			jsonObject.put(
				"operatorName",
				StringUtil.lowerCase(
					UnaryExpression.Operation.NOT + "-" +
						jsonObject.getString("operatorName")));

			return jsonObject;
		}

		throw new UnsupportedOperationException(
			"Unsupported method visitUnaryExpressionOperation with operation " +
				operation);
	}

	private JSONObject _getConjunctionJSONObject(
		BinaryExpression.Operation operation, JSONObject leftJSONObject,
		JSONObject rightJSONObject) {

		String conjunctionName = leftJSONObject.getString("conjunctionName");

		_groupCount++;

		if (Validator.isNotNull(conjunctionName)) {
			String operationString = operation.toString();

			if (Objects.equals(
					conjunctionName.toLowerCase(LocaleUtil.ROOT),
					operationString.toLowerCase(LocaleUtil.ROOT))) {

				return JSONUtil.put(
					"conjunctionName",
					StringUtil.lowerCase(String.valueOf(operation))
				).put(
					"groupId", "group_" + _groupCount
				).put(
					"items",
					leftJSONObject.getJSONArray(
						"items"
					).put(
						rightJSONObject
					)
				);
			}
		}

		return JSONUtil.put(
			"conjunctionName", StringUtil.lowerCase(String.valueOf(operation))
		).put(
			"groupId", "group_" + _groupCount
		).put(
			"items", JSONUtil.putAll(leftJSONObject, rightJSONObject)
		);
	}

	private JSONObject _getOperationJSONObject(
		String operatorName, Object object, List<Object> fieldValues) {

		Stream<Object> stream = fieldValues.stream();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		stream.map(
			String::valueOf
		).forEach(
			value -> jsonArray.put(value)
		);

		return JSONUtil.put(
			"operatorName", StringUtil.lowerCase(operatorName)
		).put(
			"propertyName", _getPropertyName(object)
		).put(
			"value", jsonArray
		);
	}

	private JSONObject _getOperationJSONObject(
		String operatorName, Object object, Object fieldValue) {

		return JSONUtil.put(
			"operatorName", StringUtil.lowerCase(operatorName)
		).put(
			"propertyName", _getPropertyName(object)
		).put(
			"value", fieldValue
		);
	}

	private String _getPropertyName(Object object) {
		if (object instanceof EntityField) {
			EntityField entityField = (EntityField)object;

			return entityField.getName();
		}

		return String.valueOf(object);
	}

	private final EntityModel _entityModel;
	private int _groupCount;

}