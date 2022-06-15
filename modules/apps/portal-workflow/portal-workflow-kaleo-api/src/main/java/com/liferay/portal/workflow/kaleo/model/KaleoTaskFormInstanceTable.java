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
 * The table class for the &quot;KaleoTaskFormInstance&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstance
 * @generated
 */
public class KaleoTaskFormInstanceTable
	extends BaseTable<KaleoTaskFormInstanceTable> {

	public static final KaleoTaskFormInstanceTable INSTANCE =
		new KaleoTaskFormInstanceTable();

	public final Column<KaleoTaskFormInstanceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<KaleoTaskFormInstanceTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<KaleoTaskFormInstanceTable, Long>
		kaleoTaskFormInstanceId = createColumn(
			"kaleoTaskFormInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<KaleoTaskFormInstanceTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> kaleoDefinitionId =
		createColumn(
			"kaleoDefinitionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long>
		kaleoDefinitionVersionId = createColumn(
			"kaleoDefinitionVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> kaleoInstanceId =
		createColumn(
			"kaleoInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> kaleoTaskId =
		createColumn(
			"kaleoTaskId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long>
		kaleoTaskInstanceTokenId = createColumn(
			"kaleoTaskInstanceTokenId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> kaleoTaskFormId =
		createColumn(
			"kaleoTaskFormId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, String> formValues =
		createColumn(
			"formValues", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long>
		formValueEntryGroupId = createColumn(
			"formValueEntryGroupId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, Long> formValueEntryId =
		createColumn(
			"formValueEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, String> formValueEntryUuid =
		createColumn(
			"formValueEntryUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<KaleoTaskFormInstanceTable, String> metadata =
		createColumn(
			"metadata", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private KaleoTaskFormInstanceTable() {
		super("KaleoTaskFormInstance", KaleoTaskFormInstanceTable::new);
	}

}