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

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MBThreadFlag.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBThreadFlagTable extends Table<MBThreadFlagTable> {

	public static final MBThreadFlagTable INSTANCE = new MBThreadFlagTable();

	public final Column<MBThreadFlagTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MBThreadFlagTable, Long> threadFlagId = createColumn(
		"threadFlagId", Long.class, Types.BIGINT);
	public final Column<MBThreadFlagTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MBThreadFlagTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MBThreadFlagTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MBThreadFlagTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MBThreadFlagTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MBThreadFlagTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MBThreadFlagTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT);
	public final Column<MBThreadFlagTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private MBThreadFlagTable() {
		super("MBThreadFlag", MBThreadFlagTable::new);
	}

}