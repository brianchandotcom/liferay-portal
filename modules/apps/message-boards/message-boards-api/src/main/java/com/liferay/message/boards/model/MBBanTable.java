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
 * The table class for the MBBan.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBBanTable extends Table<MBBanTable> {

	public static final MBBanTable INSTANCE = new MBBanTable();

	public final Column<MBBanTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MBBanTable, Long> banId = createColumn(
		"banId", Long.class, Types.BIGINT);
	public final Column<MBBanTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MBBanTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MBBanTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MBBanTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MBBanTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MBBanTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MBBanTable, Long> banUserId = createColumn(
		"banUserId", Long.class, Types.BIGINT);
	public final Column<MBBanTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private MBBanTable() {
		super("MBBan", MBBanTable::new);
	}

}