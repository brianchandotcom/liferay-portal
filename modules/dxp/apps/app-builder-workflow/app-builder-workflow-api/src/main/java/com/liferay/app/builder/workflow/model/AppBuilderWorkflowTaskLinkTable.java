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

package com.liferay.app.builder.workflow.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AppBuilderWorkflowTaskLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLink
 * @generated
 */
public class AppBuilderWorkflowTaskLinkTable
	extends BaseTable<AppBuilderWorkflowTaskLinkTable> {

	public static final AppBuilderWorkflowTaskLinkTable INSTANCE =
		new AppBuilderWorkflowTaskLinkTable();

	public final Column<AppBuilderWorkflowTaskLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		appBuilderWorkflowTaskLinkId = createColumn(
			"appBuilderWorkflowTaskLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		appBuilderAppVersionId = createColumn(
			"appBuilderAppVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Long>
		ddmStructureLayoutId = createColumn(
			"ddmStructureLayoutId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, Boolean> readOnly =
		createColumn(
			"readOnly", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AppBuilderWorkflowTaskLinkTable, String>
		workflowTaskName = createColumn(
			"workflowTaskName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private AppBuilderWorkflowTaskLinkTable() {
		super(
			"AppBuilderWorkflowTaskLink", AppBuilderWorkflowTaskLinkTable::new);
	}

}