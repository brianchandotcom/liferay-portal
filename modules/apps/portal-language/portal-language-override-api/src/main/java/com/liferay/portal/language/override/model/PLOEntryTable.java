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

package com.liferay.portal.language.override.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;PLOEntry&quot; database table.
 *
 * @author Drew Brokke
 * @see PLOEntry
 * @generated
 */
public class PLOEntryTable extends BaseTable<PLOEntryTable> {

	public static final PLOEntryTable INSTANCE = new PLOEntryTable();

	public final Column<PLOEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PLOEntryTable, Long> ploEntryId = createColumn(
		"ploEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PLOEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PLOEntryTable, Clob> value = createColumn(
		"value", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private PLOEntryTable() {
		super("PLOEntry", PLOEntryTable::new);
	}

}