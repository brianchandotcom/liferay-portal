/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.DSLQuery;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Preston Crary
 */
public interface DefaultDSLQuery extends DSLQuery {

	@Override
	public default Table<?> as(String name) {
		return as(name, Collections.emptyList());
	}

	@Override
	public default Table<?> as(
		String name, Collection<Column<?, ?>> templateColumns) {

		return new QueryTable(name, this, templateColumns);
	}

	@Override
	public default <T extends Table<T>> T as(String name, T templateTable) {
		return (T)as(
			name,
			(Collection<Column<?, ?>>)
				(Collection<?>)templateTable.getColumns());
	}

	@Override
	public default DSLQuery union(DSLQuery dslQuery) {
		return new SetOperation(this, SetOperationType.UNION, dslQuery);
	}

	@Override
	public default DSLQuery unionAll(DSLQuery dslQuery) {
		return new SetOperation(this, SetOperationType.UNION_ALL, dslQuery);
	}

}