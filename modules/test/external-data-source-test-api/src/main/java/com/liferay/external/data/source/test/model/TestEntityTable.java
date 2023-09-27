/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.data.source.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;TestEntity&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see TestEntity
 * @generated
 */
public class TestEntityTable extends BaseTable<TestEntityTable> {

	public static final TestEntityTable INSTANCE = new TestEntityTable();

	public final Column<TestEntityTable, Long> id = createColumn(
		"id_", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TestEntityTable, String> data = createColumn(
		"data_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private TestEntityTable() {
		super("TestEntity", TestEntityTable::new);
	}

}