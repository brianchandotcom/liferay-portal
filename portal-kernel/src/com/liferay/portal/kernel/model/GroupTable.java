/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public final Column<GroupTable, String> externalReferenceCode =
		createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
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