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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

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
		{"title", Types.VARCHAR},
		{"urlTitle", Types.VARCHAR},
		{"description", Types.CLOB},
		{"content", Types.CLOB},
		{"type_", Types.VARCHAR},
		{"structureId", Types.VARCHAR},
		{"templateId", Types.VARCHAR},
		{"layoutUuid", Types.VARCHAR},
		{"displayDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP},
		{"reviewDate", Types.TIMESTAMP},
		{"indexable", Types.BOOLEAN},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table JournalArticle (uuid_ VARCHAR(75) null,id_ LONG not null primary key,resourcePrimKey LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,classNameId LONG,classPK LONG,treePath STRING null,articleId VARCHAR(75) null,version DOUBLE,title STRING null,urlTitle VARCHAR(150) null,description TEXT null,content TEXT null,type_ VARCHAR(75) null,structureId VARCHAR(75) null,templateId VARCHAR(75) null,layoutUuid VARCHAR(75) null,displayDate DATE null,expirationDate DATE null,reviewDate DATE null,indexable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table JournalArticle";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_323DF109 on JournalArticle (companyId, status)",
		"create index IX_E82F322B on JournalArticle (companyId, version, status)",
		"create index IX_EA05E9E1 on JournalArticle (displayDate, status)",
		"create index IX_4D5CD982 on JournalArticle (groupId, articleId, status)",
		"create unique index IX_85C52EEC on JournalArticle (groupId, articleId, version)",
		"create index IX_9CE6E0FA on JournalArticle (groupId, classNameId, classPK)",
		"create index IX_A2534AC2 on JournalArticle (groupId, classNameId, layoutUuid)",
		"create index IX_91E78C35 on JournalArticle (groupId, classNameId, structureId)",
		"create index IX_F43B9FF2 on JournalArticle (groupId, classNameId, templateId)",
		"create index IX_F35391E8 on JournalArticle (groupId, folderId, status)",
		"create index IX_3C028C1E on JournalArticle (groupId, layoutUuid)",
		"create index IX_301D024B on JournalArticle (groupId, status)",
		"create index IX_2E207659 on JournalArticle (groupId, structureId)",
		"create index IX_8DEAE14E on JournalArticle (groupId, templateId)",
		"create index IX_D2D249E8 on JournalArticle (groupId, urlTitle, status)",
		"create index IX_43A0F80F on JournalArticle (groupId, userId, classNameId)",
		"create index IX_3F1EA19E on JournalArticle (layoutUuid)",
		"create index IX_451D63EC on JournalArticle (resourcePrimKey, indexable, status)",
		"create index IX_3E2765FC on JournalArticle (resourcePrimKey, status)",
		"create index IX_EF9B7028 on JournalArticle (smallImageId)",
		"create index IX_8E8710D9 on JournalArticle (structureId)",
		"create index IX_9106F6CE on JournalArticle (templateId)",
		"create index IX_71520099 on JournalArticle (uuid_, companyId)",
		"create unique index IX_3463D95B on JournalArticle (uuid_, groupId)"
	};

}