/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Counter&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Counter
 * @generated
 */
public class CounterTable extends BaseTable<CounterTable> {

	public static final CounterTable INSTANCE = new CounterTable();

	public final Column<CounterTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_PRIMARY);
	public final Column<CounterTable, Long> currentId = createColumn(
		"currentId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CounterTable() {
		super("Counter", CounterTable::new);
	}

}