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

package com.liferay.segments.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the SegmentsEntryRole.
 *
 * @author Eduardo Garcia
 * @generated
 */
public class SegmentsEntryRoleTable extends Table<SegmentsEntryRoleTable> {

	public static final SegmentsEntryRoleTable INSTANCE =
		new SegmentsEntryRoleTable();

	public final Column<SegmentsEntryRoleTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRoleTable, Long> segmentsEntryRoleId =
		createColumn("segmentsEntryRoleId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRoleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRoleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRoleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<SegmentsEntryRoleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<SegmentsEntryRoleTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<SegmentsEntryRoleTable, Long> segmentsEntryId =
		createColumn("segmentsEntryId", Long.class, Types.BIGINT);
	public final Column<SegmentsEntryRoleTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT);

	private SegmentsEntryRoleTable() {
		super("SegmentsEntryRole", SegmentsEntryRoleTable::new);
	}

}