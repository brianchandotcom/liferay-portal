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
public class JournalFeedTable {

	public static final String TABLE_NAME = "JournalFeed";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"id_", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"feedId", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"DDMStructureKey", Types.VARCHAR},
		{"DDMTemplateKey", Types.VARCHAR},
		{"DDMRendererTemplateKey", Types.VARCHAR},
		{"delta", Types.INTEGER},
		{"orderByCol", Types.VARCHAR},
		{"orderByType", Types.VARCHAR},
		{"targetLayoutFriendlyUrl", Types.VARCHAR},
		{"targetPortletId", Types.VARCHAR},
		{"contentField", Types.VARCHAR},
		{"feedFormat", Types.VARCHAR},
		{"feedVersion", Types.DOUBLE},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("id_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("feedId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("DDMStructureKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("DDMTemplateKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("DDMRendererTemplateKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("delta", Types.INTEGER);

TABLE_COLUMNS_MAP.put("orderByCol", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("orderByType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("targetLayoutFriendlyUrl", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("targetPortletId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("contentField", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("feedFormat", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("feedVersion", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table JournalFeed (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,feedId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,DDMStructureKey VARCHAR(75) null,DDMTemplateKey VARCHAR(75) null,DDMRendererTemplateKey VARCHAR(75) null,delta INTEGER,orderByCol VARCHAR(75) null,orderByType VARCHAR(75) null,targetLayoutFriendlyUrl VARCHAR(255) null,targetPortletId VARCHAR(200) null,contentField VARCHAR(75) null,feedFormat VARCHAR(75) null,feedVersion DOUBLE,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table JournalFeed";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_65576CBC on JournalFeed (groupId, feedId[$COLUMN_LENGTH:75$])",
		"create index IX_CB37A10F on JournalFeed (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_39031F51 on JournalFeed (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}