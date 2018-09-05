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
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;

/**
 * <code>FilterParser</code> transforms a String containing an oData filter in a
 * manageable expression {@link Expression}.
 *
 * @author David Arques
 * @review
 */
public interface FilterParser {

	/**
	 * Returns a {@link Expression} obtained from a String.
	 *
	 * @param  filterString - the filterString
	 * @return A {@link Expression}
	 * @review
	 */
	public Expression parse(String filterString)
		throws ExpressionVisitException;

}