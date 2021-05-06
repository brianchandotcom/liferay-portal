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

package com.liferay.json.store.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;JSONStoreEntry&quot; database table.
 *
 * @author Preston Crary
 * @see JSONStoreEntry
 * @generated
 */
public class JSONStoreEntryTable extends BaseTable<JSONStoreEntryTable> {

	public static final JSONStoreEntryTable INSTANCE =
		new JSONStoreEntryTable();

	public final Column<JSONStoreEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JSONStoreEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JSONStoreEntryTable, Long> jsonStoreEntryId =
		createColumn(
			"jsonStoreEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JSONStoreEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Long> parentJSONStoreEntryId =
		createColumn(
			"parentJSONStoreEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Integer> index = createColumn(
		"index_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, Long> valueLong = createColumn(
		"valueLong", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStoreEntryTable, String> valueString = createColumn(
		"valueString", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private JSONStoreEntryTable() {
		super("JSONStoreEntry", JSONStoreEntryTable::new);
	}

}