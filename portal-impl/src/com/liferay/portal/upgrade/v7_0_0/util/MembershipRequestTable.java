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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class MembershipRequestTable {

	public static final String TABLE_NAME = "MembershipRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"membershipRequestId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"comments", Types.VARCHAR},
		{"replyComments", Types.VARCHAR},
		{"replyDate", Types.TIMESTAMP},
		{"replierUserId", Types.BIGINT},
		{"statusId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("membershipRequestId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("comments", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("replyComments", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("replyDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("replierUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table MembershipRequest (mvccVersion LONG default 0,membershipRequestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,comments STRING null,replyComments STRING null,replyDate DATE null,replierUserId LONG,statusId LONG)";

	public static final String TABLE_SQL_DROP = "drop table MembershipRequest";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C28C72EC on MembershipRequest (groupId, statusId)",
		"create index IX_35AA8FA6 on MembershipRequest (groupId, userId, statusId)",
		"create index IX_66D70879 on MembershipRequest (userId)"
	};

}