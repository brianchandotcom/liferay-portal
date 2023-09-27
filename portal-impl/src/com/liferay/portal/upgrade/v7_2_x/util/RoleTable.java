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
public class RoleTable {

	public static final String TABLE_NAME = "Role_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"roleId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"name", Types.VARCHAR}, {"title", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"subtype", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("roleId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("subtype", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table Role_ (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,roleId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,name VARCHAR(75) null,title STRING null,description STRING null,type_ INTEGER,subtype VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Role_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_A88E424E on Role_ (companyId, classNameId, classPK)",
		"create unique index IX_EBC931B8 on Role_ (companyId, name[$COLUMN_LENGTH:75$])",
		"create index IX_F3E1C6FC on Role_ (companyId, type_)",
		"create index IX_F436EC8E on Role_ (name[$COLUMN_LENGTH:75$])",
		"create index IX_5EB4E2FB on Role_ (subtype[$COLUMN_LENGTH:75$])",
		"create index IX_CBE204 on Role_ (type_, subtype[$COLUMN_LENGTH:75$])",
		"create index IX_B9FF6043 on Role_ (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}