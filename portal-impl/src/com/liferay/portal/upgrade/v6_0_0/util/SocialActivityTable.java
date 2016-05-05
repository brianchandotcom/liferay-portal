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
public class SocialActivityTable {

	public static final String TABLE_NAME = "SocialActivity";

	public static final Object[][] TABLE_COLUMNS = {
		{"activityId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.BIGINT},
		{"mirrorActivityId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"type_", Types.INTEGER},
		{"extraData", Types.VARCHAR},
		{"receiverUserId", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,mirrorActivityId LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG)";

	public static final String TABLE_SQL_DROP = "drop table SocialActivity";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_82E39A0C on SocialActivity (classNameId)",
		"create index IX_A853C757 on SocialActivity (classNameId, classPK)",
		"create index IX_64B1BC66 on SocialActivity (companyId)",
		"create index IX_2A2468 on SocialActivity (groupId)",
		"create unique index IX_8F32DEC9 on SocialActivity (groupId, userId, createDate, classNameId, classPK, type_, receiverUserId)",
		"create index IX_1271F25F on SocialActivity (mirrorActivityId)",
		"create index IX_1F00C374 on SocialActivity (mirrorActivityId, classNameId, classPK)",
		"create index IX_121CA3CB on SocialActivity (receiverUserId)",
		"create index IX_3504B8BC on SocialActivity (userId)"
	};

}