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

package com.liferay.fragment.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FragmentEntryVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryVersion
 * @generated
 */
public class FragmentEntryVersionTable
	extends BaseTable<FragmentEntryVersionTable> {

	public static final FragmentEntryVersionTable INSTANCE =
		new FragmentEntryVersionTable();

	public final Column<FragmentEntryVersionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FragmentEntryVersionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FragmentEntryVersionTable, Long>
		fragmentEntryVersionId = createColumn(
			"fragmentEntryVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FragmentEntryVersionTable, Integer> version =
		createColumn(
			"version", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> fragmentEntryId =
		createColumn(
			"fragmentEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> fragmentCollectionId =
		createColumn(
			"fragmentCollectionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, String> fragmentEntryKey =
		createColumn(
			"fragmentEntryKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Clob> html = createColumn(
		"html", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Clob> js = createColumn(
		"js", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Boolean> cacheable =
		createColumn(
			"cacheable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Clob> configuration =
		createColumn(
			"configuration", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> previewFileEntryId =
		createColumn(
			"previewFileEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Boolean> readOnly =
		createColumn(
			"readOnly", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<FragmentEntryVersionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private FragmentEntryVersionTable() {
		super("FragmentEntryVersion", FragmentEntryVersionTable::new);
	}

}