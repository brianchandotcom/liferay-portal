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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;KaleoTaskForm&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskForm
 * @generated
 */
public class KaleoTaskFormTable extends BaseTable<KaleoTaskFormTable> {

	public static final KaleoTaskFormTable INSTANCE = new KaleoTaskFormTable();

	public final Column<KaleoTaskFormTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoTaskFormTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTaskFormTable, Long> kaleoTaskFormId =
		createColumn(
			"kaleoTaskFormId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTaskFormTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> kaleoDefinitionId =
		createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> kaleoDefinitionVersionId =
		createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> kaleoNodeId = createColumn(
		"kaleoNodeId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> kaleoTaskId = createColumn(
		"kaleoTaskId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> kaleoTaskName =
		createColumn(
			"kaleoTaskName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> formCompanyId = createColumn(
		"formCompanyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> formDefinition =
		createColumn(
			"formDefinition", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> formGroupId = createColumn(
		"formGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Long> formId = createColumn(
		"formId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> formUuid = createColumn(
		"formUuid", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, String> metadata = createColumn(
		"metadata", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private KaleoTaskFormTable() {
		super("KaleoTaskForm", KaleoTaskFormTable::new);
	}

}