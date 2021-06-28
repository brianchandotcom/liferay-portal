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

package com.liferay.journal.internal.upgrade.v4_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Javier de Arcos
 * @generated
 */
public class JournalArticleTable {

	public static final String TABLE_NAME = "JournalArticle";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"id_", Types.BIGINT},
		{"resourcePrimKey", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"externalReferenceCode", Types.VARCHAR}, {"folderId", Types.BIGINT},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"treePath", Types.VARCHAR}, {"articleId", Types.VARCHAR},
		{"version", Types.DOUBLE}, {"urlTitle", Types.VARCHAR},
		{"DDMStructureKey", Types.VARCHAR}, {"DDMTemplateKey", Types.VARCHAR},
		{"defaultLanguageId", Types.VARCHAR}, {"layoutUuid", Types.VARCHAR},
		{"displayDate", Types.TIMESTAMP}, {"expirationDate", Types.TIMESTAMP},
		{"reviewDate", Types.TIMESTAMP}, {"indexable", Types.BOOLEAN},
		{"smallImage", Types.BOOLEAN}, {"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("id_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourcePrimKey", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("articleId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);

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
	public static final String TABLE_SQL_CREATE =
"create table JournalArticle (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,id_ LONG not null,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,externalReferenceCode VARCHAR(75) null,folderId LONG,classNameId LONG,classPK LONG,treePath STRING null,articleId VARCHAR(75) null,version DOUBLE,urlTitle VARCHAR(255) null,DDMStructureKey VARCHAR(75) null,DDMTemplateKey VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,layoutUuid VARCHAR(75) null,displayDate DATE null,expirationDate DATE null,reviewDate DATE null,indexable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (id_, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table JournalArticle";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F1C2B662 on JournalArticle (DDMStructureKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_A01DB92F on JournalArticle (DDMTemplateKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_AAF3B581 on JournalArticle (companyId, ctCollectionId)",
		"create index IX_6D9F9567 on JournalArticle (companyId, status, ctCollectionId)",
		"create index IX_F12AB4A3 on JournalArticle (companyId, version, ctCollectionId)",
		"create index IX_F97E5289 on JournalArticle (companyId, version, status, ctCollectionId)",
		"create index IX_62EBBA43 on JournalArticle (ctCollectionId)",
		"create index IX_55CADE3F on JournalArticle (displayDate, status, ctCollectionId)",
		"create index IX_70325BE2 on JournalArticle (groupId, DDMStructureKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_6A6363AF on JournalArticle (groupId, DDMTemplateKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_E10314FA on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_9E386BE0 on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], status, ctCollectionId)",
		"create unique index IX_D3ACAD4A on JournalArticle (groupId, articleId[$COLUMN_LENGTH:75$], version, ctCollectionId)",
		"create index IX_8A0FEBBE on JournalArticle (groupId, classNameId, DDMStructureKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_20E66853 on JournalArticle (groupId, classNameId, DDMTemplateKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_E748358 on JournalArticle (groupId, classNameId, classPK, ctCollectionId)",
		"create index IX_6CA45D20 on JournalArticle (groupId, classNameId, layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_1ED664C3 on JournalArticle (groupId, ctCollectionId)",
		"create index IX_DB98B996 on JournalArticle (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_D9F0782E on JournalArticle (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], version, ctCollectionId)",
		"create index IX_8868DCD0 on JournalArticle (groupId, folderId, classNameId, status, ctCollectionId)",
		"create index IX_74060760 on JournalArticle (groupId, folderId, ctCollectionId)",
		"create index IX_D36D9846 on JournalArticle (groupId, folderId, status, ctCollectionId)",
		"create index IX_E6BD667C on JournalArticle (groupId, layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_E107E2A9 on JournalArticle (groupId, status, ctCollectionId)",
		"create index IX_254CBF60 on JournalArticle (groupId, urlTitle[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_E7C5046 on JournalArticle (groupId, urlTitle[$COLUMN_LENGTH:255$], status, ctCollectionId)",
		"create index IX_E20FD06D on JournalArticle (groupId, userId, classNameId, ctCollectionId)",
		"create index IX_B2F1D3FD on JournalArticle (groupId, userId, ctCollectionId)",
		"create index IX_75FE7BFC on JournalArticle (layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_7D1E8774 on JournalArticle (resourcePrimKey, ctCollectionId)",
		"create index IX_60EF9564 on JournalArticle (resourcePrimKey, indexable, ctCollectionId)",
		"create index IX_DFAE24A on JournalArticle (resourcePrimKey, indexable, status, ctCollectionId)",
		"create index IX_115EC45A on JournalArticle (resourcePrimKey, status, ctCollectionId)",
		"create index IX_DAB0F686 on JournalArticle (smallImageId, ctCollectionId)",
		"create index IX_584284F7 on JournalArticle (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_9603F88D on JournalArticle (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_4D5E99B9 on JournalArticle (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}