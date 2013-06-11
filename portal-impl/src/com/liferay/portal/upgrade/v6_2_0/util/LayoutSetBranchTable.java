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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutSetBranchTable {

	public static final String TABLE_NAME = "LayoutSetBranch";

	public static final Object[][] TABLE_COLUMNS = {
		{"layoutSetBranchId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"privateLayout", Types.BOOLEAN},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"master", Types.BOOLEAN},
		{"logo", Types.BOOLEAN},
		{"logoId", Types.BIGINT},
		{"themeId", Types.VARCHAR},
		{"colorSchemeId", Types.VARCHAR},
		{"wapThemeId", Types.VARCHAR},
		{"wapColorSchemeId", Types.VARCHAR},
		{"css", Types.CLOB},
		{"settings_", Types.VARCHAR},
		{"layoutSetPrototypeUuid", Types.VARCHAR},
		{"layoutSetPrototypeLinkEnabled", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table LayoutSetBranch (layoutSetBranchId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,name VARCHAR(75) null,description STRING null,master BOOLEAN,logo BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,wapThemeId VARCHAR(75) null,wapColorSchemeId VARCHAR(75) null,css TEXT null,settings_ STRING null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table LayoutSetBranch";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8FF5D6EA on LayoutSetBranch (groupId)",
		"create index IX_C4079FD3 on LayoutSetBranch (groupId, privateLayout)",
		"create index IX_CCF0DA29 on LayoutSetBranch (groupId, privateLayout, master)",
		"create unique index IX_5FF18552 on LayoutSetBranch (groupId, privateLayout, name)"
	};

}