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

package com.liferay.journal.internal.upgrade.v3_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Preston Crary
 * @generated
 */
public class JournalArticleResourceTable {

	public static final String TABLE_NAME = "JournalArticleResource";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"resourcePrimKey", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"articleId", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("resourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("articleId", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table JournalArticleResource (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,resourcePrimKey LONG not null,groupId LONG,companyId LONG,articleId VARCHAR(75) null,primary key (resourcePrimKey, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
"drop table JournalArticleResource";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B332E3F1 on JournalArticleResource (ctCollectionId)",
		"create unique index IX_57129BA8 on JournalArticleResource (groupId, articleId[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_FB783ED5 on JournalArticleResource (groupId, ctCollectionId)",
		"create index IX_D83FDF25 on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_63192F1F on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_37A8A767 on JournalArticleResource (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}