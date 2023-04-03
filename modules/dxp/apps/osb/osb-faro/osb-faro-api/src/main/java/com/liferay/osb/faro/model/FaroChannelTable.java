/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_FaroChannel&quot; database table.
 *
 * @author Matthew Kong
 * @see FaroChannel
 * @generated
 */
public class FaroChannelTable extends BaseTable<FaroChannelTable> {

	public static final FaroChannelTable INSTANCE = new FaroChannelTable();

	public final Column<FaroChannelTable, Long> faroChannelId = createColumn(
		"faroChannelId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FaroChannelTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> modifiedTime = createColumn(
		"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> channelId = createColumn(
		"channelId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Integer> permissionType =
		createColumn(
			"permissionType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<FaroChannelTable, Long> workspaceGroupId = createColumn(
		"workspaceGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private FaroChannelTable() {
		super("OSBFaro_FaroChannel", FaroChannelTable::new);
	}

}