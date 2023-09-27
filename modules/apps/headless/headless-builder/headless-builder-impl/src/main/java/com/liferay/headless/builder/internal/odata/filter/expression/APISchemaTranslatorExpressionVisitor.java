/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.odata.filter.expression;

import com.liferay.headless.builder.internal.odata.entity.APIPropertyEntityField;
import com.liferay.portal.odata.entity.CollectionEntityField;
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
import com.liferay.portal.odata.filter.expression.NavigationPropertyExpression;
import com.liferay.portal.odata.filter.expression.PrimitivePropertyExpression;
import com.liferay.portal.odata.filter.expression.PropertyExpression;
import com.liferay.portal.odata.filter.expression.UnaryExpression;
import com.liferay.portal.odata.filter.expression.factory.ExpressionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class APISchemaTranslatorExpressionVisitor
	implements ExpressionVisitor<Expression> {

	public APISchemaTranslatorExpressionVisitor(
		EntityModel entityModel, ExpressionFactory expressionFactory) {

		_expressionFactory = expressionFactory;

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		for (EntityField entityField : entityFieldsMap.values()) {
			APIPropertyEntityField apiPropertyEntityField =
				_getAPIPropertyEntityField(entityField);

			_propertyNames.put(
				entityField.getName(),
				apiPropertyEntityField.getInternalName());
		}
	}

	@Override
	public Expression visitBinaryExpressionOperation(
			BinaryExpression.Operation operation, Expression leftExpression,
			Expression rightExpression)
		throws ExpressionVisitException {

		return _expressionFactory.createBinaryExpression(
			leftExpression, operation, rightExpression);
	}

	@Override
	public Expression visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		LambdaFunctionExpression lambdaFunctionExpression =
			collectionPropertyExpression.getLambdaFunctionExpression();

		PropertyExpression propertyExpression =
			collectionPropertyExpression.getPropertyExpression();

		return _expressionFactory.createCollectionPropertyExpression(
			(LambdaFunctionExpression)lambdaFunctionExpression.accept(this),
			(PropertyExpression)propertyExpression.accept(this));
	}

	@Override
	public Expression visitComplexPropertyExpression(
			ComplexPropertyExpression complexPropertyExpression)
		throws ExpressionVisitException {

		return complexPropertyExpression;
	}

	@Override
	public Expression visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variableName,
			Expression expression)
		throws ExpressionVisitException {

		return _expressionFactory.createLambdaFunctionExpression(
			type, variableName, expression);
	}

	@Override
	public Expression visitLambdaVariableExpression(
			LambdaVariableExpression lambdaVariableExpression)
		throws ExpressionVisitException {

		return lambdaVariableExpression;
	}

	@Override
	public Expression visitListExpressionOperation(
			ListExpression.Operation operation, Expression leftExpression,
			List<Expression> rightExpressions)
		throws ExpressionVisitException {

		return _expressionFactory.createListExpression(
			leftExpression, operation, rightExpressions);
	}

	@Override
	public Expression visitLiteralExpression(
			LiteralExpression literalExpression)
		throws ExpressionVisitException {

		return literalExpression;
	}

	@Override
	public Expression visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException {

		Expression expression = memberExpression.getExpression();

		return _expressionFactory.createMemberExpression(
			expression.accept(this));
	}

	@Override
	public Expression visitMethodExpression(
			List<Expression> expressions, MethodExpression.Type type)
		throws ExpressionVisitException {

		return _expressionFactory.createMethodExpression(expressions, type);
	}

	@Override
	public Expression visitNavigationPropertyExpression(
		NavigationPropertyExpression navigationPropertyExpression) {

		return navigationPropertyExpression;
	}

	@Override
	public Expression visitPrimitivePropertyExpression(
			PrimitivePropertyExpression primitivePropertyExpression)
		throws ExpressionVisitException {

		return _expressionFactory.createPrimitivePropertyExpression(
			_propertyNames.get(primitivePropertyExpression.getName()));
	}

	@Override
	public Expression visitUnaryExpressionOperation(
			UnaryExpression.Operation operation, Expression expression)
		throws ExpressionVisitException {

		return _expressionFactory.createUnaryExpression(expression, operation);
	}

	private APIPropertyEntityField _getAPIPropertyEntityField(
		EntityField entityField) {

		if (Objects.equals(
				entityField.getType(), EntityField.Type.COLLECTION)) {

			CollectionEntityField collectionEntityField =
				(CollectionEntityField)entityField;

			return (APIPropertyEntityField)
				collectionEntityField.getEntityField();
		}

		return (APIPropertyEntityField)entityField;
	}

	private final ExpressionFactory _expressionFactory;
	private final Map<String, String> _propertyNames = new HashMap<>();

}