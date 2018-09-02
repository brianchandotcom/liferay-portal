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

import java.util.List;

/**
 * Represents a member expression node in the expression tree. This expression
 * is used to describe access paths to properties.
 *
 * @author Cristina González
 * @review
 */
public interface MemberExpression extends Expression {

	/**
	 * Returns the resource path of the Member Expression
	 *
	 * @return the resource path of the Member Expression
	 * @review
	 */
	public List<String> getResourcePath();

}