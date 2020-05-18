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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDMFormInstanceReport&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceReport
 * @generated
 */
public class DDMFormInstanceReportTable
	extends BaseTable<DDMFormInstanceReportTable> {

	public static final DDMFormInstanceReportTable INSTANCE =
		new DDMFormInstanceReportTable();

	public final Column<DDMFormInstanceReportTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMFormInstanceReportTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFormInstanceReportTable, Long> formInstanceReportId =
		createColumn(
			"formInstanceReportId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMFormInstanceReportTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceReportTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceReportTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceReportTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceReportTable, Long> formInstanceId =
		createColumn(
			"formInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceReportTable, Clob> data = createColumn(
		"data_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private DDMFormInstanceReportTable() {
		super("DDMFormInstanceReport", DDMFormInstanceReportTable::new);
	}

}