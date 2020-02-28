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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;WorkflowDefinitionLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowDefinitionLink
 * @generated
 */
public class WorkflowDefinitionLinkTable
	extends BaseTable<WorkflowDefinitionLinkTable> {

	public static final WorkflowDefinitionLinkTable INSTANCE =
		new WorkflowDefinitionLinkTable();

	public final Column<WorkflowDefinitionLinkTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<WorkflowDefinitionLinkTable, Long>
		workflowDefinitionLinkId = createColumn(
			"workflowDefinitionLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<WorkflowDefinitionLinkTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Long> typePK =
		createColumn("typePK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, String>
		workflowDefinitionName = createColumn(
			"workflowDefinitionName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<WorkflowDefinitionLinkTable, Integer>
		workflowDefinitionVersion = createColumn(
			"workflowDefinitionVersion", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private WorkflowDefinitionLinkTable() {
		super("WorkflowDefinitionLink", WorkflowDefinitionLinkTable::new);
	}

}