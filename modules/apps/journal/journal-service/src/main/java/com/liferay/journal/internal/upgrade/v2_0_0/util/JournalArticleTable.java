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

package com.liferay.journal.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleTable {

	public static final String TABLE_NAME = "JournalArticle";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"id_", Types.BIGINT},
		{"resourcePrimKey", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"articleId", Types.VARCHAR},
		{"version", Types.DOUBLE},
		{"urlTitle", Types.VARCHAR},
		{"content", Types.CLOB},
		{"DDMStructureKey", Types.VARCHAR},
		{"DDMTemplateKey", Types.VARCHAR},
		{"defaultLanguageId", Types.VARCHAR},
		{"layoutUuid", Types.VARCHAR},
		{"displayDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP},
		{"reviewDate", Types.TIMESTAMP},
		{"indexable", Types.BOOLEAN},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("id_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("articleId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("content", Types.CLOB);

TABLE_COLUMNS_MAP.put("DDMStructureKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("DDMTemplateKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("defaultLanguageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("reviewDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("indexable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("smallImage", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("smallImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("smallImageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table JournalArticle (uuid_ VARCHAR(75) null,id_ LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,classNameId LONG,classPK LONG,treePath STRING null,articleId VARCHAR(75) null,version DOUBLE,urlTitle VARCHAR(255) null,content TEXT null,DDMStructureKey VARCHAR(75) null,DDMTemplateKey VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,layoutUuid VARCHAR(75) null,displayDate DATE null,expirationDate DATE null,reviewDate DATE null,indexable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table JournalArticle";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_17806804 on JournalArticle (DDMStructureKey[$COLUMN_LENGTH:75$])",
		"create index IX_75CCA4D1 on JournalArticle (DDMTemplateKey[$COLUMN_LENGTH:75$])",
		"create index IX_C761B675 on JournalArticle (classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$])",
		"create index IX_717D0FA0 on JournalArticle (classNameId, expirationDate, status)",
		"create index IX_323DF109 on JournalArticle (companyId, status)",
		"create index IX_E82F322B on JournalArticle (companyId, version, status)",
		"create index IX_EA05E9E1 on JournalArticle (displayDate, status)",
		"create index IX_D8EB0D84 on JournalArticle (groupId, DDMStructureKey[$COLUMN_LENGTH:75$])",
		"create index IX_31B74F51 on JournalArticle (groupId, DDMTemplateKey[$COLUMN_LENGTH:75$])",
		"create index IX_4D5CD982 on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], status)",
		"create unique index IX_85C52EEC on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], version)",
		"create index IX_353BD560 on JournalArticle (groupId, classNameId, DDMStructureKey[$COLUMN_LENGTH:75$])",
		"create index IX_6E801BF5 on JournalArticle (groupId, classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$])",
		"create index IX_9CE6E0FA on JournalArticle (groupId, classNameId, classPK)",
		"create index IX_A2534AC2 on JournalArticle (groupId, classNameId, layoutUuid[$COLUMN_LENGTH:75$])",
		"create index IX_F35391E8 on JournalArticle (groupId, folderId, status)",
		"create index IX_3C028C1E on JournalArticle (groupId, layoutUuid[$COLUMN_LENGTH:75$])",
		"create index IX_301D024B on JournalArticle (groupId, status)",
		"create index IX_D2D249E8 on JournalArticle (groupId, urlTitle[$COLUMN_LENGTH:255$], status)",
		"create index IX_43A0F80F on JournalArticle (groupId, userId, classNameId)",
		"create index IX_3F1EA19E on JournalArticle (layoutUuid[$COLUMN_LENGTH:75$])",
		"create index IX_451D63EC on JournalArticle (resourcePrimKey, indexable, status)",
		"create index IX_3E2765FC on JournalArticle (resourcePrimKey, status)",
		"create index IX_EF9B7028 on JournalArticle (smallImageId)",
		"create index IX_71520099 on JournalArticle (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_3463D95B on JournalArticle (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}