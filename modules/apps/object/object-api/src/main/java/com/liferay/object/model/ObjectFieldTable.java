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

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectField&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectField
 * @generated
 */
public class ObjectFieldTable extends BaseTable<ObjectFieldTable> {

	public static final ObjectFieldTable INSTANCE = new ObjectFieldTable();

	public final Column<ObjectFieldTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectFieldTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Long> objectFieldId = createColumn(
		"objectFieldId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectFieldTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Long> listTypeDefinitionId =
		createColumn(
			"listTypeDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> businessType = createColumn(
		"businessType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> dbColumnName = createColumn(
		"dbColumnName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> dbTableName = createColumn(
		"dbTableName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Boolean> indexed = createColumn(
		"indexed", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Boolean> indexedAsKeyword =
		createColumn(
			"indexedAsKeyword", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> indexedLanguageId =
		createColumn(
			"indexedLanguageId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> label = createColumn(
		"label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> relationshipType =
		createColumn(
			"relationshipType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, Boolean> required = createColumn(
		"required", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectFieldTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ObjectFieldTable() {
		super("ObjectField", ObjectFieldTable::new);
	}

}