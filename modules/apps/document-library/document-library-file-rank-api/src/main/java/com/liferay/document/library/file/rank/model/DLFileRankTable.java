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

package com.liferay.document.library.file.rank.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DLFileRank.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileRankTable extends Table<DLFileRankTable> {

	public static final DLFileRankTable INSTANCE = new DLFileRankTable();

	public final Column<DLFileRankTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Long> fileRankId = createColumn(
		"fileRankId", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileRankTable, Long> fileEntryId = createColumn(
		"fileEntryId", Long.class, Types.BIGINT);
	public final Column<DLFileRankTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN);

	private DLFileRankTable() {
		super("DLFileRank", DLFileRankTable::new);
	}

}