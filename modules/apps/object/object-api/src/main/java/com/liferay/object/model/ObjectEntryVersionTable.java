/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectEntryVersion&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectEntryVersion
 * @generated
 */
public class ObjectEntryVersionTable
	extends BaseTable<ObjectEntryVersionTable> {

	public static final ObjectEntryVersionTable INSTANCE =
		new ObjectEntryVersionTable();

	public final Column<ObjectEntryVersionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectEntryVersionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Long> objectEntryVersionId =
		createColumn(
			"objectEntryVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectEntryVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Long> objectEntryId =
		createColumn(
			"objectEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Clob> content = createColumn(
		"content", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Integer> version =
		createColumn(
			"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ObjectEntryVersionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectEntryVersionTable() {
		super("ObjectEntryVersion", ObjectEntryVersionTable::new);
	}

}