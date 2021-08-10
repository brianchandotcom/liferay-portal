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

package com.liferay.commerce.product.internal.upgrade.v3_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPAttachmentFileEntryTable {

	public static final String TABLE_NAME = "CPAttachmentFileEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"CPAttachmentFileEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"fileEntryId", Types.BIGINT},
		{"cdnEnabled", Types.BOOLEAN}, {"cdnURL", Types.VARCHAR},
		{"displayDate", Types.TIMESTAMP}, {"expirationDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR}, {"json", Types.CLOB},
		{"priority", Types.DOUBLE}, {"type_", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPAttachmentFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("cdnEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("cdnURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("json", Types.CLOB);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CPAttachmentFileEntry (uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,CPAttachmentFileEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,fileEntryId LONG,cdnEnabled BOOLEAN,cdnURL STRING null,displayDate DATE null,expirationDate DATE null,title STRING null,json TEXT null,priority DOUBLE,type_ INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table CPAttachmentFileEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4E725857 on CPAttachmentFileEntry (classNameId, classPK, cdnURL[$COLUMN_LENGTH:4000$])",
		"create index IX_B2AFFCE5 on CPAttachmentFileEntry (classNameId, classPK, displayDate, status)",
		"create index IX_DD114140 on CPAttachmentFileEntry (classNameId, classPK, fileEntryId)",
		"create index IX_A6E0353A on CPAttachmentFileEntry (classNameId, classPK, type_, status)",
		"create index IX_59F57821 on CPAttachmentFileEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])",
		"create index IX_A0B4C71A on CPAttachmentFileEntry (displayDate, status)",
		"create index IX_C2C5D600 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_BFCBDC82 on CPAttachmentFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}