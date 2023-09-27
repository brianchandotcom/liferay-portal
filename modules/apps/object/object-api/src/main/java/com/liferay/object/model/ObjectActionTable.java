/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectAction&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectAction
 * @generated
 */
public class ObjectActionTable extends BaseTable<ObjectActionTable> {

	public static final ObjectActionTable INSTANCE = new ObjectActionTable();

	public final Column<ObjectActionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectActionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Long> objectActionId = createColumn(
		"objectActionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ObjectActionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Clob> conditionExpression =
		createColumn(
			"conditionExpression", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> errorMessage = createColumn(
		"errorMessage", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> label = createColumn(
		"label", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> objectActionExecutorKey =
		createColumn(
			"objectActionExecutorKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, String> objectActionTriggerKey =
		createColumn(
			"objectActionTriggerKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Clob> parameters = createColumn(
		"parameters", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<ObjectActionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectActionTable() {
		super("ObjectAction", ObjectActionTable::new);
	}

}