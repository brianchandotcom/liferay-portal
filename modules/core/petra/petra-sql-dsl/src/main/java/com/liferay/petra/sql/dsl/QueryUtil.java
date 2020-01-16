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

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expressions.Expression;
import com.liferay.petra.sql.dsl.expressions.impl.AggregateExpression;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.impl.Select;

/**
 * @author Preston Crary
 */
public class QueryUtil {

	public static FromStep count() {
		return _SELECT_COUNT_STAR_COUNT_VALUE;
	}

	public static FromStep countDistinct(Expression<?> expression) {
		return new Select(
			false, new AggregateExpression<>(true, expression, "count"));
	}

	public static FromStep select() {
		return _SELECT_STAR;
	}

	public static FromStep select(Expression<?>... expressions) {
		return new Select(false, expressions);
	}

	public static FromStep selectDistinct(Expression<?>... expressions) {
		return new Select(true, expressions);
	}

	private static final FromStep _SELECT_COUNT_STAR_COUNT_VALUE = new Select(
		false, AggregateExpression.COUNT_STAR_COUNT_VALUE);

	private static final FromStep _SELECT_STAR = new Select(false);

}