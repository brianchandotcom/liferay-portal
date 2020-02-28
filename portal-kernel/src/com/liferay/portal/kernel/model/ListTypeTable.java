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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;ListType&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ListType
 * @generated
 */
public class ListTypeTable extends BaseTable<ListTypeTable> {

	public static final ListTypeTable INSTANCE = new ListTypeTable();

	public final Column<ListTypeTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ListTypeTable, Long> listTypeId = createColumn(
		"listTypeId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ListTypeTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ListTypeTable() {
		super("ListType", ListTypeTable::new);
	}

}