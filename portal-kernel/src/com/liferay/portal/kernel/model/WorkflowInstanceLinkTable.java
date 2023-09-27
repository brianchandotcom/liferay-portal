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
 * The table class for the &quot;WorkflowInstanceLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLink
 * @generated
 */
public class WorkflowInstanceLinkTable
	extends BaseTable<WorkflowInstanceLinkTable> {

	public static final WorkflowInstanceLinkTable INSTANCE =
		new WorkflowInstanceLinkTable();

	public final Column<WorkflowInstanceLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WorkflowInstanceLinkTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<WorkflowInstanceLinkTable, Long>
		workflowInstanceLinkId = createColumn(
			"workflowInstanceLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<WorkflowInstanceLinkTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowInstanceLinkTable, Long> workflowInstanceId =
		createColumn(
			"workflowInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private WorkflowInstanceLinkTable() {
		super("WorkflowInstanceLink", WorkflowInstanceLinkTable::new);
	}

}