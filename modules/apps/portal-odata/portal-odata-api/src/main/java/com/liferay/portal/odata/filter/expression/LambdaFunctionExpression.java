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

/**
 * Represents a lambda function expression in the expression tree
 *
 * @author Rubén Pulido
 * @review
 */
public interface LambdaFunctionExpression extends Expression {

	/**
	 * Returns the child expression
	 *
	 * @return The child expression
	 * @review
	 */
	public Expression getExpression();

	/**
	 * Returns the type of the lambda function expression
	 *
	 * @return The type
	 * @review
	 */
	public Type getType();

	/**
	 * @return The name of the lambda variable
	 * @review
	 */
	public String getVariableName();

	public static enum Type {

		ANY

	}

}