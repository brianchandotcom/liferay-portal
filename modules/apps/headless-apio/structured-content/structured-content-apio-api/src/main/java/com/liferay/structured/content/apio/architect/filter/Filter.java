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

package com.liferay.structured.content.apio.architect.filter;

import com.liferay.structured.content.apio.architect.filter.expression.Expression;

/**
 * Represents a Filter for retrieving structured content by different fields. A
 * OData {@link Expression} is needed to create a new instance. This
 * instantiation is performed in the FilterProvider.
 *
 * @author Cristina González
 * @author David Arques
 * @review
 */
public class Filter {

	public static final Filter EMPTY_FILTER = new Filter();

	/**
	 * Returns an empty Filter.
	 *
	 * @return - an empty Filter
	 * @review
	 */
	public static Filter emptyFilter() {
		return EMPTY_FILTER;
	}

	/**
	 * Creates a new {@link Filter} a given {@link Expression}
	 *
	 * @param  expression -  an OData Expression
	 * @review
	 */
	public Filter(Expression expression) {
		if (expression == null) {
			throw new InvalidFilterException("Expression is null");
		}

		_expression = expression;
	}

	/**
	 * Returns the OData Expression.
	 *
	 * @return the filter expression
	 * @review
	 */
	public Expression getExpression() {
		return _expression;
	}

	private Filter() {
		_expression = null;
	}

	private final Expression _expression;

}