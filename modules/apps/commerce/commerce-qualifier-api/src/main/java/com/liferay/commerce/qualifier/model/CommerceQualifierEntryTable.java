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

package com.liferay.commerce.qualifier.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceQualifierEntry&quot; database table.
 *
 * @author Riccardo Alberti
 * @see CommerceQualifierEntry
 * @generated
 */
public class CommerceQualifierEntryTable
	extends BaseTable<CommerceQualifierEntryTable> {

	public static final CommerceQualifierEntryTable INSTANCE =
		new CommerceQualifierEntryTable();

	public final Column<CommerceQualifierEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceQualifierEntryTable, Long>
		commerceQualifierEntryId = createColumn(
			"commerceQualifierEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceQualifierEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> sourceClassNameId =
		createColumn(
			"sourceClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> sourceClassPK =
		createColumn(
			"sourceClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String>
		sourceCommerceQualifierMetadataKey = createColumn(
			"sourceCQualifierMetadataKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> targetClassNameId =
		createColumn(
			"targetClassNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, Long> targetClassPK =
		createColumn(
			"targetClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceQualifierEntryTable, String>
		targetCommerceQualifierMetadataKey = createColumn(
			"targetCQualifierMetadataKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceQualifierEntryTable() {
		super("CommerceQualifierEntry", CommerceQualifierEntryTable::new);
	}

}