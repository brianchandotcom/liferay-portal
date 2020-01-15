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

package com.liferay.layout.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the LayoutClassedModelUsage.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutClassedModelUsageTable
	extends Table<LayoutClassedModelUsageTable> {

	public static final LayoutClassedModelUsageTable INSTANCE =
		new LayoutClassedModelUsageTable();

	public final Column<LayoutClassedModelUsageTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR);
	public final Column<LayoutClassedModelUsageTable, Long>
		layoutClassedModelUsageId = createColumn(
			"layoutClassedModelUsageId", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutClassedModelUsageTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<LayoutClassedModelUsageTable, Long> classNameId =
		createColumn("classNameId", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, Long> classPK =
		createColumn("classPK", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, String> containerKey =
		createColumn("containerKey", String.class, Types.VARCHAR);
	public final Column<LayoutClassedModelUsageTable, Long> containerType =
		createColumn("containerType", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT);
	public final Column<LayoutClassedModelUsageTable, Integer> type =
		createColumn("type_", Integer.class, Types.INTEGER);
	public final Column<LayoutClassedModelUsageTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);

	private LayoutClassedModelUsageTable() {
		super("LayoutClassedModelUsage", LayoutClassedModelUsageTable::new);
	}

}