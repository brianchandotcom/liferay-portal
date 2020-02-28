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

package com.liferay.change.tracking.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CTProcess&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcess
 * @generated
 */
public class CTProcessTable extends BaseTable<CTProcessTable> {

	public static final CTProcessTable INSTANCE = new CTProcessTable();

	public final Column<CTProcessTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CTProcessTable, Long> ctProcessId = createColumn(
		"ctProcessId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CTProcessTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CTProcessTable, Long> backgroundTaskId = createColumn(
		"backgroundTaskId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private CTProcessTable() {
		super("CTProcess", CTProcessTable::new);
	}

}