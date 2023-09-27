/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;ExpandoValue&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValue
 * @generated
 */
public class ExpandoValueTable extends BaseTable<ExpandoValueTable> {

	public static final ExpandoValueTable INSTANCE = new ExpandoValueTable();

	public final Column<ExpandoValueTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ExpandoValueTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoValueTable, Long> valueId = createColumn(
		"valueId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ExpandoValueTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> tableId = createColumn(
		"tableId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> columnId = createColumn(
		"columnId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> rowId = createColumn(
		"rowId_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ExpandoValueTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private ExpandoValueTable() {
		super("ExpandoValue", ExpandoValueTable::new);
	}

}