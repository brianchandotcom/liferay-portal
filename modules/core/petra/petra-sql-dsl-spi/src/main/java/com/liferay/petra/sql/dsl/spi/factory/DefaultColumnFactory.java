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

package com.liferay.petra.sql.dsl.spi.factory;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.factory.ColumnFactory;
import com.liferay.petra.sql.dsl.spi.DefaultColumn;

/**
 * @author Preston Crary
 */
public class DefaultColumnFactory implements ColumnFactory {

	@Override
	public <T extends BaseTable<T>, C> Column<T, C> createColumn(
		T table, String name, Class<C> javaType, int sqlType, int flags) {

		return new DefaultColumn<>(table, name, javaType, sqlType, flags);
	}

}