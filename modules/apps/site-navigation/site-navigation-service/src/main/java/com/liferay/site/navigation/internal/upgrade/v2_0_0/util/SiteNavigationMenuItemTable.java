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

package com.liferay.site.navigation.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SiteNavigationMenuItemTable {

	public static final String TABLE_NAME = "SiteNavigationMenuItem";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"siteNavigationMenuItemId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"siteNavigationMenuId", Types.BIGINT},
		{"parentSiteNavigationMenuItemId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"typeSettings", Types.CLOB},
		{"order_", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("siteNavigationMenuItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("siteNavigationMenuId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentSiteNavigationMenuItemId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("order_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table SiteNavigationMenuItem (uuid_ VARCHAR(75) null,siteNavigationMenuItemId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,siteNavigationMenuId LONG,parentSiteNavigationMenuItemId LONG,name VARCHAR(255) null,type_ VARCHAR(75) null,typeSettings TEXT null,order_ INTEGER,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SiteNavigationMenuItem";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_75495C39 on SiteNavigationMenuItem (parentSiteNavigationMenuItemId)",
		"create index IX_9FA7003B on SiteNavigationMenuItem (siteNavigationMenuId, name[$COLUMN_LENGTH:255$])",
		"create index IX_2294C622 on SiteNavigationMenuItem (siteNavigationMenuId, parentSiteNavigationMenuItemId)",
		"create index IX_90D752C7 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_6FD3DF09 on SiteNavigationMenuItem (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}