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

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoLog&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLog
 * @generated
 */
public class KaleoLogTable extends BaseTable<KaleoLogTable> {

	public static final KaleoLogTable INSTANCE = new KaleoLogTable();

	public final Column<KaleoLogTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoLogTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoLogTable, Long> kaleoLogId = createColumn(
		"kaleoLogId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoLogTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> kaleoClassName = createColumn(
		"kaleoClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoClassPK = createColumn(
		"kaleoClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoDefinitionId = createColumn(
		"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoDefinitionVersionId =
		createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoInstanceId = createColumn(
		"kaleoInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoInstanceTokenId =
		createColumn(
			"kaleoInstanceTokenId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoTaskInstanceTokenId =
		createColumn(
			"kaleoTaskInstanceTokenId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> kaleoNodeName = createColumn(
		"kaleoNodeName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Boolean> terminalKaleoNode =
		createColumn(
			"terminalKaleoNode", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> kaleoActionId = createColumn(
		"kaleoActionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> kaleoActionName = createColumn(
		"kaleoActionName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> kaleoActionDescription =
		createColumn(
			"kaleoActionDescription", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> previousKaleoNodeId = createColumn(
		"previousKaleoNodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> previousKaleoNodeName =
		createColumn(
			"previousKaleoNodeName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> previousAssigneeClassName =
		createColumn(
			"previousAssigneeClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> previousAssigneeClassPK =
		createColumn(
			"previousAssigneeClassPK", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> currentAssigneeClassName =
		createColumn(
			"currentAssigneeClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> currentAssigneeClassPK =
		createColumn(
			"currentAssigneeClassPK", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Clob> comment = createColumn(
		"comment_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Date> startDate = createColumn(
		"startDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Date> endDate = createColumn(
		"endDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Long> duration = createColumn(
		"duration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoLogTable, Clob> workflowContext = createColumn(
		"workflowContext", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private KaleoLogTable() {
		super("KaleoLog", KaleoLogTable::new);
	}

}