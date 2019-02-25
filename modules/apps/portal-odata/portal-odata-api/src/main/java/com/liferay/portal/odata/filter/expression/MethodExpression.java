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

import java.util.List;

/**
 * Represents a method expression in the expression tree
 *
 * @author Cristina González
 * @review
 */
public interface MethodExpression extends Expression {

	/**
	 * @return The list of expression tree which form the actual method
	 *         parameters
	 * @review
	 */
	public List<Expression> getExpressions();

	/**
	 * Returns the method type
	 *
	 * @return The type
	 * @review
	 */
	public Type getType();

	public static enum Type {

		CONTAINS, STARSWITH

	}

}