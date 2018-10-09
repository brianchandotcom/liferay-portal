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
 * Represents a binary expression node in the expression tree.
 *
 * @author Cristina González
 */
public interface BinaryExpression extends Expression {

	/**
	 * Returns an {@link Expression} subtree of the left operation.
	 *
	 * @return the expression subtree
	 */
	public Expression getLeftOperationExpression();

	/**
	 * Returns the binary expression's operation.
	 *
	 * @return the operation
	 */
	public Operation getOperation();

	/**
	 * Returns an {@link Expression} subtree of the right operation.
	 *
	 * @return the expression subtree
	 */
	public Expression getRightOperationExpression();

	public static enum Operation {

		AND, EQ, GE, GT, LE, LT, OR

	}

}