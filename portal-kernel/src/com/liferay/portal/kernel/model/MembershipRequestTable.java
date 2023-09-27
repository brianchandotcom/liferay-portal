/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MembershipRequest&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MembershipRequest
 * @generated
 */
public class MembershipRequestTable extends BaseTable<MembershipRequestTable> {

	public static final MembershipRequestTable INSTANCE =
		new MembershipRequestTable();

	public final Column<MembershipRequestTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MembershipRequestTable, Long> membershipRequestId =
		createColumn(
			"membershipRequestId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<MembershipRequestTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, String> comments = createColumn(
		"comments", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, String> replyComments =
		createColumn(
			"replyComments", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Date> replyDate = createColumn(
		"replyDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Long> replierUserId =
		createColumn(
			"replierUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MembershipRequestTable, Long> statusId = createColumn(
		"statusId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private MembershipRequestTable() {
		super("MembershipRequest", MembershipRequestTable::new);
	}

}