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

/**
 * The table class for the &quot;DDMFieldAttribute&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFieldAttribute
 * @generated
 */
public class DDMFieldAttributeTable extends BaseTable<DDMFieldAttributeTable> {

	public static final DDMFieldAttributeTable INSTANCE =
		new DDMFieldAttributeTable();

	public final Column<DDMFieldAttributeTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMFieldAttributeTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFieldAttributeTable, Long> fieldAttributeId =
		createColumn(
			"fieldAttributeId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFieldAttributeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, Long> fieldId = createColumn(
		"fieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, Long> storageId = createColumn(
		"storageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, String> attributeName =
		createColumn(
			"attributeName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, String> languageId =
		createColumn(
			"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, Clob> largeAttributeValue =
		createColumn(
			"largeAttributeValue", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMFieldAttributeTable, String> smallAttributeValue =
		createColumn(
			"smallAttributeValue", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private DDMFieldAttributeTable() {
		super("DDMFieldAttribute", DDMFieldAttributeTable::new);
	}

}