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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDMTemplateVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersion
 * @generated
 */
public class DDMTemplateVersionTable
	extends BaseTable<DDMTemplateVersionTable> {

	public static final DDMTemplateVersionTable INSTANCE =
		new DDMTemplateVersionTable();

	public final Column<DDMTemplateVersionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMTemplateVersionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateVersionTable, Long> templateVersionId =
		createColumn(
			"templateVersionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> templateId =
		createColumn(
			"templateId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Clob> name = createColumn(
		"name", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, String> language =
		createColumn(
			"language", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Clob> script = createColumn(
		"script", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMTemplateVersionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDMTemplateVersionTable() {
		super("DDMTemplateVersion", DDMTemplateVersionTable::new);
	}

}