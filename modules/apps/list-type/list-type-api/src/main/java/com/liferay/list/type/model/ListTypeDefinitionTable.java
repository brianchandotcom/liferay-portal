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

package com.liferay.list.type.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ListTypeDefinition&quot; database table.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinition
 * @generated
 */
public class ListTypeDefinitionTable
	extends BaseTable<ListTypeDefinitionTable> {

	public static final ListTypeDefinitionTable INSTANCE =
		new ListTypeDefinitionTable();

	public final Column<ListTypeDefinitionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ListTypeDefinitionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, Long> listTypeDefinitionId =
		createColumn(
			"listTypeDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ListTypeDefinitionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ListTypeDefinitionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ListTypeDefinitionTable() {
		super("ListTypeDefinition", ListTypeDefinitionTable::new);
	}

}