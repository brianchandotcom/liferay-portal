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

import java.sql.Types;

/**
 * The table class for the &quot;DDMField&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMField
 * @generated
 */
public class DDMFieldTable extends BaseTable<DDMFieldTable> {

	public static final DDMFieldTable INSTANCE = new DDMFieldTable();

	public final Column<DDMFieldTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMFieldTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFieldTable, Long> fieldId = createColumn(
		"fieldId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFieldTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, Long> parentFieldId = createColumn(
		"parentFieldId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, Long> storageId = createColumn(
		"storageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, Long> structureVersionId = createColumn(
		"structureVersionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, String> fieldName = createColumn(
		"fieldName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, String> fieldType = createColumn(
		"fieldType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, String> instanceId = createColumn(
		"instanceId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, Boolean> localizable = createColumn(
		"localizable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DDMFieldTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private DDMFieldTable() {
		super("DDMField", DDMFieldTable::new);
	}

}