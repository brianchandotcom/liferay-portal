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