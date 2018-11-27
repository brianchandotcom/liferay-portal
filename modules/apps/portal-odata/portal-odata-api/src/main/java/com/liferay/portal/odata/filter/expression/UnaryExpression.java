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
 * Represents a unary expression node in the expression tree.
 *
 * @author Cristina González
 * @review
 */
public interface UnaryExpression extends Expression {

	/**
	 * Returns an {@link Expression} subtree of the operation.
	 *
	 * @return Expression sub tree of the operation
	 * @review
	 */
	public Expression getExpression();

	/**
	 * Returns the unary expression's operation.
	 *
	 * @return the operation of the unary Expression
	 * @review
	 */
	public UnaryExpression.Operation getOperation();

	public static enum Operation {

		NOT

	}

}