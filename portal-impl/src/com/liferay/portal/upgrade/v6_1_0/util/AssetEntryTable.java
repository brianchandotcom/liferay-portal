/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryTable {

	public static final String TABLE_NAME = "AssetEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"classUuid", Types.VARCHAR},
		{"classTypeId", Types.BIGINT},
		{"visible", Types.BOOLEAN},
		{"startDate", Types.TIMESTAMP},
		{"endDate", Types.TIMESTAMP},
		{"publishDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP},
		{"mimeType", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"summary", Types.VARCHAR},
		{"url", Types.VARCHAR},
		{"layoutUuid", Types.VARCHAR},
		{"height", Types.INTEGER},
		{"width", Types.INTEGER},
		{"priority", Types.DOUBLE},
		{"viewCount", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table AssetEntry (entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,classUuid VARCHAR(75) null,classTypeId LONG,visible BOOLEAN,startDate DATE null,endDate DATE null,publishDate DATE null,expirationDate DATE null,mimeType VARCHAR(75) null,title STRING null,description STRING null,summary STRING null,url STRING null,layoutUuid VARCHAR(75) null,height INTEGER,width INTEGER,priority DOUBLE,viewCount INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AssetEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_1E9D371D on AssetEntry (classNameId, classPK)",
		"create index IX_FC1F9C7B on AssetEntry (classUuid)",
		"create index IX_7306C60 on AssetEntry (companyId)",
		"create index IX_75D42FF9 on AssetEntry (expirationDate)",
		"create index IX_1EBA6821 on AssetEntry (groupId, classUuid)",
		"create index IX_2E4E3885 on AssetEntry (publishDate)",
		"create index IX_9029E15A on AssetEntry (visible)"
	};

}