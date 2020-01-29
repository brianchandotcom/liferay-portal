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
import com.liferay.petra.sql.dsl.expressions.impl.AliasImpl;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.impl.Select;

import java.util.Collection;

/**
 * @author Preston Crary
 */
public class DSLQueryUtil {

	public static FromStep count() {
		return _SELECT_COUNT_STAR_COUNT_VALUE;
	}

	public static FromStep countDistinct(Expression<?> expression) {
		return new Select(
			false,
			new AliasImpl<>(
				DSLFunctionUtil.countDistinct(expression), "COUNT_VALUE"));
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

	public static <T extends Table<T>> FromStep selectDistinct(T table) {
		Collection<Column<T, ?>> columns = table.getColumns();

		return new Select(true, columns.toArray(new Column[0]));
	}

	private static final FromStep _SELECT_COUNT_STAR_COUNT_VALUE = new Select(
		false, new AliasImpl<>(DSLFunctionUtil.count(null), "COUNT_VALUE"));

	private static final FromStep _SELECT_STAR = new Select(false);

}