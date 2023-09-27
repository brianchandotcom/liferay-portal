/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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