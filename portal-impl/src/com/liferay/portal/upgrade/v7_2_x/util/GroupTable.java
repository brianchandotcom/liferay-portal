/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class GroupTable {

	public static final String TABLE_NAME = "Group_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"creatorUserId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"parentGroupId", Types.BIGINT},
		{"liveGroupId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"groupKey", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"typeSettings", Types.CLOB}, {"manualMembership", Types.BOOLEAN},
		{"membershipRestriction", Types.INTEGER},
		{"friendlyURL", Types.VARCHAR}, {"site", Types.BOOLEAN},
		{"remoteStagingGroupCount", Types.INTEGER},
		{"inheritContent", Types.BOOLEAN}, {"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("creatorUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("liveGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("groupKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("manualMembership", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("membershipRestriction", Types.INTEGER);

TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("site", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("remoteStagingGroupCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("inheritContent", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table Group_ (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,groupId LONG not null primary key,companyId LONG,creatorUserId LONG,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,treePath STRING null,groupKey VARCHAR(150) null,name STRING null,description STRING null,type_ INTEGER,typeSettings TEXT null,manualMembership BOOLEAN,membershipRestriction INTEGER,friendlyURL VARCHAR(255) null,site BOOLEAN,remoteStagingGroupCount INTEGER,inheritContent BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Group_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8257E37B on Group_ (classNameId, classPK)",
		"create index IX_BD3CB13A on Group_ (classNameId, groupId, companyId, parentGroupId)",
		"create index IX_DDC91A87 on Group_ (companyId, active_)",
		"create unique index IX_D0D5E397 on Group_ (companyId, classNameId, classPK)",
		"create unique index IX_A729E3A6 on Group_ (companyId, classNameId, liveGroupId, groupKey[$COLUMN_LENGTH:150$])",
		"create index IX_ABE2D54 on Group_ (companyId, classNameId, parentGroupId)",
		"create unique index IX_5BDDB872 on Group_ (companyId, friendlyURL[$COLUMN_LENGTH:255$])",
		"create unique index IX_ACD2B296 on Group_ (companyId, groupKey[$COLUMN_LENGTH:150$])",
		"create unique index IX_AACD15F0 on Group_ (companyId, liveGroupId, groupKey[$COLUMN_LENGTH:150$])",
		"create index IX_D4BFF38B on Group_ (companyId, parentGroupId, site, inheritContent)",
		"create index IX_B91488EC on Group_ (companyId, site, active_)",
		"create index IX_16218A38 on Group_ (liveGroupId)",
		"create index IX_7B590A7A on Group_ (type_, active_)",
		"create index IX_26CC761A on Group_ (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_754FBB1C on Group_ (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}