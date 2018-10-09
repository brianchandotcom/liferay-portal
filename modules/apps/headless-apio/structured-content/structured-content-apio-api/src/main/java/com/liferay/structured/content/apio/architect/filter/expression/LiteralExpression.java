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
 * Represents a literal expression node in the expression tree. This expression
 * is not validated by default.
 *
 * @author Cristina González
 */
public interface LiteralExpression extends Expression {

	/**
	 * Returns the literal expression's text value.
	 *
	 * @return the text value
	 */
	public String getText();

	/**
	 * Returns the literal expression's type.
	 *
	 * @return the type
	 */
	public Type getType();

	public static enum Type {

		DATE, STRING

	}

}