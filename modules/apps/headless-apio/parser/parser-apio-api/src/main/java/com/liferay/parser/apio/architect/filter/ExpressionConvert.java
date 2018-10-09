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

package com.liferay.parser.apio.architect.filter;

import com.liferay.parser.apio.architect.entity.EntityModel;
import com.liferay.parser.apio.architect.filter.expression.Expression;
import com.liferay.parser.apio.architect.filter.expression.ExpressionVisitException;

import java.util.Locale;

/**
 * <code>ExpressionConvert</code> converts a Expression into a T type.
 *
 * @author David Arques
 * @review
 */
public interface ExpressionConvert<T> {

	/**
	 * Converts a expression into a T type given a locale and a entityModel.
	 *
	 * @param  expression - the expression
	 * @param  locale - the locale
	 * @param  entityModel - the entityModel
	 * @return T
	 * @review
	 */
	public T convert(
			Expression expression, Locale locale, EntityModel entityModel)
		throws ExpressionVisitException;

}