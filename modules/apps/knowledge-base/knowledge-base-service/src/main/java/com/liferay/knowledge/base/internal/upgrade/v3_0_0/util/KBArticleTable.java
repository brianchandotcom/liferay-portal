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

package com.liferay.knowledge.base.internal.upgrade.v3_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KBArticleTable {

	public static final String TABLE_NAME = "KBArticle";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"kbArticleId", Types.BIGINT},
		{"resourcePrimKey", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"rootResourcePrimKey", Types.BIGINT},
		{"parentResourceClassNameId", Types.BIGINT},
		{"parentResourcePrimKey", Types.BIGINT},
		{"kbFolderId", Types.BIGINT},
		{"version", Types.INTEGER},
		{"title", Types.VARCHAR},
		{"urlTitle", Types.VARCHAR},
		{"content", Types.CLOB},
		{"description", Types.VARCHAR},
		{"priority", Types.DOUBLE},
		{"sections", Types.VARCHAR},
		{"viewCount", Types.INTEGER},
		{"latest", Types.BOOLEAN},
		{"main", Types.BOOLEAN},
		{"sourceURL", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kbArticleId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("rootResourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentResourceClassNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentResourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kbFolderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("content", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("sections", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("viewCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("latest", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("main", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("sourceURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table KBArticle (uuid_ VARCHAR(75) null,kbArticleId LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,rootResourcePrimKey LONG,parentResourceClassNameId LONG,parentResourcePrimKey LONG,kbFolderId LONG,version INTEGER,title STRING null,urlTitle VARCHAR(75) null,content TEXT null,description STRING null,priority DOUBLE,sections STRING null,viewCount INTEGER,latest BOOLEAN,main BOOLEAN,sourceURL STRING null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table KBArticle";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_571C019E on KBArticle (companyId, latest)",
		"create index IX_5A381890 on KBArticle (companyId, main)",
		"create index IX_FBC2D349 on KBArticle (companyId, status)",
		"create index IX_2B11F674 on KBArticle (groupId, kbFolderId, latest)",
		"create index IX_CFB8C81F on KBArticle (groupId, kbFolderId, status)",
		"create index IX_379FD6BC on KBArticle (groupId, kbFolderId, urlTitle[$COLUMN_LENGTH:75$], status)",
		"create index IX_694EA2E0 on KBArticle (groupId, latest)",
		"create index IX_97C62252 on KBArticle (groupId, main)",
		"create index IX_B0FCBB47 on KBArticle (groupId, parentResourcePrimKey, latest)",
		"create index IX_D91D2879 on KBArticle (groupId, parentResourcePrimKey, main)",
		"create index IX_55A38CF2 on KBArticle (groupId, parentResourcePrimKey, status)",
		"create index IX_DF5748B on KBArticle (groupId, status)",
		"create index IX_86BA3247 on KBArticle (parentResourcePrimKey, latest)",
		"create index IX_1DCC5F79 on KBArticle (parentResourcePrimKey, main)",
		"create index IX_2B6103F2 on KBArticle (parentResourcePrimKey, status)",
		"create index IX_5FEF5F4F on KBArticle (resourcePrimKey, groupId, latest)",
		"create index IX_8EF92E81 on KBArticle (resourcePrimKey, groupId, main)",
		"create index IX_49630FA on KBArticle (resourcePrimKey, groupId, status)",
		"create unique index IX_B5B6C674 on KBArticle (resourcePrimKey, groupId, version)",
		"create index IX_A9E2C691 on KBArticle (resourcePrimKey, latest)",
		"create index IX_69C17E43 on KBArticle (resourcePrimKey, main)",
		"create index IX_4E89983C on KBArticle (resourcePrimKey, status)",
		"create unique index IX_AA304772 on KBArticle (resourcePrimKey, version)",
		"create index IX_4E87D659 on KBArticle (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_5C941F1B on KBArticle (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}