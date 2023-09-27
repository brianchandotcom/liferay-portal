/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectLayoutTab&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectLayoutTab
 * @generated
 */
public class ObjectLayoutTabTable extends BaseTable<ObjectLayoutTabTable> {

	public static final ObjectLayoutTabTable INSTANCE =
		new ObjectLayoutTabTable();

	public final Column<ObjectLayoutTabTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectLayoutTabTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Long> objectLayoutTabId =
		createColumn(
			"objectLayoutTabId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectLayoutTabTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Long> objectLayoutId =
		createColumn(
			"objectLayoutId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Long> objectRelationshipId =
		createColumn(
			"objectRelationshipId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectLayoutTabTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectLayoutTabTable() {
		super("ObjectLayoutTab", ObjectLayoutTabTable::new);
	}

}