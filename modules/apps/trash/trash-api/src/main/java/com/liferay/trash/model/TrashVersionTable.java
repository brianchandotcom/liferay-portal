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

package com.liferay.trash.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the TrashVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TrashVersionTable extends Table<TrashVersionTable> {

	public static final TrashVersionTable INSTANCE = new TrashVersionTable();

	public final Column<TrashVersionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Long> versionId = createColumn(
		"versionId", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<TrashVersionTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB);
	public final Column<TrashVersionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);

	private TrashVersionTable() {
		super("TrashVersion", TrashVersionTable::new);
	}

}