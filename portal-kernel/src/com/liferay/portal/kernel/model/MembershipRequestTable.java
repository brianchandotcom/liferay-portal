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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MembershipRequest.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MembershipRequestTable extends Table<MembershipRequestTable> {

	public static final MembershipRequestTable INSTANCE =
		new MembershipRequestTable();

	public final Column<MembershipRequestTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Long> membershipRequestId =
		createColumn("membershipRequestId", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MembershipRequestTable, String> comments = createColumn(
		"comments", String.class, Types.VARCHAR);
	public final Column<MembershipRequestTable, String> replyComments =
		createColumn("replyComments", String.class, Types.VARCHAR);
	public final Column<MembershipRequestTable, Date> replyDate = createColumn(
		"replyDate", Date.class, Types.TIMESTAMP);
	public final Column<MembershipRequestTable, Long> replierUserId =
		createColumn("replierUserId", Long.class, Types.BIGINT);
	public final Column<MembershipRequestTable, Long> statusId = createColumn(
		"statusId", Long.class, Types.BIGINT);

	private MembershipRequestTable() {
		super("MembershipRequest", MembershipRequestTable::new);
	}

}