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

package com.liferay.parser.apio.architect.filter.expression;

/**
 * Defines an exception for {@link ExpressionVisitor} to throw if an error
 * occurs while traversing the expression tree.
 *
 * @author Cristina González
 * @review
 */
public class ExpressionVisitException extends Exception {

	/**
	 * Creates a new {@code ExpressionVisitException} with a message.
	 *
	 * @param  msg the exception's message
	 * @review
	 */
	public ExpressionVisitException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new {@code ExpressionVisitException} with a message and the
	 * cause of the exception.
	 *
	 * @param  msg the exception's message
	 * @param  cause the exception's cause
	 * @review
	 */
	public ExpressionVisitException(String msg, Throwable cause) {
		super(msg, cause);
	}

}