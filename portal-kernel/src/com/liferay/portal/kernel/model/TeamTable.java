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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Team&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Team
 * @generated
 */
public class TeamTable extends BaseTable<TeamTable> {

	public static final TeamTable INSTANCE = new TeamTable();

	public final Column<TeamTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<TeamTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TeamTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Long> teamId = createColumn(
		"teamId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TeamTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TeamTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TeamTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TeamTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TeamTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private TeamTable() {
		super("Team", TeamTable::new);
	}

}