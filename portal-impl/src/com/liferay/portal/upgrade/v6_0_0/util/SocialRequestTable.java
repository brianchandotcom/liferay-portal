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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class SocialRequestTable {

	public static final String TABLE_NAME = "SocialRequest";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"requestId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.BIGINT},
		{"modifiedDate", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"type_", Types.INTEGER},
		{"extraData", Types.VARCHAR},
		{"receiverUserId", Types.BIGINT},
		{"status", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table SocialRequest (uuid_ VARCHAR(75) null,requestId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,modifiedDate LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table SocialRequest";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_D3425487 on SocialRequest (classNameId, classPK, type_, receiverUserId, status)",
		"create index IX_A90FE5A0 on SocialRequest (companyId)",
		"create index IX_32292ED1 on SocialRequest (receiverUserId)",
		"create index IX_D9380CB7 on SocialRequest (receiverUserId, status)",
		"create index IX_80F7A9C2 on SocialRequest (userId)",
		"create unique index IX_36A90CA7 on SocialRequest (userId, classNameId, classPK, type_, receiverUserId)",
		"create index IX_CC86A444 on SocialRequest (userId, classNameId, classPK, type_, status)",
		"create index IX_AB5906A8 on SocialRequest (userId, status)",
		"create index IX_49D5872C on SocialRequest (uuid_)",
		"create unique index IX_4F973EFE on SocialRequest (uuid_, groupId)"
	};

}