/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Reports_Definition&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Definition
 * @generated
 */
public class DefinitionTable extends BaseTable<DefinitionTable> {

	public static final DefinitionTable INSTANCE = new DefinitionTable();

	public final Column<DefinitionTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Long> definitionId = createColumn(
		"definitionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DefinitionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Long> sourceId = createColumn(
		"sourceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, String> reportName = createColumn(
		"reportName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Clob> reportParameters = createColumn(
		"reportParameters", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DefinitionTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DefinitionTable() {
		super("Reports_Definition", DefinitionTable::new);
	}

}