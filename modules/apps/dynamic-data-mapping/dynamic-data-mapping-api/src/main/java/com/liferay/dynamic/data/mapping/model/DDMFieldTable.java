/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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