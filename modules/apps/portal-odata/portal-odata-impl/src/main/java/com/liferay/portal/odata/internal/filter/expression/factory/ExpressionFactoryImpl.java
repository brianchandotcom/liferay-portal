/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression.factory;

import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.CollectionPropertyExpression;
import com.liferay.portal.odata.filter.expression.ComplexPropertyExpression;
import com.liferay.portal.odata.filter.expression.Expression;
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
import com.liferay.portal.odata.internal.filter.expression.BinaryExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.CollectionPropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.ComplexPropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaFunctionExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LambdaVariableExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.ListExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.LiteralExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.MemberExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.MethodExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.NavigationPropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.PrimitivePropertyExpressionImpl;
import com.liferay.portal.odata.internal.filter.expression.UnaryExpressionImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(service = ExpressionFactory.class)
public class ExpressionFactoryImpl implements ExpressionFactory {

	@Override
	public BinaryExpression createBinaryExpression(
		Expression leftOperationExpression,
		BinaryExpression.Operation operation,
		Expression rightOperationExpression) {

		return new BinaryExpressionImpl(
			leftOperationExpression, operation, rightOperationExpression);
	}

	@Override
	public CollectionPropertyExpression createCollectionPropertyExpression(
		LambdaFunctionExpression lambdaFunctionExpression,
		PropertyExpression propertyExpression) {

		return new CollectionPropertyExpressionImpl(
			propertyExpression, lambdaFunctionExpression);
	}

	@Override
	public ComplexPropertyExpression createComplexPropertyExpression(
		String name, PropertyExpression propertyExpression) {

		return new ComplexPropertyExpressionImpl(name, propertyExpression);
	}

	@Override
	public LambdaFunctionExpression createLambdaFunctionExpression(
		LambdaFunctionExpression.Type type, String variableName,
		Expression expression) {

		return new LambdaFunctionExpressionImpl(type, variableName, expression);
	}

	@Override
	public LambdaVariableExpression createLambdaVariableExpression(
		String variable) {

		return new LambdaVariableExpressionImpl(variable);
	}

	@Override
	public ListExpression createListExpression(
		Expression leftOperationExpression, ListExpression.Operation operation,
		List<Expression> rightOperationExpressions) {

		return new ListExpressionImpl(
			leftOperationExpression, operation, rightOperationExpressions);
	}

	@Override
	public LiteralExpression createLiteralExpression(
		String text, LiteralExpression.Type type) {

		return new LiteralExpressionImpl(text, type);
	}

	@Override
	public MemberExpression createMemberExpression(Expression expression) {
		return new MemberExpressionImpl(expression);
	}

	@Override
	public MethodExpression createMethodExpression(
		List<Expression> expressions, MethodExpression.Type type) {

		return new MethodExpressionImpl(expressions, type);
	}

	@Override
	public NavigationPropertyExpression createNavigationPropertyExpression(
		String name, NavigationPropertyExpression.Type type) {

		return new NavigationPropertyExpressionImpl(name, type);
	}

	@Override
	public PrimitivePropertyExpression createPrimitivePropertyExpression(
		String name) {

		return new PrimitivePropertyExpressionImpl(name);
	}

	@Override
	public UnaryExpression createUnaryExpression(
		Expression expression, UnaryExpression.Operation operation) {

		return new UnaryExpressionImpl(expression, operation);
	}

}