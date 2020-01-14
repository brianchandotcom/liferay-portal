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
import com.liferay.petra.sql.dsl.query.AggregateExpression;
import com.liferay.petra.sql.dsl.query.Select;

/**
 * @author Preston Crary
 */
public class SelectUtil {

	public static Select count() {
		return new Select(false, AggregateExpression.COUNT_STAR_COUNT_VALUE);
	}

	public static Select countDistinct(Expression<?> expression) {
		return new Select(
			false, new AggregateExpression<>(true, expression, "count"));
	}

	public static Select select(Expression<?>... expressions) {
		return new Select(false, expressions);
	}

	public static Select selectDistinct(Expression<?>... expressions) {
		return new Select(true, expressions);
	}

}