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

package com.liferay.petra.sql.dsl.query;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.query.sort.OrderByInfo;

import java.util.function.Function;

/**
 * @author Preston Crary
 */
public interface OrderByStep extends LimitStep {

	public default LimitStep orderBy(
		Function<OrderByStep, LimitStep> function) {

		LimitStep limitStep = function.apply(this);

		if (limitStep == null) {
			return this;
		}

		return limitStep;
	}

	public LimitStep orderBy(OrderByExpression... orderByExpressions);

	public LimitStep orderBy(Table<?> table, OrderByInfo orderByInfo);

}