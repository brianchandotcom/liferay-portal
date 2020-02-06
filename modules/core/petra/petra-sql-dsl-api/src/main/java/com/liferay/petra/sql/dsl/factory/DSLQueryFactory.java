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

package com.liferay.petra.sql.dsl.factory;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.FromStep;

/**
 * @author Preston Crary
 */
public interface DSLQueryFactory {

	public FromStep count();

	public FromStep countDistinct(Expression<?> expression);

	public FromStep select();

	public FromStep select(Expression<?>... expressions);

	public <T extends Table<T>> FromStep select(Table<T> table);

	public FromStep selectDistinct(Expression<?>... expressions);

	public <T extends Table<T>> FromStep selectDistinct(T table);

}