/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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