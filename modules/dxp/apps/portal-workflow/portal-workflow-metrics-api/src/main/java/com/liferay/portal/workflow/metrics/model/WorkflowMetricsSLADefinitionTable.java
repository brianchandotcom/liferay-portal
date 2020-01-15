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

package com.liferay.portal.workflow.metrics.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the WorkflowMetricsSLADefinition.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WorkflowMetricsSLADefinitionTable
	extends Table<WorkflowMetricsSLADefinitionTable> {

	public static final WorkflowMetricsSLADefinitionTable INSTANCE =
		new WorkflowMetricsSLADefinitionTable();

	public final Column<WorkflowMetricsSLADefinitionTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Long>
		workflowMetricsSLADefinitionId = createColumn(
			"wmSLADefinitionId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowMetricsSLADefinitionTable, Boolean> active =
		createColumn("active_", Boolean.class, Types.BOOLEAN);
	public final Column<WorkflowMetricsSLADefinitionTable, String> calendarKey =
		createColumn("calendarKey", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Clob> description =
		createColumn("description", Clob.class, Types.CLOB);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> duration =
		createColumn("duration", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		pauseNodeKeys = createColumn(
			"pauseNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Long> processId =
		createColumn("processId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		processVersion = createColumn(
			"processVersion", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		startNodeKeys = createColumn(
			"startNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		stopNodeKeys = createColumn(
			"stopNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, String> version =
		createColumn("version", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Integer> status =
		createColumn("status", Integer.class, Types.INTEGER);
	public final Column<WorkflowMetricsSLADefinitionTable, Long>
		statusByUserId = createColumn(
			"statusByUserId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionTable, Date> statusDate =
		createColumn("statusDate", Date.class, Types.TIMESTAMP);

	private WorkflowMetricsSLADefinitionTable() {
		super("WMSLADefinition", WorkflowMetricsSLADefinitionTable::new);
	}

}