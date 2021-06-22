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

package com.liferay.portal.rolling.restart.sample.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;RollingRestartSampleEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see RollingRestartSampleEntry
 * @generated
 */
public class RollingRestartSampleEntryTable
	extends BaseTable<RollingRestartSampleEntryTable> {

	public static final RollingRestartSampleEntryTable INSTANCE =
		new RollingRestartSampleEntryTable();

	public final Column<RollingRestartSampleEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RollingRestartSampleEntryTable, Long> entryId =
		createColumn("entryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RollingRestartSampleEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RollingRestartSampleEntryTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private RollingRestartSampleEntryTable() {
		super("RollingRestartSampleEntry", RollingRestartSampleEntryTable::new);
	}

}