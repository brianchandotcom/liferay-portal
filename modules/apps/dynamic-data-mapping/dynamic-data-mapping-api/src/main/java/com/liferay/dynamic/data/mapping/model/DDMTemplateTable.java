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
 * The table class for the &quot;DDMTemplate&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplate
 * @generated
 */
public class DDMTemplateTable extends BaseTable<DDMTemplateTable> {

	public static final DDMTemplateTable INSTANCE = new DDMTemplateTable();

	public final Column<DDMTemplateTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMTemplateTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> templateId = createColumn(
		"templateId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMTemplateTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> versionUserId = createColumn(
		"versionUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> versionUserName =
		createColumn(
			"versionUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> resourceClassNameId =
		createColumn(
			"resourceClassNameId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> templateKey = createColumn(
		"templateKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Clob> name = createColumn(
		"name", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Clob> description = createColumn(
		"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> mode = createColumn(
		"mode_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> language = createColumn(
		"language", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Clob> script = createColumn(
		"script", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Boolean> cacheable = createColumn(
		"cacheable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Boolean> smallImage = createColumn(
		"smallImage", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Long> smallImageId = createColumn(
		"smallImageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, String> smallImageURL = createColumn(
		"smallImageURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMTemplateTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDMTemplateTable() {
		super("DDMTemplate", DDMTemplateTable::new);
	}

}