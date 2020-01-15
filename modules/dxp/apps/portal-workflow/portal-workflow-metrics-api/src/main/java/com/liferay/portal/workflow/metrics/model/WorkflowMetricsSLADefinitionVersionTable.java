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
 * The table class for the WorkflowMetricsSLADefinitionVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WorkflowMetricsSLADefinitionVersionTable
	extends Table<WorkflowMetricsSLADefinitionVersionTable> {

	public static final WorkflowMetricsSLADefinitionVersionTable INSTANCE =
		new WorkflowMetricsSLADefinitionVersionTable();

	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		mvccVersion = createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		workflowMetricsSLADefinitionVersionId = createColumn(
			"wmSLADefinitionVersionId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		groupId = createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		companyId = createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		userName = createColumn("userName", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Date>
		createDate = createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Boolean>
		active = createColumn("active_", Boolean.class, Types.BOOLEAN);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		calendarKey = createColumn("calendarKey", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Clob>
		description = createColumn("description", Clob.class, Types.CLOB);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		duration = createColumn("duration", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		pauseNodeKeys = createColumn(
			"pauseNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		processId = createColumn("processId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		processVersion = createColumn(
			"processVersion", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		startNodeKeys = createColumn(
			"startNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		stopNodeKeys = createColumn(
			"stopNodeKeys", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		version = createColumn("version", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		workflowMetricsSLADefinitionId = createColumn(
			"wmSLADefinitionId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Integer>
		status = createColumn("status", Integer.class, Types.INTEGER);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Long>
		statusByUserId = createColumn(
			"statusByUserId", Long.class, Types.BIGINT);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR);
	public final Column<WorkflowMetricsSLADefinitionVersionTable, Date>
		statusDate = createColumn("statusDate", Date.class, Types.TIMESTAMP);

	private WorkflowMetricsSLADefinitionVersionTable() {
		super(
			"WMSLADefinitionVersion",
			WorkflowMetricsSLADefinitionVersionTable::new);
	}

}