/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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