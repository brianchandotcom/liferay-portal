/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class GroupTable {

	public static final String TABLE_NAME = "Group_";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"creatorUserId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"parentGroupId", Types.BIGINT},
		{"liveGroupId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"type_", Types.INTEGER},
		{"typeSettings", Types.CLOB},
		{"friendlyURL", Types.VARCHAR},
		{"site", Types.BOOLEAN},
		{"active_", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table Group_ (uuid_ VARCHAR(75) null,groupId LONG not null primary key,companyId LONG,creatorUserId LONG,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,treePath VARCHAR(75) null,name VARCHAR(150) null,description STRING null,type_ INTEGER,typeSettings TEXT null,friendlyURL VARCHAR(255) null,site BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Group_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_ABA5CEC2 on Group_ (companyId)",
		"create unique index IX_D0D5E397 on Group_ (companyId, classNameId, classPK)",
		"create unique index IX_5DE0BE11 on Group_ (companyId, classNameId, liveGroupId, name)",
		"create index IX_ABE2D54 on Group_ (companyId, classNameId, parentGroupId)",
		"create unique index IX_5BDDB872 on Group_ (companyId, friendlyURL)",
		"create unique index IX_BBCA55B on Group_ (companyId, liveGroupId, name)",
		"create unique index IX_5AA68501 on Group_ (companyId, name)",
		"create index IX_5D75499E on Group_ (companyId, parentGroupId)",
		"create index IX_6C499099 on Group_ (companyId, parentGroupId, site)",
		"create index IX_63A2AABD on Group_ (companyId, site)",
		"create index IX_16218A38 on Group_ (liveGroupId)",
		"create index IX_7B590A7A on Group_ (type_, active_)",
		"create index IX_F981514E on Group_ (uuid_)",
		"create index IX_26CC761A on Group_ (uuid_, companyId)",
		"create unique index IX_754FBB1C on Group_ (uuid_, groupId)"
	};

}