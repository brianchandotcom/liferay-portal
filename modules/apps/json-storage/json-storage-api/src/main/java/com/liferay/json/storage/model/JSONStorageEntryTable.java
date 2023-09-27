/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;JSONStorageEntry&quot; database table.
 *
 * @author Preston Crary
 * @see JSONStorageEntry
 * @generated
 */
public class JSONStorageEntryTable extends BaseTable<JSONStorageEntryTable> {

	public static final JSONStorageEntryTable INSTANCE =
		new JSONStorageEntryTable();

	public final Column<JSONStorageEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<JSONStorageEntryTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<JSONStorageEntryTable, Long> jsonStorageEntryId =
		createColumn(
			"jsonStorageEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<JSONStorageEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Long> parentJSONStorageEntryId =
		createColumn(
			"parentJSONStorageEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Integer> index = createColumn(
		"index_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Long> valueLong = createColumn(
		"valueLong", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<JSONStorageEntryTable, Clob> valueString = createColumn(
		"valueString", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private JSONStorageEntryTable() {
		super("JSONStorageEntry", JSONStorageEntryTable::new);
	}

}