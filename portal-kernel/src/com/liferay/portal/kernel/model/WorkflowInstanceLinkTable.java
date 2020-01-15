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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the WorkflowInstanceLink.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WorkflowInstanceLinkTable
	extends Table<WorkflowInstanceLinkTable> {

	public static final WorkflowInstanceLinkTable INSTANCE =
		new WorkflowInstanceLinkTable();

	public final Column<WorkflowInstanceLinkTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long>
		workflowInstanceLinkId = createColumn(
			"workflowInstanceLinkId", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<WorkflowInstanceLinkTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowInstanceLinkTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<WorkflowInstanceLinkTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<WorkflowInstanceLinkTable, Long> workflowInstanceId =
		createColumn("workflowInstanceId", Long.class, Types.BIGINT);

	private WorkflowInstanceLinkTable() {
		super("WorkflowInstanceLink", WorkflowInstanceLinkTable::new);
	}

}