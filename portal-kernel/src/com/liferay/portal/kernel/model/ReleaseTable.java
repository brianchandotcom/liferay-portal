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
 * The table class for the &quot;Release_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Release
 * @generated
 */
public class ReleaseTable extends BaseTable<ReleaseTable> {

	public static final ReleaseTable INSTANCE = new ReleaseTable();

	public final Column<ReleaseTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ReleaseTable, Long> releaseId = createColumn(
		"releaseId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<ReleaseTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, String> servletContextName = createColumn(
		"servletContextName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, String> schemaVersion = createColumn(
		"schemaVersion", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, Integer> buildNumber = createColumn(
		"buildNumber", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, Date> buildDate = createColumn(
		"buildDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, Boolean> verified = createColumn(
		"verified", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, Integer> state = createColumn(
		"state_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ReleaseTable, String> testString = createColumn(
		"testString", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private ReleaseTable() {
		super("Release_", ReleaseTable::new);
	}

}