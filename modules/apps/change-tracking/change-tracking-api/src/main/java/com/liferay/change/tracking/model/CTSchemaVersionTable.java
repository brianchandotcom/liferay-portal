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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;CTSchemaVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersion
 * @generated
 */
public class CTSchemaVersionTable extends BaseTable<CTSchemaVersionTable> {

	public static final CTSchemaVersionTable INSTANCE =
		new CTSchemaVersionTable();

	public final Column<CTSchemaVersionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTSchemaVersionTable, Long> schemaVersionId =
		createColumn(
			"schemaVersionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTSchemaVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTSchemaVersionTable, Clob> schemaContext =
		createColumn(
			"schemaContext", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private CTSchemaVersionTable() {
		super("CTSchemaVersion", CTSchemaVersionTable::new);
	}

}