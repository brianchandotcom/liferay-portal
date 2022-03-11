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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Group_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @generated
 */
public class GroupTable extends BaseTable<GroupTable> {

	public static final GroupTable INSTANCE = new GroupTable();

	public final Column<GroupTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<GroupTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<GroupTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<GroupTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> creatorUserId = createColumn(
		"creatorUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> parentGroupId = createColumn(
		"parentGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Long> liveGroupId = createColumn(
		"liveGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<GroupTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, String> groupKey = createColumn(
		"groupKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Boolean> manualMembership = createColumn(
		"manualMembership", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Integer> membershipRestriction =
		createColumn(
			"membershipRestriction", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<GroupTable, String> friendlyURL = createColumn(
		"friendlyURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Boolean> site = createColumn(
		"site", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Integer> remoteStagingGroupCount =
		createColumn(
			"remoteStagingGroupCount", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<GroupTable, Boolean> inheritContent = createColumn(
		"inheritContent", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<GroupTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private GroupTable() {
		super("Group_", GroupTable::new);
	}

}