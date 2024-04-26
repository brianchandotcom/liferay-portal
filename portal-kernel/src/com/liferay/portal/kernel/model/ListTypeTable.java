/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ListType&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see ListType
 * @generated
 */
public class ListTypeTable extends BaseTable<ListTypeTable> {

	public static final ListTypeTable INSTANCE = new ListTypeTable();

	public final Column<ListTypeTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ListTypeTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, Long> listTypeId = createColumn(
		"listTypeId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ListTypeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ListTypeTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ListTypeTable() {
		super("ListType", ListTypeTable::new);
	}

}