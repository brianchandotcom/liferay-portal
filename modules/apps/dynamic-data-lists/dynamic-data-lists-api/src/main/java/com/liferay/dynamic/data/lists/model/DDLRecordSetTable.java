/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDLRecordSet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSet
 * @generated
 */
public class DDLRecordSetTable extends BaseTable<DDLRecordSetTable> {

	public static final DDLRecordSetTable INSTANCE = new DDLRecordSetTable();

	public final Column<DDLRecordSetTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDLRecordSetTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordSetTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Long> recordSetId = createColumn(
		"recordSetId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordSetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Long> versionUserId = createColumn(
		"versionUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> versionUserName =
		createColumn(
			"versionUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Long> DDMStructureId = createColumn(
		"DDMStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> recordSetKey = createColumn(
		"recordSetKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Integer> minDisplayRows =
		createColumn(
			"minDisplayRows", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Integer> scope = createColumn(
		"scope", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Clob> settings = createColumn(
		"settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDLRecordSetTable() {
		super("DDLRecordSet", DDLRecordSetTable::new);
	}

}