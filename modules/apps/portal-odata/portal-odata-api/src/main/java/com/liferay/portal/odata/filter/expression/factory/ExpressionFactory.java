/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter.expression.factory;

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

import java.util.List;

/**
 * @author Carlos Correa
 */
public interface ExpressionFactory {

	public BinaryExpression createBinaryExpression(
		Expression leftOperationExpression,
		BinaryExpression.Operation operation,
		Expression rightOperationExpression);

	public CollectionPropertyExpression createCollectionPropertyExpression(
		LambdaFunctionExpression lambdaFunctionExpression,
		PropertyExpression propertyExpression);

	public ComplexPropertyExpression createComplexPropertyExpression(
		String name, PropertyExpression propertyExpression);

	public LambdaFunctionExpression createLambdaFunctionExpression(
		LambdaFunctionExpression.Type type, String variableName,
		Expression expression);

	public LambdaVariableExpression createLambdaVariableExpression(
		String variable);

	public ListExpression createListExpression(
		Expression leftOperationExpression, ListExpression.Operation operation,
		List<Expression> rightOperationExpressions);

	public LiteralExpression createLiteralExpression(
		String text, LiteralExpression.Type type);

	public MemberExpression createMemberExpression(Expression expression);

	public MethodExpression createMethodExpression(
		List<Expression> expressions, MethodExpression.Type type);

	public NavigationPropertyExpression createNavigationPropertyExpression(
		String name, NavigationPropertyExpression.Type type);

	public PrimitivePropertyExpression createPrimitivePropertyExpression(
		String name);

	public UnaryExpression createUnaryExpression(
		Expression expression, UnaryExpression.Operation operation);

}