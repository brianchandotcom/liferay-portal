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

package com.liferay.structured.content.apio.architect.filter.expression;

/**
 * Represents a binary expression node in the expression tree
 *
 * @author Cristina González
 * @review
 */
public interface BinaryExpression extends Expression {

	/**
	 * Returns a Expression sub tree of the left operation.
	 *
	 * @return Expression sub tree of the left operation
	 * @review
	 */
	public Expression getLeftOperationExpression();

	/**
	 * Returns the operation of the binary Expression.
	 *
	 * @return the operation of the binary Expression
	 * @review
	 */
	public Operation getOperation();

	/**
	 * Returns a Expression sub tree of the right operation.
	 *
	 * @return Expression sub tree of the right operation
	 * @review
	 */
	public Expression getRightOperationExpression();

	public static enum Operation {

		EQ

	}

}