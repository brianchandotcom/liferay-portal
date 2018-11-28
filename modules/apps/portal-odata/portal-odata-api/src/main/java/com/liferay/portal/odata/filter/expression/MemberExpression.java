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
 * Represents a member expression node in the expression tree. This expression
 * is used to describe access paths to properties.
 *
 * @author Cristina González
 * @review
 */
public interface MemberExpression extends Expression {

	/**
	 * Returns the expression which forms this {@code MemberExpression}.
	 *
	 * @return the expression.
	 * @review
	 */
	public default Expression getExpression() {
		throw new UnsupportedOperationException(
			"Unsupported method getExpression");
	}

	/**
	 * Returns the member expression's resource path.
	 *
	 * @return the resource path
	 * @deprecated As of Judson (7.1.x)
	 * @review
	 */
	@Deprecated
	public List<String> getResourcePath();

}