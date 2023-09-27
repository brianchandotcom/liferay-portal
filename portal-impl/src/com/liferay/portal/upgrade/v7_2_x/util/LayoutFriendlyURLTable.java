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
public class LayoutFriendlyURLTable {

	public static final String TABLE_NAME = "LayoutFriendlyURL";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"layoutFriendlyURLId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"plid", Types.BIGINT},
		{"privateLayout", Types.BOOLEAN}, {"friendlyURL", Types.VARCHAR},
		{"languageId", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutFriendlyURLId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table LayoutFriendlyURL (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,layoutFriendlyURLId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,plid LONG,privateLayout BOOLEAN,friendlyURL VARCHAR(255) null,languageId VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table LayoutFriendlyURL";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EAB317C8 on LayoutFriendlyURL (companyId)",
		"create unique index IX_A6FC2B28 on LayoutFriendlyURL (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$])",
		"create index IX_59051329 on LayoutFriendlyURL (plid, friendlyURL[$COLUMN_LENGTH:255$])",
		"create unique index IX_C5762E72 on LayoutFriendlyURL (plid, languageId[$COLUMN_LENGTH:75$])",
		"create index IX_F4321A54 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_326525D6 on LayoutFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}