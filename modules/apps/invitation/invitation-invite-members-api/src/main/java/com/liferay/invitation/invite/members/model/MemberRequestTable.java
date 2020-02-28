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

package com.liferay.invitation.invite.members.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;IM_MemberRequest&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MemberRequest
 * @generated
 */
public class MemberRequestTable extends BaseTable<MemberRequestTable> {

	public static final MemberRequestTable INSTANCE = new MemberRequestTable();

	public final Column<MemberRequestTable, Long> memberRequestId =
		createColumn(
			"memberRequestId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MemberRequestTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Long> receiverUserId = createColumn(
		"receiverUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Long> invitedRoleId = createColumn(
		"invitedRoleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Long> invitedTeamId = createColumn(
		"invitedTeamId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MemberRequestTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private MemberRequestTable() {
		super("IM_MemberRequest", MemberRequestTable::new);
	}

}