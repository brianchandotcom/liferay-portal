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

package com.liferay.friendly.url.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the FriendlyURLEntry.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FriendlyURLEntryTable extends Table<FriendlyURLEntryTable> {

	public static final FriendlyURLEntryTable INSTANCE =
		new FriendlyURLEntryTable();

	public final Column<FriendlyURLEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<FriendlyURLEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<FriendlyURLEntryTable, String> defaultLanguageId =
		createColumn("defaultLanguageId", String.class, Types.VARCHAR);
	public final Column<FriendlyURLEntryTable, Long> friendlyURLEntryId =
		createColumn("friendlyURLEntryId", Long.class, Types.BIGINT);
	public final Column<FriendlyURLEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<FriendlyURLEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<FriendlyURLEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<FriendlyURLEntryTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<FriendlyURLEntryTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<FriendlyURLEntryTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);

	private FriendlyURLEntryTable() {
		super("FriendlyURLEntry", FriendlyURLEntryTable::new);
	}

}