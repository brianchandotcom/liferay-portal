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

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.ColumnAlias;

/**
 * @author Preston Crary
 */
public class DefaultColumnAlias<T extends Table<T>, C>
	extends DefaultAlias<C> implements ColumnAlias<T, C> {

	public DefaultColumnAlias(Column<T, C> column, String name) {
		super(column, name);

		_table = column.getTable();
	}

	@Override
	public T getTable() {
		return _table;
	}

	private final T _table;

}