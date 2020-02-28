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

package com.liferay.adaptive.media.image.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AMImageEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntry
 * @generated
 */
public class AMImageEntryTable extends BaseTable<AMImageEntryTable> {

	public static final AMImageEntryTable INSTANCE = new AMImageEntryTable();

	public final Column<AMImageEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> amImageEntryId = createColumn(
		"amImageEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AMImageEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, String> configurationUuid =
		createColumn(
			"configurationUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> fileVersionId = createColumn(
		"fileVersionId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, String> mimeType = createColumn(
		"mimeType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Integer> height = createColumn(
		"height", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Integer> width = createColumn(
		"width", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<AMImageEntryTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AMImageEntryTable() {
		super("AMImageEntry", AMImageEntryTable::new);
	}

}