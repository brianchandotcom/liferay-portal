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
 * Generic interface to define expression visitors with arbitrary return types.
 *
 * @author Cristina González
 * @review
 */
public interface ExpressionVisitor<T> {

	/**
	 * Called for each traversed <code>Binary<</code> expression
	 *
	 * @param  operation - operation
	 * @param  left - return value of left sub tree
	 * @param  right -return value of right sub tree
	 * @return T
	 * @throws ExpressionVisitException
	 * @review
	 */
	public T visitBinaryOperator(Binary.Operation operation, T left, T right)
		throws ExpressionVisitException;

	/**
	 * Called for each traversed <code>Literal<</code> expression
	 *
	 * @param  literal - Literal
	 * @return T
	 * @throws ExpressionVisitException
	 * @review
	 */
	public T visitLiteral(Literal literal) throws ExpressionVisitException;

	/**
	 * Called for each traversed <code>Member<</code> expression
	 *
	 * @param  member - Member
	 * @return T
	 * @throws ExpressionVisitException
	 * @review
	 */
	public T visitMember(Member member) throws ExpressionVisitException;

}