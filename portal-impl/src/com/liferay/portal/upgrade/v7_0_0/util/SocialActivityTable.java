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
public class SocialActivityTable {

	public static final String TABLE_NAME = "SocialActivity";

	public static final Object[][] TABLE_COLUMNS = {
		{"activityId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.BIGINT},
		{"activitySetId", Types.BIGINT},
		{"mirrorActivityId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"parentClassNameId", Types.BIGINT},
		{"parentClassPK", Types.BIGINT},
		{"type_", Types.INTEGER},
		{"extraData", Types.CLOB},
		{"receiverUserId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("activityId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.BIGINT);

TABLE_COLUMNS_MAP.put("activitySetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("mirrorActivityId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentClassNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("extraData", Types.CLOB);

TABLE_COLUMNS_MAP.put("receiverUserId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,activitySetId LONG,mirrorActivityId LONG,classNameId LONG,classPK LONG,parentClassNameId LONG,parentClassPK LONG,type_ INTEGER,extraData TEXT null,receiverUserId LONG)";

	public static final String TABLE_SQL_DROP = "drop table SocialActivity";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F542E9BC on SocialActivity (activitySetId)",
		"create index IX_D0E9029E on SocialActivity (classNameId, classPK, type_)",
		"create index IX_64B1BC66 on SocialActivity (companyId)",
		"create index IX_FB604DC7 on SocialActivity (groupId, userId, classNameId, classPK, type_, receiverUserId)",
		"create unique index IX_8F32DEC9 on SocialActivity (groupId, userId, createDate, classNameId, classPK, type_, receiverUserId)",
		"create index IX_1F00C374 on SocialActivity (mirrorActivityId, classNameId, classPK)",
		"create index IX_121CA3CB on SocialActivity (receiverUserId)",
		"create index IX_3504B8BC on SocialActivity (userId)"
	};

}