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
public class LayoutRevisionTable {

	public static final String TABLE_NAME = "LayoutRevision";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"layoutRevisionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"layoutSetBranchId", Types.BIGINT}, {"layoutBranchId", Types.BIGINT},
		{"parentLayoutRevisionId", Types.BIGINT}, {"head", Types.BOOLEAN},
		{"major", Types.BOOLEAN}, {"plid", Types.BIGINT},
		{"privateLayout", Types.BOOLEAN}, {"name", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"keywords", Types.VARCHAR}, {"robots", Types.VARCHAR},
		{"typeSettings", Types.CLOB}, {"iconImageId", Types.BIGINT},
		{"themeId", Types.VARCHAR}, {"colorSchemeId", Types.VARCHAR},
		{"css", Types.CLOB}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutRevisionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("layoutSetBranchId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutBranchId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentLayoutRevisionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("head", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("major", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("keywords", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("robots", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("iconImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("themeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("colorSchemeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table LayoutRevision (mvccVersion LONG default 0 not null,layoutRevisionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,layoutSetBranchId LONG,layoutBranchId LONG,parentLayoutRevisionId LONG,head BOOLEAN,major BOOLEAN,plid LONG,privateLayout BOOLEAN,name STRING null,title STRING null,description STRING null,keywords STRING null,robots STRING null,typeSettings TEXT null,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,css TEXT null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table LayoutRevision";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_43E8286A on LayoutRevision (head, plid)",
		"create index IX_E10AC39 on LayoutRevision (layoutSetBranchId, head, plid)",
		"create index IX_9EC9F954 on LayoutRevision (layoutSetBranchId, head, status)",
		"create index IX_38C5DF14 on LayoutRevision (layoutSetBranchId, layoutBranchId, head, plid)",
		"create index IX_13984800 on LayoutRevision (layoutSetBranchId, layoutBranchId, plid)",
		"create index IX_4A84AF43 on LayoutRevision (layoutSetBranchId, parentLayoutRevisionId, plid)",
		"create index IX_70DA9ECB on LayoutRevision (layoutSetBranchId, plid, status)",
		"create index IX_7FFAE700 on LayoutRevision (layoutSetBranchId, status)",
		"create index IX_8EC3D2BC on LayoutRevision (plid, status)",
		"create index IX_421223B1 on LayoutRevision (status)"
	};

}