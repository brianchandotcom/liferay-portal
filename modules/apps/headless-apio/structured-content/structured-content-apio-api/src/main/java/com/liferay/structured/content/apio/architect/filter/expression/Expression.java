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
 * <code>Expression</code> provides a common abstraction for expression
 * evaluation
 *
 * @author Cristina González
 * @review
 */
public interface Expression {

	/**
	 * This method is called when traversing the expression tree.
	 *
	 * @param  expressionVisitor - Visitor object (implementing
	 *         ExpressionVisitor) whose methods are called during traversing a
	 *         expression node of the expression tree.
	 * @return Object of type T which should be passed to the processing
	 *         algorithm of the parent expression node
	 * @review
	 */
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException;

}