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

package com.liferay.invitation.invite.members.internal.upgrade.v1_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Adolfo Pérez
 * @generated
 */
public class MemberRequestTable {

	public static final String TABLE_NAME = "MemberRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"memberRequestId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"key_", Types.VARCHAR},
		{"receiverUserId", Types.BIGINT},
		{"invitedRoleId", Types.BIGINT},
		{"invitedTeamId", Types.BIGINT},
		{"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("memberRequestId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("receiverUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("invitedRoleId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("invitedTeamId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table MemberRequest (memberRequestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,key_ VARCHAR(75) null,receiverUserId LONG,invitedRoleId LONG,invitedTeamId LONG,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table MemberRequest";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_85C0843E on MemberRequest (groupId, receiverUserId, status)",
		"create index IX_9BBFC28F on MemberRequest (key_[$COLUMN_LENGTH:75$])",
		"create index IX_F6ED732A on MemberRequest (receiverUserId, status)"
	};

}