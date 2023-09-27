/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WMSLADefinition&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLADefinition
 * @generated
 */
public class WorkflowMetricsSLADefinitionTable
	extends BaseTable<WorkflowMetricsSLADefinitionTable> {

	public static final WorkflowMetricsSLADefinitionTable INSTANCE =
		new WorkflowMetricsSLADefinitionTable();

	public final Column<WorkflowMetricsSLADefinitionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WorkflowMetricsSLADefinitionTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long>
		workflowMetricsSLADefinitionId = createColumn(
			"wmSLADefinitionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> calendarKey =
		createColumn(
			"calendarKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> duration =
		createColumn("duration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		pauseNodeKeys = createColumn(
			"pauseNodeKeys", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> processId =
		createColumn(
			"processId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		processVersion = createColumn(
			"processVersion", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		startNodeKeys = createColumn(
			"startNodeKeys", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		stopNodeKeys = createColumn(
			"stopNodeKeys", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long>
		statusByUserId = createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private WorkflowMetricsSLADefinitionTable() {
		super("WMSLADefinition", WorkflowMetricsSLADefinitionTable::new);
	}

}