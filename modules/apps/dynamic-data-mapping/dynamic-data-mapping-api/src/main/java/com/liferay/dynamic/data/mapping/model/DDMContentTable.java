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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DDMContent.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMContentTable extends Table<DDMContentTable> {

	public static final DDMContentTable INSTANCE = new DDMContentTable();

	public final Column<DDMContentTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DDMContentTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<DDMContentTable, Long> contentId = createColumn(
		"contentId", Long.class, Types.BIGINT);
	public final Column<DDMContentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DDMContentTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DDMContentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DDMContentTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<DDMContentTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DDMContentTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<DDMContentTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<DDMContentTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<DDMContentTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB);

	private DDMContentTable() {
		super("DDMContent", DDMContentTable::new);
	}

}