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

package com.liferay.wiki.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class WikiPageTable {

	public static final String TABLE_NAME = "WikiPage";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"pageId", Types.BIGINT},
		{"resourcePrimKey", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"nodeId", Types.BIGINT},
		{"title", Types.VARCHAR},
		{"version", Types.DOUBLE},
		{"minorEdit", Types.BOOLEAN},
		{"content", Types.CLOB},
		{"summary", Types.VARCHAR},
		{"format", Types.VARCHAR},
		{"head", Types.BOOLEAN},
		{"parentTitle", Types.VARCHAR},
		{"redirectTitle", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("nodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("minorEdit", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("content", Types.CLOB);

TABLE_COLUMNS_MAP.put("summary", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("format", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("head", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("parentTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("redirectTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table WikiPage (uuid_ VARCHAR(75) null,pageId LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,nodeId LONG,title VARCHAR(255) null,version DOUBLE,minorEdit BOOLEAN,content TEXT null,summary STRING null,format VARCHAR(75) null,head BOOLEAN,parentTitle VARCHAR(255) null,redirectTitle VARCHAR(255) null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table WikiPage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A2001730 on WikiPage (format[$COLUMN_LENGTH:75$])",
		"create index IX_BA72B89A on WikiPage (groupId, nodeId, head, parentTitle[$COLUMN_LENGTH:255$], status)",
		"create index IX_E0092FF0 on WikiPage (groupId, nodeId, head, status)",
		"create index IX_941E429C on WikiPage (groupId, nodeId, status)",
		"create index IX_5FF21CE6 on WikiPage (groupId, nodeId, title[$COLUMN_LENGTH:255$], head)",
		"create index IX_CAA451D6 on WikiPage (groupId, userId, nodeId, status)",
		"create index IX_9F7655DA on WikiPage (nodeId, head, parentTitle[$COLUMN_LENGTH:255$], status)",
		"create index IX_40F94F68 on WikiPage (nodeId, head, redirectTitle[$COLUMN_LENGTH:255$], status)",
		"create index IX_432F0AB0 on WikiPage (nodeId, head, status)",
		"create index IX_46EEF3C8 on WikiPage (nodeId, parentTitle[$COLUMN_LENGTH:255$])",
		"create index IX_1ECC7656 on WikiPage (nodeId, redirectTitle[$COLUMN_LENGTH:255$])",
		"create index IX_546F2D5C on WikiPage (nodeId, status)",
		"create index IX_E745EA26 on WikiPage (nodeId, title[$COLUMN_LENGTH:255$], head)",
		"create index IX_BEA33AB8 on WikiPage (nodeId, title[$COLUMN_LENGTH:255$], status)",
		"create unique index IX_3D4AF476 on WikiPage (nodeId, title[$COLUMN_LENGTH:255$], version)",
		"create index IX_E1F55FB on WikiPage (resourcePrimKey, nodeId, head)",
		"create index IX_94D1054D on WikiPage (resourcePrimKey, nodeId, status)",
		"create unique index IX_2CD67C81 on WikiPage (resourcePrimKey, nodeId, version)",
		"create index IX_1725355C on WikiPage (resourcePrimKey, status)",
		"create index IX_FBBE7C96 on WikiPage (userId, nodeId, status)",
		"create index IX_5DC4BD39 on WikiPage (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_899D3DFB on WikiPage (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}