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

import com.liferay.petra.sql.dsl.expression.ColumnAlias;
import com.liferay.petra.sql.dsl.expression.Expression;

/**
 * @author Preston Crary
 */
public interface Column<T extends Table<T>, C> extends Expression<C> {

	public static int DEFAULT_FLAGS = 0;

	public static int NULLITY_FLAG = 1;

	public static int PRIMARY_FLAG = 2;

	@Override
	public ColumnAlias<T, C> as(String name);

	public int getFlags();

	public Class<C> getJavaType();

	public String getName();

	public int getSQLType();

	public T getTable();

	public default boolean isNullAllowed() {
		if ((getFlags() & NULLITY_FLAG) == 0) {
			return true;
		}

		return false;
	}

	public default boolean isPrimaryKey() {
		if ((getFlags() & PRIMARY_FLAG) == 0) {
			return false;
		}

		return true;
	}

}