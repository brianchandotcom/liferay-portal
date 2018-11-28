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

package com.liferay.portal.odata.filter.expression;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.List;

/**
 * Defines expression visitors with arbitrary return types. This interface's
 * methods are called when an expression node of the expression tree is
 * traversed.
 *
 * @author Cristina González
 * @review
 */
@ProviderType
public interface ExpressionVisitor<T> {

	/**
	 * Called for each {@link BinaryExpression}.
	 *
	 * @param  operation the binary expression's operation
	 * @param  left the return value of the left subtree
	 * @param  right the return value of the right subtree
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 * @review
	 */
	public T visitBinaryExpressionOperation(
			BinaryExpression.Operation operation, T left, T right)
		throws ExpressionVisitException;

	/**
	 * Called for each traversed {@link ComplexPropertyExpression} expression
	 *
	 * @param  collectionPropertyExpression the complex property expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException the expression visit exception
	 * @review
	 */
	public default T visitCollectionPropertyExpression(
			CollectionPropertyExpression collectionPropertyExpression)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException(
			"Unsupported method visitCollectionPropertyExpression with " +
				"collection property expression " +
					collectionPropertyExpression);
	}

	/**
	 * Called for each traversed {@link ComplexPropertyExpression} expression
	 *
	 * @param  complexPropertyExpression the complex property expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException the expression visit exception
	 * @review
	 */
	public default T visitComplexPropertyExpression(
			ComplexPropertyExpression complexPropertyExpression)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException(
			"Unsupported method visitComplexPropertyExpression with complex " +
				"property expression " + complexPropertyExpression);
	}

	/**
	 * Called for each traversed {@link LambdaFunctionExpression} expression
	 *
	 * @param  variableName the name of the lambda variable
	 * @param  expression the expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException the expression visit exception
	 * @review
	 */
	public default T visitLambdaFunctionExpression(
			LambdaFunctionExpression.Type type, String variableName,
			Expression expression)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException(
			StringBundler.concat(
				"Unsupported method visitLambdaFunctionExpression with ",
				"variableName ", variableName, " and expression ",
				expression.toString()));
	}

	/**
	 * Called for each traversed {@link LambdaVariableExpression} expression
	 *
	 * @param  lambdaVariableExpression the lambda variable expression
	 * @return the t
	 * @throws ExpressionVisitException if an expression visit exception
	 *                                  occurred
	 * @review
	 */
	public default T visitLambdaVariableExpression(
			LambdaVariableExpression lambdaVariableExpression)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException(
			"Unsupported method lambdaVariableExpression with lambda " +
				"variable expression " + lambdaVariableExpression);
	}

	/**
	 * Called for each {@link LiteralExpression}.
	 *
	 * @param  literalExpression the literal expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 * @review
	 */
	public T visitLiteralExpression(LiteralExpression literalExpression)
		throws ExpressionVisitException;

	/**
	 * Called for each {@link MemberExpression}.
	 *
	 * @param  memberExpression the member expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 * @review
	 */
	public T visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException;

	/**
	 * Called for each traversed {@link MethodExpression} expression
	 *
	 * @param  expressions List of return values created by visiting each method
	 *         expression
	 * @param  type Method.Type
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 * @review
	 */
	public T visitMethodExpression(
			List<T> expressions, MethodExpression.Type type)
		throws ExpressionVisitException;

	/**
	 * Called for each traversed {@link PrimitivePropertyExpression} expression.
	 *
	 * @param primitivePropertyExpression the primitive property expression
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 *  @review
	 */
	public default T visitPrimitivePropertyExpression(
			PrimitivePropertyExpression primitivePropertyExpression)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException(
			"Unsupported method visitPrimitivePropertyExpression with " +
				"primitive property expression " + primitivePropertyExpression);
	}

	/**
	 * Called for each traversed {@link UnaryExpression} expression
	 *
	 * @param  operation the unary expression's operation
	 * @param  operand the return value of the subtree
	 * @return T the object of type {@code T}
	 * @throws ExpressionVisitException if an expression visit exception
	 *         occurred
	 * @review
	 */
	public default T visitUnaryExpressionOperation(
			UnaryExpression.Operation operation, T operand)
		throws ExpressionVisitException {

		throw new UnsupportedOperationException();
	}

}