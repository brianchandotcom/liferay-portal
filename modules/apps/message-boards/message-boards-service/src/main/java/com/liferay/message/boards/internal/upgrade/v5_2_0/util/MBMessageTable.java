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

package com.liferay.message.boards.internal.upgrade.v5_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Javier de Arcos
 * @generated
 */
public class MBMessageTable {

	public static final String TABLE_NAME = "MBMessage";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"messageId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"categoryId", Types.BIGINT},
		{"threadId", Types.BIGINT}, {"rootMessageId", Types.BIGINT},
		{"parentMessageId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"subject", Types.VARCHAR}, {"urlSubject", Types.VARCHAR},
		{"body", Types.CLOB}, {"format", Types.VARCHAR},
		{"anonymous", Types.BOOLEAN}, {"priority", Types.DOUBLE},
		{"allowPingbacks", Types.BOOLEAN}, {"answer", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("messageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("categoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("threadId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rootMessageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentMessageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subject", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("urlSubject", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("body", Types.CLOB);

TABLE_COLUMNS_MAP.put("format", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("anonymous", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("allowPingbacks", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("answer", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table MBMessage (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,messageId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,categoryId LONG,threadId LONG,rootMessageId LONG,parentMessageId LONG,treePath STRING null,subject VARCHAR(75) null,urlSubject VARCHAR(255) null,body TEXT null,format VARCHAR(75) null,anonymous BOOLEAN,priority DOUBLE,allowPingbacks BOOLEAN,answer BOOLEAN,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (messageId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table MBMessage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_860370AB on MBMessage (classNameId, classPK, ctCollectionId)",
		"create index IX_19FE8691 on MBMessage (classNameId, classPK, status, ctCollectionId)",
		"create index IX_5C8DA38E on MBMessage (companyId, ctCollectionId)",
		"create index IX_9CE52674 on MBMessage (companyId, status, ctCollectionId)",
		"create index IX_F0A963FD on MBMessage (groupId, categoryId, ctCollectionId)",
		"create index IX_82ED07E3 on MBMessage (groupId, categoryId, status, ctCollectionId)",
		"create index IX_AAAD4168 on MBMessage (groupId, categoryId, threadId, answer, ctCollectionId)",
		"create index IX_158DD1B6 on MBMessage (groupId, categoryId, threadId, ctCollectionId)",
		"create index IX_CC88AC9C on MBMessage (groupId, categoryId, threadId, status, ctCollectionId)",
		"create index IX_A3E7210 on MBMessage (groupId, ctCollectionId)",
		"create index IX_7BEA05A9 on MBMessage (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_F6A852F6 on MBMessage (groupId, status, ctCollectionId)",
		"create unique index IX_8813E901 on MBMessage (groupId, urlSubject[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_C892444A on MBMessage (groupId, userId, ctCollectionId)",
		"create index IX_6C8B4B30 on MBMessage (groupId, userId, status, ctCollectionId)",
		"create index IX_D6EAC68E on MBMessage (parentMessageId, ctCollectionId)",
		"create index IX_C56F4974 on MBMessage (parentMessageId, status, ctCollectionId)",
		"create index IX_E84A6B81 on MBMessage (threadId, answer, ctCollectionId)",
		"create index IX_297F24CF on MBMessage (threadId, ctCollectionId)",
		"create index IX_7A7FD535 on MBMessage (threadId, parentMessageId, ctCollectionId)",
		"create index IX_A25D6B5 on MBMessage (threadId, status, ctCollectionId)",
		"create index IX_93815565 on MBMessage (userId, classNameId, classPK, ctCollectionId)",
		"create index IX_711114B on MBMessage (userId, classNameId, classPK, status, ctCollectionId)",
		"create index IX_8B146CBA on MBMessage (userId, classNameId, ctCollectionId)",
		"create index IX_1C5603A0 on MBMessage (userId, classNameId, status, ctCollectionId)",
		"create index IX_3F043E90 on MBMessage (userId, ctCollectionId)",
		"create index IX_F6B01E4A on MBMessage (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_DAB8F51A on MBMessage (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_EAF86BCC on MBMessage (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}