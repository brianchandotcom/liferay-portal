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

package com.liferay.announcements.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AnnouncementsFlag&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlag
 * @generated
 */
public class AnnouncementsFlagTable extends BaseTable<AnnouncementsFlagTable> {

	public static final AnnouncementsFlagTable INSTANCE =
		new AnnouncementsFlagTable();

	public final Column<AnnouncementsFlagTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AnnouncementsFlagTable, Long> flagId = createColumn(
		"flagId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AnnouncementsFlagTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsFlagTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsFlagTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsFlagTable, Long> entryId = createColumn(
		"entryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsFlagTable, Integer> value = createColumn(
		"value", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private AnnouncementsFlagTable() {
		super("AnnouncementsFlag", AnnouncementsFlagTable::new);
	}

}