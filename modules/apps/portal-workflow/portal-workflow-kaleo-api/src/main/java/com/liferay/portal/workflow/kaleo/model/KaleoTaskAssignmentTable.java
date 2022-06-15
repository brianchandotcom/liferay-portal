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
 * The table class for the &quot;KaleoTaskAssignment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskAssignment
 * @generated
 */
public class KaleoTaskAssignmentTable
	extends BaseTable<KaleoTaskAssignmentTable> {

	public static final KaleoTaskAssignmentTable INSTANCE =
		new KaleoTaskAssignmentTable();

	public final Column<KaleoTaskAssignmentTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoTaskAssignmentTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTaskAssignmentTable, Long> kaleoTaskAssignmentId =
		createColumn(
			"kaleoTaskAssignmentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoTaskAssignmentTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String> kaleoClassName =
		createColumn(
			"kaleoClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> kaleoClassPK =
		createColumn(
			"kaleoClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> kaleoDefinitionId =
		createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> kaleoNodeId =
		createColumn(
			"kaleoNodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String> assigneeClassName =
		createColumn(
			"assigneeClassName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Long> assigneeClassPK =
		createColumn(
			"assigneeClassPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String> assigneeActionId =
		createColumn(
			"assigneeActionId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, Clob> assigneeScript =
		createColumn(
			"assigneeScript", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String>
		assigneeScriptLanguage = createColumn(
			"assigneeScriptLanguage", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskAssignmentTable, String>
		assigneeScriptRequiredContexts = createColumn(
			"assigneeScriptRequiredContexts", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private KaleoTaskAssignmentTable() {
		super("KaleoTaskAssignment", KaleoTaskAssignmentTable::new);
	}

}