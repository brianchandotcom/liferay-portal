/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expression.ColumnAlias;
import com.liferay.petra.sql.dsl.expression.Expression;

/**
 * @author Preston Crary
 */
public interface Column<T extends Table<T>, C> extends Expression<C> {

	public static int FLAG_DEFAULT = 0;

	public static int FLAG_NULLITY = 1;

	public static int FLAG_PRIMARY = 2;

	@Override
	public ColumnAlias<T, C> as(String name);

	public int getFlags();

	public Class<C> getJavaType();

	public String getName();

	public int getSQLType();

	public T getTable();

	public default boolean isNullAllowed() {
		if ((getFlags() & FLAG_NULLITY) == 0) {
			return true;
		}

		return false;
	}

	public default boolean isPrimaryKey() {
		if ((getFlags() & FLAG_PRIMARY) == 0) {
			return false;
		}

		return true;
	}

}