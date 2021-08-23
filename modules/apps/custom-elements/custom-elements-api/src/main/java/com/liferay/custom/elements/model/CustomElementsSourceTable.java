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

package com.liferay.custom.elements.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CustomElementsSource&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CustomElementsSource
 * @generated
 */
public class CustomElementsSourceTable
	extends BaseTable<CustomElementsSourceTable> {

	public static final CustomElementsSourceTable INSTANCE =
		new CustomElementsSourceTable();

	public final Column<CustomElementsSourceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CustomElementsSourceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, Long>
		customElementsSourceId = createColumn(
			"customElementsSourceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CustomElementsSourceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, String> htmlElementName =
		createColumn(
			"htmlElementName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CustomElementsSourceTable, Clob> urls = createColumn(
		"urls", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CustomElementsSourceTable() {
		super("CustomElementsSource", CustomElementsSourceTable::new);
	}

}