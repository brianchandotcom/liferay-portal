/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.Expression;

/**
 * Represents a filter for retrieving structured content by different fields. An
 * OData {@link Expression} is needed to create a new filter instance. The
 * {@code FilterProvider} performs this instantiation.
 *
 * @author Cristina González
 * @author David Arques
 * @review
 */
public class Filter {

	public static final Filter EMPTY_FILTER = new Filter();

	/**
	 * Returns an empty filter.
	 *
	 * @return the empty filter
	 * @review
	 */
	public static Filter emptyFilter() {
		return EMPTY_FILTER;
	}

	/**
	 * Creates a new filter, given an OData {@code Expression}.
	 *
	 * @param  expression the OData expression
	 * @review
	 */
	public Filter(Expression expression) {
		if (expression == null) {
			throw new InvalidFilterException("Expression is null");
		}

		_expression = expression;
	}

	/**
	 * Returns the OData {@code Expression}.
	 *
	 * @return the OData expression
	 * @review
	 */
	public Expression getExpression() {
		return _expression;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_expression=", _expression, "}");
	}

	private Filter() {
		_expression = null;
	}

	private final Expression _expression;

}